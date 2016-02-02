package com.qualcomm.ftcrobotcontroller.opmodes;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.lasarobotics.library.util.Timers;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class driveForward extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        RobotSetup bot  = new RobotSetup(hardwareMap,telemetry);
        Timers timer    = new Timers();
        timer.createClock("clock");

        bot.startRobot();           //run our initialization function
        bot.G.calibrate();          //calibrate our gyro

        //--------------------------------OPMODE START
        waitForStart();
        timer.startClock("clock");
        //bot.move(0.3, 0.3);
        //sleep(1000);
        //bot.move(0, 0);

        while (timer.getClockValue("clock") < 30000) {
            if (bot.colorSensor.blue() > bot.colorSensor.red()) {
                bot.blueLED(true);
            } else if (bot.colorSensor.blue() < bot.colorSensor.red()) {
                bot.redLED(true);
            } else {
                bot.blueLED(false);
                bot.redLED(false);
            }
        }

        //"each their own to"
        //         --Travis Day 2k16

    }

}

