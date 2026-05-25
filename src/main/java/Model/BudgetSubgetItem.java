package Model;

import java.math.BigDecimal;

public class BudgetSubgetItem {
    private Product product;
    private BigDecimal quantity;
    private BigDecimal subtotal;

    public BudgetSubgetItem() {
    }

    public BudgetSubgetItem(Product product, BigDecimal quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = (product != null && product.getPrice() != null)
                ? product.getPrice().multiply(quantity)
                : BigDecimal.ZERO;
    }

    public BudgetSubgetItem(Product product, BigDecimal quantity, BigDecimal subtotal) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        recalcularSubtotal();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void recalcularSubtotal() {
        if (this.product != null && this.quantity != null) {
            this.subtotal = this.product.getPrice().multiply(this.quantity);
        }
    }

    @Override
    public String toString() {
        return product.getName() + " | Qtd: " + quantity + " | Subtotal: R$ " + subtotal;
    }
}