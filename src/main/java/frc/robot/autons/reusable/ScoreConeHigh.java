package frc.robot.autons.reusable;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireHybridCommand;
import frc.robot.commands.HybridAcquisitionPositionCommand;
import frc.robot.commands.DisposeHybridCommand;
import frc.robot.commands.HybridAcquisitionPositionCommand.FinishActions;
import frc.robot.constants.HybridAcquisitionConstants;
import frc.robot.subsystems.HybridAcquisition.HybridAcquisitionTypes;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Wrist.WristSetpoints;

public class ScoreConeHigh extends SequentialCommandGroup {
    public ScoreConeHigh() {
        addCommands(
                new ParallelRaceGroup(
                        new SequentialCommandGroup(
                                new ParallelRaceGroup(
                                        new AcquireHybridCommand(HybridAcquisitionTypes.Cone,
                                                HybridAcquisitionConstants.kAcquireSpeed),
                                        new WaitCommand(0.25)),
                                new AcquireHybridCommand(HybridAcquisitionTypes.Cone,
                                        HybridAcquisitionConstants.kHoldConeSpeed)),
                        new HybridAcquisitionPositionCommand(WristSetpoints.coneHighAuto,
                                ShoulderSetpoints.coneHighAuto, true,
                                FinishActions.NoDisable)),
                new WaitCommand(0.25),
                new DisposeHybridCommand(HybridAcquisitionTypes.Cone, 0.5));
    }
}
