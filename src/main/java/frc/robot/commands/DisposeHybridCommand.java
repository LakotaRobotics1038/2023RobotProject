package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HybridAcquisition;
import frc.robot.subsystems.HybridAcquisition.HybridAcquisitionTypes;

public class DisposeHybridCommand extends CommandBase {
    private HybridAcquisition hybridAcquisition = HybridAcquisition.getInstance();
    private double secondsToDispose = 0.0;
    private HybridAcquisitionTypes type;
    private Timer timer = new Timer();

    public DisposeHybridCommand(HybridAcquisitionTypes type) {
        this.addRequirements(hybridAcquisition);
        this.type = type;
    }

    public DisposeHybridCommand(HybridAcquisitionTypes type, double secondsToDispose) {
        this.addRequirements(hybridAcquisition);
        this.secondsToDispose = secondsToDispose;
        this.type = type;
    }

    @Override
    public void initialize() {
        timer.restart();
        hybridAcquisition.dispose(type);
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
