package com.qualcomm.ftcrobotcontroller.opmodes;

        import com.lasarobotics.library.controller.Controller;
        import com.lasarobotics.library.drive.Tank;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;

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

        double lservovalue;
        double rservovalue;
        double left;
        double right;
        double tape;
        float winch;

        //--------------------------------ARM
        if (gamepad2.left_trigger == 1) tape = 0.2;
        else if (gamepad2.left_bumper) tape = -0.2;
        else tape = gamepad2.left_stick_y;

        if (gamepad2.right_trigger == 1) winch = 1;
        else if (gamepad2.right_bumper) winch = -1;
        else winch = 0;

        //--------------------------------SERVOS
        if (one.b == 1) lservovalue = 0;
        else lservovalue = 1;

        if (one.x == 1) rservovalue = 0;
        else rservovalue = 1;

        //--------------------------------DIRECTION
        if (one.a == 1) reverse = -reverse;
        left = one.left_stick_y;
        right = one.right_stick_y;

        Tank.motor4(frontLeft, frontRight, backLeft, backRight, -left, right);

        arm2.setPower(tape);
        arm1.setPower(winch);
        Lservo.setPosition(lservovalue);
        Rservo.setPosition(rservovalue );

    }

    public void stop() {

    }
}
