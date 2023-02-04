package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

public class CirclePath extends Auton {
    public CirclePath() {
        PathPlannerTrajectory trajectory = Trajectories.CirclePath();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory));
        this.setInitialPose(trajectory);
    }
}
