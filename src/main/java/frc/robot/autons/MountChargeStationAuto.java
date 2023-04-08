package frc.robot.autons;

import java.util.List;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.autons.DetectChargeStation.DetectionDirections;
import frc.robot.commands.BalanceRobotCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;

public class MountChargeStationAuto extends Auton {
    public MountChargeStationAuto(Alliance alliance) {
        super(alliance);

        List<PathPlannerTrajectory> trajectories = Trajectories.MountChargeStationPath();
        PathPlannerTrajectory initialTrajectory = trajectories.get(0);
        PathPlannerTrajectory finalTrajectory = trajectories.get(1);

        Dashboard.getInstance().setTrajectory(initialTrajectory.concatenate(finalTrajectory));

        super.addCommands(
                new ShootCubeCommand(CubeShooterSetpoints.high, true, 1.0),
                new ParallelCommandGroup(
                        this.driveTrain.getTrajectoryCommand(initialTrajectory),
                        new CubeAcquisitionPositionCommand(AcquisitionStates.Up)),
                new ParallelRaceGroup(
                        new DetectChargeStation(driveTrain::getRoll, DetectionDirections.On),
                        this.driveTrain.getTrajectoryCommand(finalTrajectory)),
                new BalanceRobotCommand(),
                new RunCommand(() -> driveTrain.setX(), driveTrain));

        this.setInitialPose(initialTrajectory);
    }
}
