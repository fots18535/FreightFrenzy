package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class RedDuck extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        waitForStart();
        hunk.closeClampy();
        hunk.chaChaRealSmooth(1,25);
        //SPIN DUCK WHEEL, spin right
        hunk.chaChaRealSmooth(-1,16);
        hunk.forward(-0.5, 6);
        hunk.chaChaRealSmooth(-1,23);
        hunk.raiseArm(2);
        hunk.forward(1,21);
        hunk.openClampy();
        hunk.forward(-1,23);
        hunk.chaChaRealSmooth(-1, 60);
}
}