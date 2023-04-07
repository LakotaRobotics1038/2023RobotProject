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
        TwoBallCenterMountAuto;
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
        this.autoChooser.addOption("Two Ball Center Auto", AutonChoices.TwoBallCenterMountAuto);
    }

    public Auton chooseAuton() {
        Alliance alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case LeaveCommunityCenterAuto:
                return new LeaveCommunityAuto(alliance, Trajectories.LeaveCommunityPathCenter());
            case LeaveCommunityScoringAuto:
                return new LeaveCommunityAuto(alliance, Trajectories.LeaveCommunityPathScoringTable());
            case LeaveCommunitySubstationAuto:
                return new LeaveCommunityAuto(alliance, Trajectories.LeaveCommunityPathSubstation());
            case MountChargeStationAuto:
                return new MountChargeStationAuto(alliance);
            case ShootCubeOnly:
                return new ShootCubeAuto(alliance);
            case TwoBallScoringTableAuto:
                return new TwoBallAuto(alliance, Trajectories.TwoBallScoringTable());
            case TwoBallSubstationAuto:
                return new TwoBallAuto(alliance, Trajectories.TwoBallSubstation());
            case TwoBallCenterMountAuto:
                return new TwoBallCenterAuto(alliance);
            default:
                return null;
        }
    }
}