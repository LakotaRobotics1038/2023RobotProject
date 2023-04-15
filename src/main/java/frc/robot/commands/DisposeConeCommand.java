package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HybridAcquisition;

public class DisposeConeCommand extends CommandBase {
    private HybridAcquisition hybridAcquisition = HybridAcquisition.getInstance();
    private double secondsToDispose = 0.0;
    private Timer timer = new Timer();

    public DisposeConeCommand() {
        this.addRequirements(hybridAcquisition);
    }

    public DisposeConeCommand(double secondsToDispose) {
        this.addRequirements(hybridAcquisition);
        this.secondsToDispose = secondsToDispose;
    }

    @Override
    public void initialize() {
        timer.restart();
        hybridAcquisition.disposeCone();
    }

    @Override
    public boolean isFinished() {
        return secondsToDispose == 0.0 ? false : timer.get() >= secondsToDispose;
    }

    @Override
    public void end(boolean interrupted) {
        hybridAcquisition.stop();
        timer.stop();
    }
}
