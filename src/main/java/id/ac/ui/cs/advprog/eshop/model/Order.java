package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.products = products;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT";
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this(id, products, orderTime, author);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (status.equals("WAITING_PAYMENT") || status.equals("SUCCESS")
                || status.equals("FAILED") || status.equals("CANCELLED")) {
            this.status = status;
        }
    }
}