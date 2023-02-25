package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwagLights extends SubsystemBase {
    // Enums
    public enum RobotStates {
        Enabled("A"),
        Disabled("D"),
        EmergencyStop("E");

        public final String value;

        private RobotStates(String value) {
            this.value = value;
        }
    }

    // Inputs and Outputs
    private SerialPort serialPort;

    // States
    private boolean eStopped = false;
    private boolean disabled = true;

    // Singleton Setup
    private static SwagLights instance;

    public static SwagLights getInstance() {
        if (instance == null) {
            instance = new SwagLights();
        }
        return instance;
    }

    /**
     * Initializes the serial communication
     */
    private SwagLights() {
        this.initialize();
    }

    /**
     * Creates serial port listener
     */
    public void initialize() {
        serialPort = new SerialPort(9600, SerialPort.Port.kMXP);
        serialPort.enableTermination();
        System.out.println("Created new serial reader");
    }

    /**
     * Use the state of the robot to send a command to the LEDs
     *
     * @param state Current state of the robot
     */
    public void setRobotState(RobotStates state) {
        serialPort.writeString(state.value);
    }

    @Override
    public void periodic() {
        if (eStopped) {
            this.setRobotState(RobotStates.EmergencyStop);
        } else if (disabled) {
            this.setRobotState(RobotStates.Disabled);
        }
    }

    /**
     * Closes serial port listener
     */
    public void stopSerialPort() {
        System.out.println("Closing serial port");
        serialPort.close();
    }

    /**
     * Tells the swag lights the robot is disabled
     *
     * @param isDisabled
     */
    public void setDisabled(boolean isDisabled) {
        this.disabled = isDisabled;
    }

    /**
     * Tells the swag lights the robot is e-stopped
     */
    public void setEStop() {
        this.eStopped = true;
    }
}
