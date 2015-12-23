package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.drive.Tank;
import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;

public class RobotSetup {

    private DcMotor frontLeft, frontRight, backLeft, backRight, arm1, arm2;
    private Servo Lservo, Rservo;
    private DeviceInterfaceModule cdim;
    public ModernRoboticsI2cGyro G;
    private LED extLED;

    //declare Reverse Variable
    boolean reverseVal = false;

    //declare motor control variables
    private int leftEncoderDistance;
    private int rightEncoderDistance;
    double heading;
    private Telemetry telemetry;

    RobotSetup(HardwareMap hardwareMap, Telemetry _telemetry) {
            G = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
            cdim        = hardwareMap.deviceInterfaceModule.get("dim");
            //extLED      = hardwareMap.led.get("LED");
            frontLeft   = hardwareMap.dcMotor.get("4");
            frontRight  = hardwareMap.dcMotor.get("2");
            backLeft    = hardwareMap.dcMotor.get("3");
            backRight   = hardwareMap.dcMotor.get("1");
            arm1        = hardwareMap.dcMotor.get("5");
            arm2        = hardwareMap.dcMotor.get("6");
            Lservo      = hardwareMap.servo.get("s2");
            Rservo      = hardwareMap.servo.get("s1");
            telemetry   = _telemetry; //No idea what this does, ask suitbots?
    }
    //Let's have some fun(ctions) -ta

    //--------------------------------MOVEMENT FUNCTIONS
    public void move(double l, double r) {
            Tank.motor4(frontLeft, frontRight, backLeft, backRight, -l, r);}  //tested

    public void    reverse()   {reverseVal = !reverseVal;}  //tested
    public boolean isreversed(){return reverseVal;}  //tested


    //Set Arm Motor Positions with these.
    //left  servo down position = 1
    //right servo down position = 0
    public void servoL      (double position) {Lservo.setPosition(position);}
    public void servoR      (double position) {Rservo.setPosition(position);}
    public void moveTape    (double power)    {arm2.setPower(power);}
    public void moveWinch   (double power)    {arm1.setPower(power);}

    //lDistance and rDistance will now return distance
    //    from the point where this function is run.
    // TODO ALL OF THIS IS UNTESTED
    public void resetEncoders(){
        leftEncoderDistance  = backLeft.getCurrentPosition();
        rightEncoderDistance = backRight.getCurrentPosition();
    }
    public int lDistance (){
        return backLeft.getCurrentPosition() - leftEncoderDistance;
    }
    public int rDistance(){
        return backRight.getCurrentPosition() - rightEncoderDistance;
    }


    //--------------------------------LIGHT FUNCTIONS
    public void blueLED (boolean state){cdim.setLED(0, state);} //tested
    public void redLED  (boolean state){cdim.setLED(1, state);} //tested

    //--------------------------------GYRO FUNCTIONS
    public void gyroInit(){
        G.calibrate();
        while (G.isCalibrating()){
            telemetry.addData("Gyro","Calibrating");
            blueLED(true);
        }
        blueLED(false);
        telemetry.addData("Gyro", "Calibrated");

    }
    //--------------------------------TELEMETRY FUNCTIONS
    public void defaultTelemetry(){ //BE SURE TO INCLUDE THIS IN EVERY loop()!
        heading = G.getIntegratedZValue();
        telemetry.addData("Gyro",       heading);
        telemetry.addData("reversed",   isreversed());
        telemetry.addData("Encoder L",  lDistance());
        telemetry.addData("Encoder R",  rDistance());
    }
    public void end(){
        blueLED(false);
        redLED(false);
        move(0, 0);
        moveTape(0);
        moveWinch(0);
    }
    //--------------------------------MISC FUNCTIONS
    public double accel(double input, double target){ //TODO NOT TESTED AT ALL
        if ( Math.abs(target) < Math.abs(input)){
                target += 0.05 * (int)Math.signum(target);
        }
        return target;
    }

    /*hopefully 'softens' the joystick output to prevent motors from breaking.
    * should probably only go in a loop() function.
    * should look like:
    * double output = 0;
    * output = bot.accel(one.joystick_left_y, output)
    * */
}