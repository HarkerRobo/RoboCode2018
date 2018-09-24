package org.usfirst.frc.team1072.robot.paths;

public class SamplePath extends Path {
	
	static {
		Path.paths.put("Sample", new SamplePath());
	}

	public SamplePath() {
		super(new double[][] {
			{ 0, 0, 0 }
		});
	}
	
}
