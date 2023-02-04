package frc.robot.libraries;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class FlightStick1038 extends Joystick {
    // Buttons
    public final JoystickButton trigger;
    public final JoystickButton button2;
    public final JoystickButton button3;
    public final JoystickButton button4;
    public final JoystickButton button5;
    public final JoystickButton button6;
    public final JoystickButton button7;
    public final JoystickButton button8;
    public final JoystickButton button9;
    public final JoystickButton button10;
    public final JoystickButton button11;
    public final JoystickButton button12;

    // Enums
    public enum ButtonType {
        trigger(1),
        button2(2),
        button3(3),
        button4(4),
        button5(5),
        button6(6),
        button7(7),
        button8(8),
        button9(9),
        button10(10),
        button11(11),
        button12(12);

        public final int value;

        ButtonType(int value) {
            this.value = value;
        }
    }

    public enum AxisType {
        X(0),
        Y(1),
        Z(2),
        throttle(3);

        public final int value;

        AxisType(int value) {
            this.value = value;
        }
    }

    public enum PovPositions {
        Up, Down, Left, Right, None
    }

    /**
     * Creates a new Flight Stick object
     *
     * @param port USB port the joystick should be in
     */
    public FlightStick1038(int port) {
        super(port);
        trigger = new JoystickButton(this, ButtonType.trigger.value);
        button2 = new JoystickButton(this, ButtonType.button2.value);
        button3 = new JoystickButton(this, ButtonType.button3.value);
        button4 = new JoystickButton(this, ButtonType.button4.value);
        button5 = new JoystickButton(this, ButtonType.button5.value);
        button6 = new JoystickButton(this, ButtonType.button6.value);
        button7 = new JoystickButton(this, ButtonType.button7.value);
        button8 = new JoystickButton(this, ButtonType.button8.value);
        button9 = new JoystickButton(this, ButtonType.button9.value);
        button10 = new JoystickButton(this, ButtonType.button10.value);
        button11 = new JoystickButton(this, ButtonType.button11.value);
        button12 = new JoystickButton(this, ButtonType.button12.value);

    }

    /**
     * @deprecated
     *             Throws an error, use {@link #getPOVPosition()}
     */
    @Deprecated
    public int getPOV() {
        throw new Error("Use getPOVPosition");
    }

    public PovPositions getPOVPosition() {
        int povVal = super.getPOV();
        switch (povVal) {
            case 0:
                return PovPositions.Up;
            case 90:
                return PovPositions.Right;
            case 180:
                return PovPositions.Down;
            case 270:
                return PovPositions.Left;
            default:
                return PovPositions.None;
        }
    }

    /**
     * Returns the state of the trigger button on the flight stick
     *
     * @return is the trigger button pressed
     */
    public boolean getTrigger() {
        return getRawButton(ButtonType.trigger.value);
    }

    /**
     * Returns the state of button 2 on the flight stick
     *
     * @return is the button 2 pressed
     */
    public boolean getButton2() {
        return getRawButton(ButtonType.button2.value);
    }

    /**
     * Returns the state of button 3 on the flight stick
     *
     * @return is the button 3 pressed
     */
    public boolean getButton3() {
        return getRawButton(ButtonType.button3.value);
    }

    /**
     * Returns the state of button 4 on the flight stick
     *
     * @return is the button 4 pressed
     */
    public boolean getButton4() {
        return getRawButton(ButtonType.button4.value);
    }

    /**
     * Returns the state of button 5 on the flight stick
     *
     * @return is the button 5 pressed
     */
    public boolean getButton5() {
        return getRawButton(ButtonType.button5.value);
    }

    /**
     * Returns the state of button 6 on the flight stick
     *
     * @return is the button 6 pressed
     */
    public boolean getButton6() {
        return getRawButton(ButtonType.button6.value);
    }

    /**
     * Returns the state of button 7 on the flight stick
     *
     * @return is the button 7 pressed
     */
    public boolean getButton7() {
        return getRawButton(ButtonType.button7.value);
    }

    /**
     * Returns the state of button 8 on the flight stick
     *
     * @return is the button 8 pressed
     */
    public boolean getButton8() {
        return getRawButton(ButtonType.button8.value);
    }

    /**
     * Returns the state of button 9 on the flight stick
     *
     * @return is the button 9 pressed
     */
    public boolean getButton9() {
        return getRawButton(ButtonType.button9.value);
    }

    /**
     * Returns the state of button 10 on the flight stick
     *
     * @return is the button 10 pressed
     */
    public boolean getButton10() {
        return getRawButton(ButtonType.button10.value);
    }

    /**
     * Returns the state of button 11 on the flight stick
     *
     * @return is the button 11 pressed
     */
    public boolean getButton11() {
        return getRawButton(ButtonType.button11.value);
    }

    /**
     * Returns the state of button 12 on the flight stick
     *
     * @return is the button 12 pressed
     */
    public boolean getButton12() {
        return getRawButton(ButtonType.button12.value);
    }

    /**
     * Returns the joystick axis value or 0 if less than deadband
     *
     * @return value of input axis, after deadband
     */
    public double deadband(double value) {
        return MathUtil.applyDeadband(value, 0.10);
    }

    /**
     * Returns the state of the x axis of the flight stick
     *
     * @return value of the x axis
     */
    public double getXAxis() {
        return deadband(getRawAxis(AxisType.X.value));
    }

    /**
     * Returns the state of the y axis of the flight stick
     *
     * @return value of the y axis, inverted so positive values are stick forward
     */
    public double getYAxis() {
        return deadband(getRawAxis(AxisType.Y.value));
    }

    /**
     * Returns the state of the z axis of the flight stick
     *
     * @return value of the z axis
     */
    public double getZAxis() {
        return deadband(getRawAxis(AxisType.Z.value));
    }

    /**
     * Returns the state of the flight sick throttle
     *
     * @return value of the flight sick throttle
     */
    public double getThrottleAxis() {
        return deadband(getRawAxis(AxisType.throttle.value));
    }
}
