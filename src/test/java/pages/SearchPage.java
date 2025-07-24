
package pages;

import locators.Locators;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static locators.Locators.SearchPage.*;
import static locators.Locators.SearchPage.searchInput;

public class SearchPage {
    WebDriver driver;
    WebDriverWait wait;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void search(String keyword) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.click();
        input.sendKeys(keyword);
        input.sendKeys(Keys.ENTER);
        System.out.println("🔍 Searched for: " + keyword);
    }

    public boolean isResultPresent() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(resultItems));
            List<WebElement> results = driver.findElements(resultItems);
            return !results.isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isNoResultMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(noResultsMessage));
            return driver.findElement(noResultsMessage).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }

    }public void searchMultipleKeywords(List<String> keywords) {
        for (String keyword : keywords) {
            try {
                WebElement Input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
                Input.clear();
                Input.sendKeys(keyword);
                Input.sendKeys(Keys.ENTER); // or click a button

                System.out.println("🔍 Searched for: " + keyword);

                wait.until(ExpectedConditions.visibilityOfElementLocated(resultItems));

                wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));

            } catch (Exception e) {
                System.out.println("❌ Failed to search for: " + keyword + " -> " + e.getMessage());
            }
        }
    }
}

