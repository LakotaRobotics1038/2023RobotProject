package frc.robot.autons.reusable;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireConeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand;
import frc.robot.commands.DisposeConeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand.FinishActions;
import frc.robot.constants.ConeAcquisitionConstants;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Wrist.WristSetpoints;

public class ScoreConeHigh extends SequentialCommandGroup {
    public ScoreConeHigh() {
        addCommands(
                new ParallelRaceGroup(
                        new SequentialCommandGroup(
                                new ParallelRaceGroup(
                                        new AcquireConeCommand(ConeAcquisitionConstants.kAcquireSpeed),
                                        new WaitCommand(0.25)),
                                new AcquireConeCommand(ConeAcquisitionConstants.kHoldConeSpeed)),
                        new ConeAcquisitionPositionCommand(WristSetpoints.highAuto,
                                ShoulderSetpoints.highAuto, true,
                                FinishActions.NoDisable)),
                new WaitCommand(0.25),
                new DisposeConeCommand(0.5));
    }
}
