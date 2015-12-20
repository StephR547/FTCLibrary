package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class GyroAuton extends OpMode {

    ModernRoboticsI2cGyro G = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");

    DcMotor frontLeft, frontRight, backLeft, backRight;
    DcMotor arm1;
    DcMotor arm2;
    Servo Lservo;
    Servo Rservo;
    public void init() {
        //assign variables to hardware from config
        frontLeft = hardwareMap.dcMotor.get("4");
        frontRight = hardwareMap.dcMotor.get("2");
        backLeft = hardwareMap.dcMotor.get("3");
        backRight = hardwareMap.dcMotor.get("1");
        arm1 = hardwareMap.dcMotor.get("5");
        arm2 = hardwareMap.dcMotor.get("6");
        Lservo = hardwareMap.servo.get("s2");
        Rservo = hardwareMap.servo.get("s1");

        G.calibrate();
    }
    public void loop() {

        telemetry.addData("Heading ", G.getHeading());
        telemetry.addData("Integrated ", G.getIntegratedZValue());
    }
    public void stop(){}
}
