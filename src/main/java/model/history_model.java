package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class history_model {

    private int history_session_id;

    private String history_registration_number;
    private String history_customer_name;
    private String history_vehicle_type;
    private String history_space_number;

    private LocalDateTime history_entry_time;
    private LocalDateTime history_exit_time;

    private BigDecimal history_hourly_rate;
    private BigDecimal history_parking_fee;


    public history_model(
            int history_session_id,
            String history_registration_number,
            String history_customer_name,
            String history_vehicle_type,
            String history_space_number,
            LocalDateTime history_entry_time,
            LocalDateTime history_exit_time,
            BigDecimal history_hourly_rate,
            BigDecimal history_parking_fee) {

        this.history_session_id =
                history_session_id;

        this.history_registration_number =
                history_registration_number;

        this.history_customer_name =
                history_customer_name;

        this.history_vehicle_type =
                history_vehicle_type;

        this.history_space_number =
                history_space_number;

        this.history_entry_time =
                history_entry_time;

        this.history_exit_time =
                history_exit_time;

        this.history_hourly_rate =
                history_hourly_rate;

        this.history_parking_fee =
                history_parking_fee;
    }


    public int get_history_session_id() {
        return history_session_id;
    }


    public String get_history_registration_number() {
        return history_registration_number;
    }


    public String get_history_customer_name() {
        return history_customer_name;
    }


    public String get_history_vehicle_type() {
        return history_vehicle_type;
    }


    public String get_history_space_number() {
        return history_space_number;
    }


    public LocalDateTime get_history_entry_time() {
        return history_entry_time;
    }


    public LocalDateTime get_history_exit_time() {
        return history_exit_time;
    }


    public BigDecimal get_history_hourly_rate() {
        return history_hourly_rate;
    }


    public BigDecimal get_history_parking_fee() {
        return history_parking_fee;
    }
}