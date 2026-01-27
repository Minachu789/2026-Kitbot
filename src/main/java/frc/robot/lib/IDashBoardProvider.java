package frc.robot.lib;

public interface IDashBoardProvider {
    void putDashboard();

    default void registerDashbroad() {
        DashboardHelper.register(this);
    }
}