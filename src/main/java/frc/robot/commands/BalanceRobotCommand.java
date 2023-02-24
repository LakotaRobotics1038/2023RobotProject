package frc.robot.commands;

import frc.robot.constants.BalanceConstants;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class BalanceRobotCommand extends CommandBase {

    private DriveTrain driveTrain = DriveTrain.getInstance();

    public BalanceRobotCommand() {
        this.addRequirements(driveTrain);
    }

    public boolean isFinished() {
        return BalanceConstants.pidController.atSetpoint();
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
