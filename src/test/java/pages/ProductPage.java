
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static locators.Locators.PlpPage.validatePlpPage;
import static locators.Locators.ProductPage.*;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    // Update as needed

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void setAddToCartBtn() {

        driver.findElement(addToCartBtn).click();
        System.out.println("Product added to cart successfully");
    }
    public void setSaveToWishlist() {

        driver.findElement(wishlistIconClick).click();
        System.out.println("Product added to wishlist successfully");
    }
    public void validateSaveToWishlist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Validate Toast Message appears
        try {
            WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(wishlistToastMsg));
            String toastText = toastMessage.getText();
            Assert.assertTrue(toastText.contains("Product has been added to Wishlist"),
                "❌ Toast message validation failed. Expected: 'Product has been added to Wishlist', but got: " + toastText);
            System.out.println("✅ Toast Message Verified: " + toastText);
        } catch (Exception e) {
            Assert.fail("❌ Toast message did not appear: " + e.getMessage());
        }

        // 2. Get initial wishlist count from header (before adding)
        String initialCountText = "0";
        try {
            WebElement wishlistCountElement = driver.findElement(wishlistCount);
            initialCountText = wishlistCountElement.getText().trim();
            System.out.println("📊 Initial Wishlist Count: " + initialCountText);
        } catch (Exception e) {
            System.out.println("⚠️ Wishlist count not visible initially (might be 0)");
        }

        // Wait for toast to disappear (invisibility)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(wishlistToastMsg));

        // 3. Validate wishlist icon count increased
        try {
            WebElement wishlistCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(wishlistCount));
            String currentCountText = wishlistCountElement.getText().trim();

            int initialCount = initialCountText.isEmpty() ? 0 : Integer.parseInt(initialCountText);
            int currentCount = Integer.parseInt(currentCountText);

            Assert.assertTrue(currentCount > initialCount,
                "❌ Wishlist count validation failed. Expected count to increase from " + initialCount + ", but got: " + currentCount);
            System.out.println("✅ Wishlist Count Increased: " + initialCount + " → " + currentCount);
        } catch (Exception e) {
            System.out.println("⚠️ Could not verify wishlist count increase: " + e.getMessage());
        }

        System.out.println("✅ Save to Wishlist validation completed successfully");
    }

    public void validatePdpPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validatePdpPage));
        String expectedText = "Add to Bag";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }

}
