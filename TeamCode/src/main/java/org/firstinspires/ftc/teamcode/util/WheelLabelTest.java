package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@Autonomous(group="util")
public class WheelLabelTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("motor", "leftBack");
            telemetry.update();
            leftBack.setPower(0.5);
            dowait(2);
            leftBack.setPower(0.0);

            telemetry.addData("motor", "rightBack");
            telemetry.update();
            rightBack.setPower(0.5);
            dowait(2);
            rightBack.setPower(0.0);

            telemetry.addData("motor", "leftFront");
            telemetry.update();
            leftFront.setPower(0.5);
            dowait(2);
            leftFront.setPower(0.0);

            telemetry.addData("motor", "rightFront");
            telemetry.update();
            rightFront.setPower(0.5);
            dowait(2);
            rightFront.setPower(0.0);

        }
    }

    public void dowait(int sec) {
        long time = System.currentTimeMillis();
        while(opModeIsActive() && sec*1000 > System.currentTimeMillis() - time) {
            idle();
        }
    }
}
