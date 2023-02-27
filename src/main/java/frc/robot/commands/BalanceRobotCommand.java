package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveTrain;

public class BalanceRobotCommand extends PIDCommand {

    private static DriveTrain driveTrain = DriveTrain.getInstance();

    public BalanceRobotCommand() {
        super(new PIDController(0, 0, 0),
                driveTrain::getPitch,
                0.0,
                output -> driveTrain.drive(output, 0, 0, true),
                driveTrain);

        getController().disableContinuousInput();
        getController().setTolerance(0);
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}
