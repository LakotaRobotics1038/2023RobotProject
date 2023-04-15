package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.HybridAcquisitionConstants;
import frc.robot.subsystems.HybridAcquisition;

public class AcquireConeCommand extends CommandBase {
    private HybridAcquisition hybridAcquisition = HybridAcquisition.getInstance();
    private double speed = HybridAcquisitionConstants.kAcquireSpeed;
    private double secondsToAcquire = 0.0;
    private Timer timer = new Timer();

    public AcquireConeCommand() {
        this.addRequirements(hybridAcquisition);
    }

    public AcquireConeCommand(double speed) {
        this.addRequirements(hybridAcquisition);
        this.speed = speed;
    }

    public AcquireConeCommand(double speed, double secondsToAcquire) {
        this.addRequirements(hybridAcquisition);
        this.speed = speed;
        this.secondsToAcquire = secondsToAcquire;
    }

    @Override
    public void initialize() {
        timer.restart();
        hybridAcquisition.acquireCone(speed);
    }

    @Override
    public boolean isFinished() {
        return secondsToAcquire == 0.0 ? false : timer.get() >= secondsToAcquire;
    }

    @Override
    public void end(boolean interrupted) {
        hybridAcquisition.stop();
        timer.stop();
    }
}
