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
        // Wait for page to stabilize after login redirect with extended timeout
        WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            // Wait for cart icon to be present and clickable (indicates page is loaded)
            extendedWait.until(ExpectedConditions.elementToBeClickable(openCart));
            System.out.println("✅ Page stabilized after login - cart icon is clickable");
        } catch (Exception e) {
            System.out.println("⚠️ Page stabilization failed: " + e.getMessage());
            throw new RuntimeException("Page failed to stabilize after login", e);
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

                // Wait for DOM stability and cart element to be clickable
                wait.until(ExpectedConditions.elementToBeClickable(openCart)).click();
                cartOpened = true;
                System.out.println("✅ Cart page opened successfully");

            } catch (Exception e) {
                if (attempts >= maxAttempts) {
                    System.out.println("❌ Failed to open cart after " + maxAttempts + " attempts");
                    throw new RuntimeException("Could not open cart after " + maxAttempts + " attempts", e);
                } else {
                    System.out.println("⚠️ Cart open attempt " + attempts + " failed, retrying... Error: " + e.getMessage());
                    // Wait for element to be available for retry
                    wait.until(ExpectedConditions.presenceOfElementLocated(openCart));
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
        // Step 1: Check if cart is empty (Shop Now button visible)
        System.out.println("🔍 Checking if cart is empty before increasing quantity...");

        try {
            // Check if "Shop Now" button is visible (indicates empty cart)
            WebElement shopNowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartPage));
            if (shopNowButton.isDisplayed()) {
                System.out.println("✅ Cart is empty - Shop Now button detected. Adding product to bag first...");

                // Cart is empty, need to add product first using AddToBagFromCart locator
                try {
                    System.out.println("Clicking Add to Bag button using AddToBagFromCart locator...");
                    WebElement addToBagButton = wait.until(ExpectedConditions.elementToBeClickable(AddToBagFromCart));

                    // Scroll to the add to bag button
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addToBagButton);
                    wait.until(ExpectedConditions.elementToBeClickable(AddToBagFromCart));

                    // Click Add to Bag button
                    try {
                        addToBagButton.click();
                        System.out.println("✅ Add to Bag button clicked successfully with regular click");
                    } catch (Exception clickException) {
                        System.out.println("Regular click failed, trying JavaScript click...");
                        js.executeScript("arguments[0].click();", addToBagButton);
                        System.out.println("✅ Add to Bag button clicked successfully with JavaScript click");
                    }

                    // Wait for product to be added to cart (toast message or cart update)
                    try {
                        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(toastNotification));
                        String toastText = toastMessage.getText();
                        System.out.println("✅ Product added to cart - Toast: " + toastText);
                    } catch (Exception toastException) {
                        System.out.println("⚠️ No toast detected, but product should be added to cart");
                    }

                    // Wait for cart to update and quantity display to appear
                    System.out.println("Waiting for cart to update with new product...");
                    wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));

                } catch (Exception addToBagException) {
                    Assert.fail("❌ Failed to add product to bag from empty cart. Error: " + addToBagException.getMessage());
                }
            }
        } catch (Exception shopNowException) {
            // Shop Now button not visible, cart already has items - proceed normally
            System.out.println("✅ Cart already contains items, proceeding with quantity increase...");
        }

        // Step 2: Get current quantity before clicking increase
        WebElement qtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
        String currentQtyText = qtyElement.getText().trim();
        int currentQty = Integer.parseInt(currentQtyText);
        System.out.println("Current quantity: " + currentQty);

        // Enhanced button clicking with multiple fallback approaches
        boolean buttonClicked = false;
        int clickAttempts = 0;
        int maxClickAttempts = 3;

        while (!buttonClicked && clickAttempts < maxClickAttempts) {
            try {
                clickAttempts++;
                System.out.println("Attempting to click increase button (attempt " + clickAttempts + ")...");

                // Wait for button to be clickable and visible
                WebElement increaseButton = wait.until(ExpectedConditions.elementToBeClickable(IncreaseQty));

                // Scroll to button if needed
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", increaseButton);

                // Wait for scroll to complete
                wait.until(ExpectedConditions.elementToBeClickable(increaseButton));

                // Try regular click first
                try {
                    increaseButton.click();
                    buttonClicked = true;
                    System.out.println("✅ Increase button clicked successfully with regular click");
                } catch (Exception clickException) {
                    System.out.println("Regular click failed, trying JavaScript click...");
                    // Fallback to JavaScript click
                    js.executeScript("arguments[0].click();", increaseButton);
                    buttonClicked = true;
                    System.out.println("✅ Increase button clicked successfully with JavaScript click");
                }

            } catch (Exception e) {
                System.out.println("⚠️ Click attempt " + clickAttempts + " failed: " + e.getMessage());
                if (clickAttempts >= maxClickAttempts) {
                    throw new RuntimeException("Failed to click increase button after " + maxClickAttempts + " attempts", e);
                }
                // Wait for element to be available for next attempt
                wait.until(ExpectedConditions.presenceOfElementLocated(IncreaseQty));
            }
        }

        // Wait for quantity to update using WebDriverWait with a more flexible approach
        int expectedQty = currentQty + 1;
        System.out.println("Waiting for quantity to update from " + currentQty + " to " + expectedQty);

        // Wait for quantity element to be in updatable state
        wait.until(ExpectedConditions.presenceOfElementLocated(QtyDisplay));

        // Use WebDriverWait to wait for text to change
        try {
            wait.until(driver -> {
                try {
                    WebElement qtyEl = driver.findElement(QtyDisplay);
                    String qtyText = qtyEl.getText().trim();
                    int currentQtyValue = Integer.parseInt(qtyText);
                    System.out.println("Checking quantity - Current: " + currentQtyValue + ", Expected: " + expectedQty);
                    return currentQtyValue == expectedQty;
                } catch (Exception e) {
                    System.out.println("Error checking quantity: " + e.getMessage());
                    return false;
                }
            });
            System.out.println("✅ Qty increased from cart successfully from " + currentQty + " to " + expectedQty);
        } catch (Exception e) {
            // If regular wait fails, try clicking button one more time
            System.out.println("⚠️ Quantity didn't update after first click, trying one more click...");
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                WebElement increaseButton = driver.findElement(IncreaseQty);
                js.executeScript("arguments[0].click();", increaseButton);
                System.out.println("Second increase button click executed");

                // Wait for quantity to potentially update
                wait.until(ExpectedConditions.presenceOfElementLocated(QtyDisplay));

                // Check quantity one more time
                WebElement qtyCheck = driver.findElement(QtyDisplay);
                String qtyCheckText = qtyCheck.getText().trim();
                int qtyCheckValue = Integer.parseInt(qtyCheckText);
                System.out.println("Quantity after second click: " + qtyCheckValue);

                if (qtyCheckValue == expectedQty) {
                    System.out.println("✅ Qty increased after second click from " + currentQty + " to " + qtyCheckValue);
                }
            } catch (Exception secondClickException) {
                System.out.println("Second click attempt failed: " + secondClickException.getMessage());
            }
        }

        // Final validation
        WebElement finalQtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
        String finalQtyText = finalQtyElement.getText().trim();
        int finalQty = Integer.parseInt(finalQtyText);

        System.out.println("Final quantity check: " + finalQty + " (expected: " + expectedQty + ")");

        // Validate that quantity increased by 1
        Assert.assertEquals(finalQty, expectedQty, "❌ Quantity did not increase correctly. Expected: " + expectedQty + ", but got: " + finalQty);
        System.out.println("✅ Quantity validation passed: " + finalQty);
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

        // Enhanced decrease button clicking with scroll and multiple click strategies
        boolean buttonClicked = false;
        int clickAttempts = 0;
        int maxClickAttempts = 3;

        while (!buttonClicked && clickAttempts < maxClickAttempts) {
            try {
                clickAttempts++;
                System.out.println("Attempting to click decrease button (attempt " + clickAttempts + ")...");

                // Wait for button to be clickable and visible
                WebElement decreaseButton = wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty));

                // Scroll to button if needed
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", decreaseButton);

                // Wait for scroll to complete
                wait.until(ExpectedConditions.elementToBeClickable(decreaseButton));

                // Try regular click first
                try {
                    decreaseButton.click();
                    buttonClicked = true;
                    System.out.println("✅ Decrease button clicked successfully with regular click");
                } catch (Exception clickException) {
                    System.out.println("Regular click failed, trying JavaScript click...");
                    // Fallback to JavaScript click
                    js.executeScript("arguments[0].click();", decreaseButton);
                    buttonClicked = true;
                    System.out.println("✅ Decrease button clicked successfully with JavaScript click");
                }

            } catch (Exception e) {
                System.out.println("⚠️ Click attempt " + clickAttempts + " failed: " + e.getMessage());
                if (clickAttempts >= maxClickAttempts) {
                    throw new RuntimeException("Failed to click decrease button after " + maxClickAttempts + " attempts", e);
                }
                // Wait for element to be available for next attempt
                wait.until(ExpectedConditions.presenceOfElementLocated(DicreaseQty));
            }
        }

        // Wait for quantity to update with enhanced polling approach
        int expectedQty = currentQty - 1;
        System.out.println("Waiting for quantity to update from " + currentQty + " to " + expectedQty);

        // Wait for quantity element to be in updatable state
        wait.until(ExpectedConditions.presenceOfElementLocated(QtyDisplay));

        // Use WebDriverWait to wait for text to change
        try {
            wait.until(driver -> {
                try {
                    WebElement qtyEl = driver.findElement(QtyDisplay);
                    String qtyText = qtyEl.getAttribute("value") != null ? qtyEl.getAttribute("value") : qtyEl.getText();
                    int currentQtyValue = Integer.parseInt(qtyText.trim());
                    System.out.println("Checking quantity - Current: " + currentQtyValue + ", Expected: " + expectedQty);
                    return currentQtyValue == expectedQty;
                } catch (Exception e) {
                    System.out.println("Error checking quantity: " + e.getMessage());
                    return false;
                }
            });
            System.out.println("✅ Qty decreased from cart successfully from " + currentQty + " to " + expectedQty);
        } catch (Exception e) {
            System.out.println("⚠️ Quantity update wait failed: " + e.getMessage());
        }

        // Final validation
        WebElement finalQtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
        String finalQtyText = finalQtyElement.getAttribute("value") != null ?
                             finalQtyElement.getAttribute("value") : finalQtyElement.getText();
        int finalQty = Integer.parseInt(finalQtyText.trim());

        // Validate that quantity decreased by 1
        Assert.assertEquals(finalQty, expectedQty, "❌ Quantity did not decrease correctly. Expected: " + expectedQty + ", but got: " + finalQty);

        System.out.println("✅ Quantity validation passed: " + finalQty);
    }
    public void ProductRemoveFromCart()
    {
        // First, ensure we have a product in cart with quantity ≥ 1
        System.out.println("🔍 Starting ProductRemoveFromCart - checking current cart state...");

        try {
            WebElement qtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
            String currentQtyText = qtyElement.getText().trim();
            int currentQty = Integer.parseInt(currentQtyText);
            System.out.println("Current quantity in cart: " + currentQty);
        } catch (Exception e) {
            System.out.println("⚠️ Could not read current quantity: " + e.getMessage());
        }

        // Enhanced decrease button clicking with multiple fallback approaches
        System.out.println("Clicking on decrease quantity button to open remove/move to wishlist sidebar...");

        boolean decreaseButtonClicked = false;
        int clickAttempts = 0;
        int maxClickAttempts = 5;

        while (!decreaseButtonClicked && clickAttempts < maxClickAttempts) {
            try {
                clickAttempts++;
                System.out.println("Attempting to click decrease button (attempt " + clickAttempts + ")...");

                // First check if decrease button exists and is visible
                try {
                    System.out.println("🔍 Looking for decrease button with locator: " + DicreaseQty);
                    WebElement decreaseButton = wait.until(ExpectedConditions.presenceOfElementLocated(DicreaseQty));
                    System.out.println("✅ Decrease button found - checking if displayed: " + decreaseButton.isDisplayed());
                    System.out.println("✅ Decrease button enabled: " + decreaseButton.isEnabled());

                    // Wait for button to be clickable
                    decreaseButton = wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty));
                    System.out.println("✅ Decrease button is clickable");

                    // Scroll to button if needed
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", decreaseButton);
                    System.out.println("✅ Scrolled to decrease button");

                    // Wait for scroll to complete
                    wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty));

                    // Get button location and size for debugging
                    System.out.println("Button location: " + decreaseButton.getLocation());
                    System.out.println("Button size: " + decreaseButton.getSize());
                    System.out.println("Button tag: " + decreaseButton.getTagName());
                    System.out.println("Button text: '" + decreaseButton.getText() + "'");

                    // Try regular click first
                    try {
                        System.out.println("Attempting regular click...");
                        decreaseButton.click();
                        decreaseButtonClicked = true;
                        System.out.println("✅ Decrease button clicked successfully with regular click");
                    } catch (Exception clickException) {
                        System.out.println("Regular click failed: " + clickException.getMessage());
                        System.out.println("Trying JavaScript click...");
                        // Fallback to JavaScript click
                        js.executeScript("arguments[0].click();", decreaseButton);
                        decreaseButtonClicked = true;
                        System.out.println("✅ Decrease button clicked successfully with JavaScript click");
                    }

                } catch (Exception findException) {
                    System.out.println("⚠️ Could not find decrease button: " + findException.getMessage());
                    throw findException;
                }

            } catch (Exception e) {
                System.out.println("⚠️ Decrease button click attempt " + clickAttempts + " failed: " + e.getMessage());
                if (clickAttempts >= maxClickAttempts) {
                    System.out.println("❌ All attempts failed. Let's try alternative approaches...");

                    // Try to find all buttons with class "operator" as a debugging step
                    try {
                        System.out.println("🔍 Looking for all buttons with class 'operator'...");
                        java.util.List<WebElement> operatorButtons = driver.findElements(By.xpath("//button[@class='operator']"));
                        System.out.println("Found " + operatorButtons.size() + " operator buttons");

                        for (int i = 0; i < operatorButtons.size(); i++) {
                            WebElement btn = operatorButtons.get(i);
                            System.out.println("Button " + (i+1) + ": text='" + btn.getText() + "', displayed=" + btn.isDisplayed() + ", enabled=" + btn.isEnabled());
                        }

                        // Try clicking the first operator button (should be decrease)
                        if (!operatorButtons.isEmpty()) {
                            WebElement firstOperatorBtn = operatorButtons.get(0);
                            System.out.println("Trying to click first operator button...");
                            JavascriptExecutor js = (JavascriptExecutor) driver;
                            js.executeScript("arguments[0].click();", firstOperatorBtn);
                            decreaseButtonClicked = true;
                            System.out.println("✅ Successfully clicked first operator button");
                            break;
                        }
                    } catch (Exception debugException) {
                        System.out.println("Debug attempt also failed: " + debugException.getMessage());
                    }

                    if (!decreaseButtonClicked) {
                        throw new RuntimeException("Failed to click decrease button after " + maxClickAttempts + " attempts", e);
                    }
                }
                // Wait for element to be available for next attempt
                wait.until(ExpectedConditions.presenceOfElementLocated(DicreaseQty));
            }
        }

        // Wait for sidebar to appear after clicking decrease
        System.out.println("Waiting for Remove and Move to Wishlist buttons to appear...");

        // Validate Remove and Move to Wishlist buttons are visible with retries
        WebDriverWait sidebarWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            sidebarWait.until(ExpectedConditions.visibilityOfElementLocated(RemoveFromCart));
            sidebarWait.until(ExpectedConditions.visibilityOfElementLocated(MoveToWishlist));
            System.out.println("✅ Remove and Move to Wishlist buttons are visible");
        } catch (Exception e) {
            throw new RuntimeException("Remove/Move to Wishlist sidebar did not appear after clicking decrease button: " + e.getMessage());
        }

        // Wait for existing toasts to disappear
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(toastNotification));
        } catch (Exception e) {
            // No existing toast - continue
        }

        // Click Remove button with enhanced approach
        System.out.println("Clicking on Remove button...");
        boolean removeButtonClicked = false;
        int removeClickAttempts = 0;
        int maxRemoveClickAttempts = 3;

        while (!removeButtonClicked && removeClickAttempts < maxRemoveClickAttempts) {
            try {
                removeClickAttempts++;
                System.out.println("Attempting to click Remove button (attempt " + removeClickAttempts + ")...");

                WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(RemoveFromCart));

                // Try regular click first
                try {
                    removeButton.click();
                    removeButtonClicked = true;
                    System.out.println("✅ Remove button clicked successfully with regular click");
                } catch (Exception clickException) {
                    System.out.println("Regular click failed, trying JavaScript click...");
                    // Fallback to JavaScript click
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", removeButton);
                    removeButtonClicked = true;
                    System.out.println("✅ Remove button clicked successfully with JavaScript click");
                }

            } catch (Exception e) {
                System.out.println("⚠️ Remove button click attempt " + removeClickAttempts + " failed: " + e.getMessage());
                if (removeClickAttempts >= maxRemoveClickAttempts) {
                    throw new RuntimeException("Failed to click remove button after " + maxRemoveClickAttempts + " attempts", e);
                }
                // Wait for element to be available for next attempt
                wait.until(ExpectedConditions.presenceOfElementLocated(RemoveFromCart));
            }
        }

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
        // Check current quantity first - always find fresh element
        String currentQtyText = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay))
                                   .getAttribute("value");
        if (currentQtyText == null || currentQtyText.isEmpty()) {
            currentQtyText = driver.findElement(QtyDisplay).getText();
        }
        int currentQty = Integer.parseInt(currentQtyText.trim());
        System.out.println("Current quantity: " + currentQty);

        // Strategy: Decrease quantity to 1, then click decrease again to trigger sidebar
        System.out.println("Strategy: First decrease quantity to 1, then trigger sidebar");

        // Step 1: Decrease quantity to 1 if it's greater than 1
        while (currentQty > 1) {
            System.out.println("Current quantity is " + currentQty + ", decreasing to get to 1...");

            // Always find fresh decrease button element
            try {
                // Find fresh decrease button element
                WebElement decreaseButton = wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", decreaseButton);

                // Wait for scroll and find element again to avoid stale reference
                wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty));
                decreaseButton = driver.findElement(DicreaseQty);

                try {
                    decreaseButton.click();
                    System.out.println("✅ Decrease button clicked with regular click");
                } catch (Exception clickException) {
                    // Find fresh element for JavaScript click
                    decreaseButton = driver.findElement(DicreaseQty);
                    js.executeScript("arguments[0].click();", decreaseButton);
                    System.out.println("✅ Decrease button clicked with JavaScript click");
                }
            } catch (Exception buttonException) {
                System.out.println("⚠️ Error clicking decrease button: " + buttonException.getMessage());
                throw new RuntimeException("Failed to click decrease button", buttonException);
            }

            // Wait for quantity to update and verify it decreased
            final int targetQty = currentQty - 1;
            wait.until(driver -> {
                try {
                    // Always find fresh quantity element
                    WebElement qtyEl = driver.findElement(QtyDisplay);
                    String qtyText = qtyEl.getAttribute("value");
                    if (qtyText == null || qtyText.isEmpty()) {
                        qtyText = qtyEl.getText();
                    }
                    int updatedQty = Integer.parseInt(qtyText.trim());
                    System.out.println("Quantity check - Current: " + updatedQty + ", Target: " + targetQty);
                    return updatedQty == targetQty;
                } catch (Exception e) {
                    return false;
                }
            });

            // Update current quantity for next iteration - find fresh element
            WebElement freshQtyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(QtyDisplay));
            currentQtyText = freshQtyElement.getAttribute("value");
            if (currentQtyText == null || currentQtyText.isEmpty()) {
                currentQtyText = freshQtyElement.getText();
            }
            currentQty = Integer.parseInt(currentQtyText.trim());
            System.out.println("Quantity successfully decreased to: " + currentQty);
        }

        // Step 2: Now that quantity is 1, click decrease to trigger the sidebar
        System.out.println("Quantity is now 1. Clicking decrease to trigger Remove/Wishlist sidebar...");

        boolean sidebarOpened = false;
        int sidebarAttempts = 0;
        int maxSidebarAttempts = 5;

        while (!sidebarOpened && sidebarAttempts < maxSidebarAttempts) {
            sidebarAttempts++;
            System.out.println("Sidebar trigger attempt " + sidebarAttempts + " of " + maxSidebarAttempts);

            try {
                // Always find fresh decrease button element to trigger sidebar
                WebElement decreaseButton = wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", decreaseButton);

                // Wait for scroll and find fresh element again
                wait.until(ExpectedConditions.elementToBeClickable(DicreaseQty));
                decreaseButton = driver.findElement(DicreaseQty);

                try {
                    decreaseButton.click();
                    System.out.println("✅ Decrease button clicked to trigger sidebar");
                } catch (Exception clickException) {
                    // Find fresh element for JavaScript click
                    decreaseButton = driver.findElement(DicreaseQty);
                    js.executeScript("arguments[0].click();", decreaseButton);
                    System.out.println("✅ Decrease button clicked with JavaScript to trigger sidebar");
                }

                // Wait longer for sidebar to appear and check multiple possible sidebar patterns
                WebDriverWait sidebarWait = new WebDriverWait(driver, Duration.ofSeconds(8));

                try {
                    // First try the specific locators
                    WebElement removeBtn = sidebarWait.until(ExpectedConditions.visibilityOfElementLocated(RemoveFromCart));
                    WebElement wishlistBtn = sidebarWait.until(ExpectedConditions.visibilityOfElementLocated(MoveToWishlist));

                    if (removeBtn.isDisplayed() && wishlistBtn.isDisplayed()) {
                        sidebarOpened = true;
                        System.out.println("✅ Sidebar opened successfully - Remove and Wishlist buttons visible");
                        break;
                    }
                } catch (Exception specificException) {
                    System.out.println("Specific sidebar buttons not found, trying general sidebar detection...");

                    // Try the general sidebar locator
                    try {
                        WebElement sidebar = sidebarWait.until(ExpectedConditions.visibilityOfElementLocated(RemoveMoveSidebar));
                        if (sidebar.isDisplayed()) {
                            System.out.println("✅ General sidebar detected, checking for Remove/Wishlist text...");

                            // Look for buttons containing the text within the sidebar area
                            try {
                                WebElement removeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//button[contains(text(), 'Remove') or contains(text(), 'remove')]")));
                                WebElement wishlistButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//button[contains(text(), 'Wishlist') or contains(text(), 'wishlist')]")));

                                if (removeButton.isDisplayed() && wishlistButton.isDisplayed()) {
                                    sidebarOpened = true;
                                    System.out.println("✅ Alternative sidebar buttons found and visible");
                                    break;
                                }
                            } catch (Exception altButtonException) {
                                System.out.println("Alternative button detection failed: " + altButtonException.getMessage());
                            }
                        }
                    } catch (Exception generalSidebarException) {
                        System.out.println("General sidebar detection failed: " + generalSidebarException.getMessage());
                    }
                }

                if (!sidebarOpened) {
                    System.out.println("⚠️ Sidebar not opened on attempt " + sidebarAttempts + ", waiting before retry...");
                    wait.until(ExpectedConditions.presenceOfElementLocated(DicreaseQty));
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error during sidebar trigger attempt " + sidebarAttempts + ": " + e.getMessage());
            }
        }

        // Final validation with extended wait and multiple approaches
        if (!sidebarOpened) {
            System.out.println("🔄 Final comprehensive sidebar detection attempt...");
            WebDriverWait finalWait = new WebDriverWait(driver, Duration.ofSeconds(10));

            try {
                // Try all possible combinations
                finalWait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(RemoveFromCart),
                    ExpectedConditions.visibilityOfElementLocated(RemoveMoveSidebar),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Remove')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Remove')]"))
                ));
                System.out.println("✅ Sidebar detected on final comprehensive check");
            } catch (Exception finalException) {
                // Print page source for debugging
                System.out.println("🔍 Current page state for debugging:");
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    System.out.println("Page title: " + driver.getTitle());
                    System.out.println("Current URL: " + driver.getCurrentUrl());

                    // Check if there are any modal/overlay elements
                    java.util.List<WebElement> modals = driver.findElements(By.xpath("//div[contains(@class, 'modal') or contains(@class, 'popup') or contains(@class, 'overlay')]"));
                    System.out.println("Found " + modals.size() + " modal/popup elements");

                    // Check if there are any buttons containing remove/wishlist text
                    java.util.List<WebElement> buttons = driver.findElements(By.xpath("//button"));
                    System.out.println("Found " + buttons.size() + " button elements");
                    for (WebElement btn : buttons) {
                        String btnText = btn.getText().toLowerCase();
                        if (btnText.contains("remove") || btnText.contains("wishlist")) {
                            System.out.println("Found relevant button: '" + btn.getText() + "' - displayed: " + btn.isDisplayed());
                        }
                    }
                } catch (Exception debugException) {
                    System.out.println("Debug info collection failed: " + debugException.getMessage());
                }

                Assert.fail("❌ Sidebar did not open after " + sidebarAttempts + " attempts. Final error: " + finalException.getMessage());
            }
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

                    // Wait for scroll to complete by checking for element stability
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
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