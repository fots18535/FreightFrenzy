package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Autonmous extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Move forward ?? inches
        // Scan for #ducks
        // GO TGO DUCK
        // stan lana del rey
        //

        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();
        waitForStart();

        // SCAN YUHt

        hunk.chaChaRealSmooth(1, 24);

        //  SPINM DUCK



    }
}
