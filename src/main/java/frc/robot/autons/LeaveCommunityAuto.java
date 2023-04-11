package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;

public class LeaveCommunityAuto extends Auton {
    public LeaveCommunityAuto(Alliance alliance, PathPlannerTrajectory trajectory) {
        super(alliance);

        Dashboard.getInstance().setTrajectory(trajectory);

        super.addCommands(
                new ShootCubeCommand(CubeShooterSetpoints.high, true, 1.0),
                new ParallelCommandGroup(
                        this.driveTrain.getTrajectoryCommand(trajectory),
                        new CubeAcquisitionPositionCommand(AcquisitionStates.Up)));

        this.setInitialPose(trajectory);
    }
}
