
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategoryPage {
    WebDriver driver;
    WebDriverWait wait;

    By menuMakeup = By.linkText("Makeup"); // May need better locator
    By subCategoryNail = By.partialLinkText("Nail");

    public CategoryPage(WebDriver driver)
    {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    public void navigateToLipstickCategory() {
        driver.findElement(menuMakeup).click();
        driver.findElement(subCategoryNail).click();
    }

}

