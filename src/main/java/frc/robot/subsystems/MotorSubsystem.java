package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorSubsystem extends SubsystemBase {
    private final SparkMax intake;
    private final SparkMax shooter;

    public MotorSubsystem() {
        this.intake = new SparkMax(5, MotorType.kBrushed);
        this.shooter = new SparkMax(6, MotorType.kBrushed);

        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(IdleMode.kCoast);
        config.smartCurrentLimit(60);
        config.inverted(true);

        this.intake.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        this.shooter.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void execute(double intakeVoltage, double shooterVoltage) {
        this.intake.setVoltage(intakeVoltage);
        this.shooter.setVoltage(shooterVoltage);
    }

    public void stop() {
        this.intake.stopMotor();
        this.shooter.stopMotor();
    }
}