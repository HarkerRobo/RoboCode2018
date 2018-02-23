package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class IntakeExpansionCommand extends Command {
	
	public static final DoubleSolenoid.Value defaultLift = DoubleSolenoid.Value.kReverse;
	
	public IntakeExpansionCommand() {
		requires(Robot.intake);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		if(Robot.intake.getExpansion().get() != defaultLift) {
			if(true || Robot.elevator.getMaster().getSelectedSensorPosition(0) > SmallRaiseCommand.DIST) {
				Robot.intake.getExpansion().set(defaultLift);
			} else {
				new SmallRaiseCommand().start();
			}
		} else  {
			Robot.intake.getExpansion().set(defaultLift == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
		}
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.command.Command#isFinished()
	 */
	@Override
	protected boolean isFinished() {
		return true;
	}
}
