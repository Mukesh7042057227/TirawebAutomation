
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.CartPage.*;
import static locators.Locators.LoginPage.validateLoginPage;

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
    public void validateCartPage()
    {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateCartPage));
        String expectedText = "Coupons & Bank Offers";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }
}
