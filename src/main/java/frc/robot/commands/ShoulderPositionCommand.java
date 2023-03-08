package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;

public class ShoulderPositionCommand extends CommandBase {

    private double setpoint = 0;
    private Shoulder shoulder = Shoulder.getInstance();
    private boolean noFinish = false;

    public ShoulderPositionCommand(double setpoint) {
        this.setpoint = setpoint;
        addRequirements(shoulder);
    }

    public ShoulderPositionCommand(double setpoint, boolean noFinish) {
        this.setpoint = setpoint;
        this.noFinish = noFinish;
        addRequirements(shoulder);
    }

    @Override
    public void initialize() {
        shoulder.enable();
        shoulder.setSetpoint(setpoint);
    }

    @Override
    public boolean isFinished() {
        return !noFinish && shoulder.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        shoulder.disable();
    }
}
