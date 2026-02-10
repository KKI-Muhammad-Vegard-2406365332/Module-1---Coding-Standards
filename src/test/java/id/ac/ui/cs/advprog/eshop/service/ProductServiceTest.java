package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productService.create(product);
    }

    // edit - positive case
    @Test
    void testEditExistingProduct() {
        Product existing = productService.findAll().get(0);

        existing.setProductName("Updated Product");
        existing.setProductQuantity(20);

        productService.update(existing);

        Product updated = productService.findById(existing.getProductId());
        assertNotNull(updated);
        assertEquals("Updated Product", updated.getProductName());
        assertEquals(20, updated.getProductQuantity());
    }

    // edit - negative case
    @Test
    void testEditNonExistingProduct() {
        Product existing = productService.findAll().get(0);

        Product fake = new Product();
        fake.setProductId("non-existent-id");
        fake.setProductName("Hacked");
        fake.setProductQuantity(999);

        productService.update(fake);

        Product unchanged = productService.findById(existing.getProductId());

        assertNotNull(unchanged);
        assertEquals(existing.getProductName(), unchanged.getProductName());
        assertEquals(existing.getProductQuantity(), unchanged.getProductQuantity());
    }

    // delete - positive case
    @Test
    void testDeleteExistingProduct() {
        Product existing = productService.findAll().get(0);

        productService.deleteById(existing.getProductId());

        assertNull(productService.findById(existing.getProductId()));
    }

    // delete - negative case
    @Test
    void testDeleteNonExistingProduct() {
        Product existing = productService.findAll().get(0);

        productService.deleteById("non-existent-id");

        Product stillExists = productService.findById(existing.getProductId());

        assertNotNull(stillExists);
    }

}
