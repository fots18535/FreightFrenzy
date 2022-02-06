package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class IconDetector2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //Detector d = new Detector(this);
        BlockProcessor d = new BlockProcessor(this);
        d.start();

        waitForStart();

        while (opModeIsActive()) {
            //telemetry.addData("Analysis", d.getPosition());
            //telemetry.update();

            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }

        d.stop();
    }
}
