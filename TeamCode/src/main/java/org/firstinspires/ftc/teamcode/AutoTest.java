package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;
import static org.firstinspires.ftc.teamcode.OutputController.WRIST_POSITION_OUTSIDE_ROBOT;
import static org.firstinspires.ftc.teamcode.OutputController.WRIST_POSITION_SIDEWAYS;


@Autonomous(name = "Full Run" , group = "6209")

public class AutoTest extends BaseLinearOpMode
{
    DcMotor motorFR = null;
    DcMotor motorFL = null;
    DcMotor motorBR = null;
    DcMotor motorBL = null;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize(true);
        HardwareMap hwMap = robot.driveTrain.hwMap;
        motorFR = hwMap.dcMotor.get("motorFR");
        motorFL = hwMap.dcMotor.get("motorFL");
        motorBR = hwMap.dcMotor.get("motorBR");
        motorBL = hwMap.dcMotor.get("motorBL");

        waitForStart();

        // testnewClamp();

        strafeRightByInches(1, 48);
        Thread.sleep(5000);

        strafeLeftByInches(1, 48);
        Thread.sleep(5000);
    }

    protected boolean usesTensorFlow() {
        return true;
    }

    @Override
    protected boolean usesVuforia() {
        return true;
    }

    private void printSkystonePositionUntilStarted() throws InterruptedException {
        while (!isStarted()) {
            StonePosition sp = getSkystonePosition();
            sleep(500);
        }
    }

    public void testnewClamp(){
        robot.output.rightDoor.setPosition(0.42);
        robot.output.leftDoor.setPosition(0.4);
        sleep(5_000);

       // robot.output.rightDoor.setPosition(0.5);
       // robot.output.leftDoor.setPosition(0.6);
       // sleep(25_000);
    }



    public void testStoneHooks(){
        robot.stoneHooks.raiseOneHook("L");
        sleep(2500);
        robot.stoneHooks.raiseOneHook("R");
        sleep(2500);
        robot.stoneHooks.lowerOneHook("L");
        sleep(2500);
        robot.stoneHooks.lowerOneHook("R");
        sleep(2500);
    }

    // TODO:
    //
    //  1. Measure current behavior (move forward & strafe)
    //     4 in at 0.3 power is accurate
    //     4 in at 0.8 power is inaccurate
    //
    //
    //  . Measure minimum power to move robot forward and in strafe.
    //
    //  . Ramp up and ramp down with gradual acceleration to measure ticks per inch.
    //
    //


    public void pullFoundationStraightBack()
    {
        robot.foundationHooks.lowerHooks();
        moveBackwardByInches(0.8, 36);
//        rotate(-20);
        robot.foundationHooks.raiseHooks();
        strafeLeftByInches(0.8, 10);
        rotateToAbsoluteYaw(70);
        moveBackwardByInches(0.8, 33);
    }

    public void testWrist(){
        robot.output.wrist.setPosition(0);
        sleep(2000);
        robot.output.wrist.setPosition(1);
        sleep(2000);
    }

    public void findBlockDistances() {
        moveForwardByInches(0.5, 16);
        sleep(5000);
        strafeLeftByInches(0.7, 8);
        sleep(5000);
        strafeLeftByInches(0.7, 8);
        sleep(5000);
    }

    public void arcFoundationUsingEncValues()
    {
        robot.foundationHooks.lowerHooks();
        moveWithEncoders("Backwards", 1, 10_000, -2 * 2710, -2 *332, -2*-1005, -2*-486, 90.0);
    }

    public void testTurn() {

        telemetry.addData("Status", "Running Autonomous!");
        telemetry.update();
//        rotate(90, 1);

    }


    public void testStoneHooks2(){
        robot.stoneHooks.lowerOneHook("L");
        sleep(1000);
        robot.stoneHooks.lowerOneHook("R");
        sleep(1000);
        robot.stoneHooks.lowerOneHook("L");
        sleep(1000);
        robot.stoneHooks.lowerOneHook("R");
        sleep(1000);
    }

    public void arcTest()
    {
        robot.foundationHooks.lowerHooks();
        resetEncoders();
        while (opModeIsActive())
        {
            telemetry.addData("motorFR", motorFR.getCurrentPosition());
            telemetry.addData("motorFL", motorFL.getCurrentPosition());
            telemetry.addData("motorBR", motorBR.getCurrentPosition());
            telemetry.addData("motorBL", motorBL.getCurrentPosition());
            telemetry.update();
            sleep(1000);
        }
    }

    public void main() throws InterruptedException
    {
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorBR = hardwareMap.dcMotor.get("motorBR");
        motorBL = hardwareMap.dcMotor.get("motorBL");

        runUsingEncoders();

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);

        log("Status", "Motor initialization complete");

        driveForwardDistance(0.5, 5000);
    }

    public void stopDriving()
    {
        driveForward(0);
    }

    public void driveForward(double power)
    {
        log("Status", "Driving forward with power: " + power);
        // all go forward
        motorBL.setPower(power);
        motorBR.setPower(power);
        motorFL.setPower(power);
        motorFR.setPower(power);
    }

    public void testElbowsTogether(){
        robot.output.setElbowPositions(1);
        sleep(2000);
        robot.output.setElbowPositions(0);
        sleep(2000);
    }
    public void testElbows(){
        robot.output.elbowR.setPosition(1);
        sleep(2000);
        robot.output.elbowR.setPosition(0);
        sleep(2000);
        robot.output.elbowL.setPosition(1);
        sleep(2000);
        robot.output.elbowL.setPosition(0);
    }

    public void wristInAndOut()
    {
        robot.output.moveWristToPosition(WRIST_POSITION_OUTSIDE_ROBOT);
        sleep(5000);
        robot.output.moveWristToPosition(robot.output.WRIST_POSITION_INSIDE_ROBOT);
        sleep(5000);
    }

    public void driveBackward(double power)
    {
        // all go backward
        motorBL.setPower(-power);
        motorBR.setPower(-power);
        motorFL.setPower(-power);
        motorFR.setPower(-power);
    }

    public void strafeLeft(double power)
    {
        // FL & BR go forward
        // FR & BL go backward
        motorBL.setPower(-power);
        motorBR.setPower(power);
        motorFL.setPower(power);
        motorFR.setPower(-power);
    }

    public void strafeRight(double power)
    {
        // FR & BL go forward
        // FL & BR go backward
        motorBL.setPower(power);
        motorBR.setPower(power);
        motorFL.setPower(power);
        motorFR.setPower(power);
    }

    @SuppressLint("DefaultLocale")
    public void driveForwardDistance(double power, int distance) throws InterruptedException
    {
        resetEncoders();

        // set target distance
        motorFR.setTargetPosition(distance);
        motorBR.setTargetPosition(distance);
        motorFL.setTargetPosition(distance);
        motorBL.setTargetPosition(distance);

        runToPosition();

        driveForward(power);

        log("Current Position", String.format("FR=%d BR=%d FL=%d BL=%d",
                motorFR.getCurrentPosition(),
                motorBR.getCurrentPosition(),
                motorFL.getCurrentPosition(),
                motorBL.getCurrentPosition()));

        log("Target Position", String.format("FR=%d BR=%d FL=%d BL=%d",
                motorFR.getTargetPosition(),
                motorBR.getTargetPosition(),
                motorFL.getTargetPosition(),
                motorBL.getTargetPosition()));

        while(motorFL.isBusy() || motorBL.isBusy())
        {
            log("Current Position", String.format("FR=%d BR=%d FL=%d BL=%d",
                    motorFR.getCurrentPosition(),
                    motorBR.getCurrentPosition(),
                    motorFL.getCurrentPosition(),
                    motorBL.getCurrentPosition()));

            log("Target Position", String.format("FR=%d BR=%d FL=%d BL=%d",
                    motorFR.getTargetPosition(),
                    motorBR.getTargetPosition(),
                    motorFL.getTargetPosition(),
                    motorBL.getTargetPosition()));


            Thread.sleep(100);
        }

        log("Status","done");
        stopDriving();
        resetEncoders();
    }

    public void resetEncoders()
    {
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runToPosition()
    {
        motorBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void runUsingEncoders()
    {
        motorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void log(String caption, Object message)
    {
        telemetry.addData(caption, message);
        telemetry.update();
    }

}
