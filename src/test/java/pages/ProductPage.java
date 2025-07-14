
package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static locators.Locators.PlpPage.validatePlpPage;
import static locators.Locators.ProductPage.addToCartBtn;
import static locators.Locators.ProductPage.validatePdpPage;

public class ProductPage {
    private static final WebDriver driver;
    private static final WebDriverWait wait;

    static {
        driver = BaseTest.driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    public static void setAddToCartBtn()
    {

        driver.findElement(addToCartBtn).click();
        System.out.println("Product added to cart successfully");
    }
    public static void validatePdpPage()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validatePdpPage));
        String expectedText = "Add to Bag";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }

}
