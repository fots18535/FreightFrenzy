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
            if (leftPad && gandalfStaff.getCurrentPosition()>90 && (black.isTargetColor(colors) || red.isTargetColor(colors))) {
                turnTable.setPower(0.5);
            } else if (rightPad && gandalfStaff.getCurrentPosition() >90 && (black.isTargetColor(colors) || blue.isTargetColor(colors))) {
                turnTable.setPower(-0.5);
            } else {
                turnTable.setPower(0);
            }

            //manually controls gandalf's arm
            boolean upPad = gamepad1. dpad_up;
            boolean downPad = gamepad1. dpad_down;

            if(upPad && gandalfStaff.getCurrentPosition()<1000){
                gandalfStaff. setPower(-0.75);
            }else if(downPad && !maggot.isPressed()){ // stop the down motion when limit is active
                gandalfStaff. setPower(0.75);
            }else{
                gandalfStaff.setPower(0);
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

            //set places for gandalf's arm
                //button y = 1 (ground)
                //button b = 2 (first level)
                //button a = 3 (second level)
                //button x = 4 (third level)
                //right bumper = 5 (topper)

            boolean buttonY = gamepad2. y;
            boolean buttonB = gamepad2. b;
            boolean buttonA = gamepad2. a;
            boolean buttonX = gamepad2. x;
            boolean rightBumper = gamepad2. right_bumper;

            if(buttonY){

                        //TODO:Encoder stuff
            }

            //manually controls clampy
            float rightTrigger = gamepad1.right_trigger;
            clampy. setPosition (rightTrigger);

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
}
