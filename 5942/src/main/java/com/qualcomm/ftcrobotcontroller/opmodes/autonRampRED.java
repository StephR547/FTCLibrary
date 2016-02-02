package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.util.Timers;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class autonRampRED extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        RobotSetup fetty  = new RobotSetup(hardwareMap,telemetry);

        fetty.startRobot();           //run our initialization function
        fetty.G.calibrate();          //calibrate our gyro

        //--------------------------------OPMODE START
        waitForStart();
        fetty.move(0.3, 0.3);
        sleep(4000);
<<<<<<< HEAD
        bot.gTurn(-45, 0.3);
        bot.move(0.3, 0.3);
        sleep(1000);
        bot.move(0, 0);

        //TODO do the scoring stuff


        bot.move(-0.3,-0.3);
        sleep(1000);
        bot.gTurn(45, 0.3);
        bot.move(-0.3, -0.3);
        sleep(2500);
        bot.gTurn(-85, 0.3);
        bot.move(-0.3, -0.3);
        sleep(500);
        bot.move(1,1);
=======
        fetty.move(-0.3,-0.3);
        sleep(1500);
        fetty.gTurn(-85,0.3);
        fetty.move(-1,-1);
>>>>>>> 8dfc88ad2feb13b05ee5f463431cb6d493590f1e
        sleep(5000);
        fetty.move(0,0);
        //"each their own to"
        //         --Travis Day 2k16

    }

}

