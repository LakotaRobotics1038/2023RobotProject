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
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.autons.DetectChargeStation.DetectionDirections;
import frc.robot.commands.AcquireHybridCommand;
import frc.robot.commands.AcquireCubeCommand;
import frc.robot.commands.BalanceRobotCommand;
import frc.robot.commands.HybridAcquisitionPositionCommand;
import frc.robot.commands.ManualShootCubeCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeHybridCommand;
import frc.robot.commands.HybridAcquisitionPositionCommand.FinishActions;
import frc.robot.constants.CubeShooterConstants;
import frc.robot.constants.HybridAcquisitionConstants;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.HybridAcquisition.HybridAcquisitionTypes;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Wrist.WristSetpoints;

public class TwoBallCenterAuto extends Auton {
    private HashMap<String, Command> eventMap = new HashMap<>();
    private CubeShooter cubeShooter = CubeShooter.getInstance();

    public TwoBallCenterAuto(Alliance alliance) {
        super(alliance);

        List<PathPlannerTrajectory> trajectories = Trajectories.TwoBallCenter();

        PathPlannerTrajectory overChargeStation = trajectories.get(0);
        PathPlannerTrajectory getCubeAndOverChargeStation = trajectories.get(1);

        eventMap.put("ReadyAcquireCube", new CubeAcquisitionPositionCommand(AcquisitionStates.Down));
        eventMap.put("AcquireCube", new AcquireCubeCommand());

        ManualShootCubeCommand shootCubeCommand = new ManualShootCubeCommand();

        Dashboard.getInstance().setTrajectory(
                overChargeStation
                        .concatenate(getCubeAndOverChargeStation));

        super.addCommands(
                // Score Cone
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
                new DisposeHybridCommand(HybridAcquisitionTypes.Cone, 0.5),
                // Go over charge station toward cube
                new ParallelCommandGroup(
                        new HybridAcquisitionPositionCommand(WristSetpoints.storage,
                                ShoulderSetpoints.storage, false,
                                FinishActions.NoDisable),
                        new ParallelRaceGroup(
                                new SequentialCommandGroup(
                                        new PrintCommand("Go ON"),
                                        new DetectChargeStation(driveTrain::getRoll, DetectionDirections.On),
                                        new WaitCommand(0.5),
                                        new PrintCommand("Get OFF"),
                                        new DetectChargeStation(driveTrain::getRoll, DetectionDirections.Off),
                                        new PrintCommand("DONE DETECT")),
                                new FollowPathWithEvents(
                                        this.driveTrain.getTrajectoryCommand(overChargeStation),
                                        overChargeStation.getMarkers(),
                                        eventMap))),
                new PrintCommand("NEXT SEGMENT"),
                // Get the cube and get back on charge station
                new ParallelRaceGroup(
                        new SequentialCommandGroup(
                                new DetectChargeStation(driveTrain::getRoll, DetectionDirections.On),
                                new PrintCommand("BALANCE READY")),
                        new FollowPathWithEvents(
                                this.driveTrain.getTrajectoryCommand(getCubeAndOverChargeStation),
                                getCubeAndOverChargeStation.getMarkers(),
                                eventMap)),
                new PrintCommand("DO BALANCE"),
                new BalanceRobotCommand(),
                new InstantCommand(() -> cubeShooter.setShooterSpeed(1.0)),
                new ParallelRaceGroup(
                        shootCubeCommand,
                        new ParallelCommandGroup(
                                new WaitCommand(1.0),
                                new InstantCommand(() -> shootCubeCommand.feedOut()))),
                new ParallelCommandGroup(
                        new InstantCommand(
                                () -> cubeShooter.setShooterSpeed(CubeShooterConstants.kDefaultShooterSpeed)),
                        new CubeAcquisitionPositionCommand(AcquisitionStates.Up)));

        this.setInitialPose(overChargeStation);
    }
}
