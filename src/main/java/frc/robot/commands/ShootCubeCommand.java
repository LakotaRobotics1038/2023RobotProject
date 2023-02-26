package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import frc.robot.subsystems.CubeAcquisition;

public class ShootCubeCommand extends CommandBase {
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();
    private CubeShooterSetpoints setpoint;

    public ShootCubeCommand(CubeShooterSetpoints setpoint) {
        this.addRequirements(cubeShooter, cubeAcquisition);
        this.setpoint = setpoint;
    }

    @Override
    public void initialize() {
        cubeAcquisition.setPosition(AcquisitionStates.Down);
        cubeShooter.enable();
        cubeShooter.setSetpoint(setpoint);
    }

    @Override
    public void execute() {
        if (cubeShooter.onTarget()) {
            cubeShooter.feedOut();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cubeShooter.disable();
        cubeShooter.stopFeeder();
    }
}
