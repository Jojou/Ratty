package com.partridgetech.model;

import java.util.Objects;

import com.partridgetech.common.MazeMinion;

/**
 * Represents a character in maze input String.
 * 
 * @author Zxzx
 *
 */
public class MazeCell implements Comparable<MazeCell> {
	
    /** Index of maze cell. */
	private final int index;
	
	/** Length of each row in maze. */
	private final int rowLength;
	
	/** Maze minion cell represents. */
	private final MazeMinion entity;
	
	/** X location. */
	private final int x;
	
	/** Y location. */
	private final int y;

	/**
	 * Constructor.
	 * 
	 * @param index of cell
	 * @param rowLength length of each row
	 * @param mazeCell maze cell character
	 */
	public MazeCell(final int index, final int rowLength, char mazeCell)
	{
		this.index = index;
		this.rowLength = rowLength;
		
		this.entity = MazeMinion.getMazeMinion(mazeCell);
		this.x = Math.abs(index % rowLength);
		this.y = (int) Math.floor(index / rowLength);
	}
	
	/**
	 * Get moves away from other cell.
	 * 
	 * @param otherCell
	 * @return moves away from
	 */
	public int getMoves(MazeCell otherCell){
		return Math.abs(this.x - otherCell.x) + Math.abs(this.y - otherCell.y);
	}
	
	/**
	 * Getter.
	 * 
     * @return the index
     */
    public int getIndex()
    {
        return this.index;
    }

    /**
     * Getter.
     * 
     * @return the rowLength
     */
    public int getRowLength()
    {
        return this.rowLength;
    }

    /**
     * Getter.
     * 
     * @return the entity
     */
    public MazeMinion getEntity()
    {
        return this.entity;
    }

    /**
     * Getter.
     * 
     * @return the x
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * Getter.
     * 
     * @return the y
     */
    public int getY()
    {
        return this.y;
    }


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
	@Override
	public int compareTo(MazeCell otherCell) {
		return this.index - otherCell.index;
	}
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		  return "Type " + this.entity + " x " + this.x + " y " + this.y;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other)
	{
		boolean result = false;
		if (other instanceof MazeCell) {
			MazeCell that = (MazeCell) other;
			result = (
					this.index == that.index && 
					this.rowLength == that.rowLength &&
					this.entity.equals(that.entity) &&
					this.x == that.x &&
					this.y == that.y);
		}
		return result;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override 
	public int hashCode()
	{
		return Objects.hash(this.index, this.rowLength, this.entity, this.x, this.y);
	}
}


