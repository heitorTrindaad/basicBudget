package Model;

import java.math.BigDecimal;

public class Budget {

    private int id;
    private BigDecimal quantity;
    private String measurement;

    private Product product;
    private Client client;
    private BigDecimal total;
    private Sale sale;

    private java.util.List<String> itensRelatorio = new java.util.ArrayList<>();

    @Override
    public String toString() {
        return "Orçamento #" + id +
                " | Cliente: " + client.getName() +
                " | Total: R$ " + total;
    }

    public Budget() {
    }

    public Budget(BigDecimal quantity, String measurement) {
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public java.util.List<String> getItensRelatorio() { return itensRelatorio; }

    public void setItensRelatorio(java.util.List<String> itens) { this.itensRelatorio = itens; }
}
