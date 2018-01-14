package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.Intake.*;
import static org.usfirst.frc.team1072.robot.Config.Intake.*;

import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Intakes cubes in front of the robot, then ejects them onto the switch or the
 * scale
 */
public class Intake extends Subsystem {
	
	/**
	 * Control the intake wheels
	 */
	private TalonSRX leftRoller, rightRoller;
	/**
	 * Expand and contract the intake
	 */
	private Solenoid leftSolenoid, rightSolenoid;
	
	/**
	 * Initialize the intake subsystem
	 */
	public Intake() {
		// Initialize hardware links
		leftRoller = new TalonSRX(LEFT_ROLLER);
		rightRoller = new TalonSRX(RIGHT_ROLLER);
		leftSolenoid = new Solenoid(LEFT_SOLENOID);
		rightSolenoid = new Solenoid(RIGHT_SOLENOID);
		set((talon) -> {
			talon.setNeutralMode(NEUTRAL_MODE);
			talon.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT);
			talon.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT);
			talon.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT);
			talon.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		});
	}
	
	/**
	 * Runs a consumer on both motors
	 * 
	 * @param consumer
	 *            the function (lambda?) to run on them
	 */
	public void set(Consumer<TalonSRX> consumer) {
		consumer.accept(leftRoller);
		consumer.accept(rightRoller);
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
