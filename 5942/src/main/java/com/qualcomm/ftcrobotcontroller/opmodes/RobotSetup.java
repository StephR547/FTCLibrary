package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.drive.Tank;
import com.lasarobotics.library.options.OptionMenu;
import com.lasarobotics.library.options.SingleSelectCategory;
import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;

public class RobotSetup {

    //We want these to be private because we never directly tell them what to do.
    //for control, we use move(), moveWinch(), etc. and not (arm1 = 1.0)
    //Gyro is public because sometimes I call bot.G.calibrate().
    //Could be made private once we have functions for every gyro use.
    //similarly, menu is public because we call bot.menu.show()
    private DcMotor frontLeft, frontRight, backLeft, backRight, arm1, arm2;
    private Servo Lservo, Rservo;
    private DeviceInterfaceModule cdim;
    public ModernRoboticsI2cGyro G;
    public OptionMenu allianceMenu;
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

        OptionMenu.Builder builder = new
                OptionMenu.Builder(hardwareMap.appContext);
        SingleSelectCategory alliance =
                new SingleSelectCategory("alliance");
        alliance.addOption("Red");
        alliance.addOption("Blue");
        builder.addCategory(alliance);
        allianceMenu = builder.create();
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
    public void servoL     (double position) {Lservo.setPosition(position);}
    public void servoR     (double position) {Rservo.setPosition(position);}
    public void moveTape   (double power)    {arm2.setPower(power);}
    public void moveWinch  (double power)    {arm1.setPower(power);}

    //lDistance and rDistance will now return distance
    //    from the point where this function is run.
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

    public void encoderMove(int ticks, double power){//tested
        resetEncoders();   //set encoders to 0.
        move(power,power); //start moving BEFORE while loop. see gTurn().
        while (Math.abs(lDistance()) < Math.abs(ticks)
            || Math.abs(rDistance()) < Math.abs(ticks)){
            telemetry.addData("Status","Driving");
        }
        move(0,0);
        telemetry.addData("Status", "Stopped");
    }

    //--------------------------------LIGHT FUNCTIONS
    public void blueLED (boolean state){cdim.setLED(0, state);} //tested
    public void redLED  (boolean state){cdim.setLED(1, state);} //tested

    //--------------------------------GYRO FUNCTIONS
    public int heading(){//just shorthand for our Integrated Z value.
        return G.getIntegratedZValue();
    }
    public void gTurn(int degrees, double power){ //tested
        G.resetZAxisIntegrator();               //Reset Gyro
        float direction = Math.signum(degrees); //get +/- sign of target

        move(-direction * power, direction * power);
        //move in the right direction
        //we start moving BEFORE the while loop.
        while ( Math.abs(heading()) < Math.abs(degrees)){
                //if we move IN the while loop, the app crashes.
                // we need to have something inside the loop.
                //possibly needs waitOnHardwareCycle() implemented somehow.
            telemetry.addData("Status","Turning");
            //do some BS telemetry to avoid a blank loop.
        }
        move(0, 0);
        resetEncoders();
    }
    //--------------------------------TELEMETRY FUNCTIONS
    public void defaultTelemetry(){
        telemetry.addData("Gyro", heading());
        telemetry.addData("reversed", isreversed());
        telemetry.addData("Encoder L", lDistance());
        telemetry.addData("Encoder R", rDistance());
    }
    //--------------------------------MISC FUNCTIONS
    public String getAlliance(){
        //catch{} prevents app from crashing when alliance is unselected.
        try {
            allianceMenu.selectedOption("alliance");
        } catch (IllegalArgumentException e){
            return "None";
        }
        return allianceMenu.selectedOption("alliance");
    }
    public boolean isRed(){
        return (getAlliance().equals("Red"));
    }
    public boolean isBlue(){
        return (getAlliance().equals("Blue"));
    }

    public void startRobot() {
        blueLED(false);
        redLED(false);
        move(0, 0);
        moveTape(0);
        moveWinch(0);
        resetEncoders();
    }
    public void end(){
        //most of this probably isn't necessary,
        // but I'm doing it anyway.
        blueLED(false);
        redLED(false);
        resetEncoders();
        move(0, 0);
        moveTape(0);
        moveWinch(0);
    }
}