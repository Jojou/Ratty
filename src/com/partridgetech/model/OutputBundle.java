package com.partridgetech.model;

import java.util.List;

import com.partridgetech.common.MazeConstants;
import com.partridgetech.common.MazeMinion;

/**
 * Output bundle used to report results on maze.
 * 
 * @author Zxzx
 *
 */
public class OutputBundle {

    /** String holding results. */
    private final String outputString;

    /**
     * Constructor.
     * 
     * @param mazeModel that has been solved
     * @param pathToComplete path to completion
     * @param timeTaken time taken
     * @param simpleName name of pathfinder
     */
    public OutputBundle(MazeModel mazeModel, List<MazeCell> pathToComplete, long timeTaken, String simpleName)
    {
        final StringBuilder builder = new StringBuilder().append(mazeModel.getOutputCells()).append(
                MazeConstants.NEW_LINE);
        
        // After writing maze to String then write cells in completed maze.
        pathToComplete.stream().forEach(pathCell -> builder.setCharAt(pathCell.getIndex(), 
                MazeMinion.ROUTE.getOutputChar()));
        
        // Add in end of lines
        mazeModel.getMazeCells().stream().forEach(cell -> {
            if (cell.getIndex() != 0 && cell.getIndex() % mazeModel.getDimensions().getKey() == 0)
            {
                builder.insert(mazeModel.getMazeCells().size() - cell.getIndex(), MazeConstants.NEW_LINE);
            }
        });

        // Create header for other information
        final String header = MazeConstants.NEW_LINE + simpleName + MazeConstants.TIME_TAKEN + timeTaken
                + MazeConstants.MILLISECONDS + MazeConstants.NEW_LINE;
        
        // If path is empty then maze could not be completed.  Write this to output.
        if (pathToComplete.isEmpty())
        {
            builder.append(MazeConstants.COULD_NOT_COMPLETE).append(MazeConstants.NEW_LINE);
        }

        // Set output String - no more processing is to be performed on output.
        this.outputString = header + builder.toString();
    }

    /**
     * 
     * 
     * @return the outputString
     */
    public String getOutputString()
    {
        return this.outputString;
    }
}
