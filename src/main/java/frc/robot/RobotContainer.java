package frc.robot;

import frc.robot.commands.MotorCmd;
import frc.robot.subsystems.MotorSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
	private final MotorSubsystem subsystem = new MotorSubsystem();
	private final XboxController controller = new XboxController(0);
	private final MotorCmd motorCmd = new MotorCmd(subsystem, controller);

	public RobotContainer() {
		this.subsystem.setDefaultCommand(motorCmd);
		this.configureBindings();
	}

	private void configureBindings() {

	}

	public Command getAutonomousCommand() {
		return null;
	}
}
