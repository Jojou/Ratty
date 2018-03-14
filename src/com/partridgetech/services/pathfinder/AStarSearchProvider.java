/**
 * 
 */
package com.partridgetech.services.pathfinder;

import com.partridgetech.common.IMazePathFinder;
import com.partridgetech.model.MazeModel;
import com.partridgetech.pathfinding.AStarSearchPathFinder;
import com.partridgetech.services.IPathFinderProvider;

/**
 * Service provider for AStar finder.
 * 
 * @author Zxzx
 *
 */
public class AStarSearchProvider implements IPathFinderProvider {

    /**
     * @see com.partridgetech.services.IPathFinderProvider#createInstance(com.partridgetech.model.MazeModel)
     */
    @Override
    public IMazePathFinder createInstance(MazeModel mazeModel)
    {
        return new AStarSearchPathFinder(mazeModel);
    }
}
