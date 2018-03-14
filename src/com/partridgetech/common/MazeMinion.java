package com.partridgetech.common;

import java.util.Arrays;

/**
 * 
 * Represents types of entities that can be shown in maze.  There are two types for each entity to cover
 * scenarios where a different entity output is required to input.
 * 
 * @author Zxzx
 *
 */
public enum MazeMinion {

    /** All possible entity types. */
    SPACE('0', ' '), WALL('1', '#'), START('S', 'S'), END('E', 'E'), ROUTE('X', 'X');

    /** Input of entity. */
    private final char inputChar;

    /** Output of entity. */
    private final char outputChar;

    /**
     * Constructor.
     * 
     * @param input of entity
     * @param output of entity
     */
    private MazeMinion(char input, char output)
    {
        this.inputChar = input;
        this.outputChar = output;
    }

    /**
     * Getter.
     * 
     * @return inputChar
     */
    public char getInputChar()
    {
        return inputChar;
    }

    /**
     * Getter.
     * 
     * @return outputChar
     */
    public char getOutputChar()
    {
        return outputChar;
    }

    /**
     * Allows for maze minion to be returned depending on input character provided.
     * 
     * @param inputChar to get matching maze minion of
     * @return MazeMinion
     */
    public static MazeMinion getMazeMinion(char inputChar)
    {
        return Arrays.stream(MazeMinion.values()).filter(res -> res.inputChar == inputChar).findFirst()
                .orElseThrow(() -> new RuntimeException());
    }
}
