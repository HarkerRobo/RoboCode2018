/*----------------------------------------------------------------------------*/
/*
 * Copyright (c) 2017-2018 FIRST. All Rights Reserved. Open Source Software -
 * may be modified and shared by FRC teams. The code must be accompanied by the
 * FIRST BSD license file in the root directory of the project.
 */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1072.robot;

import java.io.File;
import java.util.Arrays;

import org.usfirst.frc.team1072.harkerrobolib.wrappers.GamepadWrapper;
import org.usfirst.frc.team1072.robot.commands.ClosedLoopCommand;
import org.usfirst.frc.team1072.robot.commands.DriveStraight;
import org.usfirst.frc.team1072.robot.commands.LoadArrayProfile;
import org.usfirst.frc.team1072.robot.commands.LoadMotionProfile;
import org.usfirst.frc.team1072.robot.commands.RunMotionProfile;
import org.usfirst.frc.team1072.robot.profiling.MotionProfileBuilder;
import org.usfirst.frc.team1072.robot.profiling.MotionProfileCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.
	
	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:
	
	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());
	
	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());
	
	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	public static final GamepadWrapper gamepad = new GamepadWrapper(0);
	
	public static void initializeCommandBindings() {
		// gamepad.getButtonBumperLeft().whenPressed(new TestIntakeCommand());
		// gamepad.getButtonBumperRight().whenPressed(new EjectCommand());
		// gamepad.getButtonA().whenPressed(new SlowRaiseCommand(3.0));
		// gamepad.getButtonB().whenPressed(new ClosedLoopCommand(1920.0));
		// gamepad.getButtonX().whenPressed(new ClosedLoopCommand(-3400.0));
//		Trajectory traj = Pathfinder.readFromCSV(new File("/home/lvuser/path.csv"));
		System.out.println("Reading trajectories");
		Trajectory left = Pathfinder.readFromCSV(new File("/home/lvuser/leftPath2.csv")),
				right = Pathfinder.readFromCSV(new File("/home/lvuser/rightPath2.csv"));
		System.out.println("Read trajectories");
//		gamepad.getButtonY().whenPressed(new MotionProfileBuilder(5, Robot.drivetrain)
//				.group(left, 1, 5.876, 0.3, 0.0, 0.0, 0.00, 4096.0, 4.0 * Math.PI / 12.0/*0.31918*/, Robot.drivetrain.getLeft())
//				.group(right, 1, 5.876, 0.3, 0.0, 0.0, 0.00, 4096.0, 4.0 * Math.PI / 12.0/*0.31918*/, Robot.drivetrain.getRight()).build());
		gamepad.getButtonA().whenPressed(new DriveStraight());
		gamepad.getButtonB().whenPressed(new ClosedLoopCommand(2000));
		System.out.println("Built command");
		// gamepad.getButtonY().whenPressed(new
		// LoadMotionProfile(Robot.gearIntake.getOrientation(), traj, 10, 2, new
		// RunMotionProfile(Robot.gearIntake.getOrientation(), 10)::start));
		// gamepad.getButtonY().whenPressed(new
		// MotionProfileCommand(Robot.gearIntake.getOrientation(), traj,
		// MPSettings.ORIENTATION, 10));
		// gamepad.getButtonY().whenPressed(new PathfinderCommand(traj));
		// gamepad.getButtonY().whenPressed(new IntakeMotionProfileCommand());
	}
}
