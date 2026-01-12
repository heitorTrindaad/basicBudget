package Service;

import Model.Product;
import Repository.ProductRepositoryMemory;

import java.math.BigDecimal;

public class productService {
    private final ProductRepositoryMemory ProductRepositoryMemory;

    public productService(ProductRepositoryMemory ProductRepositoryMemory) {
        this.ProductRepositoryMemory = ProductRepositoryMemory;
    }

    public Product createProduct(String name, BigDecimal price){
        if (name==null || name.isBlank()){
            throw new IllegalArgumentException("Product name wasnt filled.");
        } //hi
        if (price == null || price.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Product price is invalid.");
        }

        Product product = new Product(name, price);
        product.setName(name.trim());
        product.setPrice(price);

        return ProductRepositoryMemory.save(product);
    }

    public Product updateProduct(int id, String name, BigDecimal price){
        Product product = ProductRepositoryMemory.findById(id);

        if (product == null) {
            throw new IllegalArgumentException("Product not found.");
        }

        if (name ==null || name.isBlank()){
            throw new IllegalArgumentException("Product name wasnt filled.");
        }

        if  (price == null || price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Product price is invalid.");
        }

        product.setName(name.trim());
        product.setPrice(price);

        return ProductRepositoryMemory.save(product);
    }

    public void deleteProduct(int id){
        ProductRepositoryMemory.delete(id);
    }

}
