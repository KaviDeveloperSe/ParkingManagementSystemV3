package model;

public class parking_space_model {

    private int space_id;
    private String space_number;
    private String space_type;
    private String status;

    public parking_space_model() {
    }

    // Constructor for adding a new space
    public parking_space_model(
            String space_number,
            String space_type) {

        this.space_number = space_number;
        this.space_type = space_type;
        this.status = "AVAILABLE";
    }

    // Constructor for data loaded from database
    public parking_space_model(
            int space_id,
            String space_number,
            String space_type,
            String status) {

        this.space_id = space_id;
        this.space_number = space_number;
        this.space_type = space_type;
        this.status = status;
    }

    public int getSpace_id() {
        return space_id;
    }

    public void setSpace_id(int space_id) {
        this.space_id = space_id;
    }

    public String getSpace_number() {
        return space_number;
    }

    public void setSpace_number(String space_number) {
        this.space_number = space_number;
    }

    public String getSpace_type() {
        return space_type;
    }

    public void setSpace_type(String space_type) {
        this.space_type = space_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}