package Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Budget {
    private int id;
    private Client client;
    private BigDecimal total = BigDecimal.ZERO;
    private List<BudgetSubgroup> subgroups = new ArrayList<>();

    public Budget() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<BudgetSubgroup> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(List<BudgetSubgroup> subgroups) {
        this.subgroups = subgroups;
    }

    @Override
    public String toString() {
        return "Orçamento #" + id + " | Cliente: " + (client != null ? client.getName() : "N/A") + " | Total: R$ "
                + total;
    }
}