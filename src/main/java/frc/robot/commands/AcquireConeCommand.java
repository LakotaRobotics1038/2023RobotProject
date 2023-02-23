package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConeAcquisition;

public class AcquireConeCommand extends CommandBase {
    private ConeAcquisition coneAcquisition = ConeAcquisition.getInstance();

    public AcquireConeCommand() {
        this.addRequirements(coneAcquisition);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public void execute() {
        coneAcquisition.acquireCone();
    }

    public void end() {
        coneAcquisition.stopMotor();
    }
}
