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
		/**
		 * Controls encoder mode (absolute or relative)
		 */
		public static final FeedbackDevice ENCODER_MODE = FeedbackDevice.CTRE_MagEncoder_Absolute;
	}
	
	public static class Elevator {
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
		/**
		 * Normal state of the elevator limit switches
		 */
		public static final LimitSwitchNormal FORWARD_NORMAL = LimitSwitchNormal.NormallyOpen,
				REVERSE_NORMAL = LimitSwitchNormal.NormallyOpen;
		/**
		 * Controls encoder mode (absolute or relative)
		 */
		public static final FeedbackDevice ENCODER_MODE = FeedbackDevice.CTRE_MagEncoder_Absolute;
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
	}
	
	public static class GearIntake {
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
	     /**
         * Controls encoder mode (absolute or relative)
         */
        public static final FeedbackDevice ENCODER_MODE = FeedbackDevice.CTRE_MagEncoder_Absolute;
        /**
         * PIDF constants for closed loop position on the orientation moving up
         * 
         * https://github.com/CrossTheRoadElec/Phoenix-Documentation#position-closed-loop-walkthrough
         */
        public static final double UP_P = 1.0, UP_I = 0.0, UP_D = 0, UP_F = 0.04;
        /**
         * PIDF constants for closed loop position on the orientation moving down
         * 
         * https://github.com/CrossTheRoadElec/Phoenix-Documentation#position-closed-loop-walkthrough
         */
        public static final double DOWN_P = 1.0, DOWN_I = 0.0, DOWN_D = 0, DOWN_F = -0.04;
        
        public static final double MAX = 2000.0, MIN = -4000.0;
	}
}
