package com.qualcomm.ftcrobotcontroller.opmodes;

    import com.lasarobotics.library.controller.ButtonState;
    import com.lasarobotics.library.controller.Controller;
    import com.lasarobotics.library.drive.Tank;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.hardware.LED;
    import com.qualcomm.robotcore.robocol.Telemetry;

public class TeleOp extends OpMode {

    //--------------------------------CONTROLLER SETUP
    Controller  one = new Controller(gamepad1),
                two = new Controller(gamepad2);

    RobotSetup bot = new RobotSetup(hardwareMap, telemetry);
    @Override public void init(){
        bot.gyroInit();
    }
    @Override public void loop() {
        //gamepads MUST be updated every loop with FTCLib.
        one.update(gamepad1);
        two.update(gamepad2);


        //--------------------------------ARM
        //Controls tape with left bumpers. If bumpers are not pressed, use the joystick value.
        if (two.left_trigger == ButtonState.HELD) bot.moveTape(0.2);
        else if (two.left_bumper == ButtonState.HELD) bot.moveTape(-0.2);
        else bot.moveTape(gamepad2.left_stick_y);

        //controls winch with right buttons. we don't use a joystick here.
        if (two.right_trigger == ButtonState.HELD) bot.moveWinch(1);
        else if (two.right_bumper == ButtonState.HELD) bot.moveWinch(-1);
        else bot.moveWinch(0);;

        //--------------------------------SERVOS
        //left  down = 1.
        //right down = 0.
        //servo is pulled down by pressing a button, and goes back up when released.
        if (two.x == ButtonState.HELD) bot.servoL(1);
        else bot.servoL(0);
        if (two.b == ButtonState.HELD) bot.servoL(0);
        else bot.servoL(0);

        //--------------------------------DIRECTION
        //This reverses our robot, so what was once our back is now our front.
        if (one.x == ButtonState.RELEASED) bot.reverse();

        if (bot.reverse()) {
            bot.move (one.left_stick_y, one.right_stick_y);
        } else {
            bot.move(one.right_stick_y, -one.left_stick_y);
        }

        //set arm motor power
        //find this function in RobotSetup. it just completes tasks necessary on every loop.
        //uses FTCLib Tank class. Thanks LASA!
        //send data to fones
    }

}
