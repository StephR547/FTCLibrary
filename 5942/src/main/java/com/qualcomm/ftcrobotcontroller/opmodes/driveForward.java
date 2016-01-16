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
        bot.resetEncoders();
        bot.encoderMove(9600, 0.3);
        bot.gTurn(180, .3);
        bot.encoderMove(4500, 0.3);
        bot.gTurn(90, .3);
        bot.encoderMove(500, 0.3);



    }

    }

