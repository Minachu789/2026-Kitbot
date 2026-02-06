package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.MotorSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class MotorCmd extends Command {
	private final MotorSubsystem subsystem;
	private final XboxController controller;

	public MotorCmd(MotorSubsystem subsystem, XboxController controller) {
		this.subsystem = subsystem;
		this.controller = controller;
		this.addRequirements(this.subsystem);
	}

	@Override
	public void initialize() {
	}

	private double goal = 0.0;

	@Override
	public void execute() {
		double intakeVolts = controller.getYButton() ? Constants.MAX_INTAKE_VOLTAGE : 0.0;
		double shooterVolts = controller.getBButton() ? Constants.MAX_SHOOTER_VOLTAGE : 0.0;
		if (controller.getRightBumperButton())
			goal += 0.1;
		else if (controller.getLeftBumperButton())
			goal -= 0.1;
		this.subsystem.execute(shooterVolts, intakeVolts, goal);
	}

	@Override
	public void end(boolean interrupted) {
		this.subsystem.stop();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
