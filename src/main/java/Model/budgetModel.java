package Model;

public class budgetModel {
    private int id;
    private float quantity;
    private String measurement;

    public budgetModel(float quantity, String measurement) {
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public int getId() { return id; }

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
