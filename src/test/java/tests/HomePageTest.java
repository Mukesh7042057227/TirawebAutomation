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
    public void testLoginToOrderFlow() {
        HomePage home = new HomePage(driver);

        Assert.assertTrue(home.isHomePageLoaded(), "Home Page did not load correctly");
        System.out.println("✅ Home page loaded successfully.");

        List<WebElement> initialCategoryLinks = home.getCategoryLinks();
        List<String> categoryNames = new ArrayList<>();

        for (WebElement el : initialCategoryLinks) {
            categoryNames.add(el.getText().trim());
        }

        for (int i = 1; i < categoryNames.size(); i++) {
            home = new HomePage(driver);  // Refresh POM
            List<WebElement> currentLinks = home.getCategoryLinks();

            WebElement currentCategory = currentLinks.get(i);
            String expectedSlug = currentCategory.getAttribute("href");

            System.out.println("🔍 Checking category: " + expectedSlug);
            currentCategory.click();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains(expectedSlug));

            String actualUrl = home.getCurrentUrl();
            System.out.println("🔗 Navigated URL: " + actualUrl);

            Assert.assertNotNull(expectedSlug, "❌ Category href is null at index " + i);
            Assert.assertTrue(actualUrl.contains(expectedSlug),
                    "❌ URL mismatch. Expected: " + expectedSlug + ", Got: " + actualUrl);
        }

        home.clickLogin();
        System.out.println("✅ Clicked on login icon.");
    }
}
