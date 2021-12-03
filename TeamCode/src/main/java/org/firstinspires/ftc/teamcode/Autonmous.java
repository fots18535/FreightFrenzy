package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public class Autonmous extends LinearOpMode {
    final float IMWIDTH = 520;

    @Override
    public void runOpMode() throws InterruptedException {
        TensorFlaw see = new TensorFlaw(this);
                // stan lana del rey

        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();
        see.turnOn();

        waitForStart();

        // SCANy

        List<Recognition> recs = see.getDetections();
        // TODO: check if anything we returned. If so the do this:

        if(recs != null) {
            // TODO: find the duck with the highest confidence
            float bestValue = -1.0f;
            Recognition bestThing = null;
            for(int j = 0; j < recs.size(); j++) {
                Recognition yoda = recs.get(j);
                // TODO: only process if yoda lable is "Duck"
                if(yoda.getLabel().equals("Duck")) {
                    if(yoda.getConfidence() > bestValue) {
                        bestValue = yoda.getConfidence();
                        bestThing = yoda;
                    }
                }
            }

            // TODO: Was a best duck even found?
             if(bestThing != null) {
                 // TODO: get the coordinates of the highest confidence duck box
                 float left = bestThing.getLeft();
                 float right = bestThing.getRight();
                 float mid = (left + right)/2.0f;

                 float firstEnd = IMWIDTH/3;
                 float secondEnd = firstEnd/IMWIDTH;
                 if(mid > 0 && mid < firstEnd) {
                    /////////////////fisr sectiom
                 } else if(mid > firstEnd && mid < secondEnd){
                     //second section
                 } else{
                     //third section
                 }
                 // TODO: figure out which third of the image the duck is in
             }

        }

        // if statement boi??

        //hunk.chaChaRealSmooth(1, 24);

        // spin xoxo gossip girl

        //hunk.turnLeft(24, 1);

        //back up 2 wall

        //hunk.chaChaRealSmooth(1,40);
        //hunk.turnLeft(12,1);

        //drop item like its hot

        //hunk.turnRight(30,1);
        //hunk.chaChaRealSmooth(-1,12);

    }
}
