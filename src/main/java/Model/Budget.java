package Model;

import java.math.BigDecimal;

public class Budget {
    private int id;
    private BigDecimal quantity;
    private String measurement;

    private Product product;
    private Sale sale;

    public Budget() {
    }

    public Budget(BigDecimal quantity, String measurement, Product product, Sale sale) {
        this.quantity = quantity;
        this.measurement = measurement;
        this.product = product;
        this.sale = sale;
    }

    public BigDecimal getSubTotal(){
        return product.getPrice().multiply(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public Sale getSale() {
        return sale;
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
