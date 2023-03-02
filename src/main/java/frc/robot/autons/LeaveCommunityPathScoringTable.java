package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

public class LeaveCommunityPathScoringTable extends Auton {
    public LeaveCommunityPathScoringTable() {
        PathPlannerTrajectory trajectory = Trajectories.LeaveCommunityPathScoringTable();

        super.addCommands(this.driveTrain.getTrajectoryCommand(trajectory));
        this.setInitialPose(trajectory);
    }
}
