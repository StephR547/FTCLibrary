package com.qualcomm.ftcrobotcontroller.opmodes;

    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.hardware.ModernRoboticsI2cGyro;
    import com.lasarobotics.library.controller.ButtonState;
    import com.lasarobotics.library.controller.Controller;
    import com.lasarobotics.library.drive.Tank;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
    import com.qualcomm.robotcore.hardware.LED;
    import com.qualcomm.robotcore.hardware.Servo;

public class BasicOpMode extends OpMode {

    //Declare Servos, motors, and controllers
    ModernRoboticsI2cGyro G;
    DcMotor frontLeft, frontRight, backLeft, backRight;
    Controller one;
    Controller two;
    DcMotor arm1;
    DcMotor arm2;
    Servo Lservo;
    Servo Rservo;

    //declare Reverse Variable
    public double reverse = -1;

    double Lservopos;
    double Rservopos;
    double heading;

    DeviceInterfaceModule cdim;
    LED light;

    public BasicOpMode() {
    }

    @Override
    public void init() {
        //DO NOT MOVE ROBOT DURING INIT OR GYRO WONT CALIBRATE
        G = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        G.calibrate();
        //assign variables to hardware from config
        frontLeft = hardwareMap.dcMotor.get("4");
        frontRight = hardwareMap.dcMotor.get("2");
        backLeft = hardwareMap.dcMotor.get("3");
        backRight = hardwareMap.dcMotor.get("1");
        arm1 = hardwareMap.dcMotor.get("5");
        arm2 = hardwareMap.dcMotor.get("6");
        Lservo = hardwareMap.servo.get("s2"); // channel 6
        Rservo = hardwareMap.servo.get("s1"); // channel 1

        cdim = hardwareMap.deviceInterfaceModule.get("dim");
        light = new LED(cdim, 0);

        one = new Controller(gamepad1);
        two = new Controller(gamepad2);

        //left  servo down position = 1
        //right servo down position = 0
        Lservopos = 0;
        Rservopos = 1;
    }
    @Override
    public void loop() {
        //gamepads MUST be updated every loop with FTClib.
        one.update(gamepad1);
        two.update(gamepad2);

        //declare motor control variables
        double left;
        double right;
        double tape;
        float winch;

        //--------------------------------ARM
        // --MUY IMPORTANTE-- use != 0 instead of == 1, it works better for some reason.

        //Controls tape with left bumpers. If bumpers are not pressed, use the joystick value.
        if      (two.left_trigger != 0) tape =  0.2;
        else if (two.left_bumper  != 0) tape = -0.2;
        else     tape = gamepad2.left_stick_y;

        //controls winch with right buttons. we don't use a joystick here.
        if      (two. right_trigger != 0) winch =  1;
        else if (two.right_bumper   != 0) winch = -1;
        else     winch = 0;

        //--------------------------------SERVOS
        //left-  down = 1.
        //right- down = 0.
        //servo is pulled down by pressing a button, and goes back up when released.
        if (two.x == ButtonState.HELD) Lservopos = 1; else Lservopos = 0;
        if (two.b == ButtonState.HELD) Rservopos = 0; else Rservopos = 1;

        Lservo.setPosition(Lservopos);
        Rservo.setPosition(Rservopos);
        //--------------------------------DIRECTION
        //This reverses our robot, so what was once our back is now our front.
        if (one.x == ButtonState.RELEASED) reverse = -reverse;

        if (reverse == 1) {
            left  = one.left_stick_y;
            right = one.right_stick_y;
            light.enable(true);
        } else {
            left  = -one.right_stick_y;
            right = -one.left_stick_y;
            light.enable(false);
        }
        //--------------------------------GYRO
        heading = G.getIntegratedZValue();
        if (one.b == ButtonState.RELEASED) G.resetZAxisIntegrator();
        if (one.back == ButtonState.RELEASED) G.calibrate();

        //--------------------------------DRIVE
        //uses FTClib Tank class. Thanks LASA!
        Tank.motor4(frontLeft, frontRight, backLeft, backRight, -left, right);

        //set arm motor power
        arm2.setPower(tape);
        arm1.setPower(winch);


        //--------------------------------TELEMETRY
        telemetry.addData("direction",reverse);
        telemetry.addData("Heading", heading);


    }

    public void stop() {

    }
}
