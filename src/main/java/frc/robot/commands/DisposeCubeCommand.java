package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.CubeAcquisition;
import frc.robot.subsystems.CubeShooter;

public class DisposeCubeCommand extends CommandBase {
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();
    private CubeShooter cubeShooter = CubeShooter.getInstance();

    public DisposeCubeCommand() {
        addRequirements(cubeShooter, cubeAcquisition);
    }

    @Override
    public void execute() {
        cubeAcquisition.dispose();
        cubeShooter.feedOut();
        cubeShooter.unloadCube();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cubeAcquisition.stop();
        cubeShooter.stopFeeder();
        cubeShooter.stopShooter();
    }
}
