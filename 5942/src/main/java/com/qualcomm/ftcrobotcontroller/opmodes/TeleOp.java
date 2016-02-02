package com.qualcomm.ftcrobotcontroller.opmodes;

    import com.lasarobotics.library.controller.ButtonState;
    import com.lasarobotics.library.controller.Controller;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
/**/

public class TeleOp extends OpMode {

    //--------------------------------CONTROLLER SETUP
    Controller  one = new Controller(gamepad1),
                two = new Controller(gamepad2);
    RobotSetup  fetty;

    @Override public void init(){
<<<<<<< HEAD
        jarjarbling = new RobotSetup(hardwareMap, telemetry);
        jarjarbling.startRobot(); //TODO light doesnt seem to work. needs more testing.
        jarjarbling.colorSensor.enableLed(false);
=======
        fetty = new RobotSetup(hardwareMap, telemetry);
        fetty.startRobot(); //TODO light doesnt seem to work. needs more testing.
>>>>>>> 8dfc88ad2feb13b05ee5f463431cb6d493590f1e

    }
    @Override public void loop() {
        //gamepads MUST be updated every loop with FTCLib.
        one.update(gamepad1);
        two.update(gamepad2);

        //--------------------------------ARM
        //Controls tape with left bumpers. If bumpers are not pressed, use the joystick value.
        if      (two.right_trigger == 1) fetty.moveTape(-0.2);
        else if (two.right_bumper  == ButtonState.HELD) fetty.moveTape(0.2);
        else    fetty.moveTape(gamepad2.left_stick_y);

        //controls winch with right buttons. we don't use a joystick here.
        if      (two.left_trigger == 1) fetty.moveWinch(1);
        else if (two.left_bumper == ButtonState.HELD) fetty.moveWinch(-1);
        else    fetty.moveWinch(0);

        //--------------------------------SERVOS
        //left  down = 1.
        //right down = 0.
        //servo is pulled down by holding a button, and goes back up when released.
<<<<<<< HEAD
        if (one.x == ButtonState.HELD) jarjarbling.servoL(1); else jarjarbling.servoL(0);
        if (one.b == ButtonState.HELD) jarjarbling.servoR(0); else jarjarbling.servoR(1);
        if (one.y == ButtonState.HELD) jarjarbling.arm(0); else jarjarbling.arm(1);

=======
        //if (two.x == ButtonState.HELD) fetty.servoL(1); else fetty.servoL(0);
        //if (two.b == ButtonState.HELD) fetty.servoR(0); else fetty.servoR(1);
>>>>>>> 8dfc88ad2feb13b05ee5f463431cb6d493590f1e

        //--------------------------------DIRECTION
        //This reverses our robot, so what was once our back is now our front.
        //call reverse() to switch directions, and check reverseVal for current state.
        if (one.x == ButtonState.RELEASED) fetty.reverse();
        fetty.redLED(fetty.isreversed());

        if (fetty.isreversed()) { //assigns left  to right and flips directions.
            fetty.move(-one.right_stick_y, -one.left_stick_y);
        } else {
            fetty.move (one.left_stick_y, one.right_stick_y);
        }

        if (one.y == ButtonState.RELEASED) fetty.resetEncoders();


<<<<<<< HEAD
        jarjarbling.defaultTelemetry();
=======
        fetty.blueLED(fetty.bumper());


        telemetry.addData("bumper",fetty.bumper());
        fetty.defaultTelemetry();
>>>>>>> 8dfc88ad2feb13b05ee5f463431cb6d493590f1e
    }
    public void stop(){
        fetty.end();
    }
}
