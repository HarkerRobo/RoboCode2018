package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.Drivetrain.*;
import static org.usfirst.frc.team1072.robot.Config.Drivetrain.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.usfirst.frc.team1072.robot.Slot;
import org.usfirst.frc.team1072.robot.commands.ArcadeDriveCommand;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 4-Cim west coast drivetrain to move the robot
 */
public class Drivetrain extends Subsystem {
	/**
	 * Singleton instance
	 */
	private static Drivetrain instance;
	/**
	 * Control only these motors, the others will copy its movement
	 */
	private TalonSRX leftMaster, rightMaster;
	/**
	 * Ignore these motors, they will simply follow the master
	 */
	private VictorSPX leftFollower, rightFollower;
	/**
	 * Are all of the parts of this elevator functioning?
	 */
	private boolean motorStatus, encoderStatus, currentLimitStatus, voltageCompensationStatus, openRampStatus,
			closedRampStatus, velocityClosedStatus, positionClosedStatus, motionProfileStatus;
	
	/**
	 * Initialize the drivetrain subsystem
	 */
	private Drivetrain() {
		// Initialize hardware links
		try {
			leftMaster = new TalonSRX(LEFT_TALON);
			rightMaster = new TalonSRX(RIGHT_TALON);
			leftFollower = new VictorSPX(LEFT_VICTOR);
			rightFollower = new VictorSPX(RIGHT_VICTOR);
			motorStatus = true;
			// invert
			leftMaster.setInverted(true);
			leftFollower.setInverted(true);
			// Set following
			leftFollower.follow(leftMaster);
			rightFollower.follow(rightMaster);
			// Configure settings (on both masters)
			set((talon) -> talon.setNeutralMode(NEUTRAL_MODE));
			// Configure current limiting
			if(currentLimitStatus = ENABLE_CURRENT_LIMIT
					&& log(leftMaster.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT),
							"Failed to configure left continuous current limit")
					&& log(leftMaster.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT),
							"Failed to configure left peak current limit")
					&& log(leftMaster.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT),
							"Failed to configure left peak current duration")
					&& log(rightMaster.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT),
							"Failed to configure right continuous current limit")
					&& log(rightMaster.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT),
							"Failed to configure right peak current limit")
					&& log(rightMaster.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT),
							"Failed to configure right peak current duration")) {
				set((talon) -> talon.enableCurrentLimit(ENABLE_CURRENT_LIMIT));
			}
			
			if(voltageCompensationStatus = ENABLE_VOLTAGE_COMPENSATION
					&& log(leftMaster.configVoltageCompSaturation(VOLTAGE_SATURATION, TIMEOUT),
							"Failed to configure left voltage saturation")
					&& log(rightMaster.configVoltageCompSaturation(VOLTAGE_SATURATION, TIMEOUT),
							"Failed to configure right voltage saturation")) {
				set((talon) -> talon.enableVoltageCompensation(ENABLE_VOLTAGE_COMPENSATION));
			}
			// Configure output ranges
			set((talon) -> {
				talon.configNominalOutputForward(0, 0);
				talon.configNominalOutputReverse(0, 0);
				talon.configPeakOutputForward(1, 0);
				talon.configPeakOutputReverse(-1, 0);
			});
			// Configure ramping
			openRampStatus = log(leftMaster.configOpenloopRamp(RAMP_SPEED, TIMEOUT),
					"Failed to configure left open loop ramping")
					&& log(rightMaster.configOpenloopRamp(RAMP_SPEED, TIMEOUT),
							"Failed to configure right open loop ramping");
			closedRampStatus = log(leftMaster.configClosedloopRamp(RAMP_SPEED, TIMEOUT),
					"Failed to configure left closed loop ramping")
					&& log(rightMaster.configClosedloopRamp(RAMP_SPEED, TIMEOUT),
							"Failed to configure right closed loop ramping");
			// Configure encoders
			encoderStatus = log(leftMaster.configSelectedFeedbackSensor(ENCODER_MODE, ENCODER, TIMEOUT),
					"Left encoder not found")
					&& log(rightMaster.configSelectedFeedbackSensor(ENCODER_MODE, ENCODER, TIMEOUT),
							"Right encoder not found")
					&& log(leftMaster.getSensorCollection().getPulseWidthRiseToRiseUs() != 0,
							"No left encoder readings")
					&& log(rightMaster.getSensorCollection().getPulseWidthRiseToRiseUs() != 0,
							"No right encoder readings");
			// Load constants
			if(!(velocityClosedStatus = Slot.LEFT_VELOCITY.configure(leftMaster, TIMEOUT)
					&& Slot.RIGHT_VELOCITY.configure(rightMaster, TIMEOUT))) {
				System.err.println("Drivetrain: Failed to configure velocity closed loop");
			}
			if(!(positionClosedStatus = Slot.LEFT_POSITION.configure(leftMaster, TIMEOUT)
					&& Slot.RIGHT_POSITION.configure(rightMaster, TIMEOUT))) {
				System.err.println("Drivetrain: Failed to configure position closed loop");
			}
			if(!(motionProfileStatus = Slot.LEFT_MOTION_PROFILE.configure(leftMaster, TIMEOUT)
					&& Slot.RIGHT_MOTION_PROFILE.configure(rightMaster, TIMEOUT))) {
				System.err.println("Drivetrain: Failed to configure motion profiling");
			}
			// Select profile zero
			set((talon) -> talon.selectProfileSlot(0, 0));
		} catch(Exception e) {
			System.err.println("Drivetrain: Failed to initialize motors");
		}
	}
	
	/**
	 * @return the motorStatus
	 */
	public boolean isMotorStatus() {
		return motorStatus;
	}

	/**
	 * @param motorStatus the motorStatus to set
	 */
	public void setMotorStatus(boolean motorStatus) {
		this.motorStatus = motorStatus;
	}

	/**
	 * @return the encoderStatus
	 */
	public boolean isEncoderStatus() {
		return encoderStatus;
	}
	
	/**
	 * @param encoderStatus
	 *            the encoderStatus to set
	 */
	public void setEncoderStatus(boolean encoderStatus) {
		this.encoderStatus = encoderStatus;
	}
	
	/**
	 * @return the currentLimitStatus
	 */
	public boolean isCurrentLimitStatus() {
		return currentLimitStatus;
	}
	
	/**
	 * @param currentLimitStatus
	 *            the currentLimitStatus to set
	 */
	public void setCurrentLimitStatus(boolean currentLimitStatus) {
		this.currentLimitStatus = currentLimitStatus;
	}
	
	/**
	 * @return the voltageCompensationStatus
	 */
	public boolean isVoltageCompensationStatus() {
		return voltageCompensationStatus;
	}
	
	/**
	 * @param voltageCompensationStatus
	 *            the voltageCompensationStatus to set
	 */
	public void setVoltageCompensationStatus(boolean voltageCompensationStatus) {
		this.voltageCompensationStatus = voltageCompensationStatus;
	}
	
	/**
	 * @return the openRampStatus
	 */
	public boolean isOpenRampStatus() {
		return openRampStatus;
	}
	
	/**
	 * @param openRampStatus
	 *            the openRampStatus to set
	 */
	public void setOpenRampStatus(boolean openRampStatus) {
		this.openRampStatus = openRampStatus;
	}
	
	/**
	 * @return the closedRampStatus
	 */
	public boolean isClosedRampStatus() {
		return closedRampStatus;
	}
	
	/**
	 * @param closedRampStatus
	 *            the closedRampStatus to set
	 */
	public void setClosedRampStatus(boolean closedRampStatus) {
		this.closedRampStatus = closedRampStatus;
	}
	
	/**
	 * @return the velocityClosedStatus
	 */
	public boolean isVelocityClosedStatus() {
		return velocityClosedStatus;
	}
	
	/**
	 * @param velocityClosedStatus
	 *            the velocityClosedStatus to set
	 */
	public void setVelocityClosedStatus(boolean velocityClosedStatus) {
		this.velocityClosedStatus = velocityClosedStatus;
	}
	
	/**
	 * @return the positionClosedStatus
	 */
	public boolean isPositionClosedStatus() {
		return positionClosedStatus;
	}
	
	/**
	 * @param positionClosedStatus
	 *            the positionClosedStatus to set
	 */
	public void setPositionClosedStatus(boolean positionClosedStatus) {
		this.positionClosedStatus = positionClosedStatus;
	}
	
	/**
	 * @return the motionProfileStatus
	 */
	public boolean isMotionProfileStatus() {
		return motionProfileStatus;
	}
	
	/**
	 * @param motionProfileStatus
	 *            the motionProfileStatus to set
	 */
	public void setMotionProfileStatus(boolean motionProfileStatus) {
		this.motionProfileStatus = motionProfileStatus;
	}
	
	public boolean log(ErrorCode err, String value) {
		return log(err == ErrorCode.OK, value);
	}
	
	public boolean log(boolean good, String value) {
		if(!good)
			System.err.println("Drivetrain: " + value);
		return good;
	}
	
	/**
	 * Runs a consumer on both masters
	 * 
	 * @param consumer
	 *            the function (lambda?) to run on them
	 */
	public void set(Consumer<TalonSRX> consumer) {
		consumer.accept(leftMaster);
		consumer.accept(rightMaster);
	}
	
	/**
	 * Runs a consumer on both masters
	 * 
	 * @param consumer
	 *            the function (lambda?) to run on them
	 */
	public void set(BiConsumer<TalonSRX, String> consumer) {
		consumer.accept(leftMaster, "left");
		consumer.accept(rightMaster, "right");
	}
	
	/**
	 * @return the left master talon
	 */
	public TalonSRX getLeft() {
		return leftMaster;
	}
	
	/**
	 * @return the right master talon
	 */
	public TalonSRX getRight() {
		return rightMaster;
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveCommand());
		// setDefaultCommand(new TestDrive());
	}
	
	/**
	 * Reference to the singleton instance
	 * 
	 * @return the singleton instance
	 */
	public static Drivetrain getInstance() {
		if(instance == null) {
			instance = new Drivetrain();
		}
		return instance;
	}
}
