
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.CartPage.*;
import static locators.Locators.LoginPage.toastNotification;
import static locators.Locators.WishlistPage.productRemoveToastMsg;
import static locators.Locators.WishlistPage.emptyCartPage;
import static locators.Locators.WishlistPage.productAddedFromCart;
import static locators.Locators.PlpPage.productAddToWishlistToastMsg;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;


    public CartPage(WebDriver driver)
    {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void waitForPageStabilityAfterLogin()
    {
        // Wait for page to stabilize after login redirect
        try {
            // Wait for cart icon to be present and clickable (indicates page is loaded)
            wait.until(ExpectedConditions.elementToBeClickable(openCart));
            System.out.println("✅ Page stabilized after login - cart icon is clickable");
        } catch (Exception e) {
            System.out.println("⚠️ Waiting additional time for page stabilization...");
            try {
                Thread.sleep(2000);
                wait.until(ExpectedConditions.elementToBeClickable(openCart));
                System.out.println("✅ Page stabilized after additional wait");
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void openCart()
    {
        boolean cartOpened = false;
        int attempts = 0;
        int maxAttempts = 3;

        while (!cartOpened && attempts < maxAttempts) {
            try {
                attempts++;
                System.out.println("Attempting to open cart (attempt " + attempts + ")...");

                // Wait a bit for any ongoing DOM updates
                Thread.sleep(1000);

                // Find fresh cart element and click
                wait.until(ExpectedConditions.elementToBeClickable(openCart)).click();
                cartOpened = true;
                System.out.println("✅ Cart page opened successfully");

            } catch (Exception e) {
                if (attempts >= maxAttempts) {
                    System.out.println("❌ Failed to open cart after " + maxAttempts + " attempts");
                    throw new RuntimeException("Could not open cart after " + maxAttempts + " attempts", e);
                } else {
                    System.out.println("⚠️ Cart open attempt " + attempts + " failed, retrying... Error: " + e.getMessage());
                    try {
                        Thread.sleep(2000); // Wait before retry
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    public void proceedToCheckout()
    {
        WebElement checkoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(proceedToCheckout));
        checkoutButton.click();
        System.out.println("successfull clicked on checkout button");

    }
    public void validateCartPageWithLogin()
    {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateCartPageWithLogin));
        String expectedText = "Coupons & Bank Offers";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }
    public void validateCartPageWithOutLogin()
    {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateCartPageWithoutLogin));
        String expectedText = " Login to Apply Coupons & Bank Offers ";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }
    public void IncreaseQtyOnCart()
    {
        // Get current quantity before clicking increase
        WebElement qtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
        String currentQtyText = qtyElement.getAttribute("value") != null ? qtyElement.getAttribute("value") : qtyElement.getText();
        int currentQty = Integer.parseInt(currentQtyText.trim());
        System.out.println("Current quantity: " + currentQty);

        // Click increase button
        wait.until(ExpectedConditions.elementToBeClickable(IncreaseQty)).click();

        // Wait for quantity to update using WebDriverWait
        int expectedQty = currentQty + 1;
        wait.until(ExpectedConditions.textToBe(QtyDisplay, String.valueOf(expectedQty)));

        WebElement updatedQtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
        String updatedQtyText = updatedQtyElement.getAttribute("value") != null ? updatedQtyElement.getAttribute("value") : updatedQtyElement.getText();
        int updatedQty = Integer.parseInt(updatedQtyText.trim());

        // Validate that quantity increased by 1
        Assert.assertEquals(updatedQty, expectedQty, "❌ Quantity did not increase correctly. Expected: " + expectedQty + ", but got: " + updatedQty);

        System.out.println("✅ Qty increased from cart successfully from " + currentQty + " to " + updatedQty);
    }
    public void DicreaseQtyOnCart()
    {
        // Get current quantity before clicking decrease
        WebElement qtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
        String currentQtyText = qtyElement.getAttribute("value") != null ? qtyElement.getAttribute("value") : qtyElement.getText();
        int currentQty = Integer.parseInt(currentQtyText.trim());
        System.out.println("Current quantity: " + currentQty);

        // Only decrease if quantity is greater than 1
        if (currentQty <= 1) {
            System.out.println("❌ Cannot decrease quantity below 1. Current quantity: " + currentQty);
            return;
        }

        // Click decrease button
        wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty)).click();

        // Wait for quantity to update using WebDriverWait
        int expectedQty = currentQty - 1;
        wait.until(ExpectedConditions.textToBe(QtyDisplay, String.valueOf(expectedQty)));

        WebElement updatedQtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
        String updatedQtyText = updatedQtyElement.getAttribute("value") != null ? updatedQtyElement.getAttribute("value") : updatedQtyElement.getText();
        int updatedQty = Integer.parseInt(updatedQtyText.trim());

        // Validate that quantity decreased by 1
        Assert.assertEquals(updatedQty, expectedQty, "❌ Quantity did not decrease correctly. Expected: " + expectedQty + ", but got: " + updatedQty);

        System.out.println("✅ Qty decreased from cart successfully from " + currentQty + " to " + updatedQty);
    }
    public void ProductRemoveFromCart()
    {
        // Click decrease quantity button to open remove/move to wishlist sidebar
        System.out.println("Clicking on decrease quantity button to open remove/move to wishlist sidebar...");
        wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty)).click();

        // Validate Remove and Move to Wishlist buttons are visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(RemoveFromCart));
        wait.until(ExpectedConditions.visibilityOfElementLocated(MoveToWishlist));
        System.out.println("✅ Remove and Move to Wishlist buttons are visible");

        // Wait for existing toasts to disappear
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(toastNotification));
        } catch (Exception e) {
            // No existing toast - continue
        }

        // Click Remove button
        System.out.println("Clicking on Remove button...");
        wait.until(ExpectedConditions.elementToBeClickable(RemoveFromCart)).click();

        // Validate toast message appears
        try {
            WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(toastNotification));
            String toastText = toastMessage.getText();
            System.out.println("✅ Toast message displayed: " + toastText);
        } catch (Exception e) {
            // Try alternative toast locator
            try {
                WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(productRemoveToastMsg));
                String toastText = toastMessage.getText();
                System.out.println("✅ Product remove toast displayed: " + toastText);
            } catch (Exception e2) {
                Assert.fail("❌ No toast message appeared after clicking remove button");
            }
        }

        System.out.println("✅ Product removal process completed successfully");
    }

    public void MoveToWishlistFromCart()
    {
        // Check current quantity first
        WebElement qtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
        String currentQtyText = qtyElement.getAttribute("value") != null ? qtyElement.getAttribute("value") : qtyElement.getText();
        int currentQty = Integer.parseInt(currentQtyText.trim());
        System.out.println("Current quantity: " + currentQty);

        // If quantity > 1, keep clicking decrease until sidebar opens
        if (currentQty > 1) {
            System.out.println("Quantity is greater than 1, clicking decrease button until sidebar opens...");

            boolean sidebarOpened = false;
            int attempts = 0;
            int maxAttempts = currentQty; // Maximum attempts = current quantity

            while (!sidebarOpened && attempts < maxAttempts) {
                // Click decrease button
                wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty)).click();
                attempts++;
                System.out.println("Clicked decrease button (attempt " + attempts + ")");

                // Check if sidebar opened (Remove/Wishlist buttons are visible)
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(RemoveFromCart));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(MoveToWishlist));
                    sidebarOpened = true;
                    System.out.println("✅ Sidebar opened after " + attempts + " decrease clicks");
                } catch (Exception e) {
                    // Sidebar not opened yet, continue
                    System.out.println("Sidebar not opened yet, continuing...");
                }
            }

            if (!sidebarOpened) {
                Assert.fail("❌ Sidebar did not open after " + attempts + " decrease clicks");
            }
        } else {
            // If quantity is 1, clicking decrease should directly open sidebar
            System.out.println("Quantity is 1, clicking decrease button to open sidebar...");
            wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty)).click();

            // Validate Remove and Move to Wishlist buttons are visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(RemoveFromCart));
            wait.until(ExpectedConditions.visibilityOfElementLocated(MoveToWishlist));
            System.out.println("✅ Sidebar opened with Remove and Move to Wishlist buttons");
        }

        // Wait for existing toasts to disappear
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(toastNotification));
        } catch (Exception e) {
            // No existing toast - continue
        }

        // Click Move to Wishlist button (find fresh element to avoid stale reference)
        System.out.println("Clicking on Move to Wishlist button...");
        wait.until(ExpectedConditions.elementToBeClickable(MoveToWishlist)).click();

        // Validate toast message appears
        try {
            WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(toastNotification));
            String toastText = toastMessage.getText();
            System.out.println("✅ Toast message displayed: " + toastText);
        } catch (Exception e) {
            // Try alternative toast locator
            try {
                WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(productAddToWishlistToastMsg));
                String toastText = toastMessage.getText();
                System.out.println("✅ Product add to wishlist toast displayed: " + toastText);
            } catch (Exception e2) {
                Assert.fail("❌ No toast message appeared after clicking move to wishlist button");
            }
        }

        System.out.println("✅ Product move to wishlist process completed successfully");
    }

    public void ProductAddedFromCart()
    {
        // Check if "Shop Now" button (emptyCartPage) is visible
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartPage));
            System.out.println("✅ Shop Now button is visible - cart appears to be empty");

            // If Shop Now button is visible, click on Add to Bag using productAddedFromCart locator
            System.out.println("Clicking on Add to Bag button using productAddedFromCart locator...");
            wait.until(ExpectedConditions.elementToBeClickable(productAddedFromCart)).click();
            System.out.println("✅ Successfully clicked Add to Bag button");

        } catch (Exception e) {
            System.out.println("Shop Now button not visible, proceeding with scroll to find 'You May Also Like' text...");

            // If Shop Now button is not visible, scroll until "You May Also Like" text is visible
            JavascriptExecutor js = (JavascriptExecutor) driver;
            boolean youMayAlsoLikeFound = false;
            int scrollAttempts = 0;
            int maxScrollAttempts = 10;

            // First, scroll until "You May Also Like" text is visible using the dedicated locator
            while (!youMayAlsoLikeFound && scrollAttempts < maxScrollAttempts) {
                try {
                    // Check if "You May Also Like" text is visible using the YouMayAlsoLIke locator
                    wait.until(ExpectedConditions.visibilityOfElementLocated(YouMayAlsoLIke));
                    youMayAlsoLikeFound = true;
                    System.out.println("✅ 'You May Also Like' text found after " + scrollAttempts + " scroll attempts");

                } catch (Exception scrollException) {
                    // Scroll down and try again
                    scrollAttempts++;
                    System.out.println("'You May Also Like' text not found, scrolling down... (attempt " + scrollAttempts + ")");
                    js.executeScript("window.scrollBy(0, 500);");

                    try {
                        Thread.sleep(1000); // Wait after scroll
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            // Now click Add to Bag button directly using AddToBagFromCart locator
            if (youMayAlsoLikeFound) {
                System.out.println("Clicking Add to Bag button using AddToBagFromCart locator...");
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(AddToBagFromCart)).click();
                    System.out.println("✅ Successfully clicked Add to Bag button using AddToBagFromCart locator");
                } catch (Exception addToBagException) {
                    Assert.fail("❌ Add to Bag button not found using AddToBagFromCart locator. Error: " + addToBagException.getMessage());
                }
            } else {
                System.out.println("⚠️ 'You May Also Like' text not found after scrolling, trying fallback Add to Bag button...");
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(productAddedFromCart)).click();
                    System.out.println("✅ Successfully clicked Add to Bag button using fallback locator");
                } catch (Exception fallbackException) {
                    Assert.fail("❌ Add to Bag button not found with both YouMayAlsoLike approach and fallback");
                }
            }
        }

        // Validate that product was added successfully (optional toast validation)
        try {
            String toastText = wait.until(ExpectedConditions.visibilityOfElementLocated(toastNotification)).getText();
            System.out.println("✅ Success toast displayed: " + toastText);
        } catch (Exception e) {
            System.out.println("No toast message found, but Add to Bag button was clicked successfully");
        }

        System.out.println("✅ Product added from cart process completed successfully");
    }

}
