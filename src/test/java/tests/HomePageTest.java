package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Listeners(TestListener.class)
public class HomePageTest extends BaseTest {


    @Test
    public void testLoginToOrderFlow() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.isHomePageLoaded();

        System.out.println("🚀 Driver in test: " + driver);
        Assert.assertTrue(home.isHomePageLoaded(), "Home Page did not load correctly");
        System.out.println("Home page loaded successfully.");

        // Step 1: Capture category names only (to avoid stale elements later)
        List<WebElement> initialCategoryLinks = home.getCategoryLinks();
        List<String> categoryNames = new ArrayList<>();

        for (WebElement el : initialCategoryLinks) {
            categoryNames.add(el.getText().trim());
        }

        // Step 2: Loop through each category by index
        for (int i = 1; i < categoryNames.size(); i++) {
            // Refresh homepage and locate fresh category links
            home = new HomePage(driver);
            List<WebElement> currentLinks = home.getCategoryLinks();

            WebElement currentCategory = currentLinks.get(i);
            String expectedSlug = currentCategory.getAttribute("href");

            System.out.println("🔍 Checking category: " + expectedSlug);
            currentCategory.click();

            // Wait for correct slug in the URL
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains(expectedSlug));

            String actualUrl = home.getCurrentUrl();
            System.out.println("🔗 Navigated URL: " + actualUrl);

            // ✅ Assertions
            Assert.assertNotNull(expectedSlug, "❌ Category name is null at index " + i);
            Assert.assertTrue(actualUrl.contains(expectedSlug),
                    "❌ URL mismatch for category [" + expectedSlug + "]. Expected to contain: [" + expectedSlug + "], but got: " + actualUrl);

        }
        home.clickLogin();
    }


}

