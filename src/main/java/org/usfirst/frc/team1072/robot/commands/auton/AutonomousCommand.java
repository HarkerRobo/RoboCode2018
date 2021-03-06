package org.usfirst.frc.team1072.robot.commands.auton;

import org.usfirst.frc.team1072.robot.Config.Elevator;
import org.usfirst.frc.team1072.robot.commands.elevator.SlowRaiseCommand;
import org.usfirst.frc.team1072.robot.commands.elevator.ZeroElevatorCommand;
import org.usfirst.frc.team1072.robot.commands.util.WaitCommand;
import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.Slot;
import org.usfirst.frc.team1072.robot.profiling.MotionProfileCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {

    public AutonomousCommand(MotionProfileCommand mainProfile, double height, double delay) {
//        addParallel(new SetElevatorCommand(height));
    		addSequential(new ZeroElevatorCommand());
    		addSequential(new WaitCommand(delay));
    		addParallel(new SlowRaiseCommand(height, mainProfile.getGroups()[0].getTrajectory().length() * 10));
//    		Robot.elevator.getMaster().selectProfileSlot(Slot.ELEVATOR_MOTION_MAGIC.getSlot(), 0);
//    		Robot.elevator.getMaster().set(ControlMode.MotionMagic, height * Elevator.ENCODERTOFEET, DemandType.ArbitraryFeedForward, Robot.IS_COMP ? 0.11: 0.08);
        addSequential(mainProfile);
//        addSequential(new SetSolenoidCommand(Robot.intake.getRaise(), Value.kForward));
        if(height != 0) {
	        addSequential(new AutonomousReleaseCommand());
	        addSequential(new InstantCommand() {
	        		@Override
	        		public void initialize() {
	        			Robot.intake.open();
	        		}
	        });
        }
    }
    
    class AutonomousReleaseCommand extends TimedCommand {

		/**
		 * @param timeout
		 */
		public AutonomousReleaseCommand() {
			super(2.0);
			requires(Robot.intake);
		}
    		
		@Override
		public void initialize() {
			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, 0.5);
			Robot.intake.getRightRoller().set(ControlMode.PercentOutput, 0.5);
		}
		
		@Override
		public void end() {
			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, 0);
			Robot.intake.getRightRoller().set(ControlMode.PercentOutput, 0);
		}
		
		@Override
		public void interrupted(){
			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, 0);
			Robot.intake.getRightRoller().set(ControlMode.PercentOutput, 0);
		}
    }
}
