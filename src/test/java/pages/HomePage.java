package pages;

import locators.Locators;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public void assertHomePageLoaded() {
        String title = driver.getTitle();
        boolean isHomePageLoaded = title.contains("Tira") || title.contains("Beauty");
        Assert.assertTrue(isHomePageLoaded, "❌ Home Page did not load correctly");
        System.out.println("✅ Home page loaded successfully.");
    }

    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.HomePage.logoLink));

        Assert.assertTrue(driver.findElement(Locators.HomePage.logoLink).isDisplayed(), "❌ Logo is not visible after page load");
        Assert.assertTrue(driver.getCurrentUrl().contains("tirabeauty"), "❌ URL does not contain expected domain after page load");
        Assert.assertFalse(driver.getTitle().isEmpty(), "❌ Page title is empty after page load");

        String readyState = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState;");
        Assert.assertEquals(readyState, "complete", "❌ Page is not fully loaded (readyState: " + readyState + ")");

        System.out.println("✅ Homepage loaded completely");
    }

    // Navigation methods
    public void clickTiraRedNavigation() {
        driver.findElement(Locators.HomePage.tiraRedNavLink).click();
        wait.until(ExpectedConditions.urlContains("tira-red"));
    }

    public void clickOffersNavigation() {
        driver.findElement(Locators.HomePage.offersNavLink).click();
        wait.until(ExpectedConditions.urlContains("offers"));
    }

    public void clickTopShelfNavigation() {
        driver.findElement(Locators.HomePage.topShelfNavLink).click();
        wait.until(ExpectedConditions.urlContains("top-shelf"));
    }

    public void clickForYouNavigation() {
        driver.findElement(Locators.HomePage.forYouNavLink).click();
        wait.until(ExpectedConditions.urlContains("for-you"));
    }

    public void clickWhatsNewNavigation() {
        driver.findElement(Locators.HomePage.whatsNewNavLink).click();
        wait.until(ExpectedConditions.urlContains("whats-new"));
    }

    public void clickMakeupNavigation() {
        driver.findElement(Locators.HomePage.makeupNavLink).click();
        wait.until(ExpectedConditions.urlContains("makeup"));
    }

    public void clickSkinNavigation() {
        driver.findElement(Locators.HomePage.skinNavLink).click();
        wait.until(ExpectedConditions.urlContains("skin"));
    }

    public void clickHairNavigation() {
        driver.findElement(Locators.HomePage.hairNavLink).click();
        wait.until(ExpectedConditions.urlContains("hair"));
    }

    public void clickFragranceNavigation() {
        driver.findElement(Locators.HomePage.fragranceNavLink).click();
        wait.until(ExpectedConditions.urlContains("fragrance"));
    }

    public void clickMenNavigation() {
        driver.findElement(Locators.HomePage.menNavLink).click();
        wait.until(ExpectedConditions.urlContains("men"));
    }

    public void clickBathBodyNavigation() {
        driver.findElement(Locators.HomePage.bathBodyNavLink).click();
        wait.until(ExpectedConditions.urlContains("bath-body"));
    }

    public void clickToolsAppliancesNavigation() {
        driver.findElement(Locators.HomePage.toolsAppliancesNavLink).click();
        wait.until(ExpectedConditions.urlContains("tools-and-appliances"));
    }

    public void clickMomBabyNavigation() {
        driver.findElement(Locators.HomePage.momBabyNavLink).click();
        wait.until(ExpectedConditions.urlContains("mom-and-baby"));
    }

    public void clickWellnessNavigation() {
        driver.findElement(Locators.HomePage.wellnessNavLink).click();
        wait.until(ExpectedConditions.urlContains("wellness"));
    }

    public void clickMinisNavigation() {
        driver.findElement(Locators.HomePage.minisNavLink).click();
        wait.until(ExpectedConditions.urlContains("minis"));
    }

    public void clickHomegrownNavigation() {
        driver.findElement(Locators.HomePage.homegrownNavLink).click();
        wait.until(ExpectedConditions.urlContains("homegrown"));
    }

    public void clickGiftsNavigation() {
        driver.findElement(Locators.HomePage.giftsNavLink).click();
        wait.until(ExpectedConditions.urlContains("gifting"));
    }

    // Header action methods
    public void clickLogo() {
        driver.findElement(Locators.HomePage.logoLink).click();
    }

    public void clickCartIcon() {
        driver.findElement(Locators.HomePage.cartIcon).click();
        wait.until(ExpectedConditions.urlContains("cart"));
    }

    public void clickProfileIconAndValidateLoginRedirect() {
        driver.findElement(Locators.HomePage.profileIcon).click();
        wait.until(ExpectedConditions.urlContains("login"));
    }

    public void validateCategoryNavigation() {
        // Validate that all main navigation categories are visible and clickable
        wait.until(ExpectedConditions.elementToBeClickable(Locators.HomePage.makeupNavLink));
        Assert.assertTrue(driver.findElement(Locators.HomePage.makeupNavLink).isDisplayed(), "❌ Makeup navigation link is not visible");
        Assert.assertTrue(driver.findElement(Locators.HomePage.skinNavLink).isDisplayed(), "❌ Skin navigation link is not visible");
        Assert.assertTrue(driver.findElement(Locators.HomePage.hairNavLink).isDisplayed(), "❌ Hair navigation link is not visible");
        System.out.println("✅ Category navigation validated successfully");
    }

    public void clickLoginIcon() {
        driver.findElement(Locators.LoginPage.loginIcon).click();
        wait.until(ExpectedConditions.urlContains("login"));
        System.out.println("✅ Login icon clicked and navigated to login page");
    }

    public void clickWishlistIcon() {
        driver.findElement(Locators.WishlistPage.clickOnWishlistIconFromHome).click();
        wait.until(ExpectedConditions.urlContains("wishlist"));
        System.out.println("✅ Wishlist icon clicked and navigated to wishlist page");
    }
}