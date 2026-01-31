package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
	private final TalonFX lifter = new TalonFX(1);
	private final TalonFX roller = new TalonFX(2);
	private final MotionMagicVoltage request = new MotionMagicVoltage(0.0);
	public static boolean isZeroed = false;

	public IntakeSubsystem() {
		this.configMotor();
		this.setZeroPosition();
	}

	public void setZeroPosition() {
		this.lifter.setPosition(0.0);
		isZeroed = true;
	}

	public void configMotor() {
		TalonFXConfiguration motorConfig = new TalonFXConfiguration();
		motorConfig.MotorOutput
				.withNeutralMode(NeutralModeValue.Coast)
				.withInverted(InvertedValue.Clockwise_Positive);

		Slot0Configs slot0Configs = new Slot0Configs();
		slot0Configs.kP = 180.0;
		slot0Configs.kD = 0.0;
		slot0Configs.kV = 0.0;
		slot0Configs.kS = 0.0;

		TalonFXConfiguration lifterConfig = new TalonFXConfiguration();
		lifterConfig.MotorOutput
				.withInverted(InvertedValue.CounterClockwise_Positive)
				.withNeutralMode(NeutralModeValue.Brake);
		lifterConfig.Feedback
				.withSensorToMechanismRatio(Constants.GEAR_RATIO);
		lifterConfig.MotionMagic
				.withMotionMagicJerk(2000.0)
				.withMotionMagicAcceleration(200.0)
				.withMotionMagicCruiseVelocity(1.0);
		lifterConfig.Slot0 = slot0Configs;

		this.roller.getConfigurator().apply(motorConfig);
		this.lifter.getConfigurator().apply(lifterConfig);
	}

	public void execute(double rollerVoltage, double goal) {
		this.roller.setVoltage(rollerVoltage);
		this.lifter.setControl(request.withPosition(goal));
	}

	public void stop() {
		this.roller.stopMotor();
		this.lifter.stopMotor();
	}

	@Override
	public void periodic() {
		if (!isZeroed)
			return;
		SmartDashboard.putNumber("RollerVoltage", this.roller.getMotorVoltage().getValueAsDouble());
		SmartDashboard.putNumber("LifterPosition", this.lifter.getPosition().getValueAsDouble());
	}
}
