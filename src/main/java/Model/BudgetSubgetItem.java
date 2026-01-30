package Model;

import java.math.BigDecimal;

public class BudgetSubgetItem {
    private Product product;
    private BigDecimal quantity;
    private BigDecimal subtotal;

    public BudgetSubgetItem(Product product, BigDecimal quantity, BigDecimal subtotal) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    // Getters e Setters
    public Product getProduct() { return product; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return product.getName() + " | Qtd: " + quantity + " | Subtotal: R$ " + subtotal;
    }
}