package pages;

import locators.Locators;
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
        System.out.println("🧭 Trying to click on makeup category...");

        // Wait and find makeup menu
        WebElement makeupMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(menuMakeup));

        // Hover to reveal subcategories
        Actions actions = new Actions(driver);
        actions.moveToElement(makeupMenu).perform();
        System.out.println("🖱️ Hovered on makeup menu");

        // Ensure it's clickable, then click
        wait.until(ExpectedConditions.elementToBeClickable(makeupMenu)).click();

        // Wait for and click on subcategory (like Nail)
        WebElement subCategory = wait.until(ExpectedConditions.elementToBeClickable(subCategoryNail));
        subCategory.click();
        System.out.println("✅ Clicked on subcategory under makeup");
    }
}
