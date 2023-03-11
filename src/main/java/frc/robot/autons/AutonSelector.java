package frc.robot.autons;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        kNoAuto,
        kLeaveCommunityCenterAuto,
        kLeaveCommunityScoringAuto,
        kLeaveCommunitySubstationAuto,
        kMountChargeStationAuto,
        kTest;
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

        this.autoChooser.setDefaultOption("No Auto", AutonChoices.kNoAuto);
        this.autoChooser.addOption("Leave Community Center Auto", AutonChoices.kLeaveCommunityCenterAuto);
        this.autoChooser.addOption("Leave Community Scoring Table Auto", AutonChoices.kLeaveCommunityScoringAuto);
        this.autoChooser.addOption("Leave Community Substation Auto", AutonChoices.kLeaveCommunitySubstationAuto);
        this.autoChooser.addOption("Mount Charge Station Auto", AutonChoices.kMountChargeStationAuto);
        this.autoChooser.addOption("Test Auto", AutonChoices.kTest);
    }

    public Auton chooseAuton() {
        Alliance alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case kLeaveCommunityCenterAuto:
                return new LeaveCommunityPathCenterAuto(alliance);
            case kLeaveCommunityScoringAuto:
                return new LeaveCommunityPathScoringTableAuto(alliance);
            case kLeaveCommunitySubstationAuto:
                return new LeaveCommunityPathSubstationAuto(alliance);
            case kMountChargeStationAuto:
                return new MountChargeStationAuto(alliance);
            case kTest:
                return new TwoMeterAuto(alliance);
            default:
                return null;
        }
    }
}