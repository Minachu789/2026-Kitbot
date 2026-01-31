package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MotorSubsystem extends SubsystemBase {
	private final TalonFX shooter = new TalonFX(1); // Kraken X60
	private final TalonFX shooterFollower = new TalonFX(2); // Kraken X60
	private final Follower follower = new Follower(this.shooter.getDeviceID(), MotorAlignmentValue.Opposed);
	private final TalonFX intake = new TalonFX(3); // TalonFX
	private final TalonFX lifter = new TalonFX(4); // Kraken X44
	private final MotionMagicVoltage request = new MotionMagicVoltage(0.0);
	public static boolean isZeroed = false;

	public MotorSubsystem() {
		this.shooterFollower.setControl(follower);
		this.configMotor();
		this.setZeroPosition();
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

		this.shooter.getConfigurator().apply(motorConfig);
		this.shooterFollower.getConfigurator().apply(motorConfig);
		this.intake.getConfigurator().apply(motorConfig);
		this.lifter.getConfigurator().apply(lifterConfig);
	}

	public void setZeroPosition() {
		this.lifter.setPosition(0.0);
		isZeroed = true;
	}

	public void execute(double shooterVoltage, double intakeVoltage, double liftergoal) {
		this.shooter.setVoltage(shooterVoltage);
		this.intake.setVoltage(intakeVoltage);
		this.lifter.setControl(request.withPosition(liftergoal));
	}

	public void stop() {
		this.intake.stopMotor();
		this.shooter.stopMotor();
		this.lifter.stopMotor();
	}

	@Override
	public void periodic() {
		if (!isZeroed)
			return;
		SmartDashboard.putNumber("ShooterVoltage", this.shooter.getMotorVoltage().getValueAsDouble());
		SmartDashboard.putNumber("IntakeVoltage", this.intake.getMotorVoltage().getValueAsDouble());
		SmartDashboard.putNumber("LifterPosition", this.lifter.getPosition().getValueAsDouble());
	}
}