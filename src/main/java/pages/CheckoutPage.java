package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;

    // Locator for review order / payment button
    By reviewOrder = By.xpath("//button[text()=' Select Payment Method ']");

    // Constructor to receive driver from test
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void reviewOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(reviewOrder));

        driver.findElement(reviewOrder).click();
        System.out.println("Reached checkout page - stop before payment");
    }
}
