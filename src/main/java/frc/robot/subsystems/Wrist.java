package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import frc.robot.constants.AcquisitionConstants;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

public class Wrist extends PIDSubsystem{
    private static Wrist wristInstance;

    private CANSparkMax wristMotor = new CANSparkMax(AcquisitionConstants.kWristMotorPort, MotorType.kBrushless);
    private AbsoluteEncoder wristEncoder = wristMotor.getAbsoluteEncoder(Type.kDutyCycle);
    private PIDController wristPIDController;
    private boolean isPIDEnabled;

    public static Wrist getInstance() {
        if (wristInstance == null) {
            wristInstance = new Wrist();
        }
        return wristInstance;
    }

    private Wrist() {
        super(new PIDController(AcquisitionConstants.kWristP, AcquisitionConstants.kWristI,
        AcquisitionConstants.kWristD));
        getController().disableContinuousInput();
    }

    public void setPIDTolerance() {
        getController().setTolerance(AcquisitionConstants.kWristPIDTolerance);
    }

    public void setPIDcontroller() {
        wristPIDController.setPID(AcquisitionConstants.kWristP, AcquisitionConstants.kWristI,
        AcquisitionConstants.kWristD);
    }

    public void enable() {
        wristPIDController.enableContinuousInput(AcquisitionConstants.kWristPIDMinimum,
                AcquisitionConstants.kWristPIDMinimum);
    }

    public void periodic() {
        if(isPIDEnabled) {
            wristMotor.set(wristPIDController.calculate(AcquisitionConstants.kWristPIDSpeed));
        }
    }

    public void disable() {
    }

    public void useOutput(double output, double setpoint) {

    }

    public double getMeasurement() {
        return 0.0;
    }

}
