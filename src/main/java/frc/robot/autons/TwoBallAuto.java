package frc.robot.autons;

import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireConeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeConeCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand.FinishActions;
import frc.robot.constants.ConeAcquisitionConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Wrist.WristSetpoints;

public class TwoBallAuto extends Auton {
    private HashMap<String, Command> eventMap = new HashMap<>();

    public TwoBallAuto(Alliance alliance, List<PathPlannerTrajectory> trajectories) {
        super(alliance);

        PathPlannerTrajectory initialTrajectory = trajectories.get(0);
        PathPlannerTrajectory returnTrajectory = trajectories.get(1);
        eventMap.put("AcquireCone", new ConeAcquisitionPositionCommand(WristSetpoints.acquire,
                ShoulderSetpoints.acquire, true,
                FinishActions.NoDisable));
        eventMap.put("ReadyScoreCone", new ConeAcquisitionPositionCommand(WristSetpoints.high,
                ShoulderSetpoints.high, true,
                FinishActions.NoDisable));

        Dashboard.getInstance().setTrajectory(initialTrajectory.concatenate(returnTrajectory));

        super.addCommands(
                new ShootCubeCommand(CubeShooterSetpoints.high, 1.0),
                new ParallelCommandGroup(
                        new CubeAcquisitionPositionCommand(AcquisitionStates.Up),
                        new FollowPathWithEvents(
                                this.driveTrain.getTrajectoryCommand(initialTrajectory),
                                initialTrajectory.getMarkers(),
                                eventMap)),
                new AcquireConeCommand(ConeAcquisitionConstants.kAcquireSpeed, 1.0),
                new ParallelCommandGroup(
                        new AcquireConeCommand(ConeAcquisitionConstants.kHoldConeSpeed),
                        new ConeAcquisitionPositionCommand(WristSetpoints.carry,
                                ShoulderSetpoints.storage, false,
                                FinishActions.NoDisable),
                        new FollowPathWithEvents(
                                this.driveTrain.getTrajectoryCommand(returnTrajectory),
                                returnTrajectory.getMarkers(),
                                eventMap)),
                new WaitCommand(0.5),
                new DisposeConeCommand(2.0),
                new ConeAcquisitionPositionCommand(WristSetpoints.storage,
                        ShoulderSetpoints.storage, false,
                        FinishActions.NoDisable));

        this.setInitialPose(initialTrajectory);
    }
}
