package com.partridgetech.pathfinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.partridgetech.model.MazeCell;
import com.partridgetech.model.MazeModel;
import com.partridgetech.model.OutputBundle;
import com.partridgetech.pathfinding.component.BasePathNode;

/**
 * Depth first algorithm.
 * 
 * Pseudo code - http://www.cs.toronto.edu/~heap/270F02/node36.html
 * 
 * @author Zxzx
 *
 */
public class DepthFirstPathFinder extends BasePathFinder {

    /**
     * Constructor
     * 
     * @param mazeModel
     *            to solve
     */
    public DepthFirstPathFinder(MazeModel mazeModel)
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
        final Stack<BasePathNode> stack = new Stack<BasePathNode>();
        stack.push(new BasePathNode(mazeModel.getStartCell()));
        return new OutputBundle(this.mazeModel, this.getSolvedMazeCells(stack, new ArrayList<BasePathNode>()),
                this.getTimeTaken(), this.getClass().getSimpleName());
    }

    /**
     * Provides list of cells required to solve maze.
     * 
     * @param openList candidate list
     * @param closedList completed list
     * @return List<MazeCell> cells
     */
    protected List<MazeCell> getSolvedMazeCells(Stack<BasePathNode> stack, List<BasePathNode> visitedCells)
    {
        while (!stack.isEmpty())
        {
            final BasePathNode headCell = stack.pop();

            // Get adjacent cells to closed list.
            final List<MazeCell> adjacentMazeCells = this.getMoveableAdjacentPathNodes(headCell.getMazeCell());

            // Check if maze is completed.
            if (adjacentMazeCells.contains(this.mazeModel.getEndCell()))
            {
                // Return node path to head.
                return headCell.getNodePath();
            }
            
            visitedCells.add(headCell);
            
            // Iterate through adjacent node cells.
            adjacentMazeCells.stream().filter(mazeCell -> visitedCells.stream().noneMatch(vis -> vis
                    .getMazeCell().equals(mazeCell))).forEach(mazeCell -> 
                    {
                        final BasePathNode node = new BasePathNode(mazeCell, headCell);
                        visitedCells.add(node);
                        stack.add(node);
                    });
        }
        return new ArrayList<MazeCell>();
    }
}
