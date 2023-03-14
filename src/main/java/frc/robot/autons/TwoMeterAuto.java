package frc.robot.autons;

import java.util.HashMap;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;

public class TwoMeterAuto extends Auton {
    private HashMap<String, Command> eventMap = new HashMap<>();

    public TwoMeterAuto(Alliance alliance) {
        super(alliance);

        eventMap.put("AcqDown", new CubeAcquisitionPositionCommand(AcquisitionStates.Down));
        PathPlannerTrajectory trajectory = Trajectories.TwoMeterPath();

        Dashboard.getInstance().setTrajectory(trajectory);

        super.addCommands(
                new FollowPathWithEvents(
                        this.driveTrain.getTrajectoryCommand(trajectory),
                        trajectory.getMarkers(),
                        eventMap));

        this.setInitialPose(trajectory);
    }
}
