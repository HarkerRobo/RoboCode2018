//package org.usfirst.frc.team1072.robot.subsystems;
//
//import static org.usfirst.frc.team1072.robot.Config.Elevator.*;
//import static org.usfirst.frc.team1072.robot.RobotMap.Elevator.ENCODER;
//
//import org.usfirst.frc.team1072.robot.Robot;
//
//import com.ctre.phoenix.ErrorCode;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
//
//import edu.wpi.first.wpilibj.command.Subsystem;
//
///**
// *
// */
//public class Elevator2 extends Subsystem {
//	
//	enum EncoderState {
//		Unknown, Present, Missing
//	}
//	
//	/**
//	 * Singleton instance
//	 */
//	private static Elevator instance;
//	/**
//	 * Control only this motor, the others will copy its movement
//	 */
//	private TalonSRX master;
//	/**
//	 * Ignore these motors, they will simply follow the master
//	 */
//	private VictorSPX follower1, follower2, follower3;
//	/**
//	 * Are all of the parts of this elevator functioning?
//	 */
//	private boolean forwardLimitPresent, reverseLimitPresent, forwardSoftLimitPresent, reverseSoftLimitPresent,
//			velocityClosedStatus, positionClosedStatus, motionMagicStatus;
//	
//	private EncoderState encoder;
//	
//	private boolean sensorPhase = Robot.IS_COMP;
//	
//	public void initDefaultCommand() {
//		// Set the default command for a subsystem here.
//		// setDefaultCommand(new MySpecialCommand());
//	}
//	
//	private void configMaster() {
//		master.setNeutralMode(NEUTRAL_MODE);
//		log(master.configNominalOutputForward(0, TIMEOUT), "Could not configure nominal forward");
//		log(master.configNominalOutputReverse(0, TIMEOUT), "Could not configure nominal reverse");
//		log(master.configSelectedFeedbackSensor(ENCODER_MODE, ENCODER, TIMEOUT), "Encoder not found");
//		log(master.configOpenloopRamp(RAMP_SPEED, TIMEOUT), "Failed to configure open loop ramping");
//		log(master.configClosedloopRamp(RAMP_SPEED, TIMEOUT), "Failed to configure closed loop ramping");
//		master.setSensorPhase(sensorPhase);
//		if(log(master.configVoltageCompSaturation(10, TIMEOUT), "Failed to configure voltage saturation")) {
//			master.enableVoltageCompensation(ENABLE_VOLTAGE_COMPENSATION);
//		}
//	}
//	
//	private void configEncoder() {
//		if(encoder == EncoderState.Unknown) {
//			if(!log(master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, TIMEOUT),
//					"Could not configure right encoder")) {
//				return;
//			}
//		}
//		boolean present = master.getSensorCollection().getPulseWidthRiseToRiseUs() != 0;
//		if(present && encoder != EncoderState.Present) {
//			encoder = EncoderState.Present;
//			log(master.configPeakOutputForward(1, TIMEOUT), "Could not configure peak forward");
//			log(master.configPeakOutputReverse(-1, TIMEOUT), "Could not configure peak reverse");
//			if(log(master.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT),
//					"Failed to configure continuous current limit")
//					&& log(master.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT),
//							"Failed to configure peak current limit")
//					&& log(master.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT),
//							"Failed to configure peak current duration")) {
//				master.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
//			}
//		} else if(!present && encoder != EncoderState.Missing) {
//			encoder = EncoderState.Missing;
//			log(master.configPeakOutputForward(0.7, TIMEOUT), "Could not configure peak forward");
//			log(master.configPeakOutputReverse(-0.7, TIMEOUT), "Could not configure peak reverse");
//			if(log(master.configContinuousCurrentLimit(BAD_ENCODER_CONTINUOUS_CURRENT_LIMIT, TIMEOUT),
//					"Failed to configure continuous current limit")
//					&& log(master.configPeakCurrentLimit(BAD_ENCODER_PEAK_CURRENT_LIMIT, TIMEOUT),
//							"Failed to configure peak current limit")
//					&& log(master.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT),
//							"Failed to configure peak current duration")) {
//				master.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
//			}
//		}
//	}
//	
//	public static boolean log(ErrorCode err, String value) {
//		return log(err == ErrorCode.OK, value);
//	}
//	
//	public static boolean log(boolean good, String value) {
//		if(!good)
//			log(value);
//		return good;
//	}
//	
//	public static void log(String value) {
//		Robot.log("Elevator: " + value);
//	}
//}
