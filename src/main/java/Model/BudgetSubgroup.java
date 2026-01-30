package Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BudgetSubgroup {
    private String name;
    private List<BudgetSubgetItem> items = new ArrayList<>();

    public BudgetSubgroup(String name) { this.name = name; }

    public void addItem(BudgetSubgetItem item) {
        this.items.add(item);
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(BudgetSubgetItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getName() { return name; }
    public List<BudgetSubgetItem> getItems() { return items; }

    @Override
    public String toString() {
        return "GRUPO: " + name + " | Total: R$ " + getSubtotal();
    }
}