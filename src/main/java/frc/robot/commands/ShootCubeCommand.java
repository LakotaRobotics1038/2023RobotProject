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
        cubeAcquisition.setPosition(cubeAcquisition.getDownState());
    }

    public void execute() {
        cubeShooter.shootCube();
    }

    public void end() {

        cubeAcquisition.stopFeeder();
        cubeAcquisition.stopAcquisition();
        cubeShooter.getLeftShooterMotor().set(0);
        cubeShooter.getRightShooterMotor().set(0);
        cubeAcquisition.stopFeeder();
        cubeAcquisition.stopAcquisition();

    }
}
