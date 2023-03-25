package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConeAcquisition;

public class DisposeConeCommand extends CommandBase {
    private ConeAcquisition coneAcquisition = ConeAcquisition.getInstance();
    private double secondsToDispose = 0.0;
    private Timer timer = new Timer();

    public DisposeConeCommand() {
        this.addRequirements(coneAcquisition);
    }

    public DisposeConeCommand(double secondsToDispose) {
        this.addRequirements(coneAcquisition);
        this.secondsToDispose = secondsToDispose;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        coneAcquisition.dispose();
    }

    @Override
    public boolean isFinished() {
        return secondsToDispose == 0.0 ? false : timer.get() >= secondsToDispose;
    }

    @Override
    public void end(boolean interrupted) {
        coneAcquisition.stop();
        timer.stop();
    }
}
