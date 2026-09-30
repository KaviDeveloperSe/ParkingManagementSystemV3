package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class parking_session_model {

    private int session_id;
    private int vehicle_id;
    private int space_id;

    private Timestamp entry_time;
    private Timestamp exit_time;

    private BigDecimal hourly_rate;
    private BigDecimal parking_fee;

    private String status;

    public parking_session_model() {
    }

    // Used when parking a vehicle
    public parking_session_model(
            int vehicle_id,
            int space_id,
            Timestamp entry_time,
            BigDecimal hourly_rate) {

        this.vehicle_id = vehicle_id;
        this.space_id = space_id;
        this.entry_time = entry_time;
        this.hourly_rate = hourly_rate;
        this.status = "ACTIVE";
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getSpace_id() {
        return space_id;
    }

    public void setSpace_id(int space_id) {
        this.space_id = space_id;
    }

    public Timestamp getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(Timestamp entry_time) {
        this.entry_time = entry_time;
    }

    public Timestamp getExit_time() {
        return exit_time;
    }

    public void setExit_time(Timestamp exit_time) {
        this.exit_time = exit_time;
    }

    public BigDecimal getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(BigDecimal hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public BigDecimal getParking_fee() {
        return parking_fee;
    }

    public void setParking_fee(BigDecimal parking_fee) {
        this.parking_fee = parking_fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}