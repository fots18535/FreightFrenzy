package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class SimpleAutonomous extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        waitForStart();

        hunk.forward(0.5, 12);
        hunk.turnLeft(90, 0.5);
        hunk.chaChaRealSmooth(0.5, 12);
    }
}
