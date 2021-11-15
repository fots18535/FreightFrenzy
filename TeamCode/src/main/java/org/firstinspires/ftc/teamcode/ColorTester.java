package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.NormalizedRGBA;

public class ColorTester {

    float hsvValues[] = new float[]{0F, 0F, 0F};
    float minH;
    float maxH;
    float minS;
    float maxS;
    float minV;
    float maxV;

    public ColorTester(float minH, float maxH, float minS, float maxS, float minV, float maxV) {
        this.minH = minH;
        this.maxH = maxH;
        this.minS = minS;
        this.maxS = maxS;
        this.minV = minV;
        this.maxV = maxV;
    }

    public boolean isTargetColor(NormalizedRGBA colors)
    {
        boolean f = false;

        Color.colorToHSV(colors.toColor(), hsvValues);
        // changes rbg to hsv

        float h = hsvValues[0];
        float s = hsvValues[1];
        float v = hsvValues[2];

        if (h >= minH && h <= maxH && s >= minS && s<= maxS && v >= minV && v <= maxV)
        {
            f = true;
        }

        return f;
    }
}
