package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        this.products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708560000L, "Safira Sudrajat");
    }

    // ===== VOUCHER CODE TESTS =====

    // Happy: valid voucher code -> SUCCESS
    @Test
    void testPaymentVoucherCodeValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("pay-001", "VOUCHER_CODE", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    // Unhappy: voucher code not 16 characters -> REJECTED
    @Test
    void testPaymentVoucherCodeNot16Chars() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234");

        Payment payment = new Payment("pay-002", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // Unhappy: voucher code doesn't start with ESHOP -> REJECTED
    @Test
    void testPaymentVoucherCodeNotStartWithEshop() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ABCD1234EFGH5678");

        Payment payment = new Payment("pay-003", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // Unhappy: voucher code doesn't contain 8 numerical characters -> REJECTED
    @Test
    void testPaymentVoucherCodeNotEnoughDigits() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");

        Payment payment = new Payment("pay-004", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // Unhappy: voucher code has more than 8 numerical characters -> still valid
    @Test
    void testPaymentVoucherCodeAllDigitsAfterEshop() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678901");

        Payment payment = new Payment("pay-005", "VOUCHER_CODE", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    // ===== BANK TRANSFER TESTS =====

    // Happy: valid bank transfer data -> SUCCESS
    @Test
    void testPaymentBankTransferValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-006", "BANK_TRANSFER", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    // Unhappy: bank name is empty -> REJECTED
    @Test
    void testPaymentBankTransferEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-007", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // Unhappy: reference code is null -> REJECTED
    @Test
    void testPaymentBankTransferNullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", null);

        Payment payment = new Payment("pay-008", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // Unhappy: bank name is null -> REJECTED
    @Test
    void testPaymentBankTransferNullBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-009", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // Unhappy: reference code is empty -> REJECTED
    @Test
    void testPaymentBankTransferEmptyReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("pay-010", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // Unhappy: both empty -> REJECTED
    @Test
    void testPaymentBankTransferBothEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("pay-011", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // ===== GENERAL TESTS =====

    // Test getters
    @Test
    void testPaymentGetters() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("pay-012", "VOUCHER_CODE", paymentData);
        assertEquals("pay-012", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    // Test setStatus
    @Test
    void testPaymentSetStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("pay-013", "VOUCHER_CODE", paymentData);
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    // Test setStatus with invalid status
    @Test
    void testPaymentSetStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("pay-014", "VOUCHER_CODE", paymentData);
        payment.setStatus("MEOW");
        assertEquals("SUCCESS", payment.getStatus());
    }
}