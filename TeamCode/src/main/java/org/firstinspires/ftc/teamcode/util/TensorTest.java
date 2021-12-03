package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.HunkOfMetal;
import org.firstinspires.ftc.teamcode.TensorFlaw;

import java.util.List;

@Autonomous
public class TensorTest extends LinearOpMode {
    // 0 - 780
    final float IMWIDTH = 780;

    @Override
    public void runOpMode() throws InterruptedException {
        TensorFlaw see = new TensorFlaw(this);
        // stan lana del rey
        see.turnOn();

        waitForStart();

        // SCANy

        while (opModeIsActive()) {
            List<Recognition> recs = see.getDetections();
            // TODO: check if anything we returned. If so the do this:

            if (recs != null) {
                // TODO: find the duck with the highest confidence
                float bestValue = -1.0f;
                Recognition bestThing = null;
                for (int j = 0; j < recs.size(); j++) {
                    Recognition yoda = recs.get(j);
                    // TODO: only process if yoda lable is "Duck"
                    if (yoda.getLabel().equals("Duck")) {
                        if (yoda.getConfidence() > bestValue) {
                            bestValue = yoda.getConfidence();
                            bestThing = yoda;
                        }
                    }
                }

                // TODO: Was a best duck even found?
                if (bestThing != null) {
                    // TODO: get the coordinates of the highest confidence duck box
                    float left = bestThing.getLeft();
                    float right = bestThing.getRight();
                    float mid = (left + right) / 2.0f;
                    telemetry.addData("BestMid: ", mid);

                    float divWidth = IMWIDTH / 3;

                    if (mid > 0 && mid < divWidth) {
                        telemetry.addData("Section: ", "left");
                    } else if (mid > divWidth && mid < 2*divWidth) {
                        telemetry.addData("Section: ", "mid");
                    } else {
                        telemetry.addData("Section: ", "right");
                    }
                    // TODO: figure out which third of the image the duck is in
                }
            }

            telemetry.update();
        }
    }
}
