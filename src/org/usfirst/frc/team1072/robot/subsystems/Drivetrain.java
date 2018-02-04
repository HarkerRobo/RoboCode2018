package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.Drivetrain.*;
import static org.usfirst.frc.team1072.robot.Config.Drivetrain.*;

import java.util.function.Consumer;

import org.usfirst.frc.team1072.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team1072.robot.commands.ManualDriveCommand;

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
	private final TalonSRX leftMaster, rightMaster;
	/**
	 * Ignore these motors, they will simply follow the master
	 */
	private final VictorSPX leftFollower, rightFollower;
	
	/**
	 * Initialize the drivetrain subsystem
	 */
	private Drivetrain() {
		// Initialize hardware links
		leftMaster = new TalonSRX(LEFT_TALON);
		rightMaster = new TalonSRX(RIGHT_TALON);
		leftFollower = new VictorSPX(LEFT_VICTOR);
		rightFollower = new VictorSPX(RIGHT_VICTOR);
		//invert
		leftMaster.setInverted(true);
		leftFollower.setInverted(true);
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
			//Velocity closed loop
			talon.configNominalOutputForward(0, 0);
			talon.configNominalOutputReverse(0, 0);
			talon.configPeakOutputForward(1, 0);
			talon.configPeakOutputReverse(-1, 0);
			talon.config_kF(0, 0.1097 + 0.075, 0);
			talon.config_kP(0, 0.113333 + 0.05, 0);
			talon.config_kI(0, 0, 0);
			talon.config_kD(0, 0, 0);
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
