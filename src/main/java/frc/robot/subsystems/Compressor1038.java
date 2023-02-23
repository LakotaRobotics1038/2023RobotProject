package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import frc.robot.constants.PneumaticsConstants;

public class Compressor1038 {
    private static final Compressor compressor = new Compressor(PneumaticsConstants.kPressureSensorPort,
            PneumaticsConstants.kModuleType);

    public static void run() {
        compressor.enableAnalog(PneumaticsConstants.kMinPressure, PneumaticsConstants.kMaxPressure);
    }
}
