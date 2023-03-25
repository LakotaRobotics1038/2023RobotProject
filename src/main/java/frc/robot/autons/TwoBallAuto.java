package frc.robot.autons;

import java.util.List;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.commands.AcquireConeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeConeCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand.FinishActions;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Wrist.WristSetpoints;

public class TwoBallAuto extends Auton {
    public TwoBallAuto(Alliance alliance, List<PathPlannerTrajectory> trajectories) {
        super(alliance);

        PathPlannerTrajectory initialTrajectory = trajectories.get(0);
        PathPlannerTrajectory returnTrajectory = trajectories.get(1);

        Dashboard.getInstance().setTrajectory(initialTrajectory.concatenate(returnTrajectory));

        super.addCommands(
                new ShootCubeCommand(CubeShooterSetpoints.high, 1.0),
                new ParallelCommandGroup(
                        new CubeAcquisitionPositionCommand(AcquisitionStates.Up),
                        this.driveTrain.getTrajectoryCommand(initialTrajectory)),
                new ConeAcquisitionPositionCommand(WristSetpoints.acquire,
                        ShoulderSetpoints.acquire, true,
                        FinishActions.NoDisable),
                new AcquireConeCommand(1.0),
                new ConeAcquisitionPositionCommand(WristSetpoints.carry,
                        ShoulderSetpoints.storage, false,
                        FinishActions.NoDisable),
                this.driveTrain.getTrajectoryCommand(returnTrajectory),
                new ConeAcquisitionPositionCommand(WristSetpoints.carry,
                        ShoulderSetpoints.high, false,
                        FinishActions.NoDisable),
                new DisposeConeCommand(1.0),
                new ConeAcquisitionPositionCommand(WristSetpoints.storage,
                        ShoulderSetpoints.storage, false,
                        FinishActions.NoDisable));

        this.setInitialPose(initialTrajectory);
    }
}
