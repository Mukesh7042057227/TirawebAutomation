package pages;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.CartPage.validateCartPage;
import static locators.Locators.CheckoutPage.*;

public class CheckoutPage {
    private static final WebDriver driver;
    private static final WebDriverWait wait;

    static {
        driver = BaseTest.driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public static void reviewOrder() {

        wait.until(ExpectedConditions.elementToBeClickable(reviewOrder));

        driver.findElement(reviewOrder).click();
        System.out.println("Reached checkout page - stop before payment");
    }


    public static void scrollSidebarTillElementVisible() throws InterruptedException {
        WebElement sidebar = wait.until(ExpectedConditions.visibilityOfElementLocated(sidebarContainer));
        WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(scrollToElement));

        // Hover to activate sidebar scroll behavior
        new Actions(driver).moveToElement(sidebar, 15, 15).perform();
        Thread.sleep(500); // Allow JavaScript re-render after hover

        // Scroll the target element into view within the sidebar
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'end'});", targetElement);
        System.out.println("✅ Scrolled to element inside sidebar.");

        driver.findElement(scrollToElement).click();
        System.out.println("Selected COD MOP");
    }
    public static void clickOnBuyNow() throws InterruptedException {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable((buyNowClick)));

        element.click();
        System.out.println("Clicked on buyNow Button");
    }
    public static void validateCheckoutPage(){

        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateCheckoutPage));
        String expectedText = "Review Order Details";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);

    }
}
