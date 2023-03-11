package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import frc.robot.constants.BalanceConstants;
import frc.robot.subsystems.DriveTrain;

public class BalanceRobotCommand extends PIDCommand {

    private static DriveTrain driveTrain = DriveTrain.getInstance();

    public BalanceRobotCommand() {
        super(new PIDController(BalanceConstants.kP, BalanceConstants.kI, BalanceConstants.kD),
                driveTrain::getRoll,
                BalanceConstants.kSetpoint,
                output -> {
                    output = MathUtil.clamp(output, -BalanceConstants.kMaxSpeed, BalanceConstants.kMaxSpeed);
                    driveTrain.drive(output, 0, 0, true);
                },
                driveTrain);

        getController().disableContinuousInput();
        getController().setTolerance(BalanceConstants.kTolerance);
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}
