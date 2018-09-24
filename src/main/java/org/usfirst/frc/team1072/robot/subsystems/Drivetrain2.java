//package org.usfirst.frc.team1072.robot.subsystems;
//
//import com.ctre.phoenix.ErrorCode;
//import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.DemandType;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
//import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
//import com.ctre.phoenix.motorcontrol.StickyFaults;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
//
//import edu.wpi.first.wpilibj.command.Subsystem;
//
//import static org.usfirst.frc.team1072.robot.RobotMap.Drivetrain.*;
//
//import org.usfirst.frc.team1072.robot.Robot;
//import org.usfirst.frc.team1072.robot.Slot;
//
//import static org.usfirst.frc.team1072.robot.Config.Drivetrain.*;
//
///**
// *
// */
//public class Drivetrain2 extends Subsystem {
//	enum EncoderState {
//		Unknown, Present, Missing
//	}
//	
//	enum PigeonState {
//		Unknown, Present, Missing
//	}
//	
//	enum Mode {
//		Unknown, Autonomous, Teleop
//	}
//	
//	/**
//	 * PID slots
//	 */
//	public static final int MAIN_PID = 0, AUX_PID = 1;
//	/**
//	 * What is stored in each slot
//	 */
//	public static final int MOTION_PROFILE = 0, VELOCITY = 1, PITCH = 2, YAW = 3;
//	/**
//	 * Max speeds
//	 */
//	public static final double LEFT_MAX = 2500, RIGHT_MAX = 2400, MAX_SPEED = Math.max(LEFT_MAX, RIGHT_MAX);
//	/**
//	 * Singleton instance
//	 */
//	private static Drivetrain2 instance;
//	/**
//	 * Control only these motors, the others will copy its movement
//	 */
//	private TalonSRX leftMaster, rightMaster;
//	/**
//	 * Ignore these motors, they will simply follow the master
//	 */
//	private VictorSPX leftFollower, rightFollower;
//	/**
//	 * Is everything on this drivetrain working?
//	 */
//	private boolean leftEncoderPresent, rightEncoderPresent, leftPigeonPresent, rightPigeonPresent,
//			leftVelocityClosedStatus, rightVelocityClosedStatus, leftMotionProfileStatus, rightMotionProfileStatus,
//			leftPitchClosedStatus, rightPitchClosedStatus, leftYawClosedStatus, rightYawClosedStatus;
//	
//	private EncoderState leftEncoder = EncoderState.Unknown, rightEncoder = EncoderState.Unknown;
//	
//	private PigeonState leftPigeon = PigeonState.Unknown, rightPigeon = PigeonState.Unknown;
//	
//	private Mode leftMode = Mode.Unknown, rightMode = Mode.Unknown;
//	
//	private boolean leftSensorPhase = false, rightSensorPhase = false;
//	
//	private StickyFaults sticky;
//	
//	public Drivetrain2() {
//		leftMaster = new TalonSRX(LEFT_TALON);
//		rightMaster = new TalonSRX(RIGHT_TALON);
//		leftFollower = new VictorSPX(LEFT_VICTOR);
//		rightFollower = new VictorSPX(RIGHT_VICTOR);
//		autonomousInit();
//	}
//	
//	public void configLeftMaster() {
//		leftMaster.setInverted(true);
//		leftMaster.setNeutralMode(NEUTRAL_MODE);
//	}
//	
//	public void configRightMaster() {
//		rightMaster.setNeutralMode(NEUTRAL_MODE);
//	}
//	
//	public void configLeftFollower() {
//		leftFollower.setInverted(true);
//		leftFollower.follow(leftMaster);
//		leftFollower.setNeutralMode(NEUTRAL_MODE);
//	}
//	
//	public void configRightFollower() {
//		rightFollower.follow(rightMaster);
//		rightFollower.setNeutralMode(NEUTRAL_MODE);
//	}
//	
//	public void configLeftEncoder() {
//		if(leftEncoder == EncoderState.Unknown) {
//			if(!log(leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, MAIN_PID, TIMEOUT),
//					"Could not configure left encoder")) {
//				return;
//			}
//		}
//		boolean present = leftMaster.getSensorCollection().getPulseWidthRiseToRiseUs() != 0;
//		if(present && leftEncoder != EncoderState.Present) {
//			leftEncoder = EncoderState.Present;
//		} else if(!present && leftEncoder != EncoderState.Missing) {
//			leftEncoder = EncoderState.Missing;
//		}
//	}
//	
//	public void configRightEncoder() {
//		if(leftEncoder == EncoderState.Unknown) {
//			if(!log(rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, MAIN_PID,
//					TIMEOUT), "Could not configure right encoder")) {
//				return;
//			}
//		}
//		boolean present = rightMaster.getSensorCollection().getPulseWidthRiseToRiseUs() != 0;
//		if(present && rightEncoder != EncoderState.Present) {
//			rightEncoder = EncoderState.Present;
//		} else if(!present && rightEncoder != EncoderState.Missing) {
//			rightEncoder = EncoderState.Missing;
//		}
//	}
//	
//	public void configLeftAutonomous() {
//		leftMotionProfileStatus = log(Slot.LEFT_MOTION_PROFILE.configure(leftMaster, TIMEOUT),
//				"Could not configure left motion profiling");
//	}
//	
//	public void autonomousInit() {
//		leftMotionProfileStatus = log(Slot.LEFT_MOTION_PROFILE.configure(leftMaster, TIMEOUT),
//				"Could not configure left motion profiling");
//		rightMotionProfileStatus = log(Slot.RIGHT_MOTION_PROFILE.configure(rightMaster, TIMEOUT),
//				"Could not configure right motion profiling");
//		leftYawClosedStatus = log(Slot.LEFT_YAW.configure(leftMaster, TIMEOUT),
//				"Could not configure left yaw correction");
//		rightYawClosedStatus = log(Slot.RIGHT_YAW.configure(rightMaster, TIMEOUT),
//				"Could not configure right yaw correction");
//	}
//	
//	// Put methods for controlling this subsystem
//	// here. Call these from Commands.
//	
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see edu.wpi.first.wpilibj.command.Subsystem#periodic()
//	 */
//	@Override
//	public void periodic() {
//		leftMaster.getStickyFaults(sticky);
//		if(sticky.hasAnyFault()) {
//			if(sticky.HardwareESDReset || sticky.ResetDuringEn) {
//				log("Left master reset");
//				configLeftMaster();
//			}
//			if(sticky.ForwardLimitSwitch) {
//				leftMaster.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, TIMEOUT);
//			}
//			if(sticky.ReverseLimitSwitch) {
//				leftMaster.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, TIMEOUT);
//			}
//			if(sticky.SensorOutOfPhase) {
//				leftMaster.setSensorPhase(leftSensorPhase = !leftSensorPhase);
//			}
//			if(sticky.UnderVoltage) {
//				log("Left master under voltage");
//			}
//			leftMaster.clearStickyFaults(0);
//		}
//		rightMaster.getStickyFaults(sticky);
//		if(sticky.hasAnyFault()) {
//			if(sticky.HardwareESDReset || sticky.ResetDuringEn) {
//				log("Right master reset");
//				configLeftMaster();
//			}
//			if(sticky.ForwardLimitSwitch) {
//				rightMaster.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled,
//						TIMEOUT);
//			}
//			if(sticky.ReverseLimitSwitch) {
//				rightMaster.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled,
//						TIMEOUT);
//			}
//			if(sticky.SensorOutOfPhase) {
//				rightMaster.setSensorPhase(rightSensorPhase = !rightSensorPhase);
//			}
//			if(sticky.UnderVoltage) {
//				log("Right master under voltage");
//			}
//			rightMaster.clearStickyFaults(0);
//		}
//	}
//	
//	public void initDefaultCommand() {
//		// Set the default command for a subsystem here.
//		// setDefaultCommand(new MySpecialCommand());
//	}
//	
//	public void set(double left, double right) {
//		if(leftEncoderPresent && rightEncoderPresent) {
//			if(leftPigeonPresent && rightPigeonPresent) {
//				leftMaster.set(ControlMode.Velocity, left * MAX_SPEED, DemandType.AuxPID, 0);
//				rightMaster.set(ControlMode.Velocity, right * MAX_SPEED, DemandType.AuxPID, 0);
//			} else {
//				leftMaster.set(ControlMode.Velocity, left * MAX_SPEED);
//				rightMaster.set(ControlMode.Velocity, right * MAX_SPEED);
//			}
//		} else {
//			leftMaster.set(ControlMode.PercentOutput, left / LEFT_MAX);
//			rightMaster.set(ControlMode.PercentOutput, right / RIGHT_MAX);
//		}
//	}
//	
//	public boolean log(ErrorCode err, String value) {
//		return log(err == ErrorCode.OK, value);
//	}
//	
//	public boolean log(boolean good, String value) {
//		if(!good)
//			log(value);
//		return good;
//	}
//	
//	public void log(String value) {
//		Robot.log("Drivetrain: " + value);
//	}
//}
