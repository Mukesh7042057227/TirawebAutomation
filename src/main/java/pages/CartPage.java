
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    WebDriver driver;

    By openCart=By.xpath("//div/img[@title='Cart']");
    By proceedToCheckout = By.xpath("//button[text()=' Checkout ']");


    public CartPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public void openCart()
    {
        driver.findElement(openCart).click();
        System.out.println("cart page opened successfully");
    }

    public void proceedToCheckout()
    {
        driver.findElement(proceedToCheckout).click();
        System.out.println("successfull clicked on checkout button");

    }
}
