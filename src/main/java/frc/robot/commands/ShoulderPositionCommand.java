package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;

public class ShoulderPositionCommand extends CommandBase {
    private Shoulder shoulder = Shoulder.getInstance();
    private ShoulderSetpoints setpoint;
    private boolean noFinish = false;

    /**
     * @deprecated
     *             Use {@link ConeAcquisitionCommand} instead
     */
    @Deprecated
    public ShoulderPositionCommand(ShoulderSetpoints setpoint) {
        this.setpoint = setpoint;
        addRequirements(shoulder);
    }

    public ShoulderPositionCommand(ShoulderSetpoints setpoint, boolean noFinish) {
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
