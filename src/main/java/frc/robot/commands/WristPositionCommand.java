package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Wrist.WristSetpoints;

public class WristPositionCommand extends CommandBase {
    private Wrist wrist = Wrist.getInstance();
    private WristSetpoints setpoint;
    private boolean noFinish = false;

    /**
     * @deprecated
     *             Use {@link ConeAcquisitionCommand} instead
     */
    @Deprecated
    public WristPositionCommand(WristSetpoints setpoint) {
        this.addRequirements(wrist);
        this.setpoint = setpoint;
    }

    public WristPositionCommand(WristSetpoints setpoint, boolean noFinish) {
        this.addRequirements(wrist);
        this.setpoint = setpoint;
        this.noFinish = noFinish;
    }

    @Override
    public void initialize() {
        wrist.enable();
        wrist.setSetpoint(setpoint);
    }

    @Override
    public boolean isFinished() {
        return !noFinish && wrist.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        wrist.disable();
    }
}
