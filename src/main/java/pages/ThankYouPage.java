package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ThankYouPage {

    WebDriver driver;
    WebDriverWait wait;

    By getOrderId=By.xpath("//div[contains(text(),'OrderID')]");

    public ThankYouPage(WebDriver driver) {
        this.driver = driver;
    }

    public String isThankYouPageLoaded()
    {
//        String currentUrl = driver.getCurrentUrl();
//        System.out.println("current url is" +currentUrl);
//        return currentUrl.contains("order is placed") || currentUrl.contains("OrderID");
        WebElement heading = driver.findElement(getOrderId);
        String text= heading.getText();
        return text;

    }
}
