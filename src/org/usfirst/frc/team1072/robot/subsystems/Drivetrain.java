package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.Drivetrain.*;
import static org.usfirst.frc.team1072.robot.Config.Drivetrain.*;

import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 4-Cim west coast drivetrain to move the robot
 */
public class Drivetrain extends Subsystem {
	
	/**
	 * Control only these motors, the others will copy its movement
	 */
	private TalonSRX leftMaster, rightMaster;
	/**
	 * Ignore these motors, they will simply follow the master
	 */
	private VictorSPX leftFollower, rightFollower;
	
	/**
	 * Initialize the drivetrain subsystem
	 */
	public Drivetrain() {
		// Initialize hardware links
		leftMaster = new TalonSRX(LEFT_TALON);
		rightMaster = new TalonSRX(RIGHT_TALON);
		leftFollower = new VictorSPX(LEFT_VICTOR);
		rightFollower = new VictorSPX(RIGHT_VICTOR);
		// Set following
		leftFollower.follow(leftMaster);
		rightFollower.follow(rightMaster);
		// Configure settings (on both masters)
		set((talon) -> {
			talon.setNeutralMode(NEUTRAL_MODE);
			talon.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT);
			talon.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT);
			talon.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT);
			talon.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
			talon.configSelectedFeedbackSensor(ENCODER_MODE, ENCODER, TIMEOUT);
		});
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
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
