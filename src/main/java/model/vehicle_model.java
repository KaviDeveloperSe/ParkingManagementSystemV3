package model;

import java.sql.Timestamp;

public class vehicle_model {

    private int vehicle_id;
    private String registration_number;
    private String vehicle_type;
    private String brand;
    private String model;
    private int customer_id;
    private String customer_name;
    private Timestamp created_at;

    public vehicle_model(
            String registration_number,
            String vehicle_type,
            String brand,
            String model,
            int customer_id) {

        this.registration_number = registration_number;
        this.vehicle_type = vehicle_type;
        this.brand = brand;
        this.model = model;
        this.customer_id = customer_id;
    }

    public vehicle_model(
            int vehicle_id,
            String registration_number,
            String vehicle_type,
            String brand,
            String model,
            int customer_id,
            String customer_name,
            Timestamp created_at) {

        this.vehicle_id = vehicle_id;
        this.registration_number = registration_number;
        this.vehicle_type = vehicle_type;
        this.brand = brand;
        this.model = model;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.created_at = created_at;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}