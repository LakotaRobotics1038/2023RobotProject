package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.constants.CubeShooterConstants;
import frc.robot.subsystems.CubeAcquisition;

public class ShootCubeCommand extends PIDCommand {
    private static CubeShooter cubeShooter = CubeShooter.getInstance();
    private static CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();

    public ShootCubeCommand() {
        super(
                new PIDController(
                        CubeShooterConstants.kShooterP,
                        CubeShooterConstants.kShooterI,
                        CubeShooterConstants.kShooterD),
                cubeShooter::getShooterVelocity,
                CubeShooterConstants.kShooterSetpoint,
                power -> cubeShooter.setShooterSpeed(power),
                cubeShooter,
                cubeAcquisition);
    }

    @Override
    public void initialize() {
        cubeAcquisition.setPosition(AcquisitionStates.Down);
    }

    @Override
    public void execute() {
        if (getController().atSetpoint()) {
            cubeShooter.feedOut();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cubeShooter.stopMotor();
        cubeShooter.stopFeeder();
    }
}
