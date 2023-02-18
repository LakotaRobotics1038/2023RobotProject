package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.CubeAcquisition;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;

public class CubeAcquisitionPositionToggleCommand extends CommandBase {
    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();

    private AcquisitionStates state;

    public CubeAcquisitionPositionToggleCommand() {
        this.addRequirements(cubeAcquisition);
    }

    @Override
    public void execute() {
        switch (state) {
            case Up:
                cubeAcquisition.setPosition(AcquisitionStates.Down);
                break;

            case Down:
                cubeAcquisition.setPosition(AcquisitionStates.Up);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
