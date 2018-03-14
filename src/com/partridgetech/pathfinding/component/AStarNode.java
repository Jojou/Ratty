package com.partridgetech.pathfinding.component;

import java.util.Objects;

import com.partridgetech.model.MazeCell;

public class AStarNode extends BasePathNode{
	
	/** g value - distance from start. */
	private final int g;
	
	/** h value - distance from end. */
	private final int h;
	
	/** f value - combination of h + f. */
	private final int f;
	
	/**
	 * Constructor.
	 * 
	 * @param mazeCell maze cell
	 * @param gValue distance to start
	 * @param hValue disance to end
	 */
	public AStarNode(MazeCell mazeCell, int gValue, int hValue) {
		super(mazeCell);
		this.g = gValue;
		this.h = hValue;
		this.f = this.g + this.h;
	}
	
	/**
     * Constructor.
     * 
     * @param mazeCell maze cell
     * @param gValue distance to start
     * @param hValue disance to end
     * @param pathNode parent node
     */
	public AStarNode(MazeCell mazeCell, int gValue, int hValue, BasePathNode pathNode) {
		super(mazeCell, pathNode);
		this.g = gValue;
		this.h = hValue;
		this.f = this.g + this.h;
	}
	
	/**
	 * Getter.
	 * 
	 * @return g
	 */
	public int getG() {
		return g;
	}

    /**
     * Getter.
     * 
     * @return h
     */
	public int getH() {
		return h;
	}

    /**
     * Getter.
     * 
     * @return f
     */
	public int getF() {
		return f;
	}
	
	/**
	 * @see com.partridgetech.pathfinding.component.BasePathNode#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other)
	{
		boolean result = false;
		if (other instanceof AStarNode) {
			AStarNode that = (AStarNode) other;
			result = (
					super.equals(that) &&
					this.g == that.g &&
					this.f == that.f &&
					this.h == that.h);
		}
		return result;
	}
	
	/**
	 * @see com.partridgetech.pathfinding.component.BasePathNode#hashCode()
	 */
	@Override 
	public int hashCode()
	{
		return super.hashCode() + Objects.hash(this.g, this.h, this.f);
	}
}
