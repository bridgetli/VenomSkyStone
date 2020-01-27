package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.io.PrintWriter;
import java.io.StringWriter;

@TeleOp(name = "VenomTeleOp", group = "6209")
public class VenomTeleOp extends OpMode
{
    VenomRobot robot = new VenomRobot();
    double intakePower = 1;
    double drivePower = 1;

    @Override
    public void init()
    {
        try {
            robot.init(hardwareMap, telemetry, false);
            robot.output.motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.output.motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            telemetry.addData("Initialization complete", "");
            telemetry.update();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            telemetry.addData(sw.toString().substring(0, Math.min(500, sw.toString().length())), "");
            telemetry.update();
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }
            throw new RuntimeException(e);
        }

    }

    public void loop()
    {
        doDrive();
        doFoundation();
        doOutput();
    }

//    public void encoderTelemetry()
//    {
//        int liftEnc = robot.output.motorLift.getCurrentPosition();
//        telemetry.addData("lift encoder", liftEnc);
//        telemetry.update();
//    }

    void doDrive()
    {
        doBrake();
        if(robot.driveTrain.isBraking()){
            return;
        }
        slowDown();

        double forward = -gamepad1.left_stick_y * drivePower;
        double strafe = -gamepad1.left_stick_x * drivePower;
        double rotate = gamepad1.right_stick_x * drivePower;

        robot.driveTrain.arcadeDrive(forward, strafe, rotate);
    }

    public void doBrake(){

        if(gamepad1.dpad_down || gamepad1.dpad_up || gamepad1.dpad_left || gamepad1.dpad_right ||
                gamepad2.right_trigger > 0.5 || gamepad2.left_trigger > 0.5 || gamepad2.left_bumper || gamepad2.right_bumper){
            robot.driveTrain.brake();
        }
        else {
            robot.driveTrain.unbrake();
        }
    }

    public void slowDown() {
        //TODO: ask David what button he wants to override this for when the hooks are down

        if (gamepad1.left_trigger > 0.5) {
            drivePower = 0.25;
        } else if (gamepad1.right_trigger > 0.5 || robot.foundationHooks.areHooksDown()) {
            drivePower = 0.5;
        } else {
            drivePower = 1;
        }

        // Coast if and only if we are at full power.
        robot.driveTrain.setHaltModeCoast(drivePower == 1);
    }

    /** public void doIntake()
    {
        if(gamepad2.right_bumper)
            robot.intake.setPower(intakePower);
        else if (gamepad2.left_bumper)
            robot.intake.setPower(-intakePower);
        else
            robot.intake.setPower(0);
    }**/

    public void doOutput()
    {
        // lift --> dpad
        if (gamepad2.dpad_up)
        {
            robot.output.startMoveLiftUp();
        }
        else if (gamepad2.dpad_down)
        {
            robot.output.startMoveLiftDown();
        }
        else
            {
            robot.output.stopLift();
        }

        // TODO: do we want to be able to do this?
        // also x is now used for hooks
        if (gamepad2.x)
        {
            //robot.output.moveClampIntoRobot();
        }

        if (gamepad2.y)
        {
            robot.driveTrain.stopDriveMotors();
            robot.output.moveClampOutOfRobot();
        }

        if (gamepad2.right_bumper)
        {
            robot.output.startOpeningClamp();
        }

        else if (gamepad2.left_bumper)
        {
            robot.output.startClosingClamp();
        }

        else
        {
            robot.output.stopClamp();
        }

    }

    public void doFoundation()
    {
        if(gamepad2.a||gamepad1.a)
        {
            robot.foundationHooks.lowerHooks();
        }
        else if(gamepad2.x || gamepad1.x)
        {
            robot.foundationHooks.raiseHooks();
        }
    }

//    }
}




