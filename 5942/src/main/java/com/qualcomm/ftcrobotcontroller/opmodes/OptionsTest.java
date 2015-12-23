package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ta on 12/23/15.
 */
public class OptionsTest extends LinearOpMode{
    @Override public void runOpMode() throws InterruptedException{
        RobotSetup bot  = new RobotSetup(hardwareMap,telemetry);

        bot.startRobot();
        bot.G.calibrate();
        bot.allianceMenu.show(); //Necessary to show options menu.
        waitForStart();

        telemetry.addData("Alliance",bot.getAlliance());

        bot.end();
    }
}
