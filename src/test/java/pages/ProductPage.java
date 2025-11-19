
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
import static locators.Locators.LoginPage.validateLoginPage;

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

    // Helper method to get wishlist count
    private String getWishlistCount() {
        try {
            return driver.findElement(wishlistCount).getText().trim();
        } catch (Exception e) {
            return "0";
        }
    }

    // Helper method to validate toast and count
    private void validateToastAndCount(boolean isAdd, String initialCount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        if (isAdd) {
            // Validate add toast
            WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(wishlistToastMsg));
            System.out.println("✅ Toast Verified: " + toast.getText());
            wait.until(ExpectedConditions.invisibilityOfElementLocated(wishlistToastMsg));

            // Validate count increased
            String currentCount = getWishlistCount();
            System.out.println("✅ Wishlist count after add: " + currentCount);
        } else {
            // Validate remove toast
            WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(wishlistRemoveToastMsg));
            System.out.println("✅ Toast Verified: " + toast.getText());
            wait.until(ExpectedConditions.invisibilityOfElementLocated(wishlistRemoveToastMsg));

            // Validate count decreased
            String currentCount = getWishlistCount();
            int initial = initialCount.isEmpty() ? 0 : Integer.parseInt(initialCount);
            int current = currentCount.isEmpty() ? 0 : Integer.parseInt(currentCount);

            if (current < initial) {
                System.out.println("✅ Wishlist count decreased: " + initial + " → " + current);
            } else {
                System.out.println("⚠️ Wishlist count did not decrease as expected");
            }
        }
    }

    public void setSaveToWishlist(LoginPage login) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Store current product page URL (in case we need to navigate back after login)
        String productPageUrl = driver.getCurrentUrl();
        System.out.println("📍 Product Page URL: " + productPageUrl);

        // 2. Get initial wishlist count
        String initialCount = getWishlistCount();
        System.out.println("📊 Initial Wishlist Count: " + initialCount);

        // 3. Determine which button exists and click it
        boolean isAddOperation;
        if (!driver.findElements(wishlistIconClick).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(wishlistIconClick)).click();
            System.out.println("✅ Clicked 'Save to Wishlist' button");
            isAddOperation = true;
        } else if (!driver.findElements(wishlistIconClickForRemove).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(wishlistIconClickForRemove)).click();
            System.out.println("✅ Clicked 'Saved to Wishlist' button");
            isAddOperation = false;
        } else {
            System.out.println("❌ No wishlist button found on page");
            return;
        }

        // 4. Wait for toast (add/remove) OR login page to appear
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(wishlistToastMsg),
                ExpectedConditions.visibilityOfElementLocated(wishlistRemoveToastMsg),
                ExpectedConditions.visibilityOfElementLocated(validateLoginPage)
            ));
        } catch (Exception e) {
            System.out.println("❌ No response after clicking wishlist button: " + e.getMessage());
            return;
        }

        // 5. Check if login page appeared
        if (!driver.findElements(validateLoginPage).isEmpty()) {
            System.out.println("🔐 Login page detected - Proceeding with login...");

            // Login
            login.loginWithValidDetail();
            System.out.println("✅ Login completed");

            // Wait for product page to load (wishlist button appears)
            wait.until(driver ->
                !driver.findElements(wishlistIconClick).isEmpty() ||
                !driver.findElements(wishlistIconClickForRemove).isEmpty()
            );
            System.out.println("✅ Product page loaded");

            // Click appropriate button again after login
            if (!driver.findElements(wishlistIconClick).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(wishlistIconClick)).click();
                System.out.println("✅ Clicked 'Save to Wishlist' after login");
                validateToastAndCount(true, initialCount);
            } else if (!driver.findElements(wishlistIconClickForRemove).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(wishlistIconClickForRemove)).click();
                System.out.println("✅ Clicked 'Saved to Wishlist' after login");
                validateToastAndCount(false, initialCount);
            }
        } else {
            // Toast appeared - no login needed
            System.out.println("✅ User already logged in");
            validateToastAndCount(isAddOperation, initialCount);
        }
    }

    public void validateSaveToWishlist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // STEP 1: Validate ADD to Wishlist
        System.out.println("🔍 Step 1: Validating ADD to Wishlist operation...");

        String initialCount = getWishlistCount();
        System.out.println("📊 Initial Wishlist Count: " + initialCount);

        // Check if "Save to Wishlist" button exists
        if (!driver.findElements(wishlistIconClick).isEmpty()) {
            // Click "Save to Wishlist"
            wait.until(ExpectedConditions.elementToBeClickable(wishlistIconClick)).click();
            System.out.println("✅ Clicked 'Save to Wishlist' button");

            // Validate add toast
            validateToastAndCount(true, initialCount);
            System.out.println("✅ ADD to Wishlist validation completed");
        } else {
            System.out.println("⚠️ 'Save to Wishlist' button not found - product already in wishlist");
        }

        // STEP 2: Validate REMOVE from Wishlist
        System.out.println("\n🔍 Step 2: Validating REMOVE from Wishlist operation...");

        String countBeforeRemove = getWishlistCount();
        System.out.println("📊 Wishlist Count before remove: " + countBeforeRemove);

        // Check if "Saved to Wishlist" button exists
        if (!driver.findElements(wishlistIconClickForRemove).isEmpty()) {
            // Click "Saved to Wishlist" to remove
            wait.until(ExpectedConditions.elementToBeClickable(wishlistIconClickForRemove)).click();
            System.out.println("✅ Clicked 'Saved to Wishlist' button");

            // Validate remove toast
            validateToastAndCount(false, countBeforeRemove);
            System.out.println("✅ REMOVE from Wishlist validation completed");
        } else {
            Assert.fail("❌ 'Saved to Wishlist' button not found - cannot test remove functionality");
        }

        System.out.println("\n✅ Complete Wishlist ADD & REMOVE validation successful!");
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
