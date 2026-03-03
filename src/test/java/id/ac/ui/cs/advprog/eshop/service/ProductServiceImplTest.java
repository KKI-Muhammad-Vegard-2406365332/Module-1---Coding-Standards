package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreate() {
        Product product = new Product();
        productService.create(product);
        Mockito.verify(productRepository).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        Iterator<Product> iterator = productList.iterator();
        Mockito.when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindById_ProductFound() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Target Product");

        // Mock the findById method instead of findAll
        Mockito.when(productRepository.findById("1")).thenReturn(product);

        Product result = productService.findById("1");
        assertNotNull(result);
        assertEquals("Target Product", result.getProductName());

        // Optional: Verify that the repository's findById was indeed called
        Mockito.verify(productRepository).findById("1");
    }

    @Test
    void testFindById_ProductNotFound() {
        // Mock the findById method to return null when searching for "2"
        Mockito.when(productRepository.findById("2")).thenReturn(null);

        Product result = productService.findById("2");
        assertNull(result);

        // Optional: Verify that the repository's findById was indeed called
        Mockito.verify(productRepository).findById("2");
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        productService.update(product);
        Mockito.verify(productRepository).update(product);
    }

    @Test
    void testDeleteById() {
        productService.deleteById("1");
        // Fixed the method name to match your ProductRepository
        Mockito.verify(productRepository).deleteById("1");
    }
}