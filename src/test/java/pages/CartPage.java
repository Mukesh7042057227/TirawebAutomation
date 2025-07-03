
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static locators.Locators.CartPage.openCart;
import static locators.Locators.CartPage.proceedToCheckout;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;


    public CartPage(WebDriver driver)
    {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openCart()
    {
        driver.findElement(openCart).click();
        System.out.println("cart page opened successfully");
    }

    public void proceedToCheckout()
    {
        WebElement checkoutButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(proceedToCheckout));
        checkoutButton.click();
        System.out.println("successfull clicked on checkout button");

    }
}
