package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);
    }

    // Happy: createOrder
    @Test
    void testCreateOrder() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).save(order);
        orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
    }

    // Unhappy: createOrder with already existing order
    @Test
    void testCreateOrderAlreadyExists() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).save(order);
        doReturn(order).when(orderRepository).findById(order.getId());
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(order);
        });
    }

    // Happy: updateStatus
    @Test
    void testUpdateStatus() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(order).when(orderRepository).save(any(Order.class));

        Order result = orderService.updateStatus(order.getId(), "SUCCESS");
        assertEquals("SUCCESS", result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // Unhappy: updateStatus with invalid status
    @Test
    void testUpdateStatusInvalidStatus() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.updateStatus(order.getId(), "MEOW");
        });
    }

    // Unhappy: updateStatus with ID not found
    @Test
    void testUpdateStatusIdNotFound() {
        doReturn(null).when(orderRepository).findById("zzz");
        assertThrows(NoSuchElementException.class, () -> {
            orderService.updateStatus("zzz", "SUCCESS");
        });
    }

    // Happy: findById
    @Test
    void testFindByIdIfIdFound() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());
        Order result = orderService.findById(order.getId());
        assertEquals(order.getId(), result.getId());
    }

    // Unhappy: findById with invalid ID
    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(orderRepository).findById("zzz");
        assertNull(orderService.findById("zzz"));
    }

    // Happy: findAllByAuthor
    @Test
    void testFindAllByAuthor() {
        doReturn(orders).when(orderRepository).findAllByAuthor("Safira Sudrajat");
        List<Order> results = orderService.findAllByAuthor("Safira Sudrajat");
        assertEquals(2, results.size());
    }

    // Unhappy: findAllByAuthor with all-lowercase
    @Test
    void testFindAllByAuthorIfAllLowercase() {
        doReturn(new ArrayList<>()).when(orderRepository).findAllByAuthor("safira sudrajat");
        List<Order> results = orderService.findAllByAuthor("safira sudrajat");
        assertTrue(results.isEmpty());
    }
}