package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by ta on 12/21/15.
 */
public class TestOp extends OpMode{
    RobotSetup bot;
    @Override public void init(){
        bot = new RobotSetup(hardwareMap,telemetry);
    }
    @Override public void loop(){

    }
}
