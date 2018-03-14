package com.partridgetech.pathfinding;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.partridgetech.common.IMazePathFinder;
import com.partridgetech.common.MazeMinion;
import com.partridgetech.model.MazeCell;
import com.partridgetech.model.MazeModel;

/**
 * Base path finder model.  Contains convenience methods and holds maze model.
 * 
 * @author Zxzx
 *
 */
public abstract class BasePathFinder implements IMazePathFinder {

    /** Maze model. */
    protected MazeModel mazeModel;

    /** Start time. */
    protected long startTime;

    /**
     * Constructor.
     * 
     * @param mazeModel to solve
     */
    public BasePathFinder(final MazeModel mazeModel)
    {
        this.mazeModel = mazeModel;
    }

    /**
     * Get movable adjacent path nodes.
     * 
     * @param mazeCell to get nodes next to which can be travelled onto.
     * @return List of mazeCells
     */
    public List<MazeCell> getMoveableAdjacentPathNodes(MazeCell mazeCell)
    {
        return this.mazeModel.getAdjacentCells(mazeCell).stream()
                .filter(res -> !res.getEntity().equals(MazeMinion.WALL)).collect(Collectors.toList());
    }

    /**
     * Get time taken to solve maze.
     * 
     * @return long
     */
    protected long getTimeTaken()
    {
        return TimeUnit.MILLISECONDS.convert(System.nanoTime() - this.startTime, TimeUnit.NANOSECONDS);
    }
}
