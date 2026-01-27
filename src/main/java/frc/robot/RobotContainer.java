package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DriveCmd;
import frc.robot.subsystems.DriveMotorSubsystem;
import frc.robot.subsystems.MotorSubsystem;

public class RobotContainer {
  private final XboxController controller = new XboxController(0);
  private final DriveMotorSubsystem driveMotorSubsystem = new DriveMotorSubsystem();
  private final MotorSubsystem motorSubsystem = new MotorSubsystem();
  private final DriveCmd driveCmd = new DriveCmd(driveMotorSubsystem, motorSubsystem, controller);

  public RobotContainer() {
    this.driveMotorSubsystem.setDefaultCommand(this.driveCmd);
  }

  public Command getAutonomousCommand() {
    return null;
  }
}