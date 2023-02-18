package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;

public class ShoulderPositionCommand extends CommandBase {

    // creates setPoint, creates Shoulder instance
    private double setPoint = 0;
    private Shoulder shoulder = Shoulder.getInstance();

    public ShoulderPositionCommand() {
        // requires shoulder subsystem
        addRequirements(shoulder);
    }

    @Override
    public void initialize() {
        // enables shoulder, sets setpoint
        shoulder.enable();
        shoulder.setSetpoint(setPoint);
    }

    @Override
    public boolean isFinished() {
        // isFinished if shoulder setpoint = setpoint
        return (shoulder.onTarget(setPoint));
    }

    @Override
    public void end(boolean interrupted) {
        // disable shoulder
        shoulder.disable();
    }
}
