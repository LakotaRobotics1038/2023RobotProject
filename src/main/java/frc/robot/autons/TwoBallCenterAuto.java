package frc.robot.autons;

import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.autons.DetectChargeStation.DetectionDirections;
import frc.robot.commands.AcquireConeCommand;
import frc.robot.commands.AcquireCubeCommand;
import frc.robot.commands.BalanceRobotCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeConeCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand.FinishActions;
import frc.robot.constants.ConeAcquisitionConstants;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Wrist.WristSetpoints;

public class TwoBallCenterAuto extends Auton {
    private HashMap<String, Command> eventMap = new HashMap<>();
    private CubeShooter cubeShooter = CubeShooter.getInstance();

    public TwoBallCenterAuto(Alliance alliance) {
        super(alliance);

        ShootCubeCommand shootCube = new ShootCubeCommand(CubeShooterSetpoints.high, 0.5);
        List<PathPlannerTrajectory> trajectories = Trajectories.TwoBallCenter();

        PathPlannerTrajectory overChargeStation = trajectories.get(0);
        PathPlannerTrajectory getCubeAndOverChargeStation = trajectories.get(1);
        PathPlannerTrajectory scoreCubeTraj = trajectories.get(2);
        PathPlannerTrajectory toBalance = trajectories.get(3);

        eventMap.put("ReadyAcquireCube", new CubeAcquisitionPositionCommand(AcquisitionStates.Down));
        eventMap.put("AcquireCube", new AcquireCubeCommand());

        Dashboard.getInstance().setTrajectory(
                overChargeStation
                        .concatenate(getCubeAndOverChargeStation)
                        .concatenate(scoreCubeTraj)
                        .concatenate(toBalance));

        super.addCommands(
                // Score Cone
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
                new DisposeConeCommand(0.5),
                // Go over charge station toward cube
                new ParallelCommandGroup(
                        new ConeAcquisitionPositionCommand(WristSetpoints.storage,
                                ShoulderSetpoints.storage, false,
                                FinishActions.NoDisable),
                        new ParallelRaceGroup(
                                new SequentialCommandGroup(
                                        new DetectChargeStation(driveTrain, DetectionDirections.On),
                                        new DetectChargeStation(driveTrain, DetectionDirections.Off)),
                                new FollowPathWithEvents(
                                        this.driveTrain.getTrajectoryCommand(overChargeStation),
                                        overChargeStation.getMarkers(),
                                        eventMap))),
                // Get the cube and get back on charge station
                new ParallelRaceGroup(
                        new SequentialCommandGroup(
                                new DetectChargeStation(driveTrain, DetectionDirections.On),
                                new DetectChargeStation(driveTrain, DetectionDirections.Off)),
                        new FollowPathWithEvents(
                                this.driveTrain.getTrajectoryCommand(getCubeAndOverChargeStation),
                                getCubeAndOverChargeStation.getMarkers(),
                                eventMap)),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new AcquireCubeCommand(),
                                shootCube),
                        new SequentialCommandGroup(
                                // Back off charge station
                                new ParallelRaceGroup(
                                        new DetectChargeStation(driveTrain, DetectionDirections.Off),
                                        new FollowPathWithEvents(
                                                this.driveTrain.getTrajectoryCommand(scoreCubeTraj),
                                                scoreCubeTraj.getMarkers(),
                                                eventMap)),
                                // Score the cube
                                new WaitUntilCommand(cubeShooter::onTarget),
                                new InstantCommand(() -> shootCube.overrideFeed()))),
                new ParallelCommandGroup(
                        new CubeAcquisitionPositionCommand(AcquisitionStates.Up),
                        // Balance
                        new ParallelRaceGroup(
                                new DetectChargeStation(driveTrain, DetectionDirections.On),
                                this.driveTrain.getTrajectoryCommand(toBalance))),
                new BalanceRobotCommand());

        this.setInitialPose(overChargeStation);
    }
}
