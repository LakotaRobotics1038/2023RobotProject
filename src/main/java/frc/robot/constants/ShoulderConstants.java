package frc.robot.constants;

public class ShoulderConstants {
    public static final int kShoulderMotorPort = 10;
    public static final double kMaxPower = 0.75;
    public static final double kEncoderConversion = 360.0;
    public static final double kMaxDistance = 90.0;
    public static final double kTolerance = 2.0;
    public static final double kP = 0.040;
    public static final double kI = 0.000;
    public static final double kD = 0.000;

    public static final int kMinExtensionPosition = 8;

    public static final int kStorageSetpoint = 0;

    // Cone Setpoints
    public static final int kConeAcqFloorSetpoint = 25;
    public static final double kConeAcqFloorArmDelay = 1.0;
    public static final int kConeMidSetpoint = 75;
    public static final int kConeHumanPlayerSetpoint = 32;
    public static final int kConeHumanPlayerChuteSetpoint = 32;
    public static final int kConeHighAutoSetpoint = 71;
    public static final int kConeHighTeleopSetpoint = 87;
    public static final double kConeHighArmDelay = 0.15;

    // Cube Setpoints
    public static final int kCubeAcqFloorSetpoint = 12;
    public static final double kCubeAcqFloorArmDelay = 1.0;
    public static final int kCubeMidSetpoint = 50;
    public static final int kCubeHumanPlayerSetpoint = 32;
    public static final int kCubeHumanPlayerChuteSetpoint = 32;
    public static final int kCubeHighSetpoint = 78;
    public static final double kCubeHighArmDelay = 0.15;
}
