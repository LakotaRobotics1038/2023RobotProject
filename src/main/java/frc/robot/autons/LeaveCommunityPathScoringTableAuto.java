package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class LeaveCommunityPathScoringTableAuto extends Auton {
    public LeaveCommunityPathScoringTableAuto(Alliance alliance) {
        super(alliance);

        PathPlannerTrajectory trajectory = Trajectories.LeaveCommunityPathScoringTable();

        Dashboard.getInstance().setTrajectory(trajectory);

        super.addCommands(this.driveTrain.getTrajectoryCommand(trajectory));

        this.setInitialPose(trajectory);
    }
}
