package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class BlueDuckBox extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        waitForStart();
        hunk.closeClampy();
        hunk.forward(1,40);
        hunk.turnLeft(90, 0.5);
        hunk.raiseArm(2);
        hunk.forward(1, 6);
        hunk.openClampy();
        hunk.forwardNoGyro(-1,40);
        hunk.chaChaRealSmooth(1, 42);
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
