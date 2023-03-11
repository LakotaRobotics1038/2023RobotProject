package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;

public class LeaveCommunityPathScoringTableShootAuto extends Auton {
    public LeaveCommunityPathScoringTableShootAuto(Alliance alliance) {
        super(alliance);

        PathPlannerTrajectory trajectory = Trajectories.LeaveCommunityPathScoringTable();

        super.addCommands(
                new ShootCubeCommand(CubeShooterSetpoints.high, 1.0),
                this.driveTrain.getTrajectoryCommand(trajectory));

        this.setInitialPose(trajectory);
    }
}
