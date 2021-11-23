package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ControllerState {
    public boolean a = false;
    public boolean b = false;
    public boolean x = false;

    public void update(Gamepad gp) {
        a = gp.a;
        b = gp.b;
        x = gp.x;
    }
}
