package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;

public class WristPositionCommand extends CommandBase {
    private Wrist wrist = Wrist.getInstance();

    WristPositionCommand() {
        this.addRequirements(wrist);
    }

    public void initialize() {
        wrist.enable();
        wrist.setSetpoint(0);
    }

    public boolean isFinished() {
        return Wrist.onTarget();
    }

    public void end() {
        wrist.disable();
    }
}
