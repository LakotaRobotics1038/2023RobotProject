package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeAcquisition;

public class ShootCubeCommand extends CommandBase {
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();

    public double cubeShooterSpeed = 0;

    public ShootCubeCommand() {
        this.addRequirements(cubeAcquisition, cubeShooter);
    }

    public boolean isFinished() {
        return false;
    }

    public void initialize() {
        cubeAcquisition.setPosition(AcquisitionStates.Down);
    }

    public void execute() {
        cubeShooter.shootCube();
    }

    public void end() {
        cubeAcquisition.stopFeeder();
        cubeAcquisition.stopAcquisition();
    }
}
