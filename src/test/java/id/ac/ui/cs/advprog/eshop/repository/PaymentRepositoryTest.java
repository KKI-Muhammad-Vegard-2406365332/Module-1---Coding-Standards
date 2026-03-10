package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Payment payment1;
    Payment payment2;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        payment1 = new Payment("pay-001", "VOUCHER_CODE", paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "BCA");
        paymentData2.put("referenceCode", "REF123456");
        payment2 = new Payment("pay-002", "BANK_TRANSFER", paymentData2);
    }

    @Test
    void testSaveCreate() {
        Payment result = paymentRepository.save(payment1);
        assertEquals(payment1.getId(), result.getId());
        Payment findResult = paymentRepository.findById(payment1.getId());
        assertEquals(payment1.getId(), findResult.getId());
    }

    @Test
    void testSaveUpdate() {
        paymentRepository.save(payment1);
        payment1.setStatus("REJECTED");
        Payment result = paymentRepository.save(payment1);
        Payment findResult = paymentRepository.findById(payment1.getId());
        assertEquals("REJECTED", findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        Payment findResult = paymentRepository.findById("pay-002");
        assertEquals("pay-002", findResult.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        paymentRepository.save(payment1);
        Payment findResult = paymentRepository.findById("zzz");
        assertNull(findResult);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        List<Payment> allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size());
    }

    @Test
    void testFindAllEmpty() {
        List<Payment> allPayments = paymentRepository.findAll();
        assertTrue(allPayments.isEmpty());
    }
}