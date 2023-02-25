package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;

public class WristPositionCommand extends CommandBase {
    private Wrist wrist = Wrist.getInstance();
    // Used in constructor do not delete
    private double wristSetpoint;

    WristPositionCommand(double wristSetpoint) {
        this.addRequirements(wrist);
        this.wristSetpoint = wristSetpoint;
    }

    public void initialize() {
        wrist.enable();
        wrist.setSetpoint(wristSetpoint);
    }

    public boolean isFinished() {
        return wrist.onTarget();
    }

    public void end() {
        wrist.disable();
        if (wristSetpoint == 0) {
        }
    }
}
