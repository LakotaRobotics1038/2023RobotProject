package frc.robot.autons;

import java.util.HashMap;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;

public class TwoMeterPath extends Auton {
    private HashMap<String, Command> eventMap = new HashMap<>();

    public TwoMeterPath(Alliance alliance) {
        super(alliance);
        eventMap.put("marker1", new PrintCommand("Passed marker 1"));
        PathPlannerTrajectory trajectory = Trajectories.TwoMeterPath();
        super.addCommands(
                new FollowPathWithEvents(
                        this.driveTrain.getTrajectoryCommand(trajectory),
                        trajectory.getMarkers(),
                        eventMap));
        this.setInitialPose(trajectory);
    }
}
