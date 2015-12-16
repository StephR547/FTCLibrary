package com.qualcomm.ftcrobotcontroller.opmodes;

        import com.lasarobotics.library.controller.Controller;
        import com.lasarobotics.library.drive.Tank;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.Range;

public class BasicOpMode extends OpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight;
    Controller one;
    DcMotor arm1;
    DcMotor arm2;
    Servo Lservo;
    Servo Rservo;
    public double reverse = 1;

    public void init() {
        frontLeft = hardwareMap.dcMotor.get("4");
        frontRight = hardwareMap.dcMotor.get("2");
        backLeft = hardwareMap.dcMotor.get("3");
        backRight = hardwareMap.dcMotor.get("1");
        arm1 = hardwareMap.dcMotor.get("5");
        arm2 = hardwareMap.dcMotor.get("6");
        Lservo = hardwareMap.servo.get("s1");
        Rservo = hardwareMap.servo.get("s2");

        one = new Controller(gamepad1);
        
    }

    public void loop() {
        one.update(gamepad1);

        double left;
        double right;
        double slow;
        double tape;
        float winch;
        double Lservo_pos;
        double Rservo_pos;

        left = one.left_stick_y;
        right = one.right_stick_y;

        Tank.motor4(frontLeft, frontRight, backLeft, backRight, one.left_stick_y, one.right_stick_y);


        //--------------------------------SLOW
        if (gamepad1.left_bumper)  {
            slow =  1.0;
        }else slow = 0.3;

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
            frontRight.setPower(-left);
            frontLeft.setPower(-right);
            backLeft.setPower(-right);
            backRight.setPower(-left);
        } else {
            frontRight.setPower(right);
            frontLeft.setPower(left);
            backLeft.setPower(left);
            backRight.setPower(right);
        }


        arm2.setPower(tape);
        arm1.setPower(winch);
        Lservo.setPosition(Lservo_pos);
        Rservo.setPosition(Rservo_pos);

    }

    public void stop() {

    }
}
