package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static locators.Locators.CategoryPage.menuMakeup;
import static locators.Locators.CategoryPage.subCategoryNail;
import static locators.Locators.HomePage.makeupNavLink;

public class CategoryPage {
    // Constants like HomePage
    private static final String SUCCESS_PREFIX = "✅ ";
    private static final String ERROR_PREFIX = "❌ ";
    private static final String INFO_PREFIX = "🔍 ";
    private static final String WARNING_PREFIX = "⚠️ ";

    WebDriver driver;
    WebDriverWait wait;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void navigateToLipstickCategory() {
        System.out.println(INFO_PREFIX + "Starting navigation to makeup -> nail category...");
        try {
            // Wait for login to complete and page to be ready for navigation
            System.out.println(INFO_PREFIX + "Waiting for page to be ready after login...");
            Thread.sleep(2000);
            System.out.println(SUCCESS_PREFIX + "Page is ready for navigation");

            hoverOverMakeup();
            System.out.println(SUCCESS_PREFIX + "Makeup hover completed successfully");

            // Wait for nail subcategory to appear and become clickable
            WebElement nailCategory = wait.until(ExpectedConditions.elementToBeClickable(subCategoryNail));
            System.out.println(SUCCESS_PREFIX + "Nail subcategory is now clickable");

            // Click on nail subcategory
            nailCategory.click();
            System.out.println(SUCCESS_PREFIX + "Successfully clicked on nail subcategory");
            System.out.println(SUCCESS_PREFIX + "Navigation completed successfully: makeup -> nail");

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to navigate to nail category: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to nail category: " + e.getMessage());
        }
    }

    // Wait for page to be ready after login without using Thread.sleep
    private void waitForPageToBeReady() {
        // Wait for URL to contain tirabeauty (indicating we're on homepage after login)
        wait.until(ExpectedConditions.urlContains("tirabeauty"));

        // Wait for makeup navigation to be visible and clickable
        wait.until(ExpectedConditions.elementToBeClickable(makeupNavLink));

        // Wait for page to be stable by checking if makeup element is interactable
        wait.until(ExpectedConditions.visibilityOfElementLocated(makeupNavLink));
    }

    // Same hover method as HomePage
    public void hoverOverMakeup() {
        Actions actions = new Actions(driver);
        WebElement makeupNav = wait.until(ExpectedConditions.elementToBeClickable(makeupNavLink));
        // Scroll element into view first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", makeupNav);
        // Move to makeup element and hold
        actions.moveToElement(makeupNav).perform();
        // Also trigger hover using JavaScript as backup
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", makeupNav);
    }
}
