
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ServoCode extends OpMode {

    final static double MOTOR_POWER = 0.15; // Higher values will cause the robot to move faster

    DcMotor FRmotor;
    DcMotor FLmotor;
    DcMotor BRmotor;
    DcMotor BLmotor;
    DcMotor arm1;
    DcMotor arm2;
    Servo Lservo;
    Servo Rservo;

    private String startDate;
    private ElapsedTime runtime = new ElapsedTime();
    public double reverse = 1;

    @Override
    public void init() {
        startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        runtime.reset();

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

        Rservo.setDirection(Servo.Direction.REVERSE);
        arm2.setDirection(DcMotor.Direction.REVERSE);
        FLmotor.setDirection(DcMotor.Direction.REVERSE);
        BLmotor.setDirection(DcMotor.Direction.REVERSE);
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
        double tape;
        float winch;
        double Lservo_pos;
        double Rservo_pos;


        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;

        //--------------------------------SLOW
        if (gamepad1.left_bumper)  {
            slow =  1.0;
        }else slow = 0.3;

        right = Range.clip(right * slow, -1, 1);
        left  = Range.clip(left * slow, -1, 1);

        //--------------------------------ARM
        if (gamepad2.left_trigger == 1) tape = 0.2;
        else if (gamepad2.left_bumper) tape = -0.2;
        else tape = gamepad2.left_stick_y;

        if (gamepad2.right_trigger == 1) winch = 1;
        else if (gamepad2.right_bumper) winch = -1;
        else winch = 0;

        //--------------------------------SERVOS
        if (gamepad2.x) Lservo_pos = 0;
        else Lservo_pos = 1;

        if (gamepad2.b) Rservo_pos = 0;
        else Rservo_pos = 1;

        //--------------------------------DIRECTION
        if (gamepad1.x) reverse = -reverse;
        if (reverse < 0){
            FRmotor.setPower(-left);
            FLmotor.setPower(-right);
            BLmotor.setPower(-right);
            BRmotor.setPower(-left);
        } else {
            FRmotor.setPower(right);
            FLmotor.setPower(left);
            BLmotor.setPower(left);
            BRmotor.setPower(right);
        }


        // write the values to the motors
        arm2.setPower(tape);
        arm1.setPower(winch);
        Lservo.setPosition(Lservo_pos);
        Rservo.setPosition(Rservo_pos);
        telemetry.addData("1 Start", "TeleOp started at " + runtime);
        telemetry.addData("joyjoy", "joy status " + gamepad1.left_stick_y);
    }
    /*
    * Code to run when the op mode is first disabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
    */
    @Override
    public void stop() {

    }
}
