package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.vision.Detector;
import org.firstinspires.ftc.teamcode.vision.IconPosition;

@Autonomous
public class RedDuck extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        Detector d = new Detector(this);
        d.start();
        IconPosition position;

        waitForStart();
        hunk.closeClampy();
        position = d.getPosition();
        hunk.chaChaRealSmooth(1,25);
        hunk.spinEyeballCCW();
        hunk.chaChaRealSmooth(-1,16);
        hunk.turnRight(10,0.5);
        hunk.forwardNoGyro(-0.5, 12);
        hunk.chaChaRealSmooth(-1,23);
        if (position == IconPosition.LEFT) {
            hunk.raiseArm(1);
        } else if (position == IconPosition.CENTER) {
            hunk.raiseArm(2);
        } else if (position == IconPosition.RIGHT) {
            hunk.raiseArm(3);
        } else {
            //defult behavior (dance around?)
        }
        hunk.forward(1,17);
        hunk.openClampy();
        hunk.forward(-1,23);
        hunk.chaChaRealSmooth(-1, 60);
}
}