package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.IntakeCmd;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {
	private final IntakeSubsystem subsystem = new IntakeSubsystem();
	private final XboxController controller = new XboxController(0);
	private final IntakeCmd intakeCmd = new IntakeCmd(subsystem, controller);

	public RobotContainer() {
		this.subsystem.setDefaultCommand(intakeCmd);
	}

	public Command getAutonomousCommand() {
		return null;
	}
}
