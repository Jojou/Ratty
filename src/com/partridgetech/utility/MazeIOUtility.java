package com.partridgetech.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.partridgetech.common.MazeConstants;
import com.partridgetech.model.InputBundle;
import com.partridgetech.model.OutputBundle;

/**
 * Utility for handing output / input of mazes.
 * 
 * @author Zxzx
 *
 */
public class MazeIOUtility {
	
    /**
     * Constructor.
     */
	private MazeIOUtility()
	{
	}
	
	/**
	 * Get input bundle from mazes and create input bundles.
	 * 
	 * @param filePaths
	 * @return List<InputBundle> one for each file
	 */
	public static List<InputBundle> getInputFromLocations(final List<String> filePaths)
	{
		final List<File> directories = filePaths.stream().flatMap(directory -> Arrays.stream(new File(directory)
				.listFiles())).collect(Collectors.toList());

		final List<String> fileContents = directories.stream().map(file -> {
			String result = null;
			try {
				result = new String(Files
						.readAllBytes(Paths.get(file.getAbsolutePath())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}).collect(Collectors.toList());
		
		return fileContents.stream().map(res -> new InputBundle(res)).collect(Collectors.toList());
	}
	
	/**
	 * Get output from bundles.  Returns single string from all bundles seperated by new line.
	 * 
	 * @param outputBundles to get output from
	 * @return String containing results
	 */
	public static String getOutputFromBundles(List<OutputBundle> outputBundles)
	{
		final StringBuilder builder = new StringBuilder();
		outputBundles.stream().forEach(bundle -> builder.append(bundle.getOutputString() + MazeConstants.NEW_LINE));
		return builder.toString();
	}
}
