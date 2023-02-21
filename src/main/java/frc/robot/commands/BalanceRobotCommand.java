package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class BalanceRobotCommand extends CommandBase {

    private DriveTrain driveTrain = DriveTrain.getInstance();

    public BalanceRobotCommand() {
        this.addRequirements(driveTrain);
    }

    public boolean isFinished() {
        boolean isPIDSet = false;

        // Add code to check if PID is at a setPoint.
        // and set booolean to true if so
        // What is setPoint?

        return isPIDSet;
    }

    public void execute() {
        // Function to use a PID to balance the Robots
    }
}
