
package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class Auton extends OpMode {

    final static double MOTOR_POWER = 0.15; // Higher values will cause the robot to move faster

    DcMotor FRmotor;
    DcMotor FLmotor;
    DcMotor BRmotor;
    DcMotor BLmotor;
    DcMotor arm1;
    DcMotor arm2;
    Servo Lservo;
    Servo Rservo;


    public long startTime;
    public long currentTime;

    @Override
    public void init() {

    }

    /*
    * Code to run when the op mode is first enabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
    */
    @Override
    public void start() {

        BLmotor = hardwareMap.dcMotor.get("1");
        FLmotor = hardwareMap.dcMotor.get("2");

        BRmotor = hardwareMap.dcMotor.get("3");
        FRmotor = hardwareMap.dcMotor.get("4");

        arm1 = hardwareMap.dcMotor.get("5");
        arm2 = hardwareMap.dcMotor.get("6");

        Lservo = hardwareMap.servo.get("s1");
        Rservo = hardwareMap.servo.get("s2");

        arm2.setDirection(DcMotor.Direction.REVERSE);
        FLmotor.setDirection(DcMotor.Direction.REVERSE);
        BLmotor.setDirection(DcMotor.Direction.REVERSE);

        startTime = System.currentTimeMillis();
    }
    /*
    * This method will be called repeatedly in a loop
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
    */
    @Override
    public void loop() {

        double left;
        double right;
        double slow;
        float tape;
        float winch;
        double Lservo_pos;
        double Rservo_pos;

        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - startTime;

        if (deltaTime < 2000) {
            left = 1;
            right = 1;
        }
        else {
            left = 0;
            right = 0;
        }

        // write the values to the motors
        FRmotor.setPower(right);
        FLmotor.setPower(left);
        BLmotor.setPower(left);
        BRmotor.setPower(right);
//        arm2.setPower(tape);
//        arm1.setPower(winch);
//        Lservo.setPosition(Lservo_pos);
//        Rservo.setPosition(Rservo_pos);
        telemetry.addData("Motor power = ", + right);
        telemetry.addData("Delta Time", "time is" + deltaTime);



        // write the values to the motors
        //
    }
    /*
    * Code to run when the op mode is first disabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
    */
    @Override
    public void stop() {

    }
}
