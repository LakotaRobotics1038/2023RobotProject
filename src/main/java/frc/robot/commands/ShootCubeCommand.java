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
    private AcquisitionStates initialAcqState;
    private double secondsToShoot = 0.0;
    private double secondsToWaitForAcquisition = 0.5;
    private boolean overrideFeedOut = false;
    private boolean autoFire = false;
    private Timer shootDelayTimer = new Timer();
    private Timer cubeAcqDelayTimer = new Timer();

    public ShootCubeCommand(CubeShooterSetpoints setpoint) {
        this.addRequirements(cubeShooter, cubeAcquisition);
        this.setpoint = setpoint;
    }

    public ShootCubeCommand(CubeShooterSetpoints setpoint, double secondsToShoot) {
        this.addRequirements(cubeShooter, cubeAcquisition);
        this.setpoint = setpoint;
        this.secondsToShoot = secondsToShoot;
    }

    public ShootCubeCommand(CubeShooterSetpoints setpoint, boolean autoFire, double secondsToShoot) {
        this.addRequirements(cubeShooter, cubeAcquisition);
        this.setpoint = setpoint;
        this.secondsToShoot = secondsToShoot;
        this.autoFire = autoFire;
    }

    @Override
    public void initialize() {
        this.overrideFeedOut = false;
        if (this.autoFire) {
            cubeAcquisition.setPosition(AcquisitionStates.Down);
        }
        this.initialAcqState = cubeAcquisition.getPosition();
        cubeShooter.enable(setpoint);
        shootDelayTimer.reset();
        cubeAcqDelayTimer.reset();
    }

    @Override
    public void execute() {
        if (overrideFeedOut || (autoFire && cubeShooter.onTarget())) {
            if (this.initialAcqState == AcquisitionStates.Up) {
                cubeAcqDelayTimer.start();
                cubeAcquisition.setPosition(AcquisitionStates.Down);
                if (cubeAcqDelayTimer.get() >= secondsToWaitForAcquisition) {
                    cubeShooter.feedOut();
                    shootDelayTimer.start();
                }
            } else {
                cubeShooter.feedOut();
                shootDelayTimer.start();
            }
        }
    }

    public void feedOut() {
        this.overrideFeedOut = cubeShooter.onTarget();
    }

    public void overrideFeed() {
        this.overrideFeedOut = true;
    }

    @Override
    public boolean isFinished() {
        return secondsToShoot == 0.0 ? false : shootDelayTimer.get() >= secondsToShoot;
    }

    @Override
    public void end(boolean interrupted) {
        cubeShooter.disable();
        cubeShooter.stopFeeder();
        shootDelayTimer.stop();
        cubeAcqDelayTimer.stop();
    }
}
