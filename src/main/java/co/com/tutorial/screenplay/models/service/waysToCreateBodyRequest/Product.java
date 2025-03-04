package co.com.tutorial.screenplay.models.service.waysToCreateBodyRequest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {

    private int id;
    private int quantity;

    // Constructores, getters y setters

    public Product() {}

    public Product(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
