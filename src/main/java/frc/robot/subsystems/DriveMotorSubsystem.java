package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveMotorSubsystem extends SubsystemBase {
    private final DriveMotorModule leftModule;
    private final DriveMotorModule rightModule;

    public DriveMotorSubsystem() {
        this.leftModule = new DriveMotorModule(1, 2, true, true);
        this.rightModule = new DriveMotorModule(3, 4, false, false);
    }

    public void execute(double leftSpeed, double rightSpeed) {
        this.leftModule.setDesiredState(leftSpeed);
        this.rightModule.setDesiredState(rightSpeed);
    }

    public void stopModules() {
        this.leftModule.stop();
        this.rightModule.stop();
    }
}
