package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class Detector {
    //OpenCvInternalCamera phoneCam;
    OpenCvCamera phoneCam;
    IconDeterminationPipeline pipeline;
    LinearOpMode op;

    public Detector(LinearOpMode op) {
        this.op = op;
    }

    public void start() {
        //int cameraMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
        //phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        int cameraMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createWebcam(op.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);


        pipeline = new IconDeterminationPipeline();
        phoneCam.setPipeline(pipeline);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
    }

    public void stop() {
        phoneCam.stopStreaming();
        phoneCam.closeCameraDevice();
    }

    // TODO: add method to set the upper and lower threshold values

    // TODO: add method return if the claw contains a block

    // TODO: add method to change mode from icon detector to block detector

    public IconPosition getPosition() {
        return pipeline.getAnalysis();
    }

    public static class IconDeterminationPipeline extends OpenCvPipeline
    {

        /*
         * Example of how points A and B work to define a rectangle
         *
         *   ------------------------------------
         *   | (0,0) Point A                    |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                  Point B (70,50) |
         *   ------------------------------------
         *
         */

        Point lu;
        Point ll;
        Point mu;
        Point ml;
        Point ru;
        Point rl;

        static final Scalar TEAL = new Scalar(3, 148, 252);
        static final Scalar PURPLE = new Scalar(158, 52, 235);
        static final Scalar RED = new Scalar(255, 0, 0);
        static final Scalar GREEN = new Scalar(0, 255, 0);
        static final Scalar BLUE = new Scalar(0, 0, 255);

        // HSV threshold values
        Scalar lower = new Scalar(78,78,37);
        Scalar upper = new Scalar(112,255,255);

        /*
         * Working variables
         */
        Mat left, mid, right;
        Mat hsv = new Mat();
        Mat mask = new Mat();
        Mat mask3chan = new Mat();
        Mat contoursOnPlainImageMat = new Mat();
        int avg1, avg2, avg3;
        Mat output = new Mat();

        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(6, 6));

        // Volatile since accessed by OpMode thread w/o synchronization
        private volatile IconPosition position = IconPosition.LEFT;

        // TODO: add method to set the upper and lower threshold values

        @Override
        public void init(Mat firstFrame)
        {
            Imgproc.cvtColor(firstFrame, hsv, Imgproc.COLOR_RGB2HSV);
            Core.inRange(hsv, lower, upper, mask);
            Imgproc.cvtColor(mask, mask3chan, Imgproc.COLOR_GRAY2RGB);

            int w = firstFrame.width();
            int h = firstFrame.height();

            lu = new Point(0,0);
            ll = new Point(w/3,h);
            mu = new Point(w/3,0);
            ml = new Point(2 * w / 3, h);
            ru = new Point(2 * w / 3, 0);
            rl = new Point(w,h);

            /*
             * Submats are a persistent reference to a region of the parent
             * buffer. Any changes to the child affect the parent, and the
             * reverse also holds true.
             */

            left = mask.submat(new Rect(lu,ll));
            mid = mask.submat(new Rect(mu,ml));
            right = mask.submat(new Rect(ru,rl));

        }

        @Override
        public Mat processFrame(Mat input) {

            // TODO: need different modes - one for icon position, one for detecting blocks

            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            // findIcon(hsv)
            // blockInClaw(hsv)
            // findNearestBlock(hsv)

            return findNearestBlock(hsv);
        }

        public Mat findIcon(Mat hsv) {
            Core.inRange(hsv, lower, upper, mask);
            Imgproc.cvtColor(mask, mask3chan, Imgproc.COLOR_GRAY2RGB);

            avg1 = (int) Core.mean(left).val[0];
            avg2 = (int) Core.mean(mid).val[0];
            avg3 = (int) Core.mean(right).val[0];

            avgs[0] = avg1;
            avgs[1] = avg2;
            avgs[2] = avg3;

            if(avg1 > avg2 && avg1 > avg3)
            {
                position = IconPosition.LEFT;
            }
            else if(avg2 > avg1 && avg2 > avg3)
            {
                position = IconPosition.CENTER;
            }
            else
            {
                position = IconPosition.RIGHT;
            }

            return mask3chan;
        }

        // set some value (true / false) if a block is in the claw area
        public void blockInClaw(Mat input) {
            // threshold

            // is there a white blob in the claw rectangle?


        }

        // find best point where the closest blocks are - closest or straightest?
        public Mat findNearestBlock(Mat input) {
            Imgproc.erode(input, output, erodeElement);
            Imgproc.dilate(output, output, dilateElement);

            // A list we'll be using to store the contours we find
            ArrayList<MatOfPoint> contoursList = new ArrayList<>();

            Core.inRange(output, new Scalar(18,75,122), new Scalar(27,255,255), mask);

            // Ok, now actually look for the contours! We only look for external contours.
            Imgproc.findContours(mask, contoursList, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

            // We do draw the contours we find, but not to the main input buffer.
            input.copyTo(contoursOnPlainImageMat);
            Imgproc.drawContours(contoursOnPlainImageMat, contoursList, -1, RED, 3, 8);

            // loop over contours, filter by area, find closest
            for(MatOfPoint matty: contoursList)
            {
                double area = Imgproc.contourArea(matty);
                if(area > 200)
                {
                    MatOfPoint2f  matty2f = new MatOfPoint2f( matty.toArray() );
                    //RotatedRect potatorect = Imgproc.minAreaRect(matty2f);

                    double dist = Imgproc.pointPolygonTest(matty2f, new Point(1,1), true);

                }
            }

            return contoursOnPlainImageMat;
        }

        /*
         * Call this from the OpMode thread to obtain the latest analysis
         */
        public IconPosition getAnalysis()
        {
            return position;
        }

        int[] avgs = {0,0,0};
        public int[] getAvgs() {
            return avgs;
        }
    }
}
