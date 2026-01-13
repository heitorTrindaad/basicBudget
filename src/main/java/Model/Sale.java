package Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private int id;
    private LocalDate date;
    private BigDecimal totalAmount;

    private Client client;
    private List<Budget> items = new ArrayList<>();

    public Sale() {
    }

    public Sale(LocalDate date, BigDecimal totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public Sale(Client client) {
        this.date = LocalDate.now();
        this.client = client;
    }


    public void addItem(Budget b){
        items.add(b);
        calcularTotal();
    }

    private void calcularTotal(){
        totalAmount = BigDecimal.ZERO;

        for(Budget b : items){
            totalAmount = totalAmount.add(b.getSubTotal());
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Budget> getItems() {
        return items;
    }

    public void setItems(List<Budget> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
