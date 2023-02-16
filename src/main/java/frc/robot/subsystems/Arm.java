package frc.robot.subsystems;

import com.fasterxml.jackson.databind.deser.impl.NullsFailProvider;

import edu.wpi.first.wpilibj.Solenoid;

public class Arm {

    private Arm() {

    }

    // singleton setup
    private static Arm instance;

    private Solenoid armExtension = new Solenoid(null, 0);

    private enum ArmExtensionStates {
        In, Out
    }

    public static Arm getInstance() {
        if (instance == null) {
            System.out.println("creating a new Arm");
            instance = new Arm();
        }
        return instance;
    }
}
