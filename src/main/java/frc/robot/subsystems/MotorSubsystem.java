package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MotorSubsystem extends SubsystemBase {
	private final TalonFX flywheel = new TalonFX(1); // Kraken X60
	private final TalonFX flywheelFollower = new TalonFX(2); // Kraken X60
	private final Follower follower = new Follower(this.flywheel.getDeviceID(), MotorAlignmentValue.Opposed);
	private final TalonFX feeder = new TalonFX(3); // TalonFX
	private final TalonFX hood = new TalonFX(4); // Kraken X44
	private final MotionMagicVoltage request = new MotionMagicVoltage(0.0);
	public static boolean isZeroed = false;

	public MotorSubsystem() {
		this.flywheelFollower.setControl(follower);
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

		this.flywheel.getConfigurator().apply(motorConfig);
		this.flywheelFollower.getConfigurator().apply(motorConfig);
		this.feeder.getConfigurator().apply(motorConfig);
		this.hood.getConfigurator().apply(lifterConfig);
	}

	public void setZeroPosition() {
		this.hood.setPosition(0.0);
		isZeroed = true;
	}

	public void execute(double shooterVoltage, double intakeVoltage, double liftergoal) {
		this.flywheel.setVoltage(shooterVoltage);
		this.feeder.setVoltage(intakeVoltage);
		this.hood.setControl(request.withPosition(Units.degreesToRotations(liftergoal)));
	}

	public void stop() {
		this.feeder.stopMotor();
		this.flywheel.stopMotor();
		this.hood.stopMotor();
	}

	@Override
	public void periodic() {
		if (!isZeroed)
			return;
		SmartDashboard.putNumber("FlywheelVoltage", this.flywheel.getMotorVoltage().getValueAsDouble());
		SmartDashboard.putNumber("FeederVoltage", this.feeder.getMotorVoltage().getValueAsDouble());
		SmartDashboard.putNumber("HoodPosition", this.hood.getPosition().getValueAsDouble());
	}
}