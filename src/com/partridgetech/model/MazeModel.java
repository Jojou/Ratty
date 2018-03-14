package com.partridgetech.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.partridgetech.common.Direction;
import com.partridgetech.common.MazeMinion;

/**
 * Maze model representing all the cells in the maze.
 * 
 * @author Zxzx
 *
 */
public class MazeModel {
	
    /** Dimensions of maze. */
	private final Entry<Integer, Integer> dimensions;
	
	/** Start cell of maze. */
	private final MazeCell startCell;
	
	/** End cell of maze. */
	private final MazeCell endCell;
	
	/** List of all cells in maze. */
	private final List<MazeCell> mazeCells = new ArrayList<MazeCell>();
	
	/**
	 * Constructor.
	 * 
	 * @param dimensions of maze
	 * @param startLocation of maze
	 * @param endLocation of maze
	 * @param mazeText makeup of maze
	 */
	public MazeModel(final Entry<Integer, Integer> dimensions, final Entry<Integer, Integer> startLocation, 
			final Entry<Integer, Integer> endLocation, final String mazeText)
	{
		this.dimensions = dimensions;
		
        final List<Character> mazeChars = mazeText.chars().mapToObj(e->(char)e).collect(Collectors.toList());
        
        // Iterator through maze chars and create maze cells
        this.mazeCells.addAll(IntStream.range(0, mazeChars.size()).mapToObj(
                index -> new MazeCell(index, dimensions.getKey(), mazeChars.get(index).charValue()))
                .collect(Collectors.toList()));
		
		this.startCell = this.getCell(startLocation.getKey(), startLocation.getValue());
		
		this.endCell = this.getCell(endLocation.getKey(), endLocation.getValue());
	}
	

	/**
	 * Constructor.
	 * 
	 * @param inputBundle contains information for creating maze
	 */
	public MazeModel(final InputBundle inputBundle)
	{
		this(inputBundle.getDimensions(), inputBundle.getStartLocation(), 
		        inputBundle.getEndLocation(), inputBundle.getMazeText());
	}
	
	/**
	 * Get cell.
	 * 
	 * @param x location
	 * @param y location
	 * @return cell
	 */
	private MazeCell getCell(final int x, final int y) {
		return this.getMazeCells().get(this.getDimensions().getKey() * y + x);
	}
	
	/**
	 * Get cell in direction from mazeCell.
	 * 
	 * @param mazeCell start cell
	 * @param direction direction
	 * @return resulting cell
	 */
	public MazeCell getCell(final MazeCell mazeCell, final Direction direction){
		return this.getCell(mazeCell.getX() + direction.getX(), mazeCell.getY() + direction.getY());
	}
	
	/**
	 * Get adjacent cells (North, South, East, West).
	 * 
	 * @param mazeCell to get adjacent cells of
	 * @return List of maze cells
	 */
	public List<MazeCell> getAdjacentCells(final MazeCell mazeCell){
		return Arrays.stream(Direction.values()).map(res -> this.getCell(mazeCell, res)).collect(Collectors.toList());
	}
	
    /**
     * Get cells which contain a type of minion.
     * 
     * @param mazeMinions to get cells matching
     * @return List<MazeCell> containing requested minion
     */
    public List<MazeCell> getCells(final MazeMinion mazeMinion){
        return this.getCells(Arrays.asList(mazeMinion));
    }
	
	/**
	 * Get cells which contain a type of minion.
	 * 
	 * @param mazeMinions to get cells matching one of
	 * @return List<MazeCell> containing only one of requested minions
	 */
	public List<MazeCell> getCells(final List<MazeMinion> mazeMinions){
		return this.getMazeCells().stream().filter(res -> mazeMinions.contains(res)).collect(Collectors.toList());
	}
	
	/**
	 * Getter.
	 * 
     * @return the startCell
     */
    public MazeCell getStartCell()
    {
        return this.startCell;
    }

    /**
     * Getter.
     * 
     * @return the endCell
     */
    public MazeCell getEndCell()
    {
        return this.endCell;
    }

    /**
     * Getter.
     * 
     * @return the mazeCells
     */
    public List<MazeCell> getMazeCells()
    {
        return this.mazeCells;
    }

    /**
     * Get output of each cell in maze.
     * 
     * @return String containing cell outputs
     */
    public String getOutputCells()
    {
        final StringBuilder builder = new StringBuilder();
        this.mazeCells.stream().forEach(res -> {
            builder.append(res.getEntity().getOutputChar());
        });
        return builder.toString();
    }

    /**
     * @return the dimensions
     */
    public Entry<Integer, Integer> getDimensions()
    {
        return dimensions;
    }
}
