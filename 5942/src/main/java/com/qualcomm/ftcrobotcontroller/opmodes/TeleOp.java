package com.qualcomm.ftcrobotcontroller.opmodes;

    import com.lasarobotics.library.controller.ButtonState;
    import com.lasarobotics.library.controller.Controller;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class TeleOp extends OpMode {

    //--------------------------------CONTROLLER SETUP
    Controller  one = new Controller(gamepad1),
                two = new Controller(gamepad2);
    RobotSetup  jarjar;

    @Override public void init(){
        jarjar = new RobotSetup(hardwareMap, telemetry);
        jarjar.gyroInit();//TODO light doesnt seem to work. needs more testing.

    }
    @Override public void loop() {
        //gamepads MUST be updated every loop with FTCLib.
        one.update(gamepad1);
        two.update(gamepad2);


        //--------------------------------ARM
        //Controls tape with left bumpers. If bumpers are not pressed, use the joystick value.
        if      (two.left_trigger == 1) jarjar.moveTape(0.2);
        else if (two.left_bumper  == ButtonState.HELD) jarjar.moveTape(-0.2);
        else    jarjar.moveTape(gamepad2.left_stick_y);

        //controls winch with right buttons. we don't use a joystick here.
        if      (two.right_trigger == 1) jarjar.moveWinch(1);
        else if (two.right_bumper == ButtonState.HELD) jarjar.moveWinch(-1);
        else    jarjar.moveWinch(0);;

        //--------------------------------SERVOS
        //left  down = 1.
        //right down = 0.
        //servo is pulled down by holding a button, and goes back up when released.
        if (two.x == ButtonState.HELD) jarjar.servoL(1); else jarjar.servoL(0);
        if (two.b == ButtonState.HELD) jarjar.servoR(0); else jarjar.servoR(1);

        //--------------------------------DIRECTION
        //This reverses our robot, so what was once our back is now our front.
        //call reverse() to switch directions, and check reverseVal for current state.
        if (one.x == ButtonState.RELEASED) jarjar.reverse();
        jarjar.redLED(jarjar.isreversed());

        if (jarjar.isreversed()) { //assigns left  to right and flips directions.
            jarjar.move(-one.right_stick_y, -one.left_stick_y);  //TODO CHECK THIS '-' SIGN
        } else {
            jarjar.move (one.left_stick_y, one.right_stick_y);
        }
        jarjar.defaultTelemetry();
    }
    public void stop(){
        jarjar.end();
    }
}
