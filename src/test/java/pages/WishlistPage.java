package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static locators.Locators.PlpPage.clickOnWishlistIconFromPlp;
import static locators.Locators.WishlistPage.*;

public class WishlistPage {

    WebDriver driver;
    WebDriverWait wait;

    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void noItemInWishlistpage()
    {

        driver.findElement(blankWishlistPage);

    }
    public void addToBagFromWishlist()
    {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(removeProductfromWishlist),
                    ExpectedConditions.visibilityOfElementLocated(shopNowClick)
            ));
            List<WebElement> productCards = driver.findElements(hoveredOnProduct);

            if (!productCards.isEmpty()) {
                System.out.println("🛒 Product is already in wishlist.");

                // Hover and move to bag
                WebElement productCard = wait.until(ExpectedConditions.visibilityOfElementLocated(hoveredOnProduct));
                Actions actions = new Actions(driver);
                actions.moveToElement(productCard).perform();
                System.out.println("✅ Hovered on product card");

                WebElement moveToBagButton = wait.until(ExpectedConditions.elementToBeClickable(moveToBag));
                moveToBagButton.click();

                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productMoveToBagFromWishlist));
                String actualText = element.getText();
                Assert.assertTrue(actualText.contains("Product has been added to cart"),
                        "❌ Text mismatch: Expected Product has been added to cart but got: " + actualText);
                System.out.println("✅ Verified text: " + actualText);


            } else {
                System.out.println("🛍 No product in wishlist. Clicking on Shop Now...");

                // Step 2: Click "Shop Now"
                WebElement shopNowBtn = wait.until(ExpectedConditions.elementToBeClickable(shopNowClick));
                shopNowBtn.click();

                // Step 3: Add product to wishlist
                WebElement productToAdd = wait.until(ExpectedConditions.elementToBeClickable(clickOnWishlistIconFromPlp));
                productToAdd.click();
                System.out.println("✅ Product added to wishlist");

                // Step 4: Navigate to wishlist page again
                WebElement wishlistIcon = wait.until(ExpectedConditions.elementToBeClickable(clickOnWishlistIconFromHome));
                wishlistIcon.click();
                System.out.println("🔄 Returned to Wishlist page");

                // Step 5: Now hover and move to bag
                WebElement productCard = wait.until(ExpectedConditions.visibilityOfElementLocated(hoveredOnProduct));
                Actions actions = new Actions(driver);
                actions.moveToElement(productCard).perform();
                System.out.println("✅ Hovered on product card");

                WebElement moveToBagButton = wait.until(ExpectedConditions.elementToBeClickable(moveToBag));
                moveToBagButton.click();
                System.out.println("✅ Clicked on 'Move to Bag'");
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productMoveToBagFromWishlist));
                String actualText = element.getText();
                Assert.assertTrue(actualText.contains("Product has been added to cart"),
                        "❌ Text mismatch: Expected 'Product has been added to cart' but got: " + actualText);
                System.out.println("✅ Verified text: " + actualText);
            }

        } catch (Exception e) {
            System.out.println("❌ Error in Move to Bag logic: " + e.getMessage());
            Assert.fail("Failed during conditional Move to Bag flow.");
        }



    }
    public void removeProductFromWishlist()
    {
        WebElement clickOnCancelIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(removeProductfromWishlist));
        clickOnCancelIcon.click();
    }
    public void shopNowClickOnWishlist()
    {
        WebElement clickShopNow = wait.until(ExpectedConditions.visibilityOfElementLocated(shopNowClick));
        clickShopNow.click();
    }
    public void valiadationOnNoItemInWishlistpage()
    {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(blankWishlistPage));
        String actualText = element.getText();
        Assert.assertTrue(actualText.contains("Your wishlist is empty!"), "❌ Text mismatch: Expected 'Your wishlist is empty!'");
        System.out.println("✅ Verified text: " + actualText);

    }
    public void validationAftershopNowCLick()
    {

        String expectedUrlPart = "/products";
        boolean urlReached = wait.until(ExpectedConditions.urlContains(expectedUrlPart));
        Assert.assertTrue(urlReached, "❌ URL does not contain expected path: " + expectedUrlPart);
        System.out.println("✅ URL contains expected path: " + expectedUrlPart);
    }
    public void validationProductRemoveFromWishlist()
    {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productRemoveToastMsg));
        String actualText = element.getText();
        Assert.assertTrue(actualText.contains("Product has been removed from Wishlist."),
                "❌ Text mismatch: Expected 'Product has been added to Wishlist.' but got: " + actualText);
        System.out.println("✅ Verified text: " + actualText);
    }
    public void clearWishlistIfNotEmpty() {
        try {
            // Wait until either remove buttons are present or the empty wishlist message is shown
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(removeProductfromWishlist),
                    ExpectedConditions.visibilityOfElementLocated(shopNowClick)
            ));

            List<WebElement> removeButtons = driver.findElements(removeProductfromWishlist);

            if (!removeButtons.isEmpty()) {
                System.out.println("🗑 Found " + removeButtons.size() + " item(s) in wishlist. Removing...");

                for (WebElement removeBtn : removeButtons) {
                    wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
                    // Wait until the item is removed from the DOM
                    wait.until(ExpectedConditions.stalenessOf(removeBtn));
                }

                // Final check: ensure all items are removed
                boolean isEmpty = wait.until(ExpectedConditions.invisibilityOfElementLocated(removeProductfromWishlist));
                Assert.assertTrue(isEmpty, "❌ Wishlist still has products after removal.");

            } else {
                System.out.println("🛍 Wishlist is already empty. Clicking on Shop Now...");
                WebElement clickShopNow = wait.until(ExpectedConditions.visibilityOfElementLocated(shopNowClick));
                clickShopNow.click();
                String expectedUrlPart = "/products";
                boolean urlReached = wait.until(ExpectedConditions.urlContains(expectedUrlPart));
                Assert.assertTrue(urlReached, "❌ URL does not contain expected path: " + expectedUrlPart);
                System.out.println("✅ URL contains expected path: " + expectedUrlPart);
            }

        } catch (Exception e) {
            System.out.println("❌ Error clearing wishlist: " + e.getMessage());
            Assert.fail("Failed during wishlist validation or cleanup.");
        }
    }

    }
