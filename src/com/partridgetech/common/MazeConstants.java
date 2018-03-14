package com.partridgetech.common;

import java.nio.file.Paths;

/**
 * Contains constants used by other classes.
 * 
 * @author Zxzx
 *
 */
public class MazeConstants {

    /** Relative directory location. */
    private static final String MAZE_DIRECTORY = "\\mazes";

    /** New line & carriage return string. */
    public static final String CARRIAGE_NEWLINE_RETURN = "\\r\\n";

    /** Text extension. */
    public static final String TXT_EXTENSION = "txt";

    /** Space. */
    public static final String SPACE = " ";

    /** Time taken message. */
    public static final String TIME_TAKEN = " the time taken was ";
    
    /** Time taken message. */
    public static final String MILLISECONDS = " milliseconds";

    /** No escape message, */
    public static final String NO_ESCAPE = "Path out of maze could not be found!";

    /** Default maze location. */
    public static final String DEFAULT_MAZE_LOCATION = Paths.get(".").toAbsolutePath().normalize().toString()
            + MAZE_DIRECTORY;

    /** New line String. */
    public static final String NEW_LINE = System.getProperty("line.separator");
    
    /** Maze max completion time. */
    public static final int MAX_MAZE_COMPLETION_TIME = 30;
    
    /** Empty String. */
    public static final String EMPTY = "";
    
    /** Could not complete message. */
    public static final String COULD_NOT_COMPLETE = "Could not complete maze.";
}
