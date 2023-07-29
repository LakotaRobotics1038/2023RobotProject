package frc.robot.autons;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    // makes an enum of all the different auton choices
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
    // in the smart dashboard so you can choose the auton
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

    // Method to set all of the Dashboard things to link up to autons
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
        // gets the allince so pathplanner can run the right side of the field
        Alliance alliance = DriverStation.getAlliance();
        // long if statement that is if this auton is selected than run that auton
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