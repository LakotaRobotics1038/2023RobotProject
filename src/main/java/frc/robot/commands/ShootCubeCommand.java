package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import frc.robot.subsystems.CubeAcquisition;

public class ShootCubeCommand extends CommandBase {
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();
    private CubeShooterSetpoints setpoint;
    private double secondsToShoot = 0.0;
    private boolean overrideFeedOut = false;
    private double startTime = 0.0;

    public ShootCubeCommand(CubeShooterSetpoints setpoint) {
        this.addRequirements(cubeShooter, cubeAcquisition);
        this.setpoint = setpoint;
    }

    public ShootCubeCommand(CubeShooterSetpoints setpoint, double secondsToShoot) {
        this.addRequirements(cubeShooter, cubeAcquisition);
        this.setpoint = setpoint;
        this.secondsToShoot = secondsToShoot;
    }

    @Override
    public void initialize() {
        this.overrideFeedOut = false;
        cubeAcquisition.setPosition(AcquisitionStates.Down);
        cubeShooter.enable(setpoint);
    }

    @Override
    public void execute() {
        if (overrideFeedOut || cubeShooter.onTarget()) {
            if (this.startTime == 0.0 && this.secondsToShoot != 0) {
                this.startTime = Timer.getFPGATimestamp();
            }
            cubeShooter.feedOut();
        }
    }

    public void overrideFeed() {
        this.overrideFeedOut = true;
    }

    @Override
    public boolean isFinished() {
        if (this.startTime != 0.0 && this.secondsToShoot != 0) {
            return this.startTime + this.secondsToShoot < Timer.getFPGATimestamp();
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cubeShooter.disable();
        cubeShooter.stopFeeder();
    }
}
