package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.Intake.*;
import static org.usfirst.frc.team1072.robot.Config.Intake.*;

import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Intakes cubes in front of the robot, then ejects them onto the switch or the
 * scale
 */
public class Intake extends Subsystem {
	/**
	 * Singleton instance
	 */
	private static Intake instance;
	/**
	 * Control the intake wheels
	 */
	private final TalonSRX leftRoller, rightRoller;
	/**
	 * Expand and contract the intake
	 */
	private DoubleSolenoid leftSolenoid, rightSolenoid;
	/**
	 * Are all of the parts of this intake functioning?
	 */
	private boolean solenoidStatus;
	
	/**
	 * Initialize the intake subsystem
	 */
	private Intake() {
		// Initialize hardware links
		leftRoller = new TalonSRX(LEFT_ROLLER);
		rightRoller = new TalonSRX(RIGHT_ROLLER);
		try {
			leftSolenoid = new DoubleSolenoid(LEFT_SOLENOID_A, LEFT_SOLENOID_B);
			rightSolenoid = new DoubleSolenoid(RIGHT_SOLENOID_A, RIGHT_SOLENOID_B);
			solenoidStatus = true;
		} catch (Exception e) {
			System.err.println("Could not find intake solenoids");
		}
		
		leftRoller.setInverted(true);
		rightRoller.setInverted(false);
		
		set((talon) -> {
			talon.setNeutralMode(NEUTRAL_MODE);
			talon.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT);
			talon.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT);
			talon.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT);
			talon.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
			talon.setNeutralMode(NEUTRAL_MODE);
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
	
	/**
     * @return the leftRoller
     */
    public TalonSRX getLeftRoller()
    {
        return leftRoller;
    }

    /**
     * @return the rightRoller
     */
    public TalonSRX getRightRoller()
    {
        return rightRoller;
    }

    /**
     * @return the leftSolenoid
     */
    public DoubleSolenoid getLeftSolenoid()
    {
        return leftSolenoid;
    }

    /**
     * @return the rightSolenoid
     */
    public DoubleSolenoid getRightSolenoid()
    {
        return rightSolenoid;
    }

    /**
	 * Reference to the singleton instance
	 * 
	 * @return the singleton instance
	 */
	public static Intake getInstance() {
		if(instance == null) {
			instance = new Intake();
		}
		return instance;
	}
}
