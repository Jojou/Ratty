package com.partridgetech.client;

import java.util.ArrayList;
import java.util.List;

import com.partridgetech.common.MazeConstants;
import com.partridgetech.framework.MazeMaster;
import com.partridgetech.model.InputBundle;
import com.partridgetech.model.OutputBundle;
import com.partridgetech.model.observer.IObserver;
import com.partridgetech.utility.MazeIOUtility;

/**
 * Client with mazes that need to be solved.
 * 
 * @author Zxzx
 *
 */
public class MazeClient implements IObserver<List<OutputBundle>>{
    
    /** Input bundles created from mazeLocations. */
    final List<InputBundle> inputBundles = new ArrayList<InputBundle>();
    
    /** Maze locations. */
    final List<String> mazeLocations = new ArrayList<String>();
    
    /**
     * Constructor.
     * 
     * @param rawMazes to be solved
     */
    public MazeClient(final List<String> mazeLocations)
    {
        this.mazeLocations.addAll(mazeLocations);
        this.inputBundles.addAll(MazeIOUtility.getInputFromLocations(this.mazeLocations));
    }
    
    /**
     * Reports mazes are complete.
     */
    public void solveMazes()
    {
        MazeMaster.solveMazes(this.inputBundles, this);
    }
    
    /**
     * @see com.partridgetech.model.observer.IObserver#update(java.lang.Object)
     */
    @Override
    public void update(List<OutputBundle> data)
    {
        // Report updates.  Do so in separate thread so that report of other interested parties is not impacted.
        new Thread(new MazeClientReporter(data)).start();
    }
    
    /**
     * Provides reporting for results.
     * 
     * @author Zxzx
     *
     */
    protected class MazeClientReporter implements Runnable{

        /** Output data to report. */
        private List<OutputBundle> outputBundles = new ArrayList<OutputBundle>();
        
        /**
         * Constructor.
         */
        public MazeClientReporter(final List<OutputBundle> outputBundles)
        {
            this.outputBundles = outputBundles;
        }
        
        /**
         * Reports results of mazes.
         */
        @Override
        public void run()
        {
            final StringBuilder builder = new StringBuilder();
            this.outputBundles.stream().forEach(bundle -> builder.append(bundle
                    .getOutputString() + MazeConstants.NEW_LINE));
            System.out.println(builder.toString());
        }
    }

}
