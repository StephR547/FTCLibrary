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
            Tank.motor4(frontLeft, frontRight, backLeft, backRight, l, -r);}  //tested

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
        leftEncoderDistance  = frontLeft.getCurrentPosition();
        rightEncoderDistance = frontRight.getCurrentPosition();
    }
    public int lDistance (){
        return frontLeft.getCurrentPosition() - leftEncoderDistance;
    }
    public int rDistance(){
        return rightEncoderDistance - frontRight.getCurrentPosition();
    }

    public void encoderMove(int ticks, double power){//TODO Not tested going backwards.
        resetEncoders();
        move(power,power);
        while (Math.abs(lDistance()) < Math.abs(ticks)
            || Math.abs(rDistance()) < Math.abs(ticks)){
            telemetry.addData("Status","Driving");
        }
        move(0,0);
        telemetry.addData("Status","Stopped");
    }

    //--------------------------------LIGHT FUNCTIONS
    public void blueLED (boolean state){cdim.setLED(0, state);} //tested
    public void redLED  (boolean state){cdim.setLED(1, state);} //tested

    //--------------------------------GYRO FUNCTIONS
    public int heading(){
        return G.getIntegratedZValue();
    }
    public void gTurn(int degrees, double power){ //TODO test turning left (negative values)
        G.resetZAxisIntegrator();
        float direction = Math.signum(degrees);
        move(-direction * power, direction * power);
        while (Math.abs(heading()) <Math.abs(degrees)){
            telemetry.addData("Status","Turning");
        }
        move(0,0);
        resetEncoders();
    }
    //--------------------------------TELEMETRY FUNCTIONS
    public void defaultTelemetry(){ //BE SURE TO INCLUDE THIS IN EVERY loop()!
        telemetry.addData("Gyro",      heading());
        telemetry.addData("reversed",  isreversed());
        telemetry.addData("Encoder L", lDistance());
        telemetry.addData("Encoder R", rDistance());
    }
    //--------------------------------MISC FUNCTIONS
    public void end(){
        //most of this probably isn't necessary, but I'm doing it anyway.
        blueLED(false);
        redLED(false);
        resetEncoders();
        move(0, 0);
        moveTape(0);
        moveWinch(0);
    }
    public void startRobot() {
        blueLED(false);
        redLED(false);
        move(0, 0);
        moveTape(0);
        moveWinch(0);
        resetEncoders();
    }
    /*
     public double accel(double input, double target){ //TODO NOT TESTED AT ALL
        if ( Math.abs(target) < Math.abs(input)){
                target += 0.05 * (int)Math.signum(target);
        }
        return target;
    }
    hopefully 'softens' the joystick output to prevent motors from breaking.
    * should probably only go in a loop() function.
    * should look like:
    * double output = 0;
    * output = bot.accel(one.joystick_left_y, output)
    * */
}