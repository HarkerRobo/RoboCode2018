package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.profiling.MotionProfileCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {

    public AutonomousCommand(MotionProfileCommand mainProfile, SetElevatorCommand elevate) {
        addSequential(mainProfile);
        addSequential(elevate);
        addSequential(new SetSolenoidCommand(Robot.intake.getRaise(), Value.kForward));
        addSequential(new AutonomousReleaseCommand());
    }
    
    class AutonomousReleaseCommand extends TimedCommand {

		/**
		 * @param timeout
		 */
		public AutonomousReleaseCommand() {
			super(0.5);
			requires(Robot.intake);
		}
    		
		@Override
		public void initialize() {
			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, -0.4);
			Robot.intake.getRightRoller().set(ControlMode.PercentOutput, -0.4);
		}
		
		@Override
		public void end() {
			Robot.intake.getLeftRoller().set(ControlMode.Disabled, 0);
			Robot.intake.getRightRoller().set(ControlMode.Disabled, 0);
		}
    }
}
