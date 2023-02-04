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
        this.autoChooser.addOption("Test Auto", new TestPath());
        this.autoChooser.addOption("Circle Auto", new CirclePath());
        this.autoChooser.addOption("Two Meter Auto", new TwoMeterPath());
    }

    public Auton chooseAuton() {
        return this.autoChooser.getSelected();
    }
}