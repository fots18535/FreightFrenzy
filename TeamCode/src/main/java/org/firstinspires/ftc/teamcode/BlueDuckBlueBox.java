package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.vision.Detector;
import org.firstinspires.ftc.teamcode.vision.IconPosition;

@Autonomous
public class BlueDuckBlueBox extends LinearOpMode {
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
        hunk.forward(1,15);
        hunk.chaChaRealSmooth(-1,24);
        hunk.forward(1,25);
        if (position == IconPosition.LEFT) {
            hunk.raiseArm(1);
        } else if (position == IconPosition.CENTER) {
            hunk.raiseArm(2);
        } else if (position == IconPosition.RIGHT) {
            hunk.raiseArm(3);
        } else {
            hunk.raiseArm(3);
        }
        hunk.turnLeft(85, 0.5);
        hunk.forward(1, 30);
        hunk.openClampy();
        hunk.forwardNoGyro(-1,40);
        hunk.chaChaRealSmooth(.5, 40);
        hunk.spinEyeballCW();
        hunk.chaChaRealSmooth(-1,10);
        hunk.forwardNoGyro(-1,10);
        hunk.chaChaRealSmooth(-1,6);

        //hunk.chaChaRealSmooth(-1.0,25);
       // hunk.closeClampy();
       // hunk.forward(-1.0, 23);
       // hunk.raiseArm(2);

        //hunk.openClampy();
        //hunk.forward(1, 20);
        //hunk.turnLeft(75,1);
        //hunk.forward(-1,48);
    }

}
