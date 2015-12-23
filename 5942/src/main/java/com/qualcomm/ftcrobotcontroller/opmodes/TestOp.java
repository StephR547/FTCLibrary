package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class TestOp extends OpMode{
    RobotSetup bot;
    public void init(){
        bot = new RobotSetup(hardwareMap,telemetry);
    }
    public void loop(){
        bot.defaultTelemetry();
    }
}