package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class LeaveCommunityPathScoringTableAuto extends Auton {
    public LeaveCommunityPathScoringTableAuto(Alliance alliance) {
        super(alliance);

        PathPlannerTrajectory trajectory = Trajectories.LeaveCommunityPathScoringTable();

        super.addCommands(this.driveTrain.getTrajectoryCommand(trajectory));

        this.setInitialPose(trajectory);
    }
}
