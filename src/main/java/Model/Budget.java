package Model;

public class Budget {
    private int id;
    private float quantity;
    private String measurement;

    public Budget(float quantity, String measurement) {
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
