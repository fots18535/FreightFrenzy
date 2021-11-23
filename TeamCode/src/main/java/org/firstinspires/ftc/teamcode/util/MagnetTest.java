package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(group = "util")
public class MagnetTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        TouchSensor maggot = hardwareMap.get(TouchSensor.class, "maggot");

        waitForStart();
        while (opModeIsActive()) {

            if (maggot.isPressed()) {
                telemetry.addData("magnet", "pressed");
            } else {
                telemetry.addData("magnet", "not pressed");
            }
            telemetry.update();
        }
    }
}
