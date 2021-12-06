package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp
public class ManualDriveDookie extends LinearOpMode {

    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;
    DcMotor turnTable;
    DcMotor gandalfStaff;
    Servo clampy;
    TouchSensor maggot;
    TouchSensor touchyFront;
    TouchSensor touchyBack;
    DcMotor eyeball;
    public static final int blueTeam = 1;
    public static final int redShared = 3;
    public static final int blueShared = 2;
    public static final int redTeam = 4;
    public int whichPath;

    int zeropos = 0;

    public ManualDriveDookie(int x)
    {
        super();
        whichPath = x;
    }

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
        touchyBack = hardwareMap.get(TouchSensor.class, "touchyBack");
        touchyFront = hardwareMap.get(TouchSensor.class, "touchyFront");

        ColorTester black = new ColorTester(106.6f, 233.1f, 0.201f, 0.493f, 0.009f, 0.015f);
        ColorTester red = new ColorTester(0, 1, 0, 1, 0, 1);
        ColorTester blue = new ColorTester(0, 1, 0, 1, 0, 1);

        // Stops coasting
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turnTable.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        gandalfStaff.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turnTable.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turnTable.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        zeropos = turnTable.getCurrentPosition();

        waitForStart();
        clampy.setPosition(0.52);
        boolean encoderReset = false;
        int level = 0;
        int turnPosition = 0;
        while (opModeIsActive()) {

            /*****************************/
            /** Driving Control Section **/
            /*****************************/

            //Get the input from the gamepad controller
            double leftX = gamepad1.left_stick_x;
            double leftY = gamepad1.left_stick_y;
            double rightX = -gamepad1.right_stick_x;
            double rightY = gamepad1.right_stick_y;

            if(whichPath == redTeam)
            {
                rightY = -rightY;
            }
            else if(whichPath == blueShared)
            {
                rightX = -rightX;
            }

            leftBack.setPower(rightX + rightY + leftX);
            leftFront.setPower(rightX + rightY - leftX);
            rightBack.setPower(rightX - rightY + leftX);
            rightFront.setPower(rightX - rightY - leftX);


            /*******************************/
            /** Turntable Control Section **/
            /*******************************/

            // TODO: can we use color or touch sensor(s) to recalibrate encoder?
            // NormalizedRGBA colors = sensorColor.getNormalizedColors();

            // Make sure arm is up X tics before turning
            if (gamepad1.dpad_right) {
                turnPosition = -1;
                if (gandalfStaff.getCurrentPosition() >= STAFF_TURN_MIN) {
                    turnTable.setPower(-0.5);
                }
            } else if (gamepad1.dpad_left) {
                turnPosition = -1;
                if (gandalfStaff.getCurrentPosition() >= STAFF_TURN_MIN) {
                    turnTable.setPower(0.5);
                }
            } else if (gamepad1.dpad_up && whichPath == redTeam) {
                turnPosition = 0;
                level = 3;
            } else if (gamepad1.dpad_down && whichPath == redTeam) {
                turnPosition = 1;
                //level = 0;
            }
            else if (gamepad1.dpad_up && whichPath == redShared) {
                turnPosition = 2;
                level = 2;
            } else if (gamepad1.dpad_down && whichPath == redShared) {
                turnPosition = 1;
                //level = 0;
            }
            else if (gamepad1.dpad_up && whichPath == blueTeam) {
                turnPosition = 2;
                level = 3;
            } else if (gamepad1.dpad_down && whichPath == blueTeam) {
                turnPosition = 1;
                //level = 0;
            }
            else if (gamepad1.dpad_up && whichPath == blueShared) {
                turnPosition = 0;
                level = 2;
            } else if (gamepad1.dpad_down && whichPath == blueShared) {
                turnPosition = 1;
                //level = 0;
            }

            if(turnPosition == 0 || turnPosition == 1 || turnPosition == 2) {
                turnTable(turnPosition);
            } else if(!gamepad1.dpad_left && !gamepad1.dpad_right) {
                turnTable.setPower(0.0);
            }
            telemetry.addData("table", turnTable.getCurrentPosition() - zeropos);


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
            if (gamepad1.y) {
                level = 3;
            } else if (gamepad1.b) {
                level = 2;
            } else if (gamepad1.a) {
                level = 1;
            } else if (gamepad1.x) {
                level = 0;
            }

            raiseArm(level);
            telemetry.addData("staff", gandalfStaff.getCurrentPosition());


            /*******************************/
            /** Claw Control Section *******/
            /*******************************/

            if (gamepad1.right_bumper) {
                clampy.setPosition(0.52); // open
            } else if (gamepad1.left_bumper) {
                clampy.setPosition(0.75); //close
            }
            telemetry.addData("clamp", clampy.getPosition());


            /**********************************/
            /** Duck Spinner Control Section **/
            /**********************************/
            if (gamepad1.right_trigger > 0) {
                eyeball.setPower(gamepad1.right_trigger);
            } else if (gamepad1.left_trigger > 0) {
                eyeball.setPower(-gamepad1.left_trigger);
            } else {
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
    final int TOP_MIN = 864;
    final int TOP_MAX = 1000;
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
            if (where > 100) {
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
    final int TURN_POSITION_1 = -350;
    final int TURN_POSITION_2 = -750;

    public void turnTable(int position) {
        if (!(position == 0 || position == 1 || position == 2)) {
            turnTable.setPower(0.0);
            return;
        }
        if (gandalfStaff.getCurrentPosition() < STAFF_TURN_MIN) {
            turnTable.setPower(0.0);
            return;
        }

        // TODO: if you are at position 0 make sure the button1 isn't pushed
        if (position == 0) {
            if (touchyFront.isPressed()) {
                turnTable.setPower(0);
                zeropos = turnTable.getCurrentPosition();
                return;
            }

        }

        if (position == 2) {
            if (touchyBack.isPressed()) {
                turnTable.setPower(0);
                zeropos = turnTable.getCurrentPosition() + 730;
                return;
            }
        }

        // TODO: if you are at position 2 make sure the button2 isn't pushed

        int tablePosition = turnTable.getCurrentPosition() - zeropos;
        int turnTo = 0;

        if (position == 0) {
            turnTo = TURN_POSITION_0;
        } else if (position == 1) {
            turnTo = TURN_POSITION_1;
        } else if (position == 2) {
            turnTo = TURN_POSITION_2;
        }

        if (tablePosition > turnTo + 40) {
            turnTable.setPower(-0.7);
        } else if (tablePosition > turnTo + 5) {
            turnTable.setPower(-0.3);
        } else if (tablePosition < turnTo - 40) {
            turnTable.setPower(0.7);
        } else if (tablePosition < turnTo - 5) {
            turnTable.setPower(0.3);
        } else {
            turnTable.setPower(0.0);
        }
    }
}
