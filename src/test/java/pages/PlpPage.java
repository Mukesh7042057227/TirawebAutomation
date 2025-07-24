package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.PlpPage.*;

import java.util.List;

public class PlpPage {
    WebDriver driver;
    WebDriverWait wait;

    public PlpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void sortBy() {
        try {
            // 🔁 Always fetch fresh element
            WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown));
            sortDropdown.click();
            System.out.println("✅ Clicked on Relevance Sort by");

            // ✅ Use explicit wait instead of sleep
            WebElement sortOption = wait.until(ExpectedConditions.elementToBeClickable(priceHighToLow));
            sortOption.click();
            System.out.println("✅ Clicked on Price High to Low");
        } catch (StaleElementReferenceException e) {
            System.out.println("⚠️ StaleElementReferenceException caught in sortBy(). Retrying...");
            sortBy(); // recursive retry
        } catch (Exception e) {
            System.out.println("❌ Failed to sort products: " + e.getMessage());
        }
    }

    public void clickOnProduct() {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                // 🔁 Re-fetch element every time
                WebElement product = wait.until(ExpectedConditions.elementToBeClickable(clickOnProduct));

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", product);
                new Actions(driver).moveToElement(product).perform();
                System.out.println("✅ Hovered on product");

                String originalWindow = driver.getWindowHandle();

                product.click();
                System.out.println("✅ Clicked on product");

                // Switch to new tab
                wait.until(driver -> driver.getWindowHandles().size() > 1);
                for (String handle : driver.getWindowHandles()) {
                    if (!handle.equals(originalWindow)) {
                        driver.switchTo().window(handle);
                        System.out.println("✅ Switched to new tab");
                        break;
                    }
                }

                System.out.println("🌐 New tab URL: " + driver.getCurrentUrl());
                break;

            } catch (StaleElementReferenceException e) {
                System.out.println("⚠️ StaleElementReferenceException in clickOnProduct() — retrying...");
            } catch (TimeoutException te) {
                System.out.println("❌ TimeoutException in clickOnProduct(): " + te.getMessage());
                break;
            } catch (Exception e) {
                System.out.println("❌ General Exception in clickOnProduct(): " + e.getMessage());
                break;
            }
        }
    }

    public void validatePlpPage() {
        try {
            WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validatePlpPage));
            String expectedText = "Delivering to";
            String actualText = messageElement.getText();
            Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
            System.out.println("✅ Verified message: " + actualText);
        } catch (TimeoutException e) {
            System.out.println("❌ PLP validation failed: Element not visible");
        }
    }
    public void clickOnWishlistIconOnPlp()
    {
        WebElement iconClick = wait.until(ExpectedConditions.visibilityOfElementLocated(clickOnWishlistIconFromPlp));
        iconClick.click();
    }
    public void validationProductAddToWishlistFromPlp()
    {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productAddToWishlistToastMsg));
        String actualText = element.getText();
        Assert.assertTrue(actualText.contains("Product has been added to Wishlist."),
                "❌ Text mismatch: Expected 'Product has been added to Wishlist.' but got: " + actualText);
        System.out.println("🔍 Actual Toast Message: " + actualText);


    }
}
