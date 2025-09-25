
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.CartPage.*;

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
        WebElement checkoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(proceedToCheckout));
        checkoutButton.click();
        System.out.println("successfull clicked on checkout button");

    }
    public void validateCartPageWithLogin()
    {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateCartPageWithLogin));
        String expectedText = "Coupons & Bank Offers";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }
    public void validateCartPageWithOutLogin()
    {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateCartPageWithoutLogin));
        String expectedText = " Login to Apply Coupons & Bank Offers ";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }
    public void InncreaseQtyOnCart()
    {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(IncreaseQty));
        messageElement.click();
        System.out.println("✅ Qty increased from cart ");
    }
    public void DicreaseQtyOnCart()
    {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(DicreaseQty));
        messageElement.click();
        System.out.println("✅ Qty dicreased from cart ");
    }
    public void ProductRemoveFromCart()
    {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(DicreaseQty));
        messageElement.click();
        WebElement removeproduct = wait.until(ExpectedConditions.visibilityOfElementLocated(RemoveFromCart));
        removeproduct.click();
        String expectedText = " Login to Apply Coupons & Bank Offers ";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }

}
