package co.com.tutorial.screenplay.models.service.waysToCreateBodyRequest;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class Product {

    private int id;
    private int quantity;

    // Constructores, getters y setters

    private Product() {} //Constructor needed to convert Json to POJO

    public Product(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && quantity == product.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
