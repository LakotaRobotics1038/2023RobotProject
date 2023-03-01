package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

public class LeaveCommunityPathCenter extends Auton {
    public LeaveCommunityPathCenter() {
        PathPlannerTrajectory trajectory = Trajectories.LeaveCommunityPathCenter();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory));
        this.setInitialPose(trajectory);
    }
}
