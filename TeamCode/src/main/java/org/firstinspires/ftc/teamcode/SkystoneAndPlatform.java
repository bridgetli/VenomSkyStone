/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.RobotLog;


/**
 * This 2019-2020 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Skystone game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */

@Autonomous(name = "SkystoneAndPlatform", group = "6209")
@Disabled
public class SkystoneAndPlatform extends BaseLinearOpMode{

    boolean configOnly = false;

    // Boolean Values for input
    boolean isStartingBlue = true;
    boolean parkOnSide = true;

    @Override
    public void runOpMode() throws InterruptedException
    {
        initialize(true);
        configMode();

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        // TODO: This assumes blue currently.

        if (opModeIsActive())
        {
            moveForwardByInches(0.8, 14);
            if(isStartingBlue)
                strafeRightByInches(0.9, 12);
            else
                strafeLeftByInches(0.9, 12);

            if (isSkystone(isStartingBlue))
            {
                grabStoneInAuto(22);
                driveUnderBridge(isStartingBlue, 70);
            }
            else
            {
                strafeToStone(isStartingBlue);

                if(isSkystone(isStartingBlue))
                {
                    grabStoneInAuto(22);
                    driveUnderBridge(isStartingBlue, 78);
                }
                else
                {
                    strafeToStone(isStartingBlue);
                    grabStoneInAuto(22);
                    driveUnderBridge(isStartingBlue, 86);
                }
            }

            moveForwardByInches(1, 25); // move forward to foundation
            dropStone();

            pullFoundationAndPark(isStartingBlue, parkOnSide);
        }
    }

    @Override
    protected boolean usesTensorFlow() {
        return true;
    }

    public void configMode() {
        String lastModes = "";
        telemetry.addData("Entering " , "ConfigMode");
        telemetry.update();
        do {
            if (VenomUtilities.isValueChangedAndEqualTo("1.y", gamepad1.y, true))
                isStartingBlue = !isStartingBlue;

            if (VenomUtilities.isValueChangedAndEqualTo("1.a", gamepad1.a, true))
                parkOnSide = !parkOnSide;

            logConfigModes(true);
        }

        while (!gamepad1.right_bumper && !isStarted() &&  !isStopRequested());
        telemetry.addData("ConfigMode" , lastModes);
        telemetry.update();

        RobotLog.i("configMode() stop");
    }

    private String lastModes="";
    void logConfigModes(boolean update) {
        String modes="";
        //  modes+="Alliance="+(isRedAlliance?"Red":"Blue");
        modes+=", Starting="+(isStartingBlue?"Blue":"Red");
        modes+=", Starting="+(parkOnSide?"Side":"Center");

        telemetry.addData("Alliance (Y)", isStartingBlue?"Blue":"Red");
        telemetry.addData("Park Location (A)", parkOnSide?"Side":"Center");

        if (configOnly) telemetry.addData("ConfigMode" , "Press right bumper to leave config mode.");
        if (update) telemetry.update();

        if (!modes.equals(lastModes)) {
            RobotLog.i(modes);
            lastModes=modes;
        }
    }
}