package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.DriveMotorSubsystem;
import frc.robot.subsystems.MotorSubsystem;

public class DriveCmd extends Command {
    private final DriveMotorSubsystem driveSubsystem;
    private final MotorSubsystem motorSubsystem;
    private final XboxController controller;
    private final Timer timer;

    public DriveCmd(DriveMotorSubsystem driveSubsystem, MotorSubsystem motorSubsystem, XboxController controller) {
        this.driveSubsystem = driveSubsystem;
        this.motorSubsystem = motorSubsystem;
        this.controller = controller;
        this.timer = new Timer();
        this.addRequirements(this.driveSubsystem);
    }

    @Override
    public void execute() {
        double driveSpeed = -MathUtil.applyDeadband(this.controller.getLeftY(), 0.05) * 0.4;
        double turnSpeed = MathUtil.applyDeadband(this.controller.getRightX(), 0.05) * 0.2;
        double leftSpeed = driveSpeed + turnSpeed;
        double rightSpeed = driveSpeed - turnSpeed;
        this.driveSubsystem.execute(leftSpeed, rightSpeed);
        SmartDashboard.putNumber("Left Speed", leftSpeed);
        SmartDashboard.putNumber("Right Speed", rightSpeed);
        double intakeVoltage = 0.0;
        double shooterVoltage = 0.0;
        if (this.controller.getAButton()) {
            intakeVoltage = Drive.MAX_SHOOT_VOLTAGE * 0.7;
            shooterVoltage = Drive.MAX_SHOOT_VOLTAGE * 0.7;
        } else if (this.controller.getBButton()) {
            timer.start();
            intakeVoltage = Drive.MAX_SHOOT_VOLTAGE;
            if (timer.get() >= 0.5) {
                shooterVoltage = -Drive.MAX_SHOOT_VOLTAGE;
            }
            // } else if (!this.controller.getBButton()) {
            // intakeVoltage = 0.0;
            // shooterVoltage = 0.0;
        } else {
            timer.stop();
            timer.reset();
        }
        this.motorSubsystem.execute(intakeVoltage, shooterVoltage);
        SmartDashboard.putNumber("Intake Voltage", intakeVoltage);
        SmartDashboard.putNumber("Shooter Voltage", shooterVoltage);

    }

    @Override
    public void end(boolean interrupted) {
        this.driveSubsystem.stopModules();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}