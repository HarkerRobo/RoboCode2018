package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.Elevator.*;
import static org.usfirst.frc.team1072.robot.Config.Elevator.*;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Two stage elevator to lift power cubes from the floor to the scale
 */
public class Elevator extends Subsystem {
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
		master.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT);
		master.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT);
		master.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT);
		master.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		master.configForwardLimitSwitchSource(FORWARD_SWITCH, FORWARD_NORMAL, TIMEOUT);
		master.configReverseLimitSwitchSource(REVERSE_SWITCH, REVERSE_NORMAL, TIMEOUT);
		master.configSelectedFeedbackSensor(ENCODER_MODE, ENCODER, TIMEOUT);
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	/**
     * @return the master talon in the gearbox
     */
    public TalonSRX getMaster()
    {
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
}
