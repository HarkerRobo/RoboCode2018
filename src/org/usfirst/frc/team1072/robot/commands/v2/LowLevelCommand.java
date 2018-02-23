package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * @author joel
 *
 */
public class LowLevelCommand extends SetElevatorCommand {

	/**
	 * @param height
	 */
	public LowLevelCommand(double height) {
		super(height);
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1072.robot.commands.v2.SetElevatorCommand#initialize()
	 */
	@Override
	protected void initialize() {
		Robot.intake.getRaise().set(Value.kForward);
		super.initialize();
	}
	
}
