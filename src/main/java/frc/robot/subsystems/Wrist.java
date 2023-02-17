package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.constants.AcquisitionConstants;

import edu.wpi.first.math.controller.PIDController;

public class Wrist {
    private static Wrist wristInstance;

    private CANSparkMax wristMotor = new CANSparkMax(AcquisitionConstants.kWristMotorPort, MotorType.kBrushless);
    private AbsoluteEncoder wristEncoder = wristMotor.getAbsoluteEncoder(null);
    private PIDController wristPIDController;
    private boolean isPIDEnabled;

    public static Wrist getInstance() {
        if (wristInstance == null) {
            wristInstance = new Wrist();
        }
        return wristInstance;
    }

    private Wrist() {
    }

    private void setPIDcontroller() {
        wristPIDController.setPID(AcquisitionConstants.kWristP, AcquisitionConstants.kWristI,
                AcquisitionConstants.kWristD);
    }

    private void periodic() {
        wristMotor.set(wristPIDController.calculate(0.0));
    }

    public void enable() {
        wristPIDController.enableContinuousInput(AcquisitionConstants.kWristPIDMinimum,
                AcquisitionConstants.kWristPIDMinimum);
    }

    public void disable() {
    }

}
