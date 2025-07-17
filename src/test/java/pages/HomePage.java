package pages;

import locators.Locators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static locators.Locators.LoginPage.loginIcon;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public List<WebElement> getCategoryLinks() {
        return driver.findElements(Locators.HomePage.categoryLink);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void assertHomePageLoaded() throws InterruptedException {
        String title = driver.getTitle();
        boolean isHomePageLoaded = title.contains("Tira") || title.contains("Beauty");
        Assert.assertTrue(isHomePageLoaded, "❌ Home Page did not load correctly");
        System.out.println("✅ Home page loaded successfully.");
    }

    public void validateCategoryNavigation() {
        List<WebElement> initialCategoryLinks = getCategoryLinks();
        List<String> categoryNames = new ArrayList<>();

        for (WebElement el : initialCategoryLinks) {
            String name = el.getText().trim();
            if (!name.isEmpty()) {
                categoryNames.add(name);
            }
        }

        for (String categoryName : categoryNames) {
            // Refresh homepage and locate fresh category link by name
            List<WebElement> currentLinks = getCategoryLinks();
            WebElement currentCategory = currentLinks.stream()
                    .filter(link -> link.getText().trim().equals(categoryName))
                    .findFirst()
                    .orElse(null);

            if (currentCategory == null) {
                System.out.println("⚠️ Category not found: " + categoryName);
                continue;
            }

            String expectedSlug = currentCategory.getAttribute("href");
            System.out.println("🔍 Checking category: " + expectedSlug);

            currentCategory.click();

            // Wait for URL to contain expectedSlug
            wait.until(ExpectedConditions.urlContains(expectedSlug));
            String actualUrl = getCurrentUrl();

            Assert.assertNotNull(expectedSlug, "❌ Category href is null");
            Assert.assertTrue(actualUrl.contains(expectedSlug),
                    "❌ URL mismatch. Expected: " + expectedSlug + ", Got: " + actualUrl);

            // Go back to home and wait for category links again
            driver.navigate().back();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Locators.HomePage.categoryLink));
        }
    }
    public void clickLoginIcon(){
        driver.findElement(loginIcon).click();
    }

}




