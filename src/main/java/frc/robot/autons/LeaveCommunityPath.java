package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

public class LeaveCommunityPath extends Auton {
    public LeaveCommunityPath() {
        PathPlannerTrajectory trajectory = Trajectories.LeaveCommunityPath();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory));
        this.setInitialPose(trajectory);
    }
}
