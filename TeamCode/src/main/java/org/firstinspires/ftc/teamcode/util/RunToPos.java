package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Disabled
@TeleOp(group = "util")
public class RunToPos extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx gandalfStaff = hardwareMap.get(DcMotorEx.class, "staff");
        //gandalfStaff.setDirection(DcMotorSimple.Direction.REVERSE);
        gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        while (opModeIsActive()) {


            if(gamepad1.a) {
                gandalfStaff.setTargetPosition(-500);
                gandalfStaff.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                gandalfStaff.setPower(-1.0);
            } else if(gamepad1.b) {
                gandalfStaff.setTargetPosition(0);
                gandalfStaff.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                gandalfStaff.setPower(1.0);
            }

            telemetry.addData("encoder",gandalfStaff.getCurrentPosition());
            telemetry.update();
        }
    }
}
