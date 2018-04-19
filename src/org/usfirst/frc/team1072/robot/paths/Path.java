package org.usfirst.frc.team1072.robot.paths;

import java.util.HashMap;
import java.util.Map;

/**
 * @author joel
 *
 */
public class Path {
	
	public static Map<String, Path> paths = new HashMap<String, Path>();
	
	/**
	 * velocity, position
	 */
	private final double[][] path;
	
	public Path(double[][] path) {
		this.path = path;
	}
	
	public double[][] getPath(){
		return path;
	}
}
