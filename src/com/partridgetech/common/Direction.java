package com.partridgetech.common;


/**
 * Simple enum class for directions.  Can be used by Maze cells to change position.
 * 
 * @author Zxzx
 *
 */
public enum Direction {

    /** All possible directions. */
    UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0);

    /** x movement. */
    private final int x;

    /** y movement. */
    private final int y;

    /**
     * Constructor.
     * 
     * @param x movement
     * @param y movement
     */
    private Direction(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter.
     * 
     * @return x
     */
    public int getX()
    {
        return x;
    }

    /**
     * Getter.
     * 
     * @return y
     */
    public int getY()
    {
        return y;
    }
}