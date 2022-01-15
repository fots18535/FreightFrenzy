package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.vision.Detector;
import org.firstinspires.ftc.teamcode.vision.IconPosition;

@Autonomous
public class BlueDuckBoxDeux extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        Detector d = new Detector(this);
        d.start();
        IconPosition position;

        waitForStart();
        position = d.getPosition();
        hunk.closeClampy();
        hunk.chaChaRealSmooth(-1, 25);
        hunk.spinEyeballCCW();
        hunk.chaChaRealSmooth(1,3);
        // hunk.turnRight(10, 0.5);
       // hunk.forwardNoGyro(-0.5, 6);
        hunk.forward(1,12);
        hunk.chaChaRealSmooth(-1,13);
        hunk.forward(1,29);
        hunk.chaChaRealSmooth(-1,12);
        hunk.turnLeft(85,.5);
        if (position == IconPosition.LEFT) {
            hunk.raiseArm(1);
        } else if (position == IconPosition.CENTER) {
            hunk.raiseArm(2);
        } else if (position == IconPosition.RIGHT) {
            hunk.raiseArm(3);
        } else {
            hunk.raiseArm(3);
        }
        hunk.forward(1,18);
       hunk.openClampy();
       hunk.forward(-1,37);
       hunk.chaChaRealSmooth(1,14 );
       // hunk.turnRight(10, 0.5);
      //  hunk.forwardNoGyro(-0.5, 12);
        //hunk.forward(1,42);
       // hunk.chaChaRealSmooth(-1, 23);
       /* if (position == IconPosition.LEFT) {
            hunk.raiseArm(1);
        } else if (position == IconPosition.CENTER) {
            hunk.raiseArm(2);
        } else if (position == IconPosition.RIGHT) {
            hunk.raiseArm(3);
        } else {
           hunk.raiseArm(3);
        }
        hunk.turnRight(86 ,.5);
        hunk.openClampy();
        hunk.forward(-1,35);
        hunk.chaChaRealSmooth(-1, 19);



   */ }
}