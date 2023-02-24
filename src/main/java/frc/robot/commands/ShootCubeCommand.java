package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeAcquisition;

public class ShootCubeCommand extends CommandBase {
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();

    public ShootCubeCommand() {
        this.addRequirements(cubeAcquisition, cubeShooter);
    }

    @Override
    public void initialize() {
        cubeAcquisition.setPosition(AcquisitionStates.Down);
    }

    @Override
    public void execute() {
        cubeShooter.shootCube();
        cubeAcquisition.feedOut();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cubeShooter.stopMotor();
        cubeAcquisition.stopFeeder();
    }
}
