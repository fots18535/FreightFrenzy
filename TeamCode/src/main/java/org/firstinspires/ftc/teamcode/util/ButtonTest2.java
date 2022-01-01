
package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(group="util")
public class ButtonTest2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DigitalChannel touchyBack = hardwareMap.get(DigitalChannel.class, "touchyBack");
        DigitalChannel touchyFront = hardwareMap.get(DigitalChannel.class, "touchyFront");

        // set the digital channel to input.
        touchyBack.setMode(DigitalChannel.Mode.INPUT);
        touchyFront.setMode(DigitalChannel.Mode.INPUT);

        waitForStart();
        while (opModeIsActive()) {
            if(touchyFront.getState()) {
                telemetry.addData("button1","touchyFront not pressed");
            } else {
                telemetry.addData("button1","touchyFront pressed");

            }
            if(touchyBack.getState()) {
                telemetry.addData("button2","touchyBack not pressed");
            } else {
                telemetry.addData("button2","touchyBack pressed");

            }

            telemetry.update();
        }
    }
}
