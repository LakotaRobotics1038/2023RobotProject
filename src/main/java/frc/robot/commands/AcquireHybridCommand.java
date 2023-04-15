package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.HybridAcquisitionConstants;
import frc.robot.subsystems.HybridAcquisition;
import frc.robot.subsystems.HybridAcquisition.HybridAcquisitionTypes;

public class AcquireHybridCommand extends CommandBase {
    private HybridAcquisition hybridAcquisition = HybridAcquisition.getInstance();
    private double speed = HybridAcquisitionConstants.kAcquireSpeed;
    private double secondsToAcquire = 0.0;
    private HybridAcquisitionTypes type;
    private Timer timer = new Timer();

    public AcquireHybridCommand(HybridAcquisitionTypes type) {
        this.addRequirements(hybridAcquisition);
        this.type = type;
    }

    public AcquireHybridCommand(HybridAcquisitionTypes type, double speed) {
        this.addRequirements(hybridAcquisition);
        this.type = type;
        this.speed = speed;
    }

    public AcquireHybridCommand(HybridAcquisitionTypes type, double speed, double secondsToAcquire) {
        this.addRequirements(hybridAcquisition);
        this.type = type;
        this.speed = speed;
        this.secondsToAcquire = secondsToAcquire;
    }

    @Override
    public void initialize() {
        timer.restart();
        hybridAcquisition.acquire(type, type == HybridAcquisitionTypes.Cone ? speed : -speed);
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
