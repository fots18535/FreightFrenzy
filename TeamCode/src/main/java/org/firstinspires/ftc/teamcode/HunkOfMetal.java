package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class HunkOfMetal {
    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;
    Gyro2 gyro;
    DcMotor gandalfStaff;
    LinearOpMode mode;
    TouchSensor maggot;
    DcMotor turnTable;
    DcMotor eyeball;
    Servo clampy;
    NormalizedColorSensor sensorColor;
    ColorTester black;
    ColorTester red;
    ColorTester blue;
    ColorTester green;


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
        maggot = mode.hardwareMap.get(TouchSensor.class, "maggot");
        turnTable = mode.hardwareMap. get(DcMotor.class, "turnTable");
        eyeball = mode.hardwareMap.get(DcMotor.class, "eyeball");
        clampy = mode.hardwareMap.get(Servo.class, "clampy");

        gandalfStaff.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gandalfStaff.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turnTable.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turnTable.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        sensorColor = mode.hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        black = new ColorTester(106.6f,233.1f,0.201f,0.493f,0.009f,0.015f);
        red = new ColorTester(0,1,0,1,0,1);
        blue = new ColorTester(0,1,0,1,0,1);
        green = new ColorTester(0,1,0,1,0,1);

        clampy.setPosition(0.52);
        gyro.startGyro();
    }


    // Positive power slides left
    // Negative power slides right
    public void chaChaRealSmooth(double power, double length) {
        // Reset the encoder to 0
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Turn on motors to slide
        leftBack.setPower(-power);
        leftFront.setPower(power*0.8);
        rightBack.setPower(-power);
        rightFront.setPower(power*0.8);

        // Slide until encoder ticks are sufficient
        while(mode.opModeIsActive()) {
            //absolute value of getCurrentPosition()
            int tics = leftFront.getCurrentPosition();
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
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        gyro.reset();
        // Setting the motor power based on the input
        motorsForward(power);

        // Go forward and park behind the line
        while(mode.opModeIsActive()) {
            //absolute value of getCurrentPosition()
            int tics = leftFront.getCurrentPosition();
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
    final int MIDDLE_MIN = 800;
    final int MIDDLE_MAX = 700;
    final int BOTTOM_MIN = 500;
    final int BOTTOM_MAX = 400;
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

        int min = 0;
        int max = 0;
        if(level == 0)
        {
            min = GROUND_MIN;
            max = GROUND_MAX;
        } else if(level == 1) {
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

        int where = gandalfStaff.getCurrentPosition();
        while(!(where >= min && where <= max)) {
            if (where > max) {
                gandalfStaff.setPower(-1.0);
            } else if (where < min) {
                gandalfStaff.setPower(1.0);
            }
        }
        gandalfStaff.setPower(0.0);

    }

    //DcMotor turnTable;
    //NormalizedColorSensor sensorColor;
    // TODO: spin turntable until blue or red color
    public void clawForward()
    {
        NormalizedRGBA colors = sensorColor.getNormalizedColors();
        // if black turn left, if blue turn right, if green turn off
        if(black.isTargetColor(colors))
        {
            turnTable.setPower(0.5);
        }
        else if(blue.isTargetColor(colors))
        {
            turnTable.setPower(-0.5);
        }
        else
        {
            turnTable.setPower(0.0);
        }
    }

    public void clawBackward()
    {
        NormalizedRGBA colors = sensorColor.getNormalizedColors();
        // if black turn right, if red turn left, if green turn off
        if(black.isTargetColor(colors))
        {
            turnTable.setPower(-0.5);
        }
        else if(red.isTargetColor(colors))
        {
            turnTable.setPower(0.5);
        }
        else
        {
            turnTable.setPower(0.0);
        }
    }

    // TODO: open and close claw
}



