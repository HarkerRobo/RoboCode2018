package org.usfirst.frc.team1072.robot.commands.intake;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.commands.elevator.SmallRaiseCommand;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class IntakeExpansionCommand extends Command {
	
	public IntakeExpansionCommand() {
		requires(Robot.intake);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		if(Robot.intake.isOpen()) {
			if(Robot.elevator.getMaster().getSelectedSensorPosition(0) > SmallRaiseCommand.DIST) {
				Robot.intake.close();
			} else {
				new SmallRaiseCommand().start();
			}
		} else  {
			Robot.intake.open();
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
