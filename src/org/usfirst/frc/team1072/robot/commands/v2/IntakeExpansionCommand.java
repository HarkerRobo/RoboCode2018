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
	
	public static final DoubleSolenoid.Value defaultLift = DoubleSolenoid.Value.kForward;
	
	public IntakeExpansionCommand() {
		requires(Robot.intake);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		if(false){//Robot.intake.getExpansion().get() != defaultLift) {
			System.out.println("current " + Robot.intake.getExpansion().get().toString());
			if(Robot.elevator.getMaster().getSelectedSensorPosition(0) > SmallRaiseCommand.DIST) {
				System.out.println("setting to default");
				Robot.intake.getExpansion().set(defaultLift);
			} else {
				System.out.println("raising");
				new SmallRaiseCommand().start();
			}
		} else  {
			System.out.println("setting to opposite");
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
