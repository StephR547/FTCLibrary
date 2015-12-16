
package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TestCode extends OpMode {

    final static double MOTOR_POWER = 0.15; // Higher values will cause the robot to move faster

    DcMotor FRmotor;
    DcMotor FLmotor;
    DcMotor BRmotor;
    DcMotor BLmotor;
    DcMotor arm1;
    DcMotor arm2;


    private String startDate;
    private ElapsedTime runtime = new ElapsedTime();



    public TestCode() {


    }

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

        BRmotor = hardwareMap.dcMotor.get("6");
        FRmotor = hardwareMap.dcMotor.get("5");

        arm1 = hardwareMap.dcMotor.get("3");
        arm2 = hardwareMap.dcMotor.get("4");


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

        float left  = gamepad1.left_stick_y;
        float right = gamepad1.right_stick_y;
        float slow;
        float tape;
        float winch;
    //--------------------------------SLOW
            if (gamepad1.y) {
                slow = (float) -0.5;
            }else if (gamepad1.a) {
                slow = (float) 0.5;
            } else {
                slow = 0;
            }

        right = Range.clip(right + slow, -1, 1);
        left  = Range.clip(left + slow, -1, 1);

        //--------------------------------ARM
        if (gamepad2.y) {
            tape = 1;
        } else if (gamepad2.a) {
            tape = -1;
        } else {
            tape = 0;
        }

        if (gamepad2.left_trigger == 1) {
            winch = 1;
        } else if (gamepad2.left_bumper) {
            winch = -1;
        } else {
            winch = 0;
        }



        // write the values to the motors
        FRmotor.setPower(right);
        FLmotor.setPower(left);
        BLmotor.setPower(left);
        BRmotor.setPower(right);
        arm2.setPower(winch);
        arm1.setPower(tape);

        telemetry.addData("1 Start", "TeleOp started at " + startDate);
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
