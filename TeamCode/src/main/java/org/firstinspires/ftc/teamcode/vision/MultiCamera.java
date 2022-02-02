package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class MultiCamera {
    OpenCvCamera phoneCam;
    OpenCvCamera webCam;
    BlockPipeline blockPipeline;
    IconPipeline iconPipeline;
    LinearOpMode op;

    public MultiCamera(LinearOpMode op) {
        this.op = op;
    }

    public void start() {

        int cameraMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
        int[] viewportContainerIds = OpenCvCameraFactory.getInstance()
                .splitLayoutForMultipleViewports(
                        cameraMonitorViewId, //The container we're splitting
                        2, //The number of sub-containers to create
                        OpenCvCameraFactory.ViewportSplitMethod.VERTICALLY); //Whether to split the container vertically or horizontally

        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, viewportContainerIds[0]);
        webCam = OpenCvCameraFactory.getInstance().createWebcam(op.hardwareMap.get(WebcamName.class, "Webcam 1"), viewportContainerIds[1]);

        blockPipeline = new BlockPipeline();
        iconPipeline = new IconPipeline();

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.setPipeline(iconPipeline);
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

        webCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webCam.setPipeline(blockPipeline);
                webCam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
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
        webCam.stopStreaming();
        webCam.closeCameraDevice();
    }

    public IconPosition getIconPosition() {
        return iconPipeline.getAnalysis();
    }

    public void iconMode() {
        blockPipeline.setMode(BlockPipeline.Mode.NOTHING);
        iconPipeline.setMode(IconPipeline.Mode.ICON);
    }

    public void nearestBlockMode() {
        blockPipeline.setMode(BlockPipeline.Mode.NEAREST);
        iconPipeline.setMode(IconPipeline.Mode.NOTHING);
    }

    public void blockInClawMode() {
        blockPipeline.setMode(BlockPipeline.Mode.CLAW);
        iconPipeline.setMode(IconPipeline.Mode.NOTHING);
    }
}
