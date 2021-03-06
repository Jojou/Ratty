/**
 * 
 */
package com.partridgetech.services.pathfinder;

import com.partridgetech.common.IMazePathFinder;
import com.partridgetech.model.MazeModel;
import com.partridgetech.pathfinding.BreadthFirstPathFinder;
import com.partridgetech.services.IPathFinderProvider;

/**
 * Service provider for BreadthFirstPathFinder.
 * 
 * @author Zxzx
 *
 */
public class BreadthFirstProvider implements IPathFinderProvider {

    /**
     * @see com.partridgetech.services.IPathFinderProvider#createInstance(com.partridgetech.model.MazeModel)
     */
    @Override
    public IMazePathFinder createInstance(MazeModel mazeModel)
    {
        return new BreadthFirstPathFinder(mazeModel);
    }
}
