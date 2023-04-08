package frc.robot.autons;

import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.autons.reusable.ScoreConeHigh;
import frc.robot.commands.AcquireCubeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand.FinishActions;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Wrist.WristSetpoints;

public class TwoBallAuto extends Auton {
    private HashMap<String, Command> eventMap = new HashMap<>();
    private CubeShooter cubeShooter = CubeShooter.getInstance();

    public TwoBallAuto(Alliance alliance, List<PathPlannerTrajectory> trajectories) {
        super(alliance);

        ShootCubeCommand shootCube = new ShootCubeCommand(CubeShooterSetpoints.high, 0.5);

        PathPlannerTrajectory initialTrajectory = trajectories.get(0);
        PathPlannerTrajectory returnTrajectory = trajectories.get(1);
        PathPlannerTrajectory finalTrajectory = trajectories.get(2);
        eventMap.put("ReadyAcquireCube", new CubeAcquisitionPositionCommand(AcquisitionStates.Down));
        eventMap.put("AcquireCube", new AcquireCubeCommand());

        Dashboard.getInstance().setTrajectory(
                initialTrajectory
                        .concatenate(returnTrajectory)
                        .concatenate(finalTrajectory));

        super.addCommands(
                new ScoreConeHigh(),
                new ParallelCommandGroup(
                        new ConeAcquisitionPositionCommand(WristSetpoints.storage,
                                ShoulderSetpoints.storage, false,
                                FinishActions.NoDisable),
                        new FollowPathWithEvents(
                                this.driveTrain.getTrajectoryCommand(initialTrajectory),
                                initialTrajectory.getMarkers(),
                                eventMap)),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new AcquireCubeCommand(),
                                shootCube),
                        new SequentialCommandGroup(
                                new FollowPathWithEvents(
                                        this.driveTrain.getTrajectoryCommand(returnTrajectory),
                                        returnTrajectory.getMarkers(),
                                        eventMap),
                                new WaitUntilCommand(cubeShooter::onTarget),
                                new InstantCommand(() -> shootCube.overrideFeed()))),
                new ParallelCommandGroup(
                        new CubeAcquisitionPositionCommand(AcquisitionStates.Up),
                        this.driveTrain.getTrajectoryCommand(finalTrajectory)));

        this.setInitialPose(initialTrajectory);
    }
}
