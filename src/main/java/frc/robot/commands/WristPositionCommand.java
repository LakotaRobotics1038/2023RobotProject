package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;

public class WristPositionCommand extends CommandBase {
    private Wrist wrist = Wrist.getInstance();
    private double wristSetpoint;
    private boolean noFinish = false;

    public WristPositionCommand(double wristSetpoint) {
        this.addRequirements(wrist);
        this.wristSetpoint = wristSetpoint;
    }

    public WristPositionCommand(double wristSetpoint, boolean noFinish) {
        this.addRequirements(wrist);
        this.wristSetpoint = wristSetpoint;
        this.noFinish = noFinish;
    }

    @Override
    public void initialize() {
        wrist.enable();
        wrist.setSetpoint(wristSetpoint);
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
