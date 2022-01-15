package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.vision.Detector;
import org.firstinspires.ftc.teamcode.vision.IconPosition;

@Autonomous
public class SetupPositioner extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        Detector d = new Detector(this);
        d.start();
        IconPosition position;
/*
        if (position == IconPosition.LEFT) {
            while (position == IconPosition.LEFT) {
            }
            hunk.chaChaRealSmooth(1, 1);
        } else if (position == IconPosition.CENTER) {
            hunk.raiseArm(2);
        } else if (position == IconPosition.RIGHT) {
            hunk.raiseArm(3);
        } else {
            hunk.raiseArm(3);
        }
  */
}}
