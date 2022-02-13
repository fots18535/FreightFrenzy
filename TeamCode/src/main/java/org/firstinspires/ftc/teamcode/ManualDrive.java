package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.text.method.Touch;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.checkerframework.checker.units.qual.min;

@TeleOp
public class ManualDrive extends LinearOpMode {

    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;
    DcMotor turnTable;
    DcMotor gandalfStaff;
    Servo clampy;
    TouchSensor maggot;
    DigitalChannel touchyFront;
    DigitalChannel touchyBack;
    DcMotor eyeball;

    @Override
    public void runOpMode() throws InterruptedException {
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        turnTable = hardwareMap.get(DcMotor.class, "turnTable");
        gandalfStaff = hardwareMap.get(DcMotor.class, "staff");
        clampy = hardwareMap.get(Servo.class, "clampy");
        maggot = hardwareMap.get(TouchSensor.class, "maggot");
        eyeball = hardwareMap.get(DcMotor.class, "eyeball");
        touchyBack = hardwareMap.get(DigitalChannel.class, "touchyBack");
        touchyFront = hardwareMap.get(DigitalChannel.class, "touchyFront");

        touchyBack.setMode(DigitalChannel.Mode.INPUT);
        touchyFront.setMode(DigitalChannel.Mode.INPUT);

        // Stops coasting
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turnTable.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        gandalfStaff.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        eyeball.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turnTable.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turnTable.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        clampy.setPosition(0.34);
        boolean encoderReset = false;
        int level = 0;
        int turnPosition = 0;
        long ttableStart = 0;
        while (opModeIsActive()) {

            /*****************************/
            /** Driving Control Section **/
            /*****************************/

            double slowSpeed = 1.0;
            if(gamepad1.left_bumper){
                slowSpeed = 0.5;
            }else{
                slowSpeed = 1.0;
            }
            //Get the input from the gamepad controller
            double leftX = gamepad1.left_stick_x * slowSpeed;
            double leftY = gamepad1.left_stick_y* slowSpeed;
            double rightX = -gamepad1.right_stick_x* slowSpeed;
            double rightY = gamepad1.right_stick_y* slowSpeed;

            leftBack.setPower(rightX + rightY + leftX);
            leftFront.setPower(rightX + rightY - leftX);
            rightBack.setPower(rightX - rightY + leftX);
            rightFront.setPower(rightX - rightY - leftX);


            /*******************************/
            /** Turntable Control Section **/
            /*******************************/

            // TODO: can we use color or touch sensor(s) to recalibrate encoder?

            // Make sure arm is up X tics before turning
            if (gandalfStaff.getCurrentPosition() >= STAFF_TURN_MIN) {
                /*
                if(gamepad2.dpad_right && touchyBack.getState()){
                    if(ttableStart == 0) {
                        ttableStart = System.currentTimeMillis();
                    }
                    turnTable.setPower(ramp(-0.7, ttableStart));
                }else if(gamepad2.dpad_left && touchyFront.getState()){
                    if(ttableStart == 0) {
                        ttableStart = System.currentTimeMillis();
                    }
                    turnTable.setPower(ramp(0.5, ttableStart));
                }else{
                    ttableStart = 0;
                    turnTable.setPower(0);
                }*/

                if (gamepad2.dpad_right) {
                    turnPosition = 2;
                } else if (gamepad2.dpad_up) {
                    turnPosition = 1;
                } else if (gamepad2.dpad_left) {
                    turnPosition = 0;
                } else if (gamepad2.dpad_down) {
                    turnPosition = -1;
                }

            }
            turnTable(turnPosition);
           telemetry.addData("table", turnTable.getCurrentPosition());


            /*******************************/
            /** Arm/Staff Control Section **/
            /*******************************/

            // reset encoder when the magnetic limit switch is active
            if (maggot.isPressed() && !encoderReset) {
                gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                encoderReset = true;
            } else if (!maggot.isPressed()) {
                encoderReset = false;
            }

            // set arm to certain positions based on controls
            if (gamepad2.y) {
                level = 3;
            } else if (gamepad2.b) {
                level = 2;
            } else if (gamepad2.a) {
                level = 1;
            } else if (gamepad2.x) {
                level = 0;
            }else if(gamepad1.a){
                level =
                        4;
            }

            raiseArm(level);
            telemetry.addData("staff", gandalfStaff.getCurrentPosition());


            /*******************************/
            /** Claw Control Section *******/
            /*******************************/

            if (gamepad2.right_bumper) {
                clampy.setPosition(0.54); // open
            } else if (gamepad2.left_bumper) {
                clampy.setPosition(0.96); //close
            }
            telemetry.addData("clamp", clampy.getPosition());


            /**********************************/
            /** Duck Spinner Control Section **/
            /**********************************/
            if(gamepad1.right_trigger>0){
                eyeball.setPower(gamepad1.right_trigger);
            }else if(gamepad1.left_trigger>0){
                eyeball.setPower(-gamepad1.left_trigger);
            }else{
                eyeball.setPower(0);
            }

            telemetry.update();
        }

        // Stop the motors
        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);

    }

    // Raise arm to 3 different positions
    // variables declared, not the actual values bruh
    final int Cap_Min = 900;
    final int Cap_Max = 1100;
    final int TOP_MAX = 880;
    final int TOP_MIN = 770;
    final int MIDDLE_MIN = 513;
    final int MIDDLE_MAX = 600;
    final int BOTTOM_MIN = 216;
    final int BOTTOM_MAX = 300;
    final int GROUND_MIN = -100;
    final int GROUND_MAX = 100;

    public void raiseArm(int level) {
        // if magnet sensor is active reset the arm encoder
        if (maggot.isPressed()) {
            gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        int where = gandalfStaff.getCurrentPosition();

        // ground level stuff
        if (level == 0) {
            if (where > 100 || !maggot.isPressed()) {
                gandalfStaff.setPower(0.1);
            } else {
                gandalfStaff.setPower(0);
            }
            return;
        }

        // Depending on level set min and max variables and then use them
        // in the below loop
        int min = 0;
        int max = 0;
        if (level == 1) {
            min = BOTTOM_MIN;
            max = BOTTOM_MAX;
        } else if (level == 2) {
            min = MIDDLE_MIN;
            max = MIDDLE_MAX;
        } else if (level == 3) {
            min = TOP_MIN;
            max = TOP_MAX;
        }else if (level==4) {
            min = Cap_Min;
            max = Cap_Max;
        }

        // if current position > top_max then set power to turn backwards
        // if current position < top_min then set power to turn forwards
        if (where < min - 40) {
            gandalfStaff.setPower(-0.5);
        } else if (where < min - 30) {
            gandalfStaff.setPower(-0.4);
        } else if (where < min - 20) {
            gandalfStaff.setPower(-0.3);
        } else if (where < min - 10) {
            gandalfStaff.setPower(-0.2);
        } else if (where > max + 50) {
            gandalfStaff.setPower(0.1);
        } else {
            gandalfStaff.setPower(-0.13);
        }

    }

    final int STAFF_TURN_MIN = 150;
    final int TURN_POSITION_0 = 50;
    final int TURN_POSITION_1 = -590;
    final int TURN_POSITION_2 = -1800;

    public void turnTable(int position) {
        if (!(position == 0 || position == 1 || position == 2)) {
            return;
        }
        if (gandalfStaff.getCurrentPosition() < STAFF_TURN_MIN) {
            return;
        }
        // TODO: if you are at position 0 make sure the button1 isn't pushed
        if(position == 0){
            if(touchyFront.getState() == false){
                turnTable.setPower(0);
                turnTable.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnTable.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                return;
            }

        }

        if(position == 2){
            if(touchyBack.getState() == false){
                turnTable.setPower(0);
                return;
            }
        }
        // TODO: if you are at position 2 make sure the button2 isn't pushed

        int tablePosition = turnTable.getCurrentPosition();
        int turnTo = 0;

        if (position == 0) {
            turnTo = TURN_POSITION_0;
        } else if (position == 1) {
            turnTo = TURN_POSITION_1;
        } else if (position == 2) {
            turnTo = TURN_POSITION_2;
        }

        if (tablePosition > turnTo + 40) {
            turnTable.setPower(-0.4);
        } else if (tablePosition > turnTo + 25) {
            turnTable.setPower(-0.25);
        } else if (tablePosition > turnTo + 15) {
            turnTable.setPower(-0.15);
        } else if (tablePosition > turnTo + 5) {
            turnTable.setPower(0.0);
        } else if (tablePosition < turnTo - 40) {
            turnTable.setPower(0.4);
        } else if (tablePosition < turnTo - 25) {
            turnTable.setPower(0.25);
        } else if (tablePosition < turnTo - 15) {
            turnTable.setPower(0.15);
        } else if (tablePosition < turnTo - 5) {
            turnTable.setPower(0.0);
        } else {
            turnTable.setPower(0.0);
        }

    }

    public double ramp(double power, long startTime) {
        // ramp for 0.75 seconds
        long t = System.currentTimeMillis() - startTime;
        if (t >= 500) {
            return power;
        } else {
            return power / 500 * t;
        }
    }
}
