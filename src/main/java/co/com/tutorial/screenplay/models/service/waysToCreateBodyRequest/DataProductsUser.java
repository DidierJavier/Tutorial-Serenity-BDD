package co.com.tutorial.screenplay.models.service.waysToCreateBodyRequest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DataProductsUser {

    private int userId;
    private List<Product> products;

    // Constructores, getters y setters

    public DataProductsUser() {}

    public DataProductsUser(int userId, List<Product> products) {
        this.userId = userId;
        this.products = products;
    }

    @Override
    public String toString() {
        return "DataProductsUser{" +
                "userId=" + userId +
                ", products=" + products +
                '}';
    }
}
