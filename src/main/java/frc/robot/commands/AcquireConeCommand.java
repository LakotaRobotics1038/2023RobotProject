package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ConeAcquisitionConstants;
import frc.robot.subsystems.ConeAcquisition;

public class AcquireConeCommand extends CommandBase {
    private ConeAcquisition coneAcquisition = ConeAcquisition.getInstance();
    private double speed = ConeAcquisitionConstants.kAcquireSpeed;
    private double secondsToAcquire = 0.0;
    private Timer timer = new Timer();

    public AcquireConeCommand() {
        this.addRequirements(coneAcquisition);
    }

    public AcquireConeCommand(double speed) {
        this.addRequirements(coneAcquisition);
        this.speed = speed;
    }

    public AcquireConeCommand(double speed, double secondsToAcquire) {
        this.addRequirements(coneAcquisition);
        this.speed = speed;
        this.secondsToAcquire = secondsToAcquire;
    }

    @Override
    public void initialize() {
        timer.restart();
        coneAcquisition.acquire(speed);
    }

    @Override
    public boolean isFinished() {
        return secondsToAcquire == 0.0 ? false : timer.get() >= secondsToAcquire;
    }

    @Override
    public void end(boolean interrupted) {
        coneAcquisition.stop();
        timer.stop();
    }
}
