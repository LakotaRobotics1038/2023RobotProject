package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class LeaveCommunityPathSubstationAuto extends Auton {
    public LeaveCommunityPathSubstationAuto(Alliance alliance) {
        super(alliance);
        PathPlannerTrajectory trajectory = alliance == Alliance.Blue ? Trajectories.LeaveCommunityPathSubstationBlue()
                : Trajectories.LeaveCommunityPathSubstationRed();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory));
        this.setInitialPose(trajectory);
    }
}