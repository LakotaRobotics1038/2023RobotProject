package frc.robot.commands;

import frc.robot.constants.BalanceConstants;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.math.controller.PIDController;

public class BalanceRobotCommand extends PIDCommand {

    private static DriveTrain driveTrain = DriveTrain.getInstance();

    public BalanceRobotCommand() {
        super(
                new PIDController(
                        BalanceConstants.kBalanceP,
                        BalanceConstants.kBalanceI,
                        BalanceConstants.kBalanceD),
                driveTrain::getHeading,
                BalanceConstants.kBalanceSetpoint,
                speed -> driveTrain.drive(speed, speed, speed, false),
                driveTrain);
    }

    public boolean isFinished() {
        return getController().atSetpoint();
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
