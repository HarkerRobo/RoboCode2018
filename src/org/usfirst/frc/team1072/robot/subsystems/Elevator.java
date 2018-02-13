package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.Elevator.*;
import static org.usfirst.frc.team1072.robot.Config.Elevator.*;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Two stage elevator to lift power cubes from the floor to the scale
 */
public class Elevator extends Subsystem {
	
	/**
	 * Distance in encoder units from the top to the bottom of the elevator
	 */
	public static final int LENGTH = 0;
	
	/**
	 * Singleton instance
	 */
	private static Elevator instance;
	/**
	 * Control only this motor, the others will copy its movement
	 */
	private final TalonSRX master;
	/**
	 * Ignore these motors, they will simply follow the master
	 */
	private final VictorSPX follower1, follower2, follower3;
	/**
	 * Are all of the parts of this elevator functioning?
	 */
	private boolean encoderStatus, forwardLimitStatus, reverseLimitStatus, forwardSoftLimitStatus,
			reverseSoftLimitStatus, currentLimitStatus, voltageCompensationStatus, openRampStatus, closedRampStatus;
	/**
	 * minimum encoder value
	 */
	private double min;
	
	/**
	 * Initialize the elevator subsystem
	 */
	private Elevator() {
		// Initialize hardware links
		master = new TalonSRX(TALON);
		follower1 = new VictorSPX(VICTOR_1);
		follower2 = new VictorSPX(VICTOR_2);
		follower3 = new VictorSPX(VICTOR_3);
		// Set following
		follower1.follow(master);
		follower2.follow(master);
		follower3.follow(master);
		// Configure settings
		master.setNeutralMode(NEUTRAL_MODE);
		// Configure current limiting
		if(currentLimitStatus = log(master.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT),
				"Failed to configure continuous current limit")
				&& log(master.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT),
						"Failed to configure peak current limit")
				&& log(master.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT),
						"Failed to configure peak current duration")) {
			master.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		}
		if(voltageCompensationStatus = log(master.configVoltageCompSaturation(10, TIMEOUT),
				"Failed to configure voltage saturation")) {
			master.enableVoltageCompensation(ENABLE_VOLTAGE_COMPENSATION);
		}
		// Configure output ranges
		master.configNominalOutputForward(0, 0);
		master.configNominalOutputReverse(0, 0);
		master.configPeakOutputForward(1, 0);
		master.configPeakOutputReverse(-1, 0);
		// Configure ramping
		openRampStatus = log(master.configOpenloopRamp(RAMP_SPEED, TIMEOUT), "Failed to configure open loop ramping");
		closedRampStatus = log(master.configClosedloopRamp(RAMP_SPEED, TIMEOUT),
				"Failed to configure closed loop ramping");
		// Configure encoders
		encoderStatus = log(master.configSelectedFeedbackSensor(ENCODER_MODE, ENCODER, TIMEOUT), "Encoder not found")
				&& log(master.getSensorCollection().getPulseWidthRiseToRiseUs() != 0, "No encoder readings");
		// Configure limit switches
		if(!(forwardLimitStatus = log(master.configForwardLimitSwitchSource(FORWARD_SWITCH, FORWARD_NORMAL, TIMEOUT),
				"Forward limit switch not found")) && encoderStatus)
			log(master.configForwardSoftLimitThreshold(master.getSelectedSensorPosition(ENCODER) + LENGTH, TIMEOUT),
					"Failed to configure forward soft limits");
		System.out.println("Fwd: " + master.getSensorCollection().isFwdLimitSwitchClosed());
		if(!(reverseLimitStatus = log(master.configReverseLimitSwitchSource(REVERSE_SWITCH, REVERSE_NORMAL, TIMEOUT),
				"Reverse limit switch not found")) && encoderStatus) {
			log(master.configReverseSoftLimitThreshold(master.getSelectedSensorPosition(ENCODER), TIMEOUT),
					"Failed to configure reverse soft limits");
		}
		System.out.println("Rev: " + master.getSensorCollection().isRevLimitSwitchClosed());
	}
	
	public boolean log(ErrorCode err, String value) {
		return log(err == ErrorCode.OK, value);
	}
	
	public boolean log(boolean good, String value) {
		if(!good)
			System.err.println("Elevator: " + value);
		return good;
	}
	
	/**
	 * @return the master talon in the gearbox
	 */
	public TalonSRX getMaster() {
		return master;
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	/**
	 * Reference to the singleton instance
	 * 
	 * @return the singleton instance
	 */
	public static Elevator getInstance() {
		if(instance == null) {
			instance = new Elevator();
		}
		return instance;
	}
	
	/**
	 * @return the encoderStatus
	 */
	public boolean isEncoderStatus() {
		return encoderStatus;
	}
	
	/**
	 * @return the forwardLimitStatus
	 */
	public boolean isForwardLimitStatus() {
		return forwardLimitStatus;
	}
	
	/**
	 * @return the reverseLimitStatus
	 */
	public boolean isReverseLimitStatus() {
		return reverseLimitStatus;
	}
	
	/**
	 * @return the forwardSoftLimitStatus
	 */
	public boolean isForwardSoftLimitStatus() {
		return forwardSoftLimitStatus;
	}
	
	/**
	 * @return the reverseSoftLimitStatus
	 */
	public boolean isReverseSoftLimitStatus() {
		return reverseSoftLimitStatus;
	}
	
	/**
	 * @param encoderStatus
	 *            the encoderStatus to set
	 */
	public void setEncoderStatus(boolean encoderStatus) {
		this.encoderStatus = encoderStatus;
	}
	
	/**
	 * @param forwardLimitStatus
	 *            the forwardLimitStatus to set
	 */
	public void setForwardLimitStatus(boolean forwardLimitStatus) {
		this.forwardLimitStatus = forwardLimitStatus;
	}
	
	/**
	 * @param reverseLimitStatus
	 *            the reverseLimitStatus to set
	 */
	public void setReverseLimitStatus(boolean reverseLimitStatus) {
		this.reverseLimitStatus = reverseLimitStatus;
	}
	
	/**
	 * @param forwardSoftLimitStatus
	 *            the forwardSoftLimitStatus to set
	 */
	public void setForwardSoftLimitStatus(boolean forwardSoftLimitStatus) {
		this.forwardSoftLimitStatus = forwardSoftLimitStatus;
	}
	
	/**
	 * @param reverseSoftLimitStatus
	 *            the reverseSoftLimitStatus to set
	 */
	public void setReverseSoftLimitStatus(boolean reverseSoftLimitStatus) {
		this.reverseSoftLimitStatus = reverseSoftLimitStatus;
	}
	
	/**
	 * @return the min
	 */
	public double getMin() {
		return min;
	}
	
	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(double min) {
		this.min = min;
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
	
}
