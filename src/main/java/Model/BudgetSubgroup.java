package Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BudgetSubgroup {
    private String name;
    private List<String> items = new ArrayList<>();
    private BigDecimal subtotal = BigDecimal.ZERO;

    public BudgetSubgroup(String name) {
        this.name = name;
    }

    public void addItem(String detail, BigDecimal value) {
        items.add(detail);
        this.subtotal = this.subtotal.add(value);
    }

    // Getters
    public String getName() { return name; }
    public List<String> getItems() { return items; }
    public BigDecimal getSubtotal() { return subtotal; }

    @Override
    public String toString() {
        return "GRUPO: " + name + " | Subtotal: R$ " + subtotal;
    }
}