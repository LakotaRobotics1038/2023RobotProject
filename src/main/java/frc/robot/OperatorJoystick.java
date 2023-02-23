package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.constants.IOConstants;

public class OperatorJoystick extends XboxController {
    private static OperatorJoystick instance;

    public static OperatorJoystick getIntance() {
        if (instance == null) {
            System.out.println("Creating a new Operator");
            instance = new OperatorJoystick();
        }
        return instance;
    }

    private OperatorJoystick() {
        super(IOConstants.kOperatorControllerPort);

        // Cone Acquisition

    }
}
