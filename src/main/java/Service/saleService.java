package Service;

import Model.Sale;
import Repository.SaleRepositoryMemory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class saleService {
    private final SaleRepositoryMemory SaleRepositoryMemory;

    public saleService(SaleRepositoryMemory SaleRepositoryMemory) {
        this.SaleRepositoryMemory = SaleRepositoryMemory;
    }


    public Sale createSale(LocalDate date, BigDecimal totalAmount) {
        if (date==null){
            throw new IllegalArgumentException("Date is Invalid.");
        } //hi
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Total Amount is invalid.");
        }

        Sale sale = new Sale(date, totalAmount);
        sale.setDate(date);
        sale.setTotalAmount(totalAmount);

        return SaleRepositoryMemory.save(sale);
    }

    public Sale updateSale(int id, LocalDate date, BigDecimal totalAmount){
        Sale sale = SaleRepositoryMemory.findById(id);

        if (sale == null) {
            throw new IllegalArgumentException("Sale not found.");
        }

        if (date == null){
            throw new IllegalArgumentException("Date is Invalid.");
        }

        if  (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Sale price is invalid.");
        }

        sale.setDate(date);
        sale.setTotalAmount(totalAmount);

        return SaleRepositoryMemory.save(sale);
    }

    public void deleteSale(int id){
        SaleRepositoryMemory.remove(id);
    }
}
