/*----------------------------------------------------------------------------*/
/*
 * Copyright (c) 2017-2018 FIRST. All Rights Reserved. Open Source Software -
 * may be modified and shared by FRC teams. The code must be accompanied by the
 * FIRST BSD license file in the root directory of the project.
 */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1072.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * The Config keeps track of the initial states of any hardware on the robot
 */
public class Config {
	public static class Drivetrain {
		/**
		 * Default motor state (brake or coast)
		 */
		public static final NeutralMode NEUTRAL_MODE = NeutralMode.Brake;
		/**
		 * Timeout (ms) for operations that set motor controller states
		 */
		public static final int TIMEOUT = 100;
		/**
		 * Maximum standard current per motor (A)
		 */
		public static final int CONTINUOUS_CURRENT_LIMIT = 30;
		/**
		 * A motor may deviate to currents up to peak current limit (A), for a
		 * short peak current duration of time (ms)
		 */
		public static final int PEAK_CURRENT_LIMIT = 35, PEAK_CURRENT_DURATION = 100;
		/**
		 * Controls whether current limiting is active
		 */
		public static final boolean ENABLE_CURRENT_LIMIT = true;
		/**
		 * Controls encoder mode (absolute or relative)
		 */
		public static final FeedbackDevice ENCODER_MODE = FeedbackDevice.CTRE_MagEncoder_Absolute;
		/**
		 * Voltage compensation saturation
		 */
		public static double VOLTAGE_SATURATION = 10;
		/**
		 * Use voltage compensation
		 */
		public static boolean ENABLE_VOLTAGE_COMPENSATION = true;
		/**
		 * Seconds to ramp from neutral to full
		 */
		public static double RAMP_SPEED = 1.0;
		
		public static class Carpet {
			
			public static final double kF_LEFT = 0.7 * 1023.0 / 3640.0, kF_RIGHT = 0.7 * 1023.0 / 3880.0;
			
			// Autonomous public static final double kP = 0.8, kI = 0.0023, kD = 0.64;
			public static final double kP = 0.3, kI = 0.0010, kD = 0.34;
			
		}
	}
	
	public static class Elevator {
		/**
		 * Default motor state (brake or coast)
		 */
		public static final NeutralMode NEUTRAL_MODE = NeutralMode.Coast;
		/**
		 * Timeout (ms) for operations that set motor controller states
		 */
		public static final int TIMEOUT = 100;
		/**
		 * Maximum standard current per motor (A)
		 */
		public static final int CONTINUOUS_CURRENT_LIMIT = 10;
		/**
		 * A motor may deviate to currents up to peak current limit (A), for a
		 * short peak current duration of time (ms)
		 */
		public static final int PEAK_CURRENT_LIMIT = 15, PEAK_CURRENT_DURATION = 100;
		/**
		 * Controls whether current limiting is active
		 */
		public static final boolean ENABLE_CURRENT_LIMIT = true;
		/**
		 * Normal state of the elevator limit switches
		 */
		public static final LimitSwitchNormal FORWARD_NORMAL = LimitSwitchNormal.NormallyOpen,
				REVERSE_NORMAL = LimitSwitchNormal.NormallyOpen;
		/**
		 * Controls encoder mode (absolute or relative)
		 */
		public static final FeedbackDevice ENCODER_MODE = FeedbackDevice.CTRE_MagEncoder_Absolute;
		/**
		 * Voltage compensation saturation
		 */
		public static double VOLTAGE_SATURATION = 10;
		/**
		 * Use voltage compensation
		 */
		public static boolean ENABLE_VOLTAGE_COMPENSATION = true;
		/**
		 * Seconds to ramp from neutral to full
		 */
		public static double RAMP_SPEED = 1.0;
		/**
		 * Ratio of Encoder counter to feet, need to test
		 */
		public static final int ENCODERTOFEET = 0;
	}
	
	public static class Intake {
		/**
		 * Default motor state (brake or coast)
		 */
		public static final NeutralMode NEUTRAL_MODE = NeutralMode.Coast;
		/**
		 * Timeout (ms) for operations that set motor controller states
		 */
		public static final int TIMEOUT = 0;
		/**
		 * Maximum standard current per motor (A)
		 */
		public static final int CONTINUOUS_CURRENT_LIMIT = 10;
		/**
		 * A motor may deviate to currents up to peak current limit (A), for a
		 * short peak current duration of time (ms)
		 */
		public static final int PEAK_CURRENT_LIMIT = 15, PEAK_CURRENT_DURATION = 100;
		/**
		 * Controls whether current limiting is active
		 */
		public static final boolean ENABLE_CURRENT_LIMIT = true;
	}
}
