package com.partridgetech;

import java.util.Arrays;
import java.util.List;

import com.partridgetech.client.MazeClient;
import com.partridgetech.common.MazeConstants;

/**
 * Entry point of application.
 * 
 * @author Zxzx
 *
 */
public class MainApp {
	
	/**
	 * List containing directories that hold only mazes.
	 * 
	 * @param args directory containing mazes
	 */
	public static void main(String []args)
	{
	    // If no locations provided use default - /mazes.
	    final List<String> mazeLocations = args.length == 0 ? Arrays.asList(MazeConstants
	            .DEFAULT_MAZE_LOCATION) : Arrays.asList(args);
	    
	    // Create a maze client to make a solve maze request.
	    new MazeClient(mazeLocations).solveMazes();
	    
	    // Terminate application.
	    //System.exit(0);
	}
}
