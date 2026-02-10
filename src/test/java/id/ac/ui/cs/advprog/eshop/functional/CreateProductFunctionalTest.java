package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @Test
    void testCreateProduct(WebDriver driver) {
        // 1. this will open the product list
        driver.get("http://localhost:8080/product/list");

        // 2. it will create a new product
        driver.findElement(By.linkText("Create Product")).click();

        // 3. this will fills the required forms
        driver.findElement(By.id("nameInput"))
                .sendKeys("Functional Test Product");

        driver.findElement(By.id("quantityInput"))
                .sendKeys("5");

        // 4. submit the form
        driver.findElement(By.tagName("form")).submit();

        // 5. then it will verify the product exists on the list
        assertTrue(
                driver.getPageSource().contains("Functional Test Product")
        );
    }
}