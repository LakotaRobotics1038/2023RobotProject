package frc.robot.constants;

public class WristConstants {
    public static final int kWristMotorPort = 11;
    public static final double kEncoderConversion = 360.0;
    public static final double kMaxPower = 1.0;
    public static final double kP = 0.0042;
    public static final double kI = 0.0001;
    public static final double kD = 0.00;
    public static final double kTolerance = 5.0;
    public static final double kMaxDistance = 260;

    public static final int kStorageSetpoint = 0;

    // Cone Setpoints
    public static final int kConeCarrySetpoint = 32;
    public static final int kConeAcqFloorSetpoint = 115;
    public static final int kConeMidSetpoint = 205;
    public static final int kConeHumanPlayerSetpoint = 90;
    public static final int kConeHumanPlayerChuteSetpoint = 54;
    public static final int kConeHighAutoSetpoint = 180;
    public static final int kConeHighTeleopSetpoint = 190;

    // Cube Setpoints
    public static final int kCubeCarrySetpoint = 24;
    public static final int kCubeAcqFloorSetpoint = 75;
    public static final int kCubeMidSetpoint = 106;
    public static final int kCubeHumanPlayerSetpoint = 25;
    public static final int kCubeAcqChuteSetpoint = 54;
    public static final int kCubeHighSetpoint = 148;
}
