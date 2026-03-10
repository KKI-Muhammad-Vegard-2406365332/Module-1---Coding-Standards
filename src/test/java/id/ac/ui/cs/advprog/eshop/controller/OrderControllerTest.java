package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private PaymentService paymentService;

    @Test
    void testCreateOrderPage() throws Exception {
        Mockito.when(productService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("CreateOrder"));
    }

    @Test
    void testCreateOrderPost() throws Exception {
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Test");
        product.setProductQuantity(1);
        List<Product> products = List.of(product);
        Mockito.when(productService.findAll()).thenReturn(products);

        mockMvc.perform(post("/order/create")
                        .param("author", "Safira")
                        .param("selectedProductIds", "prod-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history"));
    }

    @Test
    void testOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("OrderHistory"));
    }

    @Test
    void testOrderHistoryPost() throws Exception {
        Mockito.when(orderService.findAllByAuthor("Safira")).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/order/history")
                        .param("author", "Safira"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("OrderHistoryResult"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Test");
        product.setProductQuantity(1);
        List<Product> products = List.of(product);
        Order order = new Order("order-1", products, 1708560000L, "Safira");
        Mockito.when(orderService.findById("order-1")).thenReturn(order);

        mockMvc.perform(get("/order/pay/order-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("order"))
                .andExpect(view().name("PayOrder"));
    }

    @Test
    void testPayOrderPostVoucher() throws Exception {
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Test");
        product.setProductQuantity(1);
        List<Product> products = List.of(product);
        Order order = new Order("order-1", products, 1708560000L, "Safira");
        Mockito.when(orderService.findById("order-1")).thenReturn(order);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-1", "VOUCHER_CODE", paymentData);
        Mockito.when(paymentService.addPayment(any(Order.class), eq("VOUCHER_CODE"), any(Map.class)))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/order-1")
                        .param("method", "VOUCHER_CODE")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payment"))
                .andExpect(view().name("PaymentSuccess"));
    }

    @Test
    void testPayOrderPostBankTransfer() throws Exception {
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Test");
        product.setProductQuantity(1);
        List<Product> products = List.of(product);
        Order order = new Order("order-1", products, 1708560000L, "Safira");
        Mockito.when(orderService.findById("order-1")).thenReturn(order);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123");
        Payment payment = new Payment("pay-2", "BANK_TRANSFER", paymentData);
        Mockito.when(paymentService.addPayment(any(Order.class), eq("BANK_TRANSFER"), any(Map.class)))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/order-1")
                        .param("method", "BANK_TRANSFER")
                        .param("bankName", "BCA")
                        .param("referenceCode", "REF123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payment"))
                .andExpect(view().name("PaymentSuccess"));
    }
}