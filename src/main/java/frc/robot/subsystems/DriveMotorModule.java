package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.lib.IDashBoardProvider;

public class DriveMotorModule implements IDashBoardProvider{
    private final SparkMax frontSpark;
    private final SparkMax backSpark;

    public DriveMotorModule(int frontID, int backID, Boolean frontReverse, Boolean backReverse){
        this.registerDashbroad();
        this.frontSpark = new SparkMax(frontID,MotorType.kBrushed);
        this.backSpark = new SparkMax(backID,MotorType.kBrushed);
        SparkMaxConfig frontConfig = new SparkMaxConfig();
        frontConfig
            .idleMode(IdleMode.kCoast)
            .inverted(frontReverse)
            .smartCurrentLimit(40);

        SparkMaxConfig backConfig = new SparkMaxConfig();
        backConfig
            .idleMode(IdleMode.kCoast)
            .inverted(backReverse)
            .smartCurrentLimit(40);
        this.frontSpark.configure(frontConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        this.backSpark.configure(backConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setDesiredState(double speed) {
        this.frontSpark.set(speed);
        this.backSpark.set(speed);
    }

    public void stop() {
        this.frontSpark.stopMotor();
        this.backSpark.stopMotor();
    }

    @Override
    public void putDashboard() {
        
    }
}
