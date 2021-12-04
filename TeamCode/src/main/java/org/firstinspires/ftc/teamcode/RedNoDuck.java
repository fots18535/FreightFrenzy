package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class RedNoDuck extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        waitForStart();

        hunk.closeClampy();
        hunk.chaChaRealSmooth(1.0,26);
        sleep(1000);
        hunk.raiseArm(2);
        hunk.forward(1.0, 19);
        hunk.openClampy();
        sleep(2000);
        hunk.forward(-1, 23);
        sleep(1000);
        hunk.chaChaRealSmooth(-1,65);

    }

}
