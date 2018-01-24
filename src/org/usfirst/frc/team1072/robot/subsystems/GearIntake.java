package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.GearIntake.*;
import static org.usfirst.frc.team1072.robot.Config.GearIntake.*;

import java.util.function.Consumer;

import org.usfirst.frc.team1072.robot.commands.SlowRaiseCommand;
import org.usfirst.frc.team1072.robot.commands.TestIntakeCommand;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		rollers.setInverted(true);
		orientation = new TalonSRX(ORIENTATION);
		orientation.setInverted(true);
		orientation.setSensorPhase(true);
		orientation.config_kP(0, UP_P, TIMEOUT);
		orientation.config_kI(0, UP_I, TIMEOUT);
		orientation.config_kD(0, UP_D, TIMEOUT);
		orientation.config_kF(0, UP_F, TIMEOUT);
		orientation.configAllowableClosedloopError(0, 20, 0);
		orientation.config_kP(1, DOWN_P, TIMEOUT);
		orientation.config_kI(1, DOWN_I, TIMEOUT);
		orientation.config_kD(1, DOWN_D, TIMEOUT);
		orientation.config_kF(1, DOWN_F, TIMEOUT);
		orientation.configAllowableClosedloopError(1, 20, 0);
		set((talon) -> {
			talon.setNeutralMode(NEUTRAL_MODE);
//			talon.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT);
//			talon.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT);
//			talon.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT);
//			talon.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		});
	     orientation.configSelectedFeedbackSensor(ENCODER_MODE, 0, TIMEOUT);
	}
	
	public TalonSRX getRollers() {
	    return rollers;
	}
	
	public TalonSRX getOrientation() {
	    return orientation;
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
		 //setDefaultCommand(new TestIntakeCommand());
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
}
