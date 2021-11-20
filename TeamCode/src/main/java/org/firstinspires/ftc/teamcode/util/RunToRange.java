package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp
public class RunToRange extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor gandalfStaff = hardwareMap.get(DcMotor.class, "staff");
        TouchSensor maggot = hardwareMap.get(TouchSensor.class, "maggot");

        gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        int level = 0;
        boolean encoderReset = false;
        while (opModeIsActive()) {

            if(gamepad1.a) {
                level = 1;
            } else if(gamepad1.b) {
                level = 0;
            }

            if(level == 0) {
                gandalfStaff.setPower(0);
            } else if (level == 1) {
                int pos = gandalfStaff.getCurrentPosition();
                if(pos < 360) {
                    gandalfStaff.setPower(-0.5);
                } else if (pos < 370) {
                    gandalfStaff.setPower(-0.4);
                } else if (pos < 380) {
                    gandalfStaff.setPower(-0.3);
                } else if (pos < 390) {
                    gandalfStaff.setPower(-0.2);
                } else {
                    gandalfStaff.setPower(-0.075);
                }
            }

            if(maggot.isPressed() && ! encoderReset) {
                gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                encoderReset = true;
            } else if (!maggot.isPressed()) {
                encoderReset = false;
            }

            telemetry.addData("encoder",gandalfStaff.getCurrentPosition());
            telemetry.update();
        }
    }
}
