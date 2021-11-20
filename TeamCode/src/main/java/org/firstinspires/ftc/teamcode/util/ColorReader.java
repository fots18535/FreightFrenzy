package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.ColorTester;

@TeleOp
public class ColorReader extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        NormalizedColorSensor sensorColor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        ColorTester black = new ColorTester(106.6f,233.1f,0.201f,0.493f,0.009f,0.015f);
        ColorTester red = new ColorTester(0,1,0,1,0,1);
        ColorTester blue = new ColorTester(0,1,0,1,0,1);

        waitForStart();

        while (opModeIsActive()) {
            NormalizedRGBA colors = sensorColor.getNormalizedColors();
            if(black.isTargetColor(colors)) {
                telemetry.addData("Color", "black");
            } else if(red.isTargetColor(colors)) {
                telemetry.addData("Color", "red");
            } else if(blue.isTargetColor(colors)) {
                telemetry.addData("Color", "blue");
            } else {
                telemetry.addData("Color", "unknown");
            }
            telemetry.update();
        }
    }
}
