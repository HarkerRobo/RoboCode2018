package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.GearIntake.*;

import static org.usfirst.frc.team1072.robot.Config.GearIntake.*;

import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Intakes cubes in front of the robot, then ejects them onto the switch or the
 * scale
 */
public class GearIntake extends Subsystem {
	/**
	 * Singleton instance
	 */
	private static GearIntake instance;
	/**
	 * Control the intake wheels
	 */
	private TalonSRX rollers, orientation;
	
	/**
	 * Initialize the gear intake subsystem
	 */
	private GearIntake() {
		// Initialize hardware links
		rollers = new TalonSRX(ROLLERS);
		orientation = new TalonSRX(ORIENTATION);
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
		consumer.accept(rollers);
		consumer.accept(orientation);
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	/**
	 * Reference to the singleton instance
	 * 
	 * @return the singleton instance
	 */
	public static GearIntake getInstance() {
		if(instance == null) {
			instance = new GearIntake();
		}
		return instance;
	}
	
	public static double findMaxCurrentOutput(double ms) {
		rollers.set(ControlMode.PercentOutput,1.0);
		double maxCurrent = 0;
		
		while (System.currentTimeMillis() < ms) {
			if (getCurrent() > maxCurrent) {
				maxCurrent = getTotalCurrent();	
			}
		}
		rollers.set(ControlMode.PercentOutput,0.0);
		return maxCurrent;
	}
	
	public static double findAverageCurrentOutput(double ms) {
		rollers.set(ControlMode.PercentOutput,1.0);
		double averageCurrent = 0;
		
		while (System.currentTimeMillis() < ms) {
			averageCurrent += getTotalCurrent();
		}
		rollers.set(ControlMode.PercentOutput,0.0);
		return averageCurrent/ms;
	}
	
}
