package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.Intake.*;
import static org.usfirst.frc.team1072.robot.Config.Intake.*;

import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

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
	private final VictorSPX leftRoller, rightRoller;
	/**
	 * Expand and contract the intake
	 */
	private final Solenoid leftSolenoid, rightSolenoid;
	
	/**
	 * Initialize the intake subsystem
	 */
	private Intake() {
		// Initialize hardware links
		leftRoller = new VictorSPX(LEFT_ROLLER);
		rightRoller = new VictorSPX(RIGHT_ROLLER);
		leftSolenoid = new Solenoid(LEFT_SOLENOID);
		rightSolenoid = new Solenoid(RIGHT_SOLENOID);
		set((victor) -> {
			victor.setNeutralMode(NEUTRAL_MODE);
		});
	}
	
	/**
	 * Runs a consumer on both motors
	 * 
	 * @param consumer
	 *            the function (lambda?) to run on them
	 */
	public void set(Consumer<VictorSPX> consumer) {
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
    public VictorSPX getLeftRoller()
    {
        return leftRoller;
    }

    /**
     * @return the rightRoller
     */
    public VictorSPX getRightRoller()
    {
        return rightRoller;
    }

    /**
     * @return the leftSolenoid
     */
    public Solenoid getLeftSolenoid()
    {
        return leftSolenoid;
    }

    /**
     * @return the rightSolenoid
     */
    public Solenoid getRightSolenoid()
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
