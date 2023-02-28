package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeAcquisition;

public class ManualShootCubeCommand extends CommandBase {
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();
    private boolean feedOut = false;

    public ManualShootCubeCommand() {
        this.addRequirements(cubeShooter, cubeAcquisition);
    }

    @Override
    public void initialize() {
        this.feedOut = false;
        cubeAcquisition.setPosition(AcquisitionStates.Down);
    }

    @Override
    public void execute() {
        cubeShooter.run();
        if (feedOut) {
            cubeShooter.feedOut();
        }
    }

    public void feedOut() {
        this.feedOut = true;
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
