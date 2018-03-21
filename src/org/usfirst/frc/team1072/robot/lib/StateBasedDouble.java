package org.usfirst.frc.team1072.robot.lib;

/**
 * @author joel
 *
 */
public class StateBasedDouble {
	private double standard, missingEncoder, missingIMU, missingAll;
	
	public StateBasedDouble(double[] values) {
		standard = values.length > 0 ? values[0] : 0.0;
		missingEncoder = values.length > 1 ? values[1]  : standard;
		missingIMU = values.length > 2 ? values[2] : standard;
		missingAll = values.length > 3 ? values[3] : missingEncoder;
	}
	
	public double get(boolean encoder, boolean imu) {
		if(encoder)
			if(imu)
				return standard;
			else
				return missingIMU;
		else
			if(imu)
				return missingEncoder;
			else
				return missingAll;
	}
}
