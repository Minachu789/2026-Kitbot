package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCmd extends Command {
	private final IntakeSubsystem subsystem;
	private final XboxController controller;

	public IntakeCmd(IntakeSubsystem subsystem, XboxController controller) {
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
		double intakeVolts = controller.getBButton() ? 8.0 : 0.0;
		if (controller.getRightBumperPressed())
			goal += 0.1;
		else if (controller.getLeftBumperPressed())
			goal -= 0.1;
		this.subsystem.execute(intakeVolts, goal);
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
