package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindById_ProductExists() {
        Product product = new Product();
        product.setProductName("Test Find");
        product.setProductQuantity(10);
        Product savedProduct = productRepository.create(product);

        Product foundProduct = productRepository.findById(savedProduct.getProductId());

        assertNotNull(foundProduct);
        assertEquals(savedProduct.getProductId(), foundProduct.getProductId());
        assertEquals(savedProduct.getProductName(), foundProduct.getProductName());
        assertEquals(savedProduct.getProductQuantity(), foundProduct.getProductQuantity());
    }

    @Test
    void testFindById_ProductDoesNotExist() {
        Product product = new Product();
        product.setProductName("Real Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product foundProduct = productRepository.findById("fake-id");

        assertNull(foundProduct);
    }

    @Test
    void testUpdate_ProductExists() {
        Product product = new Product();
        product.setProductName("Old Name");
        product.setProductQuantity(10);
        Product savedProduct = productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(savedProduct.getProductId()); // Match the generated ID
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(20);

        productRepository.update(updatedProduct);

        Product foundProduct = productRepository.findById(savedProduct.getProductId());
        assertNotNull(foundProduct);
        assertEquals("New Name", foundProduct.getProductName());
        assertEquals(20, foundProduct.getProductQuantity());
    }

    @Test
    void testUpdate_ProductDoesNotExist() {
        Product fakeProduct = new Product();
        fakeProduct.setProductId("fake-id");
        fakeProduct.setProductName("Fake Name");
        fakeProduct.setProductQuantity(99);

        // This should fall through the loop without throwing an error
        productRepository.update(fakeProduct);

        Product foundProduct = productRepository.findById("fake-id");
        assertNull(foundProduct);
    }

    @Test
    void testDeleteById_ProductExists() {
        Product product = new Product();
        product.setProductName("To Be Deleted");
        product.setProductQuantity(10);
        Product savedProduct = productRepository.create(product);

        // Verify it exists first
        assertNotNull(productRepository.findById(savedProduct.getProductId()));

        // Delete it
        productRepository.deleteById(savedProduct.getProductId());

        // Verify it is gone
        assertNull(productRepository.findById(savedProduct.getProductId()));
    }

    @Test
    void testDeleteById_ProductDoesNotExist() {
        // This should fall through removeIf without throwing an error
        productRepository.deleteById("fake-id");

        assertNull(productRepository.findById("fake-id"));
    }

}
