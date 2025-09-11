package pages;

import locators.Locators;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static locators.Locators.CategoryPage.menuMakeup;
import static locators.Locators.CategoryPage.subCategoryNail;

public class CategoryPage {
    WebDriver driver;
    WebDriverWait wait;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void navigateToLipstickCategory() {
        System.out.println("🧭 Trying to navigate to makeup category...");

        try {
            // Wait and find makeup menu
            WebElement makeupMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(menuMakeup));
            
            // Scroll element into view if needed
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", makeupMenu);
            
            Actions actions = new Actions(driver);
            
            // Retry hover mechanism
            for (int attempt = 0; attempt < 3; attempt++) {
                try {
                    // Fresh element reference each time
                    makeupMenu = wait.until(ExpectedConditions.elementToBeClickable(menuMakeup));
                    
                    // Hover to reveal subcategories
                    actions.moveToElement(makeupMenu).perform();
                    System.out.println("🖱️ Hovered on makeup menu (attempt " + (attempt + 1) + ")");
                    
                    // Wait for subcategory to become visible after hover
                    WebElement subCategory = wait.until(ExpectedConditions.elementToBeClickable(subCategoryNail));
                    subCategory.click();
                    System.out.println("✅ Successfully clicked on subcategory under makeup");
                    return;
                    
                } catch (Exception e) {
                    System.out.println("⚠️ Hover attempt " + (attempt + 1) + " failed: " + e.getMessage());
                    if (attempt == 2) {
                        // Final attempt - try direct click on main menu
                        System.out.println("🔄 Trying direct click on makeup menu...");
                        makeupMenu = wait.until(ExpectedConditions.elementToBeClickable(menuMakeup));
                        makeupMenu.click();
                        
                        WebElement subCategory = wait.until(ExpectedConditions.elementToBeClickable(subCategoryNail));
                        subCategory.click();
                        System.out.println("✅ Clicked on subcategory via direct menu click");
                    } else {
                        wait.until(ExpectedConditions.elementToBeClickable(menuMakeup));
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Failed to navigate to makeup category: " + e.getMessage());
            throw e;
        }
    }
}
