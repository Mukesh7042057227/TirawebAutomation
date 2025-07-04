package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.ThankYouPage.myOrderClick;

public class ThankYouPage {

    WebDriver driver;
    WebDriverWait wait;



    public ThankYouPage(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void isThankYouPageLoaded() throws InterruptedException {
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println("current url is" +currentUrl);
        boolean isCorrectPage =currentUrl.contains("/cart/order-status") && currentUrl.contains("delivery_address_id") && currentUrl.contains("order_id");

        Assert.assertTrue(isCorrectPage, "❌ Could not find thankyou page");
        System.out.println("Thank You Page loaded successfully.");


    }
    public void myOrderClick(){

       WebElement clickOnOrder=wait.until(ExpectedConditions.visibilityOfElementLocated(myOrderClick));
       clickOnOrder.click();
    }
}
