package org.usfirst.frc.team1072.robot.commands; 
 
import org.usfirst.frc.team1072.robot.Robot; 
 
import com.ctre.phoenix.motion.MotionProfileStatus; 
 
import edu.wpi.first.wpilibj.command.Command; 
 
/** 
 * 
 */ 
public class CANTestingCommand extends Command { 
 
    public CANTestingCommand() { 
        // Use requires() here to declare subsystem dependencies 
        // eg. requires(chassis); 
    } 
 
    // Called just before this Command runs the first time 
    protected void initialize() { 
    } 
 
    // Called repeatedly when this Command is scheduled to run 
    protected void execute() { 
        MotionProfileStatus mps = new MotionProfileStatus(); 
//        Robot.drivetrain.log(Robot.drivetrain.getLeft().getMotionProfileStatus(mps), "Could not load status"); 
//    Robot.drivetrain.log(Robot.drivetrain.getLeft().clearMotionProfileHasUnderrun(1000), "Could not clear underrun"); 
    Robot.drivetrain.log(Robot.drivetrain.getRight().clearMotionProfileTrajectories(), "Could not clear trajectories"); 
//    Robot.drivetrain.log(Robot.drivetrain.getLeft().setIntegralAccumulator(0, 0, 1000), "Could not clear accumulator"); 
    Robot.drivetrain.log(Robot.drivetrain.getRight().clearStickyFaults(1000), "Could not clear stickies"); 
    Robot.drivetrain.log(Robot.drivetrain.getRight().config_kD(0, Math.random(), 1000), "Could not configure"); 
    } 
 
    // Make this return true when this Command no longer needs to run execute() 
    protected boolean isFinished() { 
        return false; 
    } 
 
    // Called once after isFinished returns true 
    protected void end() { 
    } 
 
    // Called when another command which requires one or more of the same 
    // subsystems is scheduled to run 
    protected void interrupted() { 
    } 
} 