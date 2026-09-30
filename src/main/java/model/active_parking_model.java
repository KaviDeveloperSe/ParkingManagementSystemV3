package model;

import java.time.LocalDateTime;

public class active_parking_model {
    private int active_parking_session_id;
    private String active_parking_registration_number;
    private String active_parking_customer_name;
    private String active_parking_vehicle_type;
    private String active_parking_space_number;
    private LocalDateTime active_parking_entry_time;

    public active_parking_model(
            int active_parking_session_id,
            String active_parking_registration_number,
            String active_parking_customer_name,
            String active_parking_vehicle_type,
            String active_parking_space_number,
            LocalDateTime active_parking_entry_time) {

        this.active_parking_session_id = active_parking_session_id;
        this.active_parking_registration_number = active_parking_registration_number;
        this.active_parking_customer_name = active_parking_customer_name;
        this.active_parking_vehicle_type = active_parking_vehicle_type;
        this.active_parking_space_number = active_parking_space_number;
        this.active_parking_entry_time = active_parking_entry_time;
    }

    public int get_active_parking_session_id() {
        return active_parking_session_id;
    }

    public String get_active_parking_registration_number() {
        return active_parking_registration_number;
    }

    public String get_active_parking_customer_name() {
        return active_parking_customer_name;
    }

    public String get_active_parking_vehicle_type() {
        return active_parking_vehicle_type;
    }

    public String get_active_parking_space_number() {
        return active_parking_space_number;
    }

    public LocalDateTime get_active_parking_entry_time() {
        return active_parking_entry_time;
    }
}