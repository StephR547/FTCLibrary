package com.qualcomm.ftcrobotcontroller.opmodes;

        import com.lasarobotics.library.controller.ButtonState;
        import com.lasarobotics.library.controller.Controller;
        import com.lasarobotics.library.drive.Tank;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.Range;

public class BasicOpMode extends OpMode {
    double Lservopos;
    double Rservopos;


    DcMotor frontLeft, frontRight, backLeft, backRight;
    Controller one;
    Controller two;
    DcMotor arm1;
    DcMotor arm2;
    Servo Lservo;
    Servo Rservo;

    public double reverse = 1;

    @Override
    public void init() {
        //assign variables to hardware from config
        frontLeft = hardwareMap.dcMotor.get("4");
        frontRight = hardwareMap.dcMotor.get("2");
        backLeft = hardwareMap.dcMotor.get("3");
        backRight = hardwareMap.dcMotor.get("1");
        arm1 = hardwareMap.dcMotor.get("5");
        arm2 = hardwareMap.dcMotor.get("6");
        Lservo = hardwareMap.servo.get("s2"); // channel 6
        Rservo = hardwareMap.servo.get("s1"); // channel 1

        one = new Controller(gamepad1);
        two = new Controller(gamepad2);

        //left-  down = 1.
        //right- down = 0
        Lservopos = 0;
        Rservopos = 1;
    }
    @Override
    public void loop() {
        one.update(gamepad1);
        two.update(gamepad2);

        double left;
        double right;
        double tape;
        float winch;

        //--------------------------------ARM
        // --MUY IMPORTANTE-- use != 0 instead of == 1, it works better for some reason.
        if      (two.left_trigger != 0) tape = 0.2;
        else if (two.left_bumper != 0) tape = -0.2;
        else     tape = gamepad2.left_stick_y;

        if      (two. right_trigger != 0) winch = 1;
        else if (two.right_bumper != 0) winch = -1;
        else     winch = 0;

        //--------------------------------SERVOS
        //left-  down = 1.
        //right- down = 0
        if (two.x != 0) Lservopos = 1; else Lservopos = 0;
        if (two.a != 0) Rservopos = 0; else Rservopos = 1;

        Lservo.setPosition(Lservopos);
        Rservo.setPosition(Rservopos);
        //--------------------------------DIRECTION
        if (one.x != 0) reverse = -reverse;
        left  = reverse * one.left_stick_y;
        right = reverse * one.right_stick_y;

        Tank.motor4(frontLeft, frontRight, backLeft, backRight, -left, right);

        arm2.setPower(tape);
        arm1.setPower(winch);
    }

    public void stop() {

    }
}
