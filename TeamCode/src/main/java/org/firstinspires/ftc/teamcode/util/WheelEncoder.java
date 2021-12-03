package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp(group = "util")
public class WheelEncoder extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //rightFront
        DcMotor motor = hardwareMap.get(DcMotor.class, "turnTable");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.a) {
                motor.setPower(0.4);
            } else  if (gamepad1.b) {
                motor.setPower(-0.4);
            } else {
                motor.setPower(0.0);
            }

            telemetry.addData("encoder",motor.getCurrentPosition());
            telemetry.update();
        }
        motor.setPower(0.0);
    }
}
