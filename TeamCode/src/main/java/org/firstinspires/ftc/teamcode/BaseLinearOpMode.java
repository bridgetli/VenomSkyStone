package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class BaseLinearOpMode extends LinearOpMode
{
    VenomRobot robot = new VenomRobot();
    double newPower;
    DcMotor[] motors = new DcMotor[]{robot.driveTrain.motorFL, robot.driveTrain.motorFR,
            robot.driveTrain.motorBR, robot.driveTrain.motorBL};

    public void initialize()
    {
        robot.init(hardwareMap, telemetry, true);
        telemetry.addData("Super initialized", "");
        telemetry.update();
    }

    // add time out later?
    public double PID(double angle)
    {
        double kP = 0.4/90;
        double minSpeed = 0.1;

            double angleError = robot.imu.getTrueDiff(angle);

            double PIDchange = kP * angleError;

            if (PIDchange > 0 && PIDchange < minSpeed)
                PIDchange = minSpeed;
            else if (PIDchange < 0 && PIDchange > -minSpeed)
                PIDchange = -minSpeed;

        return PIDchange;
    }

    public void moveForward(double angle, double power, double encoderTicks)
    {
        robot.driveTrain.resetEncoders();
        robot.driveTrain.runWithoutEncoders();

        boolean active = true;

        robot.log("Starting loop.");

        while (active && opModeIsActive()){
            active = false;
            StringBuilder message = new StringBuilder();

            for(DcMotor m : motors) {
                if (m.getCurrentPosition() < encoderTicks) {
                    message.append("Setting power to " + power + " since m.getCurrentPosition()=" + m.getCurrentPosition() + " encoderTicks=" + encoderTicks + "\n");
                    m.setPower(power);
                    sleep(50);

                    active = true;
                }
                else {
                    m.setPower(0);
                }
            }
            robot.log(message.toString());

            try {
                Thread.sleep(100);

            } catch (Exception e) {
            }
        }

        robot.log("Loop is done.");

        // Why reset here?
        robot.driveTrain.resetEncoders();
        robot.driveTrain.runWithoutEncoders();
    }

    // Old moveForward method:
    /** public void moveForward(double angle, double power, double encoderTicks)
    {
        telemetry.addData("Encoder value: ", robot.driveTrain.getEncoderTicks() + "");
        robot.driveTrain.resetEncoders();
        robot.driveTrain.run();
        telemetry.addData("Encoder value: ", robot.driveTrain.getEncoderTicks() + "");
        while (robot.driveTrain.getEncoderTicks() < encoderTicks && opModeIsActive())
        {
            // change: scale down only last 1000 ticks
            newPower = power * ((encoderTicks - robot.driveTrain.getEncoderTicks())/encoderTicks);
            double PIDchange = PID(angle);

            if (newPower < 0.1)
            {
                newPower = 0.1;
            }

            // TODO: figure out + and -
            // either set left or right to negative
            // if statement for if knocked left or right
            // curves left


            robot.setMotorBL(PIDchange + newPower);
            robot.setMotorBR(-PIDchange + newPower);
            robot.setMotorFL(PIDchange + newPower);
            robot.setMotorFR(-PIDchange + newPower);
        }
    }
     **/

    public void moveBackward(double angle, double power, double encoderTicks)
    {
        robot.driveTrain.resetEncoders();
        robot.driveTrain.runWithoutEncoders();

        boolean active = true;

        robot.log("Starting loop.");

        while (active && opModeIsActive()){
            active = false;
            StringBuilder message = new StringBuilder();

            for(DcMotor m : motors) {
                if (m.getCurrentPosition() < encoderTicks) {
                    message.append("Setting power to " + power + " since m.getCurrentPosition()=" + m.getCurrentPosition() + " encoderTicks=" + encoderTicks + "\n");
                    m.setPower(-power);
                    sleep(50);

                    active = true;
                }
                else {
                    m.setPower(0);
                }
            }
            robot.log(message.toString());

            try {
                Thread.sleep(100);

            } catch (Exception e) {
            }
        }

        robot.log("Loop is done.");

        // Why reset here?
        robot.driveTrain.resetEncoders();
        robot.driveTrain.runWithoutEncoders();
    }

    /**public void moveBackward(double angle, double power, double encoderTicks)
    {
        robot.driveTrain.resetEncoders();
        robot.driveTrain.runWithoutEncoders();
        while (robot.driveTrain.getEncoderTicks() < encoderTicks && opModeIsActive())
        {
            // try to change to scale down at the end
            newPower = power * ((encoderTicks - robot.driveTrain.getEncoderTicks())/encoderTicks);
            double PIDchange = PID(angle);

            if (newPower < 0.1)
            {
                newPower = 0.1;
            }

            // TODO: figure out + and -
            robot.setMotorBL(PIDchange - newPower);
            robot.setMotorBR(-PIDchange - newPower);
            robot.setMotorFL(PIDchange - newPower);
            robot.setMotorFR(-PIDchange - newPower);
        }
    }
     **/

    // pass in current angle as the parameter
    public void strafeRight(double angle, double power, double encoderTicks)
    {
        robot.driveTrain.resetEncoders();
        robot.driveTrain.runWithoutEncoders();
        while (robot.driveTrain.getEncoderTicks() < encoderTicks && opModeIsActive())
        {
            newPower = power * ((encoderTicks - robot.driveTrain.getEncoderTicks())/encoderTicks);
            double PIDchange = PID(angle);

            if (newPower < 0.1)
            {
                newPower = 0.1;
            }

            // TODO: figure out + and -
            // Test
            robot.setMotorBL(PIDchange - newPower);
            robot.setMotorBR(-PIDchange + newPower);
            robot.setMotorFL(PIDchange + newPower);
            robot.setMotorFR(-PIDchange - newPower);

        }
    }

    public void strafeLeft(double angle, double power, double encoderTicks)
    {
        robot.driveTrain.resetEncoders();
        robot.driveTrain.runWithoutEncoders();
        while (robot.driveTrain.getEncoderTicks() < encoderTicks && opModeIsActive())
        {
            newPower = power * ((encoderTicks - robot.driveTrain.getEncoderTicks())/encoderTicks);
            double PIDchange = PID(angle);

            if (newPower < 0.1)
            {
                newPower = 0.1;
            }

            // TODO: figure out + and -
            robot.setMotorBL(PIDchange + newPower);
            robot.setMotorBR(-PIDchange - newPower);
            robot.setMotorFL(PIDchange - newPower);
            robot.setMotorFR(-PIDchange + newPower);
        }
    }

    public void moveToLoadingZone()
    {

    }

    // do we need this
    public void rotate()
    {

    }
}
