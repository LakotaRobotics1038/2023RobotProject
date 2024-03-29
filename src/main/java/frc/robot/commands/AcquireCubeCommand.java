package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.CubeAcquisition;
import frc.robot.subsystems.CubeShooter;

public class AcquireCubeCommand extends CommandBase {
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();
    private CubeShooter cubeShooter = CubeShooter.getInstance();

    public AcquireCubeCommand() {
        addRequirements(cubeShooter, cubeAcquisition);
    }

    @Override
    public void execute() {
        cubeAcquisition.acquire();
        cubeShooter.feedIn();
        cubeShooter.loadCube();
    }

    @Override
    public boolean isFinished() {
        return cubeShooter.getLimit();
    }

    @Override
    public void end(boolean interrupted) {
        cubeAcquisition.stop();
        cubeShooter.stopFeeder();
        cubeShooter.stopShooter();
    }
}
