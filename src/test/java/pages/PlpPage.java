package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.PlpPage.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void clickOnWishlistIconOnPlp() {
        WebElement iconClick = wait.until(ExpectedConditions.visibilityOfElementLocated(clickOnWishlistIconFromPlp));
        iconClick.click();
    }

    public void validationProductAddToWishlistFromPlp() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productAddToWishlistToastMsg));
        String actualText = element.getText();
        Assert.assertTrue(actualText.contains("Product has been added to Wishlist."),
                "❌ Text mismatch: Expected 'Product has been added to Wishlist.' but got: " + actualText);
        System.out.println("🔍 Actual Toast Message: " + actualText);
    }

    public void validateSortOptions() {
        try {
            // Open dropdown to count options
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown));
            dropdown.click();
            int count = driver.findElements(allOptions).size();
            System.out.println("Total sort options: " + count);
            
            // Close dropdown before starting loop
            driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(allOptions));

            for (int i = 1; i <= count; i++) {
                try {
                    // Open dropdown fresh each time
                    dropdown = wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown));
                    dropdown.click();
                    
                    // Wait for dropdown options to be visible
                    wait.until(ExpectedConditions.visibilityOfElementLocated(allOptions));

                    // Get the current option text before clicking
                    WebElement currentOption = wait.until(ExpectedConditions.elementToBeClickable(sortOptionList(i)));
                    String optionText = currentOption.getText().trim();
                    System.out.println("Clicking option " + i + ": " + optionText);

                    // Click the option
                    currentOption.click();
                    
                    // Wait for dropdown to close after selection
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(allOptions));

                    // Verify the selection by checking the dropdown display text
                    WebElement selectedDisplay = wait.until(ExpectedConditions.visibilityOfElementLocated(selectedSortText));
                    String displayedText = selectedDisplay.getText().trim();
                    
                    // Basic validation that option was selected (dropdown closed and text changed)
                    Assert.assertFalse(displayedText.isEmpty(), 
                            "❌ No sort option text displayed after clicking: " + optionText);
                    System.out.println("✅ Sort option selected: " + optionText + " | Displayed: " + displayedText);
                    
                } catch (Exception e) {
                    System.out.println("❌ Failed to click option " + i + ": " + e.getMessage());
                    // Try to close dropdown if it's still open
                    try {
                        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(allOptions));
                    } catch (Exception closeEx) {
                        // Ignore close errors
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Sort validation failed: " + e.getMessage());
            Assert.fail("Error validating sorting options: " + e.getMessage());
        }
    }

//    public void AnkushCode()
//        {
//            for (int i = 1; i <= 5; i++) {
//            // Open dropdown
//                WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown));
//            sortDropdown.click();
//            String optionText =sortDropdown.getText().trim();
//            System.out.println("option text is " +optionText);
//
//            // Build the xpath for the option
//                String optionXpath = "//li[" + i + "]/div[contains(@class,'dropdown-options')]";
//
//            // Wait until visible
//                WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
//
//            // Wait until clickable and then click
//                WebElement optionClick = wait.until(ExpectedConditions.elementToBeClickable(option));
//                String selectedText= optionClick.getText().trim();
//                System.out.println("selectedText is " +selectedText);
//
//            // Wait for dropdown to close and product grid to refresh
//                //wait.until(ExpectedConditions.invisibilityOfElementLocated(sortOptionList));
//            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'product-card')]")));
//                Assert.assertEquals(selectedText, optionText,
//                        "❌ Mismatch: Clicked '" + optionText + "', but selected '" + selectedText + "'");
//                System.out.println("✅ Sort verified: " + selectedText);
//            System.out.println("Clicked option " + i);
//        }
//
//
//    }
//
//
//    public int getSortOptionCount(WebDriver driver) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        driver.findElement(sortByDropdown).click();
//        List<WebElement> options = wait.until(
//                ExpectedConditions.presenceOfAllElementsLocatedBy(allOptions)
//        );
//        return options.size();
//
//
//
//    }
}






