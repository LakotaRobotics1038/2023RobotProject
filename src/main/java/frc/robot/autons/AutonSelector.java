package frc.robot.autons;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        NoAuto,
        LeaveCommunityCenterAuto,
        LeaveCommunityScoringAuto,
        LeaveCommunitySubstationAuto,
        MountChargeStationAuto,
        ShootCubeOnly,
        TwoBallScoringTableAuto,
        TwoBallSubstationAuto,
        Test;
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

        this.autoChooser.setDefaultOption("No Auto", AutonChoices.NoAuto);
        this.autoChooser.addOption("Leave Community Center Auto", AutonChoices.LeaveCommunityCenterAuto);
        this.autoChooser.addOption("Leave Community Scoring Table Auto", AutonChoices.LeaveCommunityScoringAuto);
        this.autoChooser.addOption("Leave Community Substation Auto", AutonChoices.LeaveCommunitySubstationAuto);
        this.autoChooser.addOption("Mount Charge Station Auto", AutonChoices.MountChargeStationAuto);
        this.autoChooser.addOption("Two Ball Scoring Table Auto", AutonChoices.TwoBallScoringTableAuto);
        this.autoChooser.addOption("Two Ball Substation Auto", AutonChoices.TwoBallSubstationAuto);
        this.autoChooser.addOption("Test Auto", AutonChoices.Test);
    }

    public Auton chooseAuton() {
        Alliance alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case LeaveCommunityCenterAuto:
                return new LeaveCommunityPathCenterAuto(alliance);
            case LeaveCommunityScoringAuto:
                return new LeaveCommunityPathScoringTableAuto(alliance);
            case LeaveCommunitySubstationAuto:
                return new LeaveCommunityPathSubstationAuto(alliance);
            case MountChargeStationAuto:
                return new MountChargeStationAuto(alliance);
            case ShootCubeOnly:
                return new ShootCubeAuto(alliance);
            case TwoBallScoringTableAuto:
                return new TwoBallScoringTableAuto(alliance);
            case TwoBallSubstationAuto:
                return new TwoBallSubstationAuto(alliance);
            case Test:
                return new TwoMeterAuto(alliance);
            default:
                return null;
        }
    }
}