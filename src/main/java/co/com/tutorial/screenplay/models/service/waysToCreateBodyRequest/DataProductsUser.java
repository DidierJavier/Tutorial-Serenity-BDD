package co.com.tutorial.screenplay.models.service.waysToCreateBodyRequest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataProductsUser that = (DataProductsUser) o;
        return userId == that.userId && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, products);
    }

    @Override
    public String toString() {
        return "DataProductsUser{" +
                "userId=" + userId +
                ", products=" + products +
                '}';
    }
}
