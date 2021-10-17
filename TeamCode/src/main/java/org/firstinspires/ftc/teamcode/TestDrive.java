package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TestDrive extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // get things ready before you hit the run button

        DcMotor motor1 = hardwareMap.get(DcMotor.class, "motor1");

        waitForStart();

        // do stuff after hitting the run button
        while(opModeIsActive()) {
            // loops hundreds of times per second
            if(gamepad1.dpad_down) {
                motor1.setPower(1.0);
            } else {
                motor1.setPower(0.0);
            }

        }

        // stop button pressed, exit the function
    }
}
