package frc.robot.constants;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = DriveConstants.kMaxSpeedMetersPerSecond;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1.0;
    public static final double kPYController = 2.0;
    public static final double kPThetaController = 1.5;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
}
