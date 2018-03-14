package com.partridgetech.pathfinding;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import com.partridgetech.model.MazeCell;
import com.partridgetech.model.MazeModel;
import com.partridgetech.model.OutputBundle;
import com.partridgetech.pathfinding.component.BasePathNode;

/**
 * Breadth first algorithm to solve maze.
 * 
 * Pseudo code - https://en.wikipedia.org/wiki/Breadth-first_search
 * 
 * @author Zxzx
 *
 */

public class BreadthFirstPathFinder extends BasePathFinder {

    /**
     * Constructor
     * 
     * @param mazeModel to solve
     */
    public BreadthFirstPathFinder(MazeModel mazeModel)
    {
        super(mazeModel);
    }
    
    /**
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public OutputBundle call() throws Exception
    {
        this.startTime = System.nanoTime();
        return new OutputBundle(this.mazeModel, this.getSolvedMazeCells(new ArrayDeque<BasePathNode>(
                Arrays.asList(new BasePathNode(mazeModel.getStartCell()))), new ArrayList<BasePathNode>()), 
                this.getTimeTaken(), this.getClass().getSimpleName());
    }

    /**
     * Provides list of cells required to solve maze.
     * 
     * @param queue cells to process
     * @param visited visited list
     * @return List<MazeCell> cells
     */
    protected List<MazeCell> getSolvedMazeCells(Queue<BasePathNode> queue, List<BasePathNode> visited)
    {
      
        while (!queue.isEmpty())
        {
            // Get first in queue.
            final BasePathNode headCell = queue.poll();

            // Get adjacent cells to closed list.
            final List<MazeCell> adjacentMazeCells = this.getMoveableAdjacentPathNodes(headCell.getMazeCell());

            // Check if maze is completed.
            if (adjacentMazeCells.contains(this.mazeModel.getEndCell()))
            {
                // Return node path to head.
                return headCell.getNodePath();
            }
            
            // Iterate through adjacent node cells.
            adjacentMazeCells.stream().filter(mazeCell -> visited.stream().noneMatch(vis -> vis
                    .getMazeCell().equals(mazeCell))).forEach(mazeCell -> 
                    {
                        final BasePathNode node = new BasePathNode(mazeCell, headCell);
                        visited.add(node);
                        queue.add(node);
                    });
        }
        return new ArrayList<MazeCell>();
    }
}
