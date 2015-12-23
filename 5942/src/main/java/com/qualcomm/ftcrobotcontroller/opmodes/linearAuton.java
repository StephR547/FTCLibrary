package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.options.OptionMenu;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ta on 12/21/15.
 */
public class linearAuton extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        RobotSetup bot = new RobotSetup(hardwareMap,telemetry);
        bot.redLED(true);
        bot.startRobot();
        bot.G.calibrate();

        //--------------------------------OPMODE START
        waitForStart();
        bot.redLED(false);

        bot.encoderMove(1600, 0.3);
        sleep(100);
        bot.gTurn(-90, 0.1);
        sleep(250);
        telemetry.addData("Gyro",bot.heading());
        bot.end();

    }
}
/*
* warning: dont do:
* while encodervalue < target{
*   bot.move(x,y)
* }
* sending move() data every loop seems to crash the app. oops.
* */
