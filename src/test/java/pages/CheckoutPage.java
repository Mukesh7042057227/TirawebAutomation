package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.CheckoutPage.*;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;


    // Constructor to receive driver from test
    public CheckoutPage(WebDriver driver) {
        this.driver= driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void reviewOrder() {

        wait.until(ExpectedConditions.elementToBeClickable(reviewOrder));

        driver.findElement(reviewOrder).click();
        System.out.println("Reached checkout page - stop before payment");
    }


    public void scrollSidebarTillElementVisible() {
        WebElement sidebar = wait.until(ExpectedConditions.visibilityOfElementLocated(sidebarContainer));
        WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(scrollToElement));

        // Hover to activate sidebar scroll behavior
        new Actions(driver).moveToElement(sidebar, 15, 15).perform();
        wait.until(ExpectedConditions.elementToBeClickable(targetElement)); // Allow JavaScript re-render after hover

        // Scroll the target element into view within the sidebar
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'end'});", targetElement);
        System.out.println("✅ Scrolled to element inside sidebar.");

        driver.findElement(scrollToElement).click();
        System.out.println("Selected COD MOP");
    }
    public  void clickOnBuyNow() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable((buyNowClick)));

        element.click();
        System.out.println("Clicked on buyNow Button");
    }
    public void validateCheckoutPage(){

        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateCheckoutPage));
        String expectedText = "Review Order Details";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);

    }
}
