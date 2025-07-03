package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PlpPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By sortByDropdown = By.xpath("//span[text()='Relevance']");
    By priceHighToLow = By.xpath("//span[text()='Price High to Low']");
    By clickOnProduct = By.xpath("//div[1]/a/div/div[1][@class='product-card']");

    public PlpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void sortBy() {
        try {
            // Step 1: Click sort dropdown
            wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown)).click();
            System.out.println("✅ Clicked on Relevance Sort by");

            // Step 2: Wait for "Price High to Low" option to be visible and click
            wait.until(ExpectedConditions.visibilityOfElementLocated(priceHighToLow));
            WebElement sortOption = wait.until(ExpectedConditions.elementToBeClickable(priceHighToLow));
            sortOption.click();
            System.out.println("✅ Clicked on Price High to Low");
        } catch (Exception e) {
            System.out.println("❌ Failed to sort products: " + e.getMessage());
        }
    }

    public void clickOnProduct() {
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
}
