package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.PIDController;

public class Wrist {
    private static Wrist inst;

    private CANSparkMax wristMotor = new CANSparkMax(0, null);
    private AbsoluteEncoder wristEncoder = wristMotor.getAbsoluteEncoder(null);
    private PIDController wristPIDController;
    private boolean isPIDEnabled;

    private Wrist() {
    }

    private void setPIDcontorller() {
        wristPIDController.setPID(0, 0, 0);
    }

    private void periodic() {
        wristMotor.set(wristPIDController.calculate(0.0));
    }

    public void enable() {
        wristPIDController.enableContinuousInput(0, 0);
    }

    public void disable() {
    }

    public static Wrist getInstance() {
        if (inst == null) {
            inst = new Wrist();
        }
        return inst;
    }
}
