package Model;

import java.math.BigDecimal;

public class Budget {
    private int id;
    private BigDecimal quantity;
    private String measurement;

    public Budget(BigDecimal quantity, String measurement) {
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
