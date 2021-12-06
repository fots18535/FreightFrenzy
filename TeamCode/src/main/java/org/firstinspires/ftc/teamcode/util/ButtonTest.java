package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(group="util")
public class ButtonTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        TouchSensor touchyBack = hardwareMap.get(TouchSensor.class, "touchyBack");
        TouchSensor touchyFront = hardwareMap.get(TouchSensor.class, "touchyFront");

        waitForStart();
        while (opModeIsActive()) {
            if(touchyFront.isPressed()) {
                telemetry.addData("button1","touchyFront pressed");
            } else {
                telemetry.addData("button1","touchyFront not pressed");

            }
            if(touchyBack.isPressed()) {
                telemetry.addData("button2","touchyBack pressed");
            } else {
                telemetry.addData("button2","touchyBack not pressed");

            }

            telemetry.update();
        }
    }
}
