package App;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Subgroup {
    private String name;
    private BigDecimal subtotal = BigDecimal.ZERO;
    private List<String> items = new ArrayList<>();

    public Subgroup(String name) { this.name = name; }

    public void addItem(String detail, BigDecimal value) {
        items.add(detail);
        subtotal = subtotal.add(value);
    }

    public String getName() { return name; }
    public BigDecimal getSubtotal() { return subtotal; }
    public List<String> getItems() { return items; }

    @Override
    public String toString() {
        return name + " (R$ " + subtotal + ")";
    }
}

