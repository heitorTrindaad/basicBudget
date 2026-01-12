package Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Sale {
    private int id;
    private LocalDate date;
    private BigDecimal totalAmount;

    public Sale() {
    }

    public Sale(LocalDate date, BigDecimal totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
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
