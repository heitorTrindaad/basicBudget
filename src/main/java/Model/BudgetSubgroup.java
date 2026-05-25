package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class BudgetSubgroup {
    private String name;
    private List<BudgetSubgetItem> items = new ArrayList<>();
    private BigDecimal percentage = BigDecimal.ZERO;

    public BudgetSubgroup(String name) {
        this.name = name;
    }

    public void addItem(BudgetSubgetItem item) {
        this.items.add(item);
    }

    public BigDecimal getSubtotal() {
        BigDecimal somaItens = items.stream()
                .map(BudgetSubgetItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal fator = percentage.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal adicional = somaItens.multiply(fator);

        return somaItens.add(adicional).setScale(2, RoundingMode.HALF_UP);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BudgetSubgetItem> getItems() {
        return items;
    }

    public void setItems(List<BudgetSubgetItem> items) {
        this.items = items;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return name;
    }
}