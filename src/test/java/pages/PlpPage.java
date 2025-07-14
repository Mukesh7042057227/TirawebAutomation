package pages;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.LoginPage.validateLoginPage;
import static locators.Locators.PlpPage.*;

public class PlpPage {
    private static final WebDriver driver;
    private static final WebDriverWait wait;

    static {
        driver = BaseTest.driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public static void sortBy() {
        try {
            // Step 1: Click sort dropdown
            wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown)).click();
            System.out.println("✅ Clicked on Relevance Sort by");
            Thread.sleep(4000);
            // Step 2: Wait for "Price High to Low" option to be visible and click
            wait.until(ExpectedConditions.visibilityOfElementLocated(priceHighToLow));
            WebElement sortOption = wait.until(ExpectedConditions.elementToBeClickable(priceHighToLow));
            sortOption.click();
            System.out.println("✅ Clicked on Price High to Low");
        } catch (Exception e) {
            System.out.println("❌ Failed to sort products: " + e.getMessage());
        }
    }

    public static void clickOnProduct() {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                WebElement product = wait.until(ExpectedConditions.elementToBeClickable(clickOnProduct));

                // Scroll into view and hover
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", product);
                new Actions(driver).moveToElement(product).perform();
                System.out.println("✅ Hovered on first product");

                String originalWindow = driver.getWindowHandle();

                // Click product
                product.click();
                System.out.println("✅ Clicked on product");

                // Wait and switch to new tab
                wait.until(driver -> driver.getWindowHandles().size() > 1);
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!windowHandle.equals(originalWindow)) {
                        driver.switchTo().window(windowHandle);
                        System.out.println("✅ Switched to new tab");
                        break;
                    }
                }

                System.out.println("🌐 New tab URL: " + driver.getCurrentUrl());
                break;

            } catch (StaleElementReferenceException e) {
                System.out.println("⚠️ Stale element error — retrying...");
            } catch (TimeoutException te) {
                System.out.println("❌ Timeout while trying to click product: " + te.getMessage());
                break;
            }
        }
    }
    public static void validatePlpPage()
    {

        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validatePlpPage));
        String expectedText = "Delivering to";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);

    }
}
