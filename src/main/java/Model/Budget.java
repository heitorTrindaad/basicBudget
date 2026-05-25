package Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Budget {
    private int id;
    private Client client;
    private BigDecimal total = BigDecimal.ZERO;
    private LocalDate date;
    private List<BudgetSubgroup> subgroups = new ArrayList<>();

    public Budget() {
        this.date = LocalDate.now();
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Orçamento #" + id + " | Cliente: " + (client != null ? client.getName() : "N/A") + " | Total: R$ "
                + total;
    }
}