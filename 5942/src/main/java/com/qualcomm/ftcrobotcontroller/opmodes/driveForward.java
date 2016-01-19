package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.util.Timers;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class driveForward extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        RobotSetup bot  = new RobotSetup(hardwareMap,telemetry);
        Timers timer    = new Timers();
        timer.createClock("EchoTimer");

        bot.startRobot();           //run our initialization function
        bot.G.calibrate();          //calibrate our gyro

        //--------------------------------OPMODE START
        waitForStart();
        bot.move(0.3, 0.3);
        sleep(4000);
        bot.move(0, 0);

        //"each their own to"
        //         --Travis Day 2k16

    }

}

