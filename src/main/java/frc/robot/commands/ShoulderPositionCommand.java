package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;

public class ShoulderPositionCommand extends CommandBase {

    private double setPoint = 0;
    private Shoulder shoulder = Shoulder.getInstance();

    public ShoulderPositionCommand(double setPoint) {
        this.setPoint = setPoint;

        addRequirements(shoulder);
    }

    @Override
    public void initialize() {

        shoulder.enable();
        shoulder.setSetpoint(setPoint);
    }

    @Override
    public boolean isFinished() {

        return shoulder.onTarget();
    }

    @Override
    public void end(boolean interrupted) {

        shoulder.disable();
    }
}
