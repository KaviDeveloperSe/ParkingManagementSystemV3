package model;

public class customer_combo_model {

    private final int customer_id;
    private final String customer_name;

    public customer_combo_model(
            int customer_id,
            String customer_name) {

        this.customer_id = customer_id;
        this.customer_name = customer_name;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    @Override
    public String toString() {
        return customer_name;
    }
}