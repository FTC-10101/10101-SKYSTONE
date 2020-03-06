package Worlds;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
//@Disabled
public class Tele_Worlds extends OpMode {

    // Inherits hardware class
    private SKYHardware SKY = new SKYHardware();

    // This is the same sleep method that is used in autonomous programs. We use it for delays in
    // the main loop to prevent the program from thinking a button was pressed multiple times
    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    // Variables used in controls below
    private boolean rServoWasPressed = false;
    private boolean lServoWasPressed = false;
    private boolean slowModeWasPressed = false;
    private float slowMode = 1f;

    public void init() {
        // Initializes hardware map for the phones
        SKY.init(hardwareMap);
    }
    public void loop() {

        // Telemetry statements for Driver Station

        // Shows how long we have been running teleop for practice and in case the time isn't
        // Visible during a match
        //telemetry.addData("Runtime: ",(int) getRuntime());



        // Primary driver's controls

        // Driving
        double drive;   // Power for forward and back motion
        double strafe;  // Power for left and right motion
        double rotate;  // Power for rotating the robot
        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        double frontLeftPower = (drive) + strafe + (rotate);
        double backLeftPower = (drive) - strafe - (rotate);
        double frontRightPower = (drive) - strafe + (rotate);
        double backRightPower = (drive) + strafe - (rotate);
        //SKY.leftF.setPower((frontLeftPower + frontLeftPower)*slowMode);
        //SKY.leftB.setPower((backLeftPower + backLeftPower)*slowMode);
        //SKY.rightF.setPower((backLeftPower + backLeftPower)*slowMode);
        //SKY.rightB.setPower((backRightPower + backRightPower)*slowMode);
        SKY.leftF.setPower(.86*frontLeftPower * slowMode);
        SKY.leftB.setPower(.86*backLeftPower * slowMode);
        SKY.rightF.setPower(.86*frontRightPower * slowMode);
        SKY.rightB.setPower(.86*backRightPower * slowMode);
        telemetry.addData("Left Front Power", (float) frontLeftPower);
        telemetry.addData("Left Rear Power", (float) backLeftPower);
        telemetry.addData("Right Front Power", (float) frontRightPower);
        telemetry.addData("Right Rear Power", (float) backRightPower);
        telemetry.addData("slowMode: ", slowMode);

        SKY.parkServo.setPower(gamepad1.right_trigger);
        SKY.parkServo.setPower(-gamepad1.left_trigger);

        // Controls for our servo on the back of our robot to grab the foundation
        if (gamepad1.a) {
            SKY.grabHolder.setPosition(0);
        }
        if (gamepad1.b) {
            SKY.grabHolder.setPosition(1);
        }
        if (gamepad1.y) {
            SKY.grabHolder.setPosition(.5);
        }
        // Control that helps orient stones that are not perfectly aligned using our grabbers
        if (gamepad1.x) {
            SKY.grabRight.setPosition(1);
            SKY.grabLeft.setPosition(.5);
        }
        // Controls for our front servos to grab the foundation
        if (gamepad1.right_bumper){
            if (!rServoWasPressed){
                SKY.grabRightF.setPosition(0);
                rServoWasPressed = true;
                sleep(250);
            }
            else{
                SKY.grabRightF.setPosition(1);
                rServoWasPressed = false;
                sleep(250);
            }
        }
        if (gamepad1.left_bumper) {
            if (!lServoWasPressed){
                SKY.grabLeftF.setPosition(1);
                lServoWasPressed = true;
                sleep(250);
            }
            else{
                SKY.grabLeftF.setPosition(0);
                lServoWasPressed = false;
                sleep(250);
            }
        }
        if(gamepad1.left_stick_button){
            if(!slowModeWasPressed){
                slowMode = .5f;
                slowModeWasPressed = true;
                sleep(250);
            }
            else{
                slowMode = 1f;
                slowModeWasPressed = false;
                sleep(250);
            }
        }
        // Moves our CR servo that shoots out our tape measure, which is one of our parking options
        /*if(gamepad1.dpad_right){
            SKY.parkServo.setPower(-.5);
        } else if (gamepad1.dpad_left){
            SKY.parkServo.setPower(.5);
        } else  {
            SKY.parkServo.setPower(0);
        } */
        if(gamepad1.dpad_up) {
            SKY.leftF.setPower(.8);
            SKY.leftB.setPower(.8);
            SKY.rightF.setPower(.8);
            SKY.rightB.setPower(.8);
        }
        if(gamepad1.dpad_down) {
            SKY.leftF.setPower(-.8);
            SKY.leftB.setPower(-.8);
            SKY.rightF.setPower(-.8);
            SKY.rightB.setPower(-.8);
        }
        if(gamepad1.dpad_right) {
            SKY.leftF.setPower(-.8);
            SKY.leftB.setPower(.8);
            SKY.rightF.setPower(.8);
            SKY.rightB.setPower(-.8);
        }
        if(gamepad1.dpad_left) {
            SKY.leftF.setPower(.8);
            SKY.leftB.setPower(-.8);
            SKY.rightF.setPower(-.8);
            SKY.rightB.setPower(.8);
        }
        // Accessory driver's controls

        // Moves our main building arm
        SKY.armV.setPower(gamepad2.left_stick_y);
        // Moves our back arm that we use in autonomous in case there was an issue
        SKY.armB1.setPower(gamepad2.right_stick_x);
        SKY.armB2.setPower(gamepad2.right_stick_y);

        SKY.parkServo.setPower(gamepad2.right_trigger);
        SKY.parkServo.setPower(-gamepad2.left_trigger);

        // Controls that move our grabbers
        if (gamepad2.a) {
            SKY.grabRight.setPosition(0);
            SKY.grabLeft.setPosition(1);
        }
        if (gamepad2.b) {
            SKY.grabRight.setPosition(1);
            SKY.grabLeft.setPosition(0);
        }
        if (gamepad2.y) {
            SKY.grabRight.setPosition(.55);
            SKY.grabLeft.setPosition(.45);
        }
        if (gamepad2.dpad_down) {
            SKY.armV.setPower(-.65);
        }
        if (gamepad2.dpad_up) {
            SKY.armV.setPower(.65);
        }
        // Allows us to move our linear actuator so we can adjust the horizontal position of stones
        if (gamepad2.dpad_left) {
            SKY.armH.setPower(-1);
        }
        else if (gamepad2.dpad_right) {
            SKY.armH.setPower(1);
        }
        else {
            SKY.armH.setPower(0);
        }
    }
    // Makes sure the program stops completely
    public void stop () {
    }
}