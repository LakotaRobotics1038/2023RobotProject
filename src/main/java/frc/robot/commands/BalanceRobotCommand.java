package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import frc.robot.constants.BalanceConstants;
import frc.robot.subsystems.DriveTrain;

public class BalanceRobotCommand extends PIDCommand {

    private static DriveTrain driveTrain = DriveTrain.getInstance();
    private Timer timer = new Timer();
    private double delayFinish = 0.5;

    public BalanceRobotCommand() {
        super(new PIDController(BalanceConstants.kP, BalanceConstants.kI, BalanceConstants.kD),
                driveTrain::getRoll,
                BalanceConstants.kSetpoint,
                output -> {
                    output = MathUtil.clamp(output, -BalanceConstants.kMaxSpeed, BalanceConstants.kMaxSpeed);
                    driveTrain.drive(output, 0, 0, true, false);
                },
                driveTrain);

        getController().disableContinuousInput();
        getController().setTolerance(BalanceConstants.kTolerance);
    }

    @Override
    public void execute() {
        super.execute();
        if (getController().atSetpoint()) {
            timer.start();
        } else if (!getController().atSetpoint()) {
            timer.stop();
            timer.reset();
        }
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint() && timer.get() > delayFinish;
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        timer.reset();
    }
}
