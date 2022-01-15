package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.vision.Detector;
import org.firstinspires.ftc.teamcode.vision.IconPosition;

@Autonomous
public class BlueNoDuck extends LinearOpMode {
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
        hunk.chaChaRealSmooth(-1.0,22 );
        sleep(1000);
        if (position == IconPosition.LEFT) {
            hunk.raiseArm(1);
        } else if (position == IconPosition.CENTER) {
            hunk.raiseArm(2);
        } else if (position == IconPosition.RIGHT) {
            hunk.raiseArm(3);
        } else {
            hunk.raiseArm(3);
        }
        if (position == IconPosition.RIGHT){
            hunk.forward(1,2);
        }

        hunk.forward(1.0, 21);
        hunk.openClampy();
        sleep(2000);
        hunk.forward(-1, 25);
        sleep(1000);
        hunk.chaChaRealSmooth(1,55);

    }

}
