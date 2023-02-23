package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class BalanceRobotCommand extends CommandBase {

    private DriveTrain driveTrain = DriveTrain.getInstance();
    private PIDController pidController = new PIDController(0, 0, 0);

    public BalanceRobotCommand() {
        this.addRequirements(driveTrain);
    }

    public boolean isFinished() {
        boolean isPIDSet = pidController.atSetpoint();
        return isPIDSet;
    }

    public void execute() {
        if (driveTrain.getPitch() != 10.0) {
            if (driveTrain.getPitch() < 0) {
                driveTrain.drive(0, 0, 0, isFinished());
            } else {
                driveTrain.drive(0, 0, 0, isFinished());
            }
        }
    }
}
