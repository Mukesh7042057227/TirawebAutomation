package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ThankYouPage {

    WebDriver driver;
    WebDriverWait wait;

    By getOrderId=By.xpath("//div[contains(text(),'OrderID')]");
    By myOrderClick=By.xpath("//div[text()=\"Go to My Orders\"]");

    public ThankYouPage(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isThankYouPageLoaded() throws InterruptedException {
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println("current url is" +currentUrl);
        return currentUrl.contains("/cart/order-status") && currentUrl.contains("delivery_address_id") && currentUrl.contains("order_id");


    }
    public void myOrderClick(){

       WebElement clickOnOrder=wait.until(ExpectedConditions.visibilityOfElementLocated(myOrderClick));
       clickOnOrder.click();
    }
}
