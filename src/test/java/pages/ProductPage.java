
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static locators.Locators.ProductPage.addToCartBtn;

public class ProductPage {
    WebDriver driver;


    // Update as needed

    public ProductPage(WebDriver driver)
    {
        this.driver = driver;
    }
    public void setAddToCartBtn()
    {

        driver.findElement(addToCartBtn).click();
        System.out.println("Product added to cart successfully");
    }

}
