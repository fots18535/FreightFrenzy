package org.firstinspires.ftc.teamcode.util;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class CalibrateColor extends LinearOpMode {

    //ColorSensor sensorColor;
    NormalizedColorSensor sensorColor;

    @Override
    public void runOpMode() {

        // get a reference to the color sensor.
        sensorColor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // wait for the start button to be pressed.
        waitForStart();

        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.

        List<Float> h = new ArrayList<>();
        List<Float> s = new ArrayList<>();
        List<Float> v = new ArrayList<>();
        // makes lists for h, s, and v to contain the numbers
        float hsum = 0;
        // the sum of all h values
        float ssum = 0;
        // the sum of all s values
        float vsum = 0;
        // the sum of all v values

        for(int i = 0; i < 2000; i++) {

            NormalizedRGBA colors = sensorColor.getNormalizedColors();

            Color.colorToHSV(colors.toColor(), hsvValues);
            // changes rbg to hsv

            telemetry.addData("H  ", hsvValues[0]);
            telemetry.addData("S", hsvValues[1]);
            telemetry.addData("V ", hsvValues[2]);
            telemetry.update();
            // let's u see the hsv values on the screen

            h.add(hsvValues[0]);
            s.add(hsvValues[1]);
            v.add(hsvValues[2]);
            // applying the hsv values to their lists
        }

        for (int i = 0; i < h.size(); i++) {
            hsum = hsum + h.get(i);
            // adds all the h values together
        }
        for (int i = 0; i < s.size(); i++) {
            ssum = ssum + s.get(i);
            // adds all the s values together
        }
        for (int i = 0; i < v.size(); i++) {
            vsum = vsum + v.get(i);
            // adds all the v values together
        }

        float have = hsum / h.size();
        float save = ssum / s.size();
        float vave = vsum / v.size();
        // averages the hsv values

        hsum = 0;
        ssum = 0;
        vsum = 0;
        // resets the sums

        for (int i = 0; i < h.size(); i++) {
            hsum = hsum + (float) Math.pow(h.get(i) - have, 2);
            // getting the standard deviation of h
        }
        for (int i = 0; i < s.size(); i++) {
            ssum = ssum + (float) Math.pow(s.get(i) - save, 2);
            // getting the standard deviation of s
        }
        for (int i = 0; i < v.size(); i++) {
            vsum = vsum + (float) Math.pow(v.get(i) - vave, 2);
            // getting the standard deviation of v
        }

        float hsd = (float) Math.sqrt(hsum / h.size());
        float ssd = (float) Math.sqrt(ssum / s.size());
        float vsd = (float) Math.sqrt(vsum / v.size());
        // averaging the hsv values


        telemetry.addData("H Average", have);
        telemetry.addData("S Average", save);
        telemetry.addData("V Average", vave);
        telemetry.addData("H Standard Deviation", hsd);
        telemetry.addData("S Standard Deviation", ssd);
        telemetry.addData("V Standard Deviation", vsd);
        telemetry.addData("H Standard Deviation Range Upper", have + 3 * hsd);
        telemetry.addData("H Standard Deviation Range Lower", have - 3 * hsd);
        telemetry.addData("S Standard Deviation Range Upper", save + 3 * ssd);
        telemetry.addData("S Standard Deviation Range Lower", save - 3 * ssd);
        telemetry.addData("V Standard Deviation Range Upper", vave + 3 * vsd);
        telemetry.addData("V Standard Deviation Range Lower", vave - 3 * vsd);
        telemetry.update();
        // for testing we added the different hsv values to telemetry so we knew what numbers to use


        while (opModeIsActive()) {
            idle();
        }
    }
}