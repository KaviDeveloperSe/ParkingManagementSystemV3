package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class vehicle_exit_model {

    private int session_id;
    private int vehicle_id;
    private int space_id;

    private String registration_number;
    private String vehicle_type;
    private String customer_name;
    private String space_number;

    private Timestamp entry_time;

    private BigDecimal hourly_rate;

    public vehicle_exit_model() {
    }

    public vehicle_exit_model(
            int session_id,
            int vehicle_id,
            int space_id,
            String registration_number,
            String vehicle_type,
            String customer_name,
            String space_number,
            Timestamp entry_time,
            BigDecimal hourly_rate) {

        this.session_id = session_id;
        this.vehicle_id = vehicle_id;
        this.space_id = space_id;
        this.registration_number = registration_number;
        this.vehicle_type = vehicle_type;
        this.customer_name = customer_name;
        this.space_number = space_number;
        this.entry_time = entry_time;
        this.hourly_rate = hourly_rate;
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

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(
            String registration_number) {

        this.registration_number =
                registration_number;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(
            String vehicle_type) {

        this.vehicle_type = vehicle_type;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(
            String customer_name) {

        this.customer_name = customer_name;
    }

    public String getSpace_number() {
        return space_number;
    }

    public void setSpace_number(
            String space_number) {

        this.space_number = space_number;
    }

    public Timestamp getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(
            Timestamp entry_time) {

        this.entry_time = entry_time;
    }

    public BigDecimal getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(
            BigDecimal hourly_rate) {

        this.hourly_rate = hourly_rate;
    }
}