package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class BlueNoDuck extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        waitForStart();

        hunk.closeClampy();
        hunk.forward(1.0, 23);
        hunk.raiseArm(2);
        hunk.chaChaRealSmooth(-1.0,26);
        hunk.openClampy();
        hunk.forward(-1, 20);
        hunk.turnLeft(75,1);
        hunk.forward(1,48);
    }

}
