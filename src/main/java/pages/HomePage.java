package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage {
    WebDriver driver;

    // XPath to all category anchor tags
    By loginIcon = By.xpath("//a/div[@class=\"profile-icons profile-logout\"]");
    By categoryAnchors = By.xpath("//div[@class='category-navigation']//a");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Get list of all <a> elements for categories
    public List<WebElement> getCategoryLinks() {
        return driver.findElements(categoryAnchors);
    }

    // Get current URL after clicking
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateBack() {
        driver.navigate().back();
    }
    public void clickLogin() {
        driver.findElement(loginIcon).click();
    }
        public boolean isHomePageLoaded()
    {
            String title = driver.getTitle();
            return title.contains("Tira") || title.contains("Beauty");
        }

    }
