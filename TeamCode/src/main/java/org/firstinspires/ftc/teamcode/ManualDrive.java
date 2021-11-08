package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

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


    @Override
    public void runOpMode() throws InterruptedException {
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        turnTable = hardwareMap. get(DcMotor.class, "turnTable");
        gandalfStaff = hardwareMap.get(DcMotor.class, "staff");
        clampy = hardwareMap.get(Servo.class, "clampy");


        // Stops coasting
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
       gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive()) {
            //Get the input from the gamepad controller
            double leftX =   gamepad1.left_stick_x;
            double leftY =   gamepad1.left_stick_y;
            double rightX =  -gamepad1.right_stick_x;
            double rightY =  gamepad1.right_stick_y;

            //manually controls turntable
            boolean leftPad = gamepad1. dpad_left;
            boolean rightPad = gamepad1. dpad_right;

            // Make sure arm is up X tics before turning

            telemetry.addData("encoder",gandalfStaff.getCurrentPosition());
            telemetry.update();

                if (leftPad && gandalfStaff.getCurrentPosition()>90) {
                    turnTable.setPower(0.5);
                } else if (rightPad && gandalfStaff.getCurrentPosition()>90) {
                    turnTable.setPower(-0.5);
                } else {
                   turnTable.setPower(0);
                }

            //manually controls gandalf's arm
            boolean upPad = gamepad1. dpad_up;
            boolean downPad = gamepad1. dpad_down;

            if(upPad && gandalfStaff.getCurrentPosition()<1000){
                gandalfStaff. setPower(-0.75);
            }else if(downPad){
                gandalfStaff. setPower(0.75);
            }else{
                gandalfStaff.setPower(0);
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


                // Setting the motor power based on the input
            leftBack.setPower(rightX + rightY + leftX);
            leftFront.setPower(rightX + rightY - leftX);
            rightBack.setPower(rightX - rightY + leftX);
            rightFront.setPower(rightX - rightY - leftX);


        }

        // Stop the motors
        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);

    }
}
