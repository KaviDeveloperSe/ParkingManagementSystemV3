package model;

import java.sql.Timestamp;

public class customer_model {
    private int customer_model_customer_id;
    private String customer_model_name;
    private String customer_model_phone;
    private String customer_model_email;
    private Timestamp customer_model_createdAt;

    public customer_model(String customer_model_name, String customer_model_phone, String customer_model_email) {
        this.customer_model_name = customer_model_name;
        this.customer_model_phone = customer_model_phone;
        this.customer_model_email = customer_model_email;
    }

    public customer_model(int customer_model_customer_id, String customer_model_name, String customer_model_phone, String customer_model_email, Timestamp customer_model_createdAt) {
        this.customer_model_customer_id = customer_model_customer_id;
        this.customer_model_name = customer_model_name;
        this.customer_model_phone = customer_model_phone;
        this.customer_model_email = customer_model_email;
        this.customer_model_createdAt = customer_model_createdAt;
    }

    public int get_customer_model_customer_id() {
        return customer_model_customer_id;
    }

    public String get_customer_model_name() {
        return customer_model_name;
    }

    public String get_customer_model_phone() {
        return customer_model_phone;
    }

    public String get_customer_model_email() {
        return customer_model_email;
    }

    public Timestamp get_customer_model_createdAt() {
        return customer_model_createdAt;
    }

    public void set_customer_model_customer_id(int customer_model_customer_id) {
        this.customer_model_customer_id = customer_model_customer_id;
    }

    public void set_customer_model_name(String customer_model_name) {
        this.customer_model_name = customer_model_name;
    }

    public void set_customer_model_phone(String customer_model_phone) {
        this.customer_model_phone = customer_model_phone;
    }

    public void set_customer_model_email(String customer_model_email) {
        this.customer_model_email = customer_model_email;
    }

    public void set_customer_model_createdAt(Timestamp customer_model_createdAt) {
        this.customer_model_createdAt = customer_model_createdAt;
    }
}