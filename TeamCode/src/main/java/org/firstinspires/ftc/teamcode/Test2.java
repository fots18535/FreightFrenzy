package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Test2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        telemetry.addData("Status", "Ready2!");
        telemetry.update();
        while (opModeIsActive()) {

        }
    }
}
