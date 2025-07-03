package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import locators.Locators;

import java.util.List;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getCategoryLinks() {
        return driver.findElements(Locators.HomePage.CATEGORY_LINKS);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void clickLogin() {
        driver.findElement(Locators.HomePage.LOGIN_ICON).click();
    }

    public boolean isHomePageLoaded() {
        String title = driver.getTitle();
        return title.contains("Tira") || title.contains("Beauty");
    }
}
