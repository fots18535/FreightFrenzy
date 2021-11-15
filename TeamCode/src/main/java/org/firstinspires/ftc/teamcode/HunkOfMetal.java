package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class HunkOfMetal {
    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;
    Gyro2 gyro;
    DcMotor gandalfStaff;
    LinearOpMode mode;

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
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        gandalfStaff = mode.hardwareMap.get(DcMotor.class, "staff");
        gyro.startGyro();
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

    // TODO: raise arm to 3 different positions
    // variables declared, not the actual values bruh
    final int TOP_MIN = 900;
    final int TOP_MAX = 1000;
    //final int MIDDLE_MIN = ;
    //final int MIDDLE_MAX = ;
    //final int MIDDLE_MIN = ;
    //final int BOTTOM_MAX = ;
    public void raiseArm(int level)
    {
        // if current position > top_max then set power to turn backwards
        // if current position < top_min then set power to turn forwards


        int where = gandalfStaff.getCurrentPosition();
        while(!(where >= TOP_MIN && where <= TOP_MAX)) {
            if (where > TOP_MAX) {
                gandalfStaff.setPower(-1.0);
            } else if (where < TOP_MIN) {
                gandalfStaff.setPower(1.0);
            }
        }
        gandalfStaff.setPower(0.0);

    }





    // TODO: spin turntable until blue or red color

    // TODO: open and close claw
}



