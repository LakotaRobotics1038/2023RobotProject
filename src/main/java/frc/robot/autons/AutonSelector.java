package frc.robot.autons;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        kLeaveCommunityCenterAuto,
        kLeaveCommunityScoringAuto,
        kLeaveCommunitySubstationAuto,
        kMountChargeStationAuto;
    }

    // Choosers
    SendableChooser<AutonChoices> autoChooser;

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
        // this.autoChooser.addOption("Leave Community Center Auto",
        // AutonChoices.kLeaveCommunityCenterAuto);
        this.autoChooser.addOption("Leave Community Scoring Table Auto", AutonChoices.kLeaveCommunityScoringAuto);
        // this.autoChooser.addOption("Leave Community Substation Auto",
        // AutonChoices.kLeaveCommunitySubstationAuto);
        this.autoChooser.addOption("Mount Charge Station Auto", AutonChoices.kMountChargeStationAuto);
    }

    public Auton chooseAuton() {
        switch (this.autoChooser.getSelected()) {
            case kLeaveCommunityCenterAuto:
                return new LeaveCommunityPathCenter();
            case kLeaveCommunityScoringAuto:
                return new LeaveCommunityPathScoringTable();
            case kLeaveCommunitySubstationAuto:
                return new LeaveCommunityPathSubstation();
            case kMountChargeStationAuto:
                return new MountChargeStationPath();
            default:
                return null;
        }
    }
}