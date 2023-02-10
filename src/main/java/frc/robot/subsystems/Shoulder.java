package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Shoulder {
    public static final CANSparkMax shoulderMotor = new CANSparkMax(0, CANSparkMaxLowLevel.kBrushless);
}
