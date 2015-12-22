package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ta on 12/21/15.
 */
public class linearAuton extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        RobotSetup bot = new RobotSetup(hardwareMap,telemetry);
        bot.redLED(false);
        bot.blueLED(false);
        waitForStart();
        while (opModeIsActive()){
            bot.redLED(true);
            bot.blueLED(false);
            Thread.sleep(100);
            bot.redLED(false);
            bot.blueLED(true);
            Thread.sleep(100);
        }
        bot.end();
    }
}
