package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(group ="util")
public class ServoPos extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.get(Servo.class, "clampy");

        waitForStart();

        while (opModeIsActive()) {
            servo.setPosition(gamepad1.right_trigger);
            telemetry.addData("position", servo.getPosition());
            telemetry.update();
        }

    }
}
