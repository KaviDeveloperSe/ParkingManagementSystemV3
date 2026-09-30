package model;

public class dashboard_stats_model {
    private int dashboard_stats_model_total_customers;
    private int dashboard_stats_model_total_vehicles;
    private int dashboard_stats_model_total_spaces;
    private int dashboard_stats_model_available_spaces;
    private int dashboard_stats_model_occupied_spaces;
    private int dashboard_stats_model_active_parking;
    private int dashboard_stats_model_completed_today;
    private double dashboard_stats_model_today_revenue;

    public dashboard_stats_model(int dashboard_stats_model_total_customers, int dashboard_stats_model_total_vehicles, int dashboard_stats_model_total_spaces, int dashboard_stats_model_available_spaces, int dashboard_stats_model_occupied_spaces, int dashboard_stats_model_active_parking, int dashboard_stats_model_completed_today, double dashboard_stats_model_today_revenue) {
        this.dashboard_stats_model_total_customers = dashboard_stats_model_total_customers;
        this.dashboard_stats_model_total_vehicles = dashboard_stats_model_total_vehicles;
        this.dashboard_stats_model_total_spaces = dashboard_stats_model_total_spaces;
        this.dashboard_stats_model_available_spaces = dashboard_stats_model_available_spaces;
        this.dashboard_stats_model_occupied_spaces = dashboard_stats_model_occupied_spaces;
        this.dashboard_stats_model_active_parking = dashboard_stats_model_active_parking;
        this.dashboard_stats_model_completed_today = dashboard_stats_model_completed_today;
        this.dashboard_stats_model_today_revenue = dashboard_stats_model_today_revenue;
    }

    public int get_total_customers() {
        return dashboard_stats_model_total_customers;
    }

    public int get_total_vehicles() {
        return dashboard_stats_model_total_vehicles;
    }

    public int get_total_spaces() {
        return dashboard_stats_model_total_spaces;
    }

    public int get_available_spaces() {
        return dashboard_stats_model_available_spaces;
    }

    public int get_occupied_spaces() {
        return dashboard_stats_model_occupied_spaces;
    }

    public int get_active_parking() {
        return dashboard_stats_model_active_parking;
    }

    public int get_completed_today() {
        return dashboard_stats_model_completed_today;
    }

    public double get_today_revenue() {
        return dashboard_stats_model_today_revenue;
    }

    public double get_occupancy_percentage() {
        if (dashboard_stats_model_total_spaces == 0) return 0.0;
        return dashboard_stats_model_occupied_spaces * 100.0 / dashboard_stats_model_total_spaces;
    }
}