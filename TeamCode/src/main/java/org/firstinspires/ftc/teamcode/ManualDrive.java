package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.checkerframework.checker.units.qual.min;

@TeleOp
public class ManualDrive extends LinearOpMode
{

    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;
    DcMotor turnTable;
    DcMotor gandalfStaff;
    Servo clampy;
    TouchSensor maggot;
    DcMotor eyeball;

    @Override
    public void runOpMode() throws InterruptedException {
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        turnTable = hardwareMap. get(DcMotor.class, "turnTable");
        gandalfStaff = hardwareMap.get(DcMotor.class, "staff");
        clampy = hardwareMap.get(Servo.class, "clampy");
        maggot = hardwareMap.get(TouchSensor.class, "maggot");
        eyeball = hardwareMap.get(DcMotor.class, "eyeball");

        NormalizedColorSensor sensorColor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        ColorTester black = new ColorTester(106.6f,233.1f,0.201f,0.493f,0.009f,0.015f);
        ColorTester red = new ColorTester(0,1,0,1,0,1);
        ColorTester blue = new ColorTester(0,1,0,1,0,1);

        // Stops coasting
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
       gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

       boolean encoderReset = false;

        int level = 0;
        boolean clampyopen = false;
        double lastclamptrigger = 0.0;

        while (opModeIsActive()) {
            //Get the input from the gamepad controller
            double leftX =   -gamepad1.left_stick_x;
            double leftY =   gamepad1.left_stick_y;
            double rightX =  gamepad1.right_stick_x;
            double rightY =  -gamepad1.right_stick_y;

            // Setting the motor power based on the input
            leftBack.setPower(rightX + rightY + leftX);
            leftFront.setPower(leftX + rightY - rightX);
            rightBack.setPower(leftX - rightY + rightX);
            rightFront.setPower(leftX - rightY - rightX);

            //double leftX =   gamepad1.left_stick_x;
            //double leftY =   gamepad1.left_stick_y;
            //double rightX =  -gamepad1.right_stick_x;
            //double rightY =  gamepad1.right_stick_y;
            // Setting the motor power based on the input
            //leftBack.setPower(rightX + rightY + leftX);
            //leftFront.setPower(rightX + rightY - leftX);
            //rightBack.setPower(rightX - rightY + leftX);
            //rightFront.setPower(rightX - rightY - leftX);


            //leftX_G1 = -gamepad1.left_stick_x;
            //leftY_G1 = gamepad1.left_stick_y;
            //rightX_G1 = -gamepad1.right_stick_x;
            //rightY_G1 = -gamepad1.right_stick_y;


            //manually controls turntable
            boolean leftPad = gamepad1. dpad_left;
            boolean rightPad = gamepad1. dpad_right;

            // Make sure arm is up X tics before turning
            telemetry.addData("encoder",gandalfStaff.getCurrentPosition());
            telemetry.update();

            // trun left when: 1 left dpad is pressed
            // AND the arm is lift more than 90 tics
            // AND (the color sensor reads black or it reads red)
            NormalizedRGBA colors = sensorColor.getNormalizedColors();
            if (leftPad && gandalfStaff.getCurrentPosition()>150 ) {
                turnTable.setPower(0.5);
            } else if (rightPad && gandalfStaff.getCurrentPosition() >150 ) {
                turnTable.setPower(-0.5);
            } else {
                turnTable.setPower(0);
            }

            // reset encoder when the magnetic limit switch is active

            if(maggot.isPressed() && !encoderReset)
            {
                gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                encoderReset = true;
            }
            else if(!maggot.isPressed())
            {
                encoderReset = false;
            }

           if (gamepad1.y){
              level = 0;
           }else if(gamepad1.b){
               level = 1;
            }else if(gamepad1.a){
                level = 2;
            }else if(gamepad1.x){
                level = 3;
            }else if(gamepad1.right_bumper){
               level = 4;
           }
           raiseArm(level);

            //manually controls clampy
            if (gamepad1.right_trigger > 0 && lastclamptrigger == 0){
                if(clampyopen==true){
                    clampyopen=false;
                }else{
                    clampyopen=true;
                }
            }
            lastclamptrigger = gamepad1.right_trigger;

            if(clampyopen=true){
                clampy.setPosition(0.0);
            }else{
                clampy.setPosition(1.0);
            }


            if(gamepad1.left_bumper)
            {
                eyeball.setPower(1.0);
            }
            else
            {
                eyeball.setPower(0.0);
            }
        }

        // Stop the motors
        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);

    }
    // TODO: raise arm to 3 different positions
    // variables declared, not the actual values bruh
    final int TOP_MIN = 900;
    final int TOP_MAX = 1000;
    final int MIDDLE_MIN = 700;
    final int MIDDLE_MAX = 600;
    final int BOTTOM_MIN = 400;
    final int BOTTOM_MAX = 300;
    final int GROUND_MIN = -100;
    final int GROUND_MAX = 100;

    public void raiseArm(int level)
    {
        // TODO: if magnet sensor is active reset the arm encoder
        if(maggot.isPressed())
        {
            gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // if current position > top_max then set power to turn backwards
        // if current position < top_min then set power to turn forwards

        // TODO: depending on level set min and max variables and then use them
        //      in the below loop instead of TOP_MIN TOP_MAX

        int where = gandalfStaff.getCurrentPosition();

        if(level == 0)
        {
            if(where > 100) {
                gandalfStaff.setPower(0.1);
            } else {
                gandalfStaff.setPower(0);
            }
            return;
        }

        int min = 0;
        int max = 0;
        if(level == 1) {
            min = BOTTOM_MIN;
            max = BOTTOM_MAX;
        } else if(level == 2)
        {
            min = MIDDLE_MIN;
            max = MIDDLE_MAX;
        } else if(level == 3)
        {
            min = TOP_MIN;
            max = TOP_MAX;
        }

            if(where < min-40) {
                gandalfStaff.setPower(-0.5);
            } else if (where < min-30) {
                gandalfStaff.setPower(-0.4);
            } else if (where < min-20) {
                gandalfStaff.setPower(-0.3);
            } else if (where < min-10) {
                gandalfStaff.setPower(-0.2);
            } else if (where > max+50) {
                gandalfStaff.setPower(0.1);
            } else {
                gandalfStaff.setPower(-0.075);
            }

    }

}
