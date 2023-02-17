package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.CubeAcquisition;

public class ShootCubeCommand extends CommandBase {
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();

    public ShootCubeCommand() {
        this.addRequirements(cubeShooter);
        this.addRequirements(cubeAcquisition);
    }

    public boolean isFinished() {
        return false;
    }

    public void initialize() {
        CubeAcquisition.setPosition(Down);
    }

    public void execute() {
        CubeShooter.shootCube();
    }

    public void end() {
        CubeAcquisition.stopFeeder();
        CubeAcquisition.stopAcquisition();
        cubeShooter.leftShooterMotor.set(0);
        cubeShooter.rightShooterMotor.set(0);
        cubeAcquisition.feederMotor.set(0);
        cubeAcquisition.acquisitionMotor.set(0);
    }
}
