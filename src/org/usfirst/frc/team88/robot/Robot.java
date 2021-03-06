
package org.usfirst.frc.team88.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team88.robot.commands.AutoBin;
import org.usfirst.frc.team88.robot.commands.AutoBinAndTote;
import org.usfirst.frc.team88.robot.commands.AutoBinBackup;
import org.usfirst.frc.team88.robot.commands.AutoBinForward;
import org.usfirst.frc.team88.robot.commands.AutoDrive;
import org.usfirst.frc.team88.robot.commands.AutoDriveTurnLeft90;
import org.usfirst.frc.team88.robot.commands.AutoDriveTurnRight90;
import org.usfirst.frc.team88.robot.commands.AutoGrabFromLandfill;
import org.usfirst.frc.team88.robot.commands.AutoNothing;
import org.usfirst.frc.team88.robot.commands.AutoOneBinandTwoTote;
import org.usfirst.frc.team88.robot.commands.AutoTest;
import org.usfirst.frc.team88.robot.commands.AutoThreeToteOhYeah;
import org.usfirst.frc.team88.robot.commands.AutoToteLeftSide;
import org.usfirst.frc.team88.robot.commands.AutoToteRightSide;
import org.usfirst.frc.team88.robot.commands.DriveStraight;
import org.usfirst.frc.team88.robot.commands.DriveTurnLeft10;
import org.usfirst.frc.team88.robot.commands.DriveTurnLeft90;
import org.usfirst.frc.team88.robot.commands.DriveTurnLeft90NavX;
import org.usfirst.frc.team88.robot.commands.DriveTurnRight90;
import org.usfirst.frc.team88.robot.commands.DriveTurnRight90NavX;
import org.usfirst.frc.team88.robot.commands.LiftDownOnePosition;
import org.usfirst.frc.team88.robot.commands.LiftToPosition;
import org.usfirst.frc.team88.robot.commands.LiftUpOnePosition;
import org.usfirst.frc.team88.robot.subsystems.Drive;
import org.usfirst.frc.team88.robot.subsystems.Arminator;
import org.usfirst.frc.team88.robot.subsystems.Lift;
import org.usfirst.frc.team88.robot.subsystems.Lights;
import org.usfirst.frc.team88.robot.subsystems.Schtick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drive drive;
	public static Lift lift;
	public static Arminator arminator;
	public static Schtick schtick;
	public static OI oi;
	public static Lights lights;

	private static SendableChooser autoSelector;
	private static Command autoCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		drive = new Drive();
		lift = new Lift();
		arminator = new Arminator();
		schtick = new Schtick();
		lights = new Lights();
		
        // do this last so OI can reference Robot subsystems
		oi = new OI();

		// set up the SmartDashboard
		// set up SendableChooser to select autonomous mode
		autoSelector = new SendableChooser();
		autoSelector.addDefault("Tote Left Side", new AutoToteLeftSide());
		autoSelector.addObject("Tote Right Side", new AutoToteRightSide());
		autoSelector.addObject("Bin", new AutoBin());
		autoSelector.addObject("Drive 3.4", new DriveStraight(3.4));
		autoSelector.addObject("Drive 2.0", new DriveStraight(2.0));
		autoSelector.addObject("Do Nothing", new AutoNothing());
		
		/*
		autoSelector.addObject("Drive straight Turn left 90", new AutoDriveTurnLeft90());
		autoSelector.addObject("Drive straight Turn right 90", new AutoDriveTurnRight90());
		autoSelector.addObject("Bin Forward", new AutoBinForward());
		autoSelector.addObject("Bin Backup", new AutoBinBackup());
		autoSelector.addObject("Bin and Tote", new AutoBinAndTote());
		autoSelector.addObject("Bin and Two Totes Maybe", new AutoOneBinandTwoTote());
		autoSelector.addObject("Three Tote", new AutoThreeToteOhYeah());
		*/
		
		SmartDashboard.putData("Autonomous Mode",autoSelector);

		SmartDashboard.putData(lights);
		//SmartDashboard.
    	// Testing commands for auto drive
		SmartDashboard.putData("Forward 1m", new DriveStraight(1.0));
    	SmartDashboard.putData("Forward 2m",new DriveStraight(2.0));
    	SmartDashboard.putData("Forward 3m",new DriveStraight(3.0));
    	SmartDashboard.putData("Left 90",new DriveTurnLeft90());
    	SmartDashboard.putData("Left 90 (NavX)",new DriveTurnLeft90NavX());
    	SmartDashboard.putData("Left 10",new DriveTurnLeft10());
    	SmartDashboard.putData("Right 90",new DriveTurnRight90());
    	SmartDashboard.putData("Right 90 (NavX)",new DriveTurnRight90NavX());

    	// Testing commands for auto lift
    	SmartDashboard.putData("Lift Down One",new LiftDownOnePosition());
    	SmartDashboard.putData("Lift Bottom",new LiftToPosition(Lift.POS_BOTTOM));
    	SmartDashboard.putData("Lift Travel",new LiftToPosition(Lift.POS_TRAVEL));
    	SmartDashboard.putData("Lift One",new LiftToPosition(Lift.POS_ONETOTE));
    	SmartDashboard.putData("Lift Two",new LiftToPosition(Lift.POS_TWOTOTES));
    	SmartDashboard.putData("Lift Three",new LiftToPosition(Lift.POS_THREETOTES));
    	SmartDashboard.putData("Lift Top",new LiftToPosition(Lift.POS_TOP));
    	SmartDashboard.putData("Lift Up One",new LiftUpOnePosition());

    	// Testing auto command groups
    	SmartDashboard.putData("Auto Test", new AutoTest());
    	SmartDashboard.putData("Auto Bin and Tote", new AutoBinAndTote());
    	SmartDashboard.putData("Auto Bin Only", new AutoBin());
    	SmartDashboard.putData("Auto Tote Left Side", new AutoToteLeftSide());
    	SmartDashboard.putData("Auto Three Tote", new AutoThreeToteOhYeah());
    	SmartDashboard.putData("Auto Straight turn 90 left",new AutoDriveTurnLeft90());
    	SmartDashboard.putData("Auto Straight turn 90 right",new AutoDriveTurnRight90());
    	SmartDashboard.putData("Bin and Two Totes Maybe",new AutoOneBinandTwoTote());
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command
    	autoCommand = (Command) autoSelector.getSelected();

    	if ((!drive.isNavXOn()) || (autoCommand == null)) {
    		autoCommand = new AutoNothing();
        }
    	
    	autoCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autoCommand != null) autoCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
