package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    List<Order> orderData = new ArrayList<>();

    public Order save(Order order) {
        // Check if order already exists (update case)
        int index = findIndexById(order.getId());
        if (index != -1) {
            orderData.set(index, order);
        } else {
            orderData.add(order);
        }
        return order;
    }

    public Order findById(String id) {
        for (Order order : orderData) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public List<Order> findAllByAuthor(String author) {
        List<Order> result = new ArrayList<>();
        for (Order order : orderData) {
            if (order.getAuthor().equals(author)) {
                result.add(order);
            }
        }
        return result;
    }

    private int findIndexById(String id) {
        for (int i = 0; i < orderData.size(); i++) {
            if (orderData.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}