package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutonomousBlueLeft extends LinearOpMode {
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

        hunk.forward(1, 48);

        hunk.chaChaRealSmooth(1,12);

        //drop thing

        hunk.forward(-1,20);

        hunk.turnRight(90,1);

        hunk.forward(1,48);

    }
}
