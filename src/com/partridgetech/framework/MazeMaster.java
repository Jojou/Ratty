package com.partridgetech.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import com.partridgetech.common.MazeConstants;
import com.partridgetech.model.InputBundle;
import com.partridgetech.model.MazeModel;
import com.partridgetech.model.OutputBundle;
import com.partridgetech.model.observer.IObservable;
import com.partridgetech.model.observer.IObserver;
import com.partridgetech.services.IPathFinderProvider;
import com.partridgetech.services.PathFinderService;

/**
 * Provides ability to create tasks that solve mazes.  Uses PathFinderService to get all classes capable
 * of solving mazes at runtime and measures performance.
 * 
 * @author Zxzx
 *
 */
public final class MazeMaster implements IObservable<List<OutputBundle>>{

    /** Used to schedule tasks to run. */
    private final ScheduledExecutorService execturService = Executors
            .newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    /** Mazes as initially received. */
    private final List<InputBundle> inputBundleMaze = new ArrayList<InputBundle>();

    /** Models created from input bundles appropriate for use with maze solvers. */
    private final List<MazeModel> mazeModels = new ArrayList<MazeModel>();

    /** Used to report when completion of all mazes happens. */
    private CountDownLatch countdownLatch;

    /** List containing path finder providers used to create tasks that solve mazes. */
    private final List<IPathFinderProvider> pathFinderProviders = PathFinderService.getInstance()
            .getPathFinderServiceProviders();

    /** List that contains post-processing logic for mazes. */
    protected final List<OutputBundle> outputBundles = new ArrayList<OutputBundle>();
    
    /** List of tasks scheduled to be solved. */
    protected final List<FutureTask<OutputBundle>> futureTasks = new ArrayList<FutureTask<OutputBundle>>();
    
    /** List of observers to be notified upon completion. */
    final List<IObserver<List<OutputBundle>>> observers = new CopyOnWriteArrayList<IObserver<List<OutputBundle>>>();

    /**
     * Constructor.
     * 
     * @param mazesToSolve
     * @param observers
     */
    public MazeMaster(List<InputBundle> mazesToSolve, List<IObserver<List<OutputBundle>>> observers)
    {
        this.inputBundleMaze.addAll(mazesToSolve);
        this.observers.addAll(observers);
    }

    /**
     * Calls procedures to solve maze and notify observers.
     */
    private void solveMazes()
    {
        // Create maze models.
        this.createMazeModels();
        
        // Schedule tasks.
        this.scheduleSolveTasks();
        
        // Send updates to listeners.
        this.notifyAllObservers();
        
        // Execute post processing.
        this.shutdownExecutorService();
    }
    
    /**
     * Creates maze models using inputBundles.
     */
    private void createMazeModels()
    {
        this.mazeModels.addAll(this.inputBundleMaze.stream().map(res -> new MazeModel(res)).collect(
                (Collectors.toList())));
    }

    /**
     * Creates tasks that solve mazes.  Uses local countdownLatch to report when each maze is handled by pathfinder.
     */
    private void scheduleSolveTasks()
    {
        // For each mazeFinder create a future task for each maze model and add to pathFinderProviders list.
        this.mazeModels.stream().forEach(model -> this.pathFinderProviders.forEach(
                pathFinder -> futureTasks.add(new FutureTask<OutputBundle>(pathFinder.createInstance(model)))));

        // Pass each task to executor service.  This will use thread pool to schedule efficiently the running of each
        // task.
        this.futureTasks.forEach(res -> this.execturService.submit(res));

        // Create count down latch of appropriate size.
        countdownLatch = new CountDownLatch(this.pathFinderProviders.size() * this.mazeModels.size());
        
        // Create separate thread for getting results of task as don't want application to hang waiting.
        new Thread(new Runnable() {
            /**
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run()
            {
                for (FutureTask<OutputBundle> task : MazeMaster.this.futureTasks)
                {
                    try
                    {
                        MazeMaster.this.outputBundles.add(task.get(MazeConstants.MAX_MAZE_COMPLETION_TIME, 
                                TimeUnit.SECONDS));
                        
                    } catch (InterruptedException | ExecutionException | TimeoutException e)
                    {
                        e.printStackTrace();
                    }
                    countdownLatch.countDown();
                }
            }
        }).start();

    }

    /**
     * Shutdown executor service when no further usage is required.
     */
    private void shutdownExecutorService()
    {
        this.execturService.shutdown();
    }
    
    /**
     * @see com.partridgetech.model.observer.IObservable#notifyAllObservers()
     */
    @Override
    public void notifyAllObservers()
    {
        try
        {
            this.countdownLatch.await(this.pathFinderProviders.size() * this.mazeModels.size(), TimeUnit.SECONDS);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        this.observers.stream().forEach(observer -> observer.update(this.outputBundles));
    }

    /**
     * @see com.partridgetech.model.observer.IObservable#addObserver(com.partridgetech.model.observer.IObserver)
     */
    @Override
    public void addObserver(IObserver<List<OutputBundle>> observer)
    {
        this.observers.add(observer);
        
    }

    /**
     * @see com.partridgetech.model.observer.IObservable#addObservers(java.util.List)
     */
    @Override
    public void addObservers(List<IObserver<List<OutputBundle>>> observers)
    {
        this.observers.addAll(observers);
        
    }

    /**
     * @see com.partridgetech.model.observer.IObservable#removeAllObservers()
     */
    @Override
    public void removeAllObservers()
    {
        this.observers.clear();
    }
    
    /**
     * Access point for interaction with maze master.  Provide inputs
     * 
     * @param mazesToSolve
     */
    public static void solveMazes(List<InputBundle> mazesToSolve, IObserver<List<OutputBundle>> observer)
    {
        MazeMaster.solveMazes(mazesToSolve, Arrays.asList(observer));
    }
    
    /**
     * Access point for interaction with maze master.  Provide inputs
     * 
     * @param mazesToSolve
     * @return
     */
    public static void solveMazes(List<InputBundle> mazesToSolve, List<IObserver<List<OutputBundle>>> observers)
    {
        new MazeMaster(mazesToSolve, observers).solveMazes();
    }
}
