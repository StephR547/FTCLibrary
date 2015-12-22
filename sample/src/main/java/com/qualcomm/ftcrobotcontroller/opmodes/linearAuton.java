package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ta on 12/21/15.
 */
public class linearAuton extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        RobotSetup bot = new RobotSetup(hardwareMap,telemetry);
        bot.gyroInit();
        waitForStart();
        bot.reverseVal = false;
        while (bot.G.getIntegratedZValue() > -60){
            bot.move(0,.1);
            bot.redLED(true);
        }
        bot.redLED(false);
        bot.move(0,0);
    }
}
