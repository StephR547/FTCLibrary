package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.drive.Tank;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Auton extends OpMode{
    public long startTime;
    public long currentTime;
    @Override public void init(){}
    @Override public void start() {
        startTime = System.currentTimeMillis();
    }
    @Override public void loop() {


    /*  TRAVIS OLD CODElong currentTime = System.currentTimeMillis();
        long deltaTime   = currentTime - startTime;

        if (deltaTime < 2000) {
            left = 1;
            right = 1;
        }
        else {
            left = 0;
            right = 0;
        }
        //uses FTCLib Tank class. Thanks LASA!
        Tank.motor4(frontLeft, frontRight, backLeft, backRight, -left, right);
        //send data to fones
        heading = G.getIntegratedZValue();
        telemetry.addData("direction", reverse);
        telemetry.addData("Heading", heading);    */}
    @Override public void stop() {
    }
}
