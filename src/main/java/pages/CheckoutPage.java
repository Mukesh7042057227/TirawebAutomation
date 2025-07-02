package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locator for review order / payment button
    By reviewOrder = By.xpath("//button[text()=' Select Payment Method ']");
    By sidebarContainer = By.cssSelector("div.sidebar-container");
    By buyNowClick=By.xpath("//div[1]/button[text()=\" Buy Now \"]");

    // Element to scroll into view, for example: Buy Now button
    By scrollToElement = By.xpath("//span/div[text()=\"Cash on Delivery\"]");


    // Constructor to receive driver from test
    public CheckoutPage(WebDriver driver) {
        this.driver= driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void reviewOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(reviewOrder));

        driver.findElement(reviewOrder).click();
        System.out.println("Reached checkout page - stop before payment");
    }


    public void scrollSidebarTillElementVisible() throws InterruptedException {
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
    public  void clickOnBuyNow() throws InterruptedException {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable((buyNowClick)));

        element.click();
        System.out.println("Clicked on buyNow Button");
    }
}
