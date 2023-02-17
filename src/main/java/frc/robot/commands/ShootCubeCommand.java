package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.CubeAcquisition;

public class ShootCubeCommand extends CommandBase {
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();

    public ShootCubeCommand() {
        this.addRequirements(cubeShooter);
        this.addRequirements();
    }

    public boolean isFinished() {
        return false;
    }

    public void initialize() {

    }
}
