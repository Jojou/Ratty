package com.partridgetech.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import com.partridgetech.common.MazeConstants;
import com.partridgetech.common.MazeMinion;

/**
 * Contains extracted information from maze input composed of String.
 * 
 * @author Zxzx
 *
 */
public class InputBundle {
	
    /** Dimensions. */
	private final Entry<Integer, Integer> dimensions;
	
	/** Start location. */
	private final Entry<Integer, Integer> startLocation;
	
	/** End location. */
	private final Entry<Integer, Integer> endLocation;
	
	/** Maze text. */
	private final String mazeText;
	
	/**
	 * Constructor.
	 * 
	 * @param mazeModelInput input String
	 */
	public InputBundle(final String mazeModelInput)
	{
		this.dimensions = InputBundle.convertLineToTuple(0, mazeModelInput);
		this.startLocation = InputBundle.convertLineToTuple(1, mazeModelInput);
		this.endLocation = InputBundle.convertLineToTuple(2, mazeModelInput);
		
		final StringBuilder builder = new StringBuilder().append(InputBundle
		        .formatMapCellsProcedingFrom(4, mazeModelInput));
		builder.setCharAt(getStartLocation().getKey() + getStartLocation().getValue() * this.getDimensions().getKey(), 
		        MazeMinion.START.getInputChar());
		builder.setCharAt(getEndLocation().getKey() + getEndLocation().getValue() * this.getDimensions().getKey(), 
		        MazeMinion.END.getInputChar());
		this.mazeText = builder.toString();
	}
	
	
	/**
	 * Converts line to tuple.
	 * 
	 * @param lineIndex index of line
	 * @param inputString input String
	 * @return Entry<Integer, Integer> representing line
	 */
	private static Entry<Integer, Integer> convertLineToTuple(final int lineIndex, final String inputString)
	{
		final String releventLine = inputString.split(MazeConstants.CARRIAGE_NEWLINE_RETURN)[lineIndex];
		return new SimpleEntry<Integer, Integer>(Integer.valueOf(releventLine.split(MazeConstants.SPACE)[0]),
				Integer.valueOf(releventLine.split(MazeConstants.SPACE)[1]));
	}

    /**
     * Combines all data after lineIndexFrom into a single string and removes spaces and new lines.
     * 
     * @param lineIndexFrom to use data from
     * @param inputString containing data
     * @return String formatted
     */
	private static String formatMapCellsProcedingFrom(final int lineIndexFrom, final String inputString)
	{
		final String mapLines = inputString.split(MazeConstants.CARRIAGE_NEWLINE_RETURN, lineIndexFrom
				)[lineIndexFrom - 1];
		return mapLines.replaceAll(MazeConstants.CARRIAGE_NEWLINE_RETURN, MazeConstants.EMPTY)
				.replaceAll(MazeConstants.SPACE,  MazeConstants.EMPTY);
	}

    /**
     * Getter.
     * 
     * @return the dimensions
     */
    public Entry<Integer, Integer> getDimensions()
    {
        return dimensions;
    }

    /**
     * Getter.
     * 
     * @return the startLocation
     */
    public Entry<Integer, Integer> getStartLocation()
    {
        return startLocation;
    }


    /**
     * Getter.
     * 
     * @return the endLocation
     */
    public Entry<Integer, Integer> getEndLocation()
    {
        return endLocation;
    }

    /**
     * Getter.
     * 
     * @return the mazeText
     */
    public String getMazeText()
    {
        return mazeText;
    }
}
