package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.util.Timers;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class linearAuton extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        RobotSetup bot  = new RobotSetup(hardwareMap,telemetry);
        Timers timer    = new Timers();
        timer.createClock("EchoTimer");

        bot.startRobot();           //run our initialization function
        bot.G.calibrate();          //calibrate our gyro
        bot.allianceMenu.show();    //shows Red & Blue Menu
        while (bot.getAlliance().equals("None")){
            sleep(50);
        }
        String alliance = bot.getAlliance();
        bot.redLED(bot.isRed());
        bot.blueLED(bot.isBlue());
        telemetry.addData("Alliance", alliance);
        //--------------------------------OPMODE START
        waitForStart();

        bot.EchoOut.enable(true);
        wait(1000);
        bot.EchoOut.enable(false);

        timer.startClock("EchoTimer");
        while (!bot.EchoIn.getState()){
            telemetry.addData("Time", timer.getClockValue("EchoTimer"));
        }
        timer.pauseClock("EchoTimer");
        telemetry.addData("Final Time", timer.getClockValue("EchoTimer"));
    }
}
