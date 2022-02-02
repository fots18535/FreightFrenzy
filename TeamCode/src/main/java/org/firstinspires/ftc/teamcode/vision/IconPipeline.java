package org.firstinspires.ftc.teamcode.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class IconPipeline extends OpenCvPipeline {

    public enum Mode {
        NOTHING, ICON
    }

    Mode mode = Mode.NOTHING;

    Point lu;
    Point ll;
    Point mu;
    Point ml;
    Point ru;
    Point rl;


    // HSV threshold values
    Scalar lower = new Scalar(78, 78, 37);
    Scalar upper = new Scalar(112, 255, 255);

    /*
     * Working variables
     */
    Mat left, mid, right;
    Mat hsv = new Mat();
    Mat mask = new Mat();
    Mat mask3chan = new Mat();
    int avg1, avg2, avg3;

    // Volatile since accessed by OpMode thread w/o synchronization
    private volatile IconPosition position = IconPosition.LEFT;

    // TODO: add method to set the upper and lower threshold values

    @Override
    public void init(Mat firstFrame) {
        Imgproc.cvtColor(firstFrame, hsv, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsv, lower, upper, mask);
        Imgproc.cvtColor(mask, mask3chan, Imgproc.COLOR_GRAY2RGB);

        int w = firstFrame.width();
        int h = firstFrame.height();

        lu = new Point(0, 0);
        ll = new Point(w / 3, h);
        mu = new Point(w / 3, 0);
        ml = new Point(2 * w / 3, h);
        ru = new Point(2 * w / 3, 0);
        rl = new Point(w, h);

        /*
         * Submats are a persistent reference to a region of the parent
         * buffer. Any changes to the child affect the parent, and the
         * reverse also holds true.
         */

        left = mask.submat(new Rect(lu, ll));
        mid = mask.submat(new Rect(mu, ml));
        right = mask.submat(new Rect(ru, rl));

    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat output = input;

        switch (mode) {
            case ICON:
                output = findIcon(input);
                break;
        }

        return output;
    }

    public Mat findIcon(Mat input) {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        Core.inRange(hsv, lower, upper, mask);
        Imgproc.cvtColor(mask, mask3chan, Imgproc.COLOR_GRAY2RGB);

        avg1 = (int) Core.mean(left).val[0];
        avg2 = (int) Core.mean(mid).val[0];
        avg3 = (int) Core.mean(right).val[0];

        avgs[0] = avg1;
        avgs[1] = avg2;
        avgs[2] = avg3;

        if (avg1 > avg2 && avg1 > avg3) {
            position = IconPosition.LEFT;
        } else if (avg2 > avg1 && avg2 > avg3) {
            position = IconPosition.CENTER;
        } else {
            position = IconPosition.RIGHT;
        }

        return mask3chan;
    }

    /*
     * Call this from the OpMode thread to obtain the latest analysis
     */
    public IconPosition getAnalysis() {
        return position;
    }

    int[] avgs = {0, 0, 0};

    public int[] getAvgs() {
        return avgs;
    }
}
