package org.usfirst.frc.team1072.robot.commands.auton;

import java.io.FileNotFoundException;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.Slot;
import org.usfirst.frc.team1072.robot.commands.elevator.SlowRaiseCommand;
import org.usfirst.frc.team1072.robot.commands.elevator.ZeroElevatorCommand;
import org.usfirst.frc.team1072.robot.paths.Path;
import org.usfirst.frc.team1072.robot.profiling.LocalMotionBuilder;
import org.usfirst.frc.team1072.robot.profiling.LocalMotionCommand;
import org.usfirst.frc.team1072.robot.profiling.MotionProfileBuilder;
import org.usfirst.frc.team1072.robot.profiling.MotionProfileCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.TimedCommand;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class DoubleSwitchCommand extends CommandGroup {

    public DoubleSwitchCommand() {
        addSequential(new ZeroElevatorCommand());
		LocalMotionCommand toScale = build("MidToLeftSwitch");
		addParallel(new SlowRaiseCommand(2.4, toScale.getGroups()[0].getPath().getPath().length * 10));
		addSequential(toScale);
        addSequential(new AutonomousReleaseCommand());
        addSequential(new InstantCommand() {
        		@Override
        		public void initialize() {
        			Robot.intake.open();
        			Robot.intake.lower();
        		}
        });
        LocalMotionCommand scaleToSide = build("LeftSwitchToStop1");
        addParallel(new SlowRaiseCommand(0.0, scaleToSide.getGroups()[0].getPath().getPath().length * 10));
        addSequential(scaleToSide);
        addSequential(new InstantCommand() {
	    		@Override
	    		public void initialize() {
	    			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, -0.7);
	    			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, -0.6);
	    		}
	    });
        LocalMotionCommand sideToCube = build("Stop1ToCube1");
//        addParallel(new SlowRaiseCommand(0.0, sideToCube.getGroups()[0].getTrajectory().length() * 10));
        addSequential(sideToCube);
        LocalMotionCommand backWithCube = build("Cube1ToStop1");
        addSequential(backWithCube);
        addSequential(new InstantCommand() {
	    		@Override
	    		public void initialize() {
	    			Robot.intake.close();
	    			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, 0.0);
	    			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, 0.0);
	    		}
	    });
		LocalMotionCommand secondCubePlace = build("Stop1ToLeftSwitch");
		addParallel(new SlowRaiseCommand(2.7, secondCubePlace.getGroups()[0].getPath().getPath().length * 10));
		addSequential(secondCubePlace);
        addSequential(new InstantCommand() {
	    		@Override
	    		public void initialize() {
	    			Robot.intake.open();
	    		}
	    });
        addSequential(new AutonomousReleaseCommand());
    }
    
    private static LocalMotionCommand build(String name) {
		String leftPath = name + "_left_detailed.csv";
		String rightPath = name + "_right_detailed.csv";
		try {
			Path left = Path.paths.get(leftPath);
			Path right = Path.paths.get(rightPath);
			return new LocalMotionBuilder(10, Robot.drivetrain)
					.group(left, Slot.LEFT_MOTION_PROFILE.getSlot(), 38.0 / 36.0 * 4.0 * Math.PI
							/ 12.0/* 0.31918 */, 1.0/*0.945*/, Robot.drivetrain.getLeft())
					.group(right, Slot.RIGHT_MOTION_PROFILE.getSlot(), 38.0 / 36.0 * 4.0 * Math.PI
							/ 12.0/* 0.31918 */, 1.0, Robot.drivetrain.getLeft())
					.build();
		} catch(Exception e) {

			e.printStackTrace();
		}
		return null;
    }
    
    class AutonomousReleaseCommand extends TimedCommand {

		/**
		 * @param timeout
		 */
		public AutonomousReleaseCommand() {
			super(1.3);
			requires(Robot.intake);
		}
    		
		@Override
		public void initialize() {
			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, 0.8);
			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, 0.8);
		}
		
		@Override
		public void end() {
			new AutonomousReleaseCommand();
			Robot.intake.getLeftRoller().set(ControlMode.Disabled, 0);
			Robot.intake.getLeftRoller().set(ControlMode.Disabled, 0);
		}
    }
}
