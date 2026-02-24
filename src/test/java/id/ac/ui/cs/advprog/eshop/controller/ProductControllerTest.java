package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("CreateProduct"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productId", "1")
                        .param("productName", "Test Product")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        Mockito.verify(productService).create(any(Product.class));
    }

    @Test
    void testProductListPage() throws Exception {
        List<Product> products = new ArrayList<>();
        Mockito.when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("ProductList"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        Mockito.verify(productService).deleteById("1");
    }

    @Test
    void testEditProductPage_ProductExists() throws Exception {
        Product product = new Product();
        product.setProductId("1");
        Mockito.when(productService.findById("1")).thenReturn(product);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("EditProduct"));
    }

    @Test
    void testEditProductPage_ProductDoesNotExist() throws Exception {
        Mockito.when(productService.findById("99")).thenReturn(null);

        // Tests the `if (product == null)` branch in your controller
        mockMvc.perform(get("/product/edit/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "1")
                        .param("productName", "Updated Product")
                        .param("productQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        Mockito.verify(productService).update(any(Product.class));
    }
}