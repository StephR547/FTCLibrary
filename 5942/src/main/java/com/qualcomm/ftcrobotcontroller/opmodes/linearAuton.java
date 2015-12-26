package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class linearAuton extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        RobotSetup bot = new RobotSetup(hardwareMap,telemetry);
        bot.startRobot();
        bot.G.calibrate();
        bot.allianceMenu.show();
        while (bot.getAlliance().equals("None")){
            sleep(50);
        }
        String alliance = bot.getAlliance();
        bot.redLED(bot.isRed());
        bot.blueLED(bot.isBlue());
        telemetry.addData("Alliance", alliance);
        //--------------------------------OPMODE START
        waitForStart();
        if (bot.getAlliance().equals("None")){
            //what to run when no alliance is chosen from the menu
        }

    }
}
/*
* warning: dont do:
* while encodervalue < target{
*   bot.move(x,y)
* }
* sending move() data every loop seems to crash the app. oops.
* */
