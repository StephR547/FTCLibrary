package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.drive.Tank;
import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;

public class RobotSetup {

    private DcMotor frontLeft, frontRight, backLeft, backRight, arm1, arm2;
    private Servo Lservo, Rservo;
    private DeviceInterfaceModule cdim;
    public ModernRoboticsI2cGyro G;

    //declare Reverse Variable
    boolean reverseVal = false;

    //declare motor control variables
    double Lservopos;
    double Rservopos;
    double heading;
    private Telemetry telemetry;

    RobotSetup(HardwareMap hardwareMap, Telemetry _telemetry) {
        try{
            G = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
            cdim        = hardwareMap.deviceInterfaceModule.get("dim");
            frontLeft   = hardwareMap.dcMotor.get("4");
            frontRight  = hardwareMap.dcMotor.get("2");
            backLeft    = hardwareMap.dcMotor.get("3");
            backRight   = hardwareMap.dcMotor.get("1");
            arm1        = hardwareMap.dcMotor.get("5");
            arm2        = hardwareMap.dcMotor.get("6");
            Lservo      = hardwareMap.servo.get("s2");
            Rservo      = hardwareMap.servo.get("s1");
        }catch (Exception p_exception){
            DbgLog.msg(p_exception.getLocalizedMessage());
        }
        telemetry   = _telemetry; //No idea what this does, ask suitbots?

        //left  servo down position = 1
        //right servo down position = 0
        Lservopos = 0;
        Rservopos = 1;


    }
    //Let's have some fun(ctions) -ta

    //--------------------------------MOVEMENT FUNCTIONS
    public void move(double l, double r) {
            Tank.motor4(frontLeft, frontRight, backLeft, backRight, -l, r);}
    public void reverse()   {reverseVal = !reverseVal;}
    public boolean isreversed(){return reverseVal;}

    public void servoL      (double position) {Lservo.setPosition(position);}
    public void servoR      (double position) {Rservo.setPosition(position);}
    public void moveTape    (double power)    {arm2.setPower(power);}
    public void moveWinch   (double power)    {arm1.setPower(power);}

    //--------------------------------LIGHT FUNCTIONS
    public void blueLED     (boolean state){cdim.setLED(0, state);}
    public void redLED      (boolean state){cdim.setLED(1, state);}

    //--------------------------------GYRO FUNCTIONS
    public void gyroInit(){
        while (G.isCalibrating()){
            telemetry.addData("Gyro","Calibrating");
            blueLED(true);
        }
        blueLED(false);
        telemetry.addData("Gyro", "Calibrated");

    }
    //--------------------------------TELEMETRY FUNCTIONS
    public void defaultTelemetry(){
        heading = G.getIntegratedZValue();
        if (!G.isCalibrating()) telemetry.addData("Gyro", heading);
        telemetry.addData("reversed",reverseVal);

    }
    public void end(){
        blueLED(false);
        redLED(false);
        move(0, 0);
        moveTape(0);
        moveWinch(0);
    }
}