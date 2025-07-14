package pages;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import locators.Locators;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private static final WebDriver driver;
    private static final WebDriverWait wait;

    static {
        driver = BaseTest.driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public static List<WebElement> getCategoryLinks() {
        return driver.findElements(Locators.HomePage.categoryLink);
    }

    public static String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public static void assertHomePageLoaded() {
        String title = driver.getTitle();
        boolean isHomePageLoaded = title.contains("Tira") || title.contains("Beauty");
        Assert.assertTrue(isHomePageLoaded, "Home Page did not load correctly");
        System.out.println("✅ Home page loaded successfully.");
    }

    public static void validateCategoryNavigation() {
        List<WebElement> initialCategoryLinks = getCategoryLinks();
        List<String> categoryNames = new ArrayList<>();

        for (WebElement el : initialCategoryLinks) {
            categoryNames.add(el.getText().trim());
        }

        for (int i = 1; i < categoryNames.size(); i++) {
            List<WebElement> currentLinks = getCategoryLinks();

            WebElement currentCategory = currentLinks.get(i);
            String expectedSlug = currentCategory.getAttribute("href");

            System.out.println("🔍 Checking category: " + expectedSlug);
            currentCategory.click();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains(expectedSlug));

            String actualUrl = getCurrentUrl();
            System.out.println("🔗 Navigated URL: " + actualUrl);

            Assert.assertNotNull(expectedSlug, "❌ Category href is null at index " + i);
            Assert.assertTrue(actualUrl.contains(expectedSlug),
                    "❌ URL mismatch. Expected: " + expectedSlug + ", Got: " + actualUrl);
        }

        System.out.println("✅ Clicked on login icon.");
    }
}
