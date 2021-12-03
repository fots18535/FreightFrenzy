package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Disabled
@TeleOp(group = "util")
public class ArmEncoder extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor gandalfStaff = hardwareMap.get(DcMotor.class, "staff");
        TouchSensor maggot = hardwareMap.get(TouchSensor.class, "maggot");

        waitForStart();

        gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive()) {


            if(maggot.isPressed())
            {
                gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            }

            telemetry.addData("encoder",gandalfStaff.getCurrentPosition());
            telemetry.update();
        }
    }
}
