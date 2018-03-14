package com.partridgetech.services;

import com.partridgetech.common.IMazePathFinder;
import com.partridgetech.model.MazeModel;

/**
 * Interface to allow IMazePathFinder to be found by PathFinderService.
 * 
 * @author Zxzx
 *
 */
public interface IPathFinderProvider {
	
    /**
     * Method for creating a new Maze Path Finder.
     * 
     * @param mazeModel to solve
     * @return Used to solve maze
     */
    public IMazePathFinder createInstance(MazeModel mazeModel);
}
