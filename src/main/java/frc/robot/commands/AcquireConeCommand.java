package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConeAcquisition;

public class AcquireConeCommand extends CommandBase {
    private ConeAcquisition coneAcquisition = ConeAcquisition.getInstance();
    private double secondsToAcquire = 0.0;
    private Timer timer = new Timer();

    public AcquireConeCommand() {
        this.addRequirements(coneAcquisition);
    }

    public AcquireConeCommand(double secondsToAcquire) {
        this.addRequirements(coneAcquisition);
        this.secondsToAcquire = secondsToAcquire;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        coneAcquisition.acquire();
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
