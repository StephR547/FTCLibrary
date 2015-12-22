package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.drive.Tank;
import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;

public class RobotSetup {

    DcMotor frontLeft, frontRight, backLeft, backRight, arm1, arm2;
    Servo Lservo, Rservo;
    DeviceInterfaceModule cdim;
    ModernRoboticsI2cGyro G;

    //declare Reverse Variable
    boolean reverseVal = false;

    //declare motor control variables
    double Lservopos;
    double Rservopos;
    double left;
    double right;
    double heading;
    private Telemetry telemetry;

    RobotSetup(HardwareMap hardwareMap, Telemetry _telemetry) throws InterruptedException {
        telemetry = _telemetry; //No idea what this does, ask suitbots?
        G = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        cdim = hardwareMap.deviceInterfaceModule.get("dim");
        frontLeft = hardwareMap.dcMotor.get("4");
        frontRight = hardwareMap.dcMotor.get("2");
        backLeft = hardwareMap.dcMotor.get("3");
        backRight = hardwareMap.dcMotor.get("1");
        arm1 = hardwareMap.dcMotor.get("5");
        arm2 = hardwareMap.dcMotor.get("6");
        Lservo = hardwareMap.servo.get("s2");
        Rservo = hardwareMap.servo.get("s1");
        //left  servo down position = 1
        //right servo down position = 0
        Lservopos = 0;
        Rservopos = 1;

    }
    //--------------------------------MOVEMENT FUNCTIONS
    public void move(double l, double r) {
            Tank.motor4(frontLeft, frontRight, backLeft, backRight, -l, r);

    }
    public void servoL(double position) {
        Lservo.setPosition(position);
    }
    public void moveTape(double power) {
        arm2.setPower(power);
    }
    public void moveWinch(double power) {
        arm1.setPower(power);
    }
    public boolean reverse(){
        reverseVal = !reverseVal;
        return reverseVal;
    }
    //--------------------------------LIGHT FUNCTIONS
    public boolean blueLED(boolean state){
        cdim.setLED(0,state);
        return cdim.getLEDState(0);
    }
    public boolean redLED(boolean state){
        cdim.setLED(1, state);
        return cdim.getLEDState(1);
    }
    //--------------------------------GYRO FUNCTIONS
    public void gyroInit(){
        while (G.isCalibrating()){
            blueLED(true);
        }
        blueLED(false);
    }
}