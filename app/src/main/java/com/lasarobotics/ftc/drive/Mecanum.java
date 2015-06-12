package com.lasarobotics.ftc.drive;

import com.lasarobotics.ftc.utils.MathUtil;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Arrays;

/**
 * Methods for the Mecanum multi-directional drive train
 */
public final class Mecanum {
    /**
     * Implements the Arcade drive train with three axis and four motors.
     * @param y The y-axis of the controller, forward/rev
     * @param x The x-axis of the controller, strafe
     * @param c The spin axis of the controller
     * @param leftFront The motor on the front left
     * @param rightFront The motor on the front right
     * @param leftBack The motor on the back left
     * @param rightBack The motor on the back right
     */
    public static void Arcade(double y,double x, double c,DcMotor leftFront,DcMotor rightFront,DcMotor leftBack, DcMotor rightBack){
        double leftFrontVal = y + x + c;
        double rightFrontVal = y - x - c;
        double leftBackVal  = y - x + c;
        double rightBackVal  = y + x - c;

        //Move range to between 0 and +1, if not already
        double[] wheelPowers = {rightFrontVal, leftFrontVal, leftBackVal, rightBackVal};
        Arrays.sort(wheelPowers);
        if (wheelPowers[3] > 1){
            leftFrontVal /= wheelPowers[3];
            rightFrontVal /= wheelPowers[3];
            leftBackVal /= wheelPowers[3];
            rightBackVal /= wheelPowers[3];
        }

        leftFront.setPower(leftFrontVal);
        rightFront.setPower(rightFrontVal);
        leftBack.setPower(leftBackVal);
        rightBack.setPower(rightBackVal);
    }

    /**
     * Implements the Arcade drive train with field orientation based on Gyro input
     * @param y The y-axis of the controller, forward/rev
     * @param x The x-axis of the controller, strafe
     * @param c The spin axis of the controller
     * @param gyroheading The current normalized gyro heading (between 0 and 360)
     * @param leftFront The motor on the front left
     * @param rightFront The motor on the front right
     * @param leftBack The motor on the back left
     * @param rightBack The motor on the back right
     */
    public static void Arcade_FieldOriented(double y,double x, double c, double gyroheading,DcMotor leftFront,DcMotor rightFront,DcMotor leftBack, DcMotor rightBack){
        double cosA = Math.cos(Math.toRadians(MathUtil.normalizeGyro(gyroheading)));
        double sinA = Math.sin(Math.toRadians(MathUtil.normalizeGyro(gyroheading)));
        double xOut = x * cosA - y * sinA;
        double yOut = x * sinA + y * cosA;
        Arcade(yOut, xOut, c, leftFront, rightFront, leftBack, rightBack);
    }
}
