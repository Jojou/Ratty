package com.partridgetech.pathfinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.partridgetech.model.MazeCell;
import com.partridgetech.model.MazeModel;
import com.partridgetech.model.OutputBundle;
import com.partridgetech.pathfinding.component.AStarNode;

/**
 * Type of A star search algorithm.
 * 
 * Pseudo code https://en.wikibooks.org/wiki/Artificial_Intelligence/Search/Heuristic_search/Astar_Search
 * 
 * @author Zxzx
 *
 */
public class AStarSearchPathFinder extends BasePathFinder {

    /**
     * Constructor
     * 
     * @param mazeModel to solve
     */
    public AStarSearchPathFinder(MazeModel mazeModel)
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
        return new OutputBundle(this.mazeModel, this.getSolvedMazeCells(new ArrayList<AStarNode>(
                Arrays.asList(this.createNode(mazeModel.getStartCell()))), new ArrayList<AStarNode>()),
                this.getTimeTaken(), this.getClass().getSimpleName());
    }

    /**
     * Provides list of cells required to solve maze.
     * 
     * @param openList candidate list
     * @param closedList completed list
     * @return List<MazeCell> cells
     */
    protected List<MazeCell> getSolvedMazeCells(final List<AStarNode> openList, final List<AStarNode> closedList)
    {
        while (!openList.isEmpty())
        {
            // Get open list element with lowest score
            final AStarNode lowestOpenCell = Collections.min(openList, Comparator.comparing(res -> res.getF()));

            // Move cell from open list to closed list.
            closedList.add(lowestOpenCell);
            openList.remove(lowestOpenCell);

            // Get adjacent cells to closed list.
            final List<MazeCell> adjacentMazeCells = this.getMoveableAdjacentPathNodes(lowestOpenCell.getMazeCell());

            // Check if maze is completed.
            if (adjacentMazeCells.contains(this.mazeModel.getEndCell()))
            {
                // Return node path to head.
                return lowestOpenCell.getNodePath();
            }

            // Iterate through maze cells
            adjacentMazeCells.stream().forEach(mazeCell -> {
                
                // Get g value of adjacent maze cell.
                final int updatedGValue = mazeCell.getMoves(this.mazeModel.getStartCell());

                // Get closed node if exists.
                final Optional<AStarNode> closedResult = closedList.stream()
                        .filter(closedCell -> closedCell.getMazeCell().equals(mazeCell)).findFirst();

                // Get open node if exists.
                final Optional<AStarNode> openResult = openList.stream()
                        .filter(closedCell -> closedCell.getMazeCell().equals(mazeCell)).findFirst();

                if (closedResult.isPresent() && updatedGValue < closedResult.get().getG())
                {
                    closedList.remove(closedResult.get());
                    closedList.add(this.createNode(mazeCell, lowestOpenCell));
                }
                else if (openResult.isPresent() && updatedGValue < openResult.get().getG())
                {
                    openList.remove(closedResult.get());
                    openList.add(this.createNode(mazeCell, lowestOpenCell));
                }
                else if (!closedResult.isPresent() && !openResult.isPresent())
                {
                    openList.add(this.createNode(mazeCell, lowestOpenCell));
                }
            });
        }
        return new ArrayList<MazeCell>();
    }
    
    /**
     * Convenience mechanism for creating a star node.
     * 
     * @param mazeCell to create node for
     * @return AStarNode
     */
    private AStarNode createNode(MazeCell mazeCell)
    {
        return new AStarNode(mazeCell, mazeCell.getMoves(this.mazeModel.getStartCell()),
                mazeCell.getMoves(this.mazeModel.getEndCell()));
    }

    /**
     * Convenience mechanism for creating a star node.
     * 
     * @param mazeCell to create node for
     * @param parent node
     * @return AStarNode
     */
    private AStarNode createNode(MazeCell mazeCell, AStarNode parent)
    {
        return new AStarNode(mazeCell, mazeCell.getMoves(this.mazeModel.getStartCell()),
                mazeCell.getMoves(this.mazeModel.getEndCell()), parent);
    }
}
