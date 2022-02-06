package org.firstinspires.ftc.teamcode.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Rect2d;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class BlockPipeline extends OpenCvPipeline {

    Point lu;
    Point ll;

    final Scalar TEAL = new Scalar(3, 148, 252);
    final Scalar PURPLE = new Scalar(158, 52, 235);
    final Scalar RED = new Scalar(255, 0, 0);
    final Scalar GREEN = new Scalar(0, 255, 0);
    final Scalar BLUE = new Scalar(0, 0, 255);

    // HSV threshold values
    Scalar lower = new Scalar(18, 75, 122);
    Scalar upper = new Scalar(27, 255, 255);

    /*
     * Working variables
     */
    Mat inclaw;
    Mat hsv = new Mat();
    Mat mask = new Mat();
    Mat contoursOnPlainImageMat = new Mat();
    Mat output = new Mat();

    Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
    Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(6, 6));

    @Override
    public void init(Mat firstFrame) {
        Imgproc.cvtColor(firstFrame, hsv, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsv, lower, upper, mask);

        int w = firstFrame.width();
        int h = firstFrame.height();

        lu = new Point(0, 0);
        ll = new Point(w / 3, h);

        /*
         * Submats are a persistent reference to a region of the parent
         * buffer. Any changes to the child affect the parent, and the
         * reverse also holds true.
         */
        inclaw = mask.submat(new Rect(lu, ll));
    }

    @Override
    public Mat processFrame(Mat input) {

        Mat output = findNearestBlock(input);
        return output;
    }

    // set some value (true / false) if a block is in the claw area
    public Mat blockInClaw(Mat input) {
        return input;
    }

    // find best point where the closest blocks are - closest or straightest?
    public Mat findNearestBlock(Mat input) {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        Imgproc.erode(hsv, output, erodeElement);
        Imgproc.dilate(output, output, dilateElement);

        // A list we'll be using to store the contours we find
        ArrayList<MatOfPoint> contoursList = new ArrayList<>();

        Core.inRange(output, lower, upper, mask);

        // Ok, now actually look for the contours! We only look for external contours.
        Imgproc.findContours(mask, contoursList, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        int w = input.width();
        int h = input.height();

        // loop over contours, filter by area, find closest
        double bestDist = Double.MAX_VALUE;
        MatOfPoint bestCont = null;
        for (MatOfPoint matty : contoursList) {
            double area = Imgproc.contourArea(matty);
            if (area > 200) {
                MatOfPoint2f matty2f = new MatOfPoint2f(matty.toArray());
                //RotatedRect potatorect = Imgproc.minAreaRect(matty2f);

                double dist = Imgproc.pointPolygonTest(matty2f, new Point(w/2, h/2), true);
                dist = Math.abs(dist);
                if(dist < bestDist) {
                    bestDist = dist;
                    bestCont = matty;
                }
            }
        }

        // We do draw the contours we find, but not to the main input buffer.
        input.copyTo(contoursOnPlainImageMat);
        if(bestCont != null) {
            List<MatOfPoint> bc = new LinkedList<>();
            bc.add(bestCont);
           Imgproc.drawContours(contoursOnPlainImageMat, bc, -1, RED, 3, 8);
        }
        Imgproc.rectangle(contoursOnPlainImageMat, new Rect(new Point(w/2, h/2), new Point(w/2-5, h/2-5)) ,BLUE, 4);

        return contoursOnPlainImageMat;
    }
}
