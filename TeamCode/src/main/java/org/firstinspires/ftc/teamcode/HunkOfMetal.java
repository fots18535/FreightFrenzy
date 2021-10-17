package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HunkOfMetal {
    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;
    DcMotor thrawr;
    Servo poddle;
    NormalizedColorSensor sensorColor;
    Gyro2 gyro;
    LinearOpMode mode;
    Servo wrist;
    DcMotor americaForever;
    DistanceSensor diztance;
    TouchSensor cmax;

    float ticksPerInch = 122.15f;

    public HunkOfMetal (LinearOpMode op) {
        mode = op;
    }

    public void initialize() {
        BNO055IMU imu = mode.hardwareMap.get(BNO055IMU.class, "imu");
        gyro = new Gyro2(imu, mode);


        leftBack = mode.hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = mode.hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = mode.hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = mode.hardwareMap.get(DcMotor.class, "rightFront");
        thrawr = mode.hardwareMap.get(DcMotor.class, "thrower");
        poddle = mode.hardwareMap.get(Servo.class, "poddle");
        wrist = mode.hardwareMap.get(Servo.class, "wrist");
        americaForever = mode.hardwareMap.get(DcMotor.class, "americaForever");
        sensorColor = mode.hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        cmax = mode.hardwareMap.get(TouchSensor.class, "clawMax");
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gyro.startGyro();
    }

    public void arret () {
        //graph.turnOff();
    }

    public int ringReturn() {
        return 0;
    }

    public void correct(double x, double y, double angle) {
        /*GraphResult result = graph.getPosition();
        if(result.imageSee) {
            mode.telemetry.addData("x",result.x);
            mode.telemetry.addData("y",result.y);
            mode.telemetry.addData("orientation",result.orientation);

            // TODO: if result.orientation > 5 degrees then turn right result.orientation degrees
            if(result.orientation > angle + 5) {
                turnRight(result.orientation-angle, 1);
            }

            // TODO: if result.orientation < -5 degrees then turn left abs(result.orientation degrees)
            if(result.orientation < angle - 5) {
                turnLeft(angle-result.orientation, 1);
            }

            // TODO: if result.x < 36 then move forward 36 - result.x inches
            if(result.x < x) {
                forward(1, x-result.x);
            }


            // TODO: if result.x > 36 then move backwards result.x - 36 inches
            if(result.x > x) {
                forward(-1, result.x-x);
            }

            // TODO: if result.y < 36 then slide left 36 - result.y
            if(result.y < y) {
                chaChaRealSmooth(1, y-result.y);
            }

            // TODO: if result.y > 36 then slide right result.y - 36
            if(result.y > y) {
                chaChaRealSmooth(-1, result.y-y);
            }

        }
        else {
            mode.telemetry.addData("no image :(", "");
        }
        */
    }


    // Positive power slides left
    // Negative power slides right
    public void chaChaRealSmooth(double power, double length) {
        // Reset the encoder to 0
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Turn on motors to slide
        leftBack.setPower(-power);
        leftFront.setPower(power*0.8);
        rightBack.setPower(-power);
        rightFront.setPower(power*0.8);

        // Slide until encoder ticks are sufficient
        while(mode.opModeIsActive()) {
            //absolute value of getCurrentPosition()
            int tics = rightBack.getCurrentPosition();
            if (tics < 0) {
                tics = tics * -1;
            }

            if (tics > length*ticksPerInch){
                break;
            }
            mode.idle();
        }

        // Turn off motors
        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void forward(double power, double length){
        // Reset the encoder to 0
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        gyro.reset();
        // Setting the motor power based on the input
        motorsForward(power);

        // Go forward and park behind the line
        while(mode.opModeIsActive()) {
            //absolute value of getCurrentPosition()
            int tics = rightBack.getCurrentPosition();
            if (tics < 0) {
                tics = tics * -1;
            }
            //telemetry.addData("debug tics", tics);
            //telemetry.addData("debug compare to ", length*ticksPerInch);


            if (tics > length*ticksPerInch){
                break;
            }

            // TODO: get the angle and adjust the power to correct
            // float rightX = -0.022f * (float) gyro.getAngle();
           // leftBack.setPower(rightX + power);
            // leftFront.setPower(rightX + power);
            // rightBack.setPower(rightX - power);
            // rightFront.setPower(rightX - power);

             // Check the angle and correct if needed
           if (gyro.getAngle() >4) {
                gyro.store();
                turnRight(3, .3);
               gyro.recall();
               motorsForward(power);
          } else if (gyro.getAngle() <-4) {
               gyro.store();
                turnLeft(3, .3);
                gyro.recall();
                motorsForward(power);
           }


            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void motorsForward( double power) {
        leftBack.setPower(-power);
        leftFront.setPower(-power);
        rightBack.setPower(power);
        rightFront.setPower(power);
    }

    public void forwardColor (double power){
        motorsForward(power);
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // Go forward and park behind the line
        while(mode.opModeIsActive()) {
            NormalizedRGBA colors = sensorColor.getNormalizedColors();

            Color.colorToHSV(colors.toColor(), hsvValues);
            // changes rbg to hsv

            float h = hsvValues[0];
            float s = hsvValues[1];
            float v = hsvValues[2];

            if (h >= 326.2398 && h <= 337.5946 && s >= 0.46798 && s<= 0.6815 && v >= 0.0864 && v <= 0.4998)
            {
                break;
            }

            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void turnRight(double howFar, double speed) {
        //gyro.resetWithDirection(Gyro.RIGHT);
        gyro.reset();
        leftBack.setPower(-speed);
        leftFront.setPower(-speed);
        rightBack.setPower(-speed);
        rightFront.setPower(-speed);

        // Go forward and park behind the line
        while(mode.opModeIsActive()) {
            if (gyro.getAngle()<= -howFar){ //change
                break;
            }

            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void turnLeft(double howFar, double speed) {
        //gyro.resetWithDirection(Gyro.LEFT);
        gyro.reset();
        leftBack.setPower(speed);
        leftFront.setPower(speed);
        rightBack.setPower(speed);
        rightFront.setPower(speed);

        // Go forward and park behind the line
        while(mode.opModeIsActive()) {
            if (gyro.getAngle()>= howFar){
                break;
            }

            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void turnOnThrower() {
        thrawr.setPower(0.88);
    }

    public void turnOffThrower() {
        thrawr.setPower(0.0);
    }

    public void throwRing() {
        poddle.setPosition(0.5);
        mode.sleep(300);
        poddle.setPosition(1.0);
    }

    public void wristUp() {
        wrist.setPosition(0.0);
    }

    public void wristDown() {
        wrist.setPosition(0.5);
    }

    public void clawOpen() {
        americaForever.setPower(-0.4);
        while(!cmax.isPressed()) {
            mode.idle();
        }
        americaForever.setPower(0.0);
    }

    public int sensingDistance (double power, double length){
        int numero = 0;
        // Reset the encoder to 0
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        gyro.reset();
        // Setting the motor power based on the input
        motorsForward(1);

        // Go forward and park behind the line
        while(mode.opModeIsActive()) {
            //absolute value of getCurrentPosition()
            int tics = rightBack.getCurrentPosition();
            if (tics < 0) {
                tics = tics * -1;
            }
            // Get reading for distance sensor
            double d = diztance.getDistance(DistanceUnit.INCH);
            if(d < 6) {
                numero = 4;
            }
            else if (d < 10) {
                numero = 1;
            }

            //telemetry.addData("debug tics", tics);
            //telemetry.addData("debug compare to ", length*ticksPerInch);


            if (tics > length*ticksPerInch){
                break;
            }

            // Check the angle and correct if needed
            if (gyro.getAngle() >4) {
                gyro.store();
                turnRight(3, .3);
                gyro.recall();
                motorsForward(1);
            } else if (gyro.getAngle() <-4) {
                gyro.store();
                turnLeft(3, .3);
                gyro.recall();
                motorsForward(1);
            }


            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);

        return numero;
    }

    public void throwRings(){
        turnOnThrower();
        mode.sleep(2000);
        throwRing();
        mode.sleep(1000);
        throwRing();
        mode.sleep(1000);
        throwRing();
        mode.sleep(1000);
        turnOffThrower();
    }

}



