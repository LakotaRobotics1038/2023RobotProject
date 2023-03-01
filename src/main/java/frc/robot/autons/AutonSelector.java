package frc.robot.autons;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    // Choosers
    SendableChooser<Auton> autoChooser;

    // Singleton Setup
    private static AutonSelector instance;

    public static AutonSelector getInstance() {
        if (instance == null) {
            System.out.println("Creating new AutonSelector");
            instance = new AutonSelector();
        }
        return instance;
    }

    private AutonSelector() {
        this.autoChooser = Dashboard.getInstance().getAutoChooser();

        this.autoChooser.setDefaultOption("No Auto", null);
        this.autoChooser.addOption("Leave Community Center Auto", new LeaveCommunityPathCenter());
        this.autoChooser.addOption("Leave Community Scoring Table Auto", new LeaveCommunityPathScoringTable());
        this.autoChooser.addOption("Leave Community Substation Auto", new LeaveCommunityPathSubstation());
        this.autoChooser.addOption("Mount Charge Station Auto", new MountChargeStationPath());
    }

    public Auton chooseAuton() {
        return this.autoChooser.getSelected();
    }
}