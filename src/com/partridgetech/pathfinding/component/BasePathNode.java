package com.partridgetech.pathfinding.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.partridgetech.model.MazeCell;

/**
 * Node used in path finding algorithms.
 * 
 * @author Zxzx
 *
 */
public class BasePathNode {
	
    /** Cell from maze node represents.*/
	private final MazeCell mazeCell;
	
	/** Optional parent. */
	private final Optional<BasePathNode> parent;
	
	/**
	 * Constructor.
	 * 
	 * @param mazeCell cell
	 */
	public BasePathNode(final MazeCell mazeCell)
	{
		this.mazeCell = mazeCell;
		parent = Optional.empty();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param mazeCell cell
	 * @param parentCell cell
	 */
	public BasePathNode(final MazeCell mazeCell, final BasePathNode parentCell)
	{
		this.mazeCell = mazeCell;
		parent = Optional.of(parentCell);
	}
	
	/**
	 * Get node path from this cell to parent until cell is orphan.
	 * 
	 * @return List<MazeCell> node cells
	 */
	public List<MazeCell> getNodePath(){
		final List<MazeCell> nodes = this.getNodePath(new ArrayList<MazeCell>());
		Collections.reverse(nodes);
		return nodes;
	}
	
	/**
	 * Get node path, recursively called adding each nodes parent to list.
	 * 
	 * @param nodes cells collected so far.
	 * @return List<MazeCell>
	 */
	private List<MazeCell> getNodePath(List<MazeCell> nodes)
	{
		// Keeping searching for node until orphaned node is found
		if (this.getParent().isPresent())
		{
			nodes.add(this.getMazeCell());
			this.getParent().get().getNodePath(nodes);
		}
		return nodes;
	}

	/**
	 * Getter.
	 * 
	 * @return mazeCell
	 */
	public MazeCell getMazeCell() {
		return mazeCell;
	}
	
	/**
	 * Getter.
	 * 
	 * @return the parent
	 */
	public Optional<BasePathNode> getParent() {
		return parent;
	}
	   
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other)
    {
        boolean result = false;
        if (other instanceof BasePathNode) {
            BasePathNode that = (BasePathNode) other;
            result = (this.mazeCell.equals(that.mazeCell) && this.getParent().equals(that.getParent()));
        }
        return result;
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override 
    public int hashCode()
    {
        return Objects.hash(this.mazeCell, this.getParent());
    }
	
}