package org.usfirst.frc.team1072.robot.subsystems;

import static org.usfirst.frc.team1072.robot.RobotMap.Elevator.*;
import static org.usfirst.frc.team1072.robot.Config.Elevator.*;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	
	private TalonSRX master;
	private VictorSPX follower1, follower2, follower3;
	
	/**
	 * Initialize the elevator subsystem
	 */
	public Elevator() {
		master = new TalonSRX(TALON);
		follower1 = new VictorSPX(VICTOR_1);
		follower2 = new VictorSPX(VICTOR_2);
		follower3 = new VictorSPX(VICTOR_3);
		follower1.follow(master);
		follower2.follow(master);
		follower3.follow(master);
		master.setNeutralMode(NEUTRAL_MODE);
		master.configContinuousCurrentLimit(CONTINUOUS_CURRENT_LIMIT, TIMEOUT);
		master.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, TIMEOUT);
		master.configPeakCurrentDuration(PEAK_CURRENT_DURATION, TIMEOUT);
		master.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		master.configForwardLimitSwitchSource(FORWARD_SWITCH, FORWARD_NORMAL, TIMEOUT);
		master.configReverseLimitSwitchSource(REVERSE_SWITCH, REVERSE_NORMAL, TIMEOUT);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

