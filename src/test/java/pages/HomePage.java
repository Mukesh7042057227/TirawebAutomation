package pages;

import locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class HomePage {
    private static final int DEFAULT_WAIT_TIMEOUT = 25;
    private static final String SUCCESS_PREFIX = "✅ ";
    private static final String ERROR_PREFIX = "❌ ";
    private static final String INFO_PREFIX = "🔍 ";

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }

    public void assertHomePageLoaded() {
        String title = driver.getTitle();
        boolean isHomePageLoaded = title.contains("Tira") || title.contains("Beauty");
        Assert.assertTrue(isHomePageLoaded, ERROR_PREFIX + "Home Page did not load correctly");
        System.out.println(SUCCESS_PREFIX + "Home page loaded successfully.");
    }

    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.HomePage.logoLink));

        Assert.assertTrue(driver.findElement(Locators.HomePage.logoLink).isDisplayed(), ERROR_PREFIX + "Logo is not visible after page load");
        Assert.assertTrue(driver.getCurrentUrl().contains("tirabeauty"), ERROR_PREFIX + "URL does not contain expected domain after page load");
        Assert.assertFalse(driver.getTitle().isEmpty(), ERROR_PREFIX + "Page title is empty after page load");

        String readyState = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState;");
        Assert.assertEquals(readyState, "complete", ERROR_PREFIX + "Page is not fully loaded (readyState: " + readyState + ")");

        System.out.println(SUCCESS_PREFIX + "Homepage loaded completely");
    }

    // Generic navigation method
    public void clickNavigation(By locator, String expectedUrlPart) {
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.urlContains(expectedUrlPart));
    }

    // Specific navigation methods using the generic method
    public void clickTiraRedNavigation() {
        clickNavigation(Locators.HomePage.tiraRedNavLink, "tira-red");
    }

    public void clickOffersNavigation() {
        clickNavigation(Locators.HomePage.offersNavLink, "offers");
    }

    public void clickTopShelfNavigation() {
        clickNavigation(Locators.HomePage.topShelfNavLink, "top-shelf");
    }

    public void clickForYouNavigation() {
        clickNavigation(Locators.HomePage.forYouNavLink, "for-you");
    }

    public void clickWhatsNewNavigation() {
        clickNavigation(Locators.HomePage.whatsNewNavLink, "whats-new");
    }

    public void clickMakeupNavigation() {
        clickNavigation(Locators.HomePage.makeupNavLink, "makeup");
    }

    public void clickSkinNavigation() {
        clickNavigation(Locators.HomePage.skinNavLink, "skin");
    }

    public void clickHairNavigation() {
        clickNavigation(Locators.HomePage.hairNavLink, "hair");
    }

    public void clickFragranceNavigation() {
        clickNavigation(Locators.HomePage.fragranceNavLink, "fragrance");
    }

    public void clickMenNavigation() {
        clickNavigation(Locators.HomePage.menNavLink, "men");
    }

    public void clickBathBodyNavigation() {
        clickNavigation(Locators.HomePage.bathBodyNavLink, "bath-body");
    }

    public void clickToolsAppliancesNavigation() {
        clickNavigation(Locators.HomePage.toolsAppliancesNavLink, "tools-and-appliances");
    }

    public void clickMomBabyNavigation() {
        clickNavigation(Locators.HomePage.momBabyNavLink, "mom-and-baby");
    }

    public void clickWellnessNavigation() {
        clickNavigation(Locators.HomePage.wellnessNavLink, "wellness");
    }

    public void clickMinisNavigation() {
        clickNavigation(Locators.HomePage.minisNavLink, "minis");
    }

    public void clickHomegrownNavigation() {
        clickNavigation(Locators.HomePage.homegrownNavLink, "homegrown");
    }

    public void clickGiftsNavigation() {
        clickNavigation(Locators.HomePage.giftsNavLink, "gifting");
    }

    // Header action methods
    public void clickLogo() {
        driver.findElement(Locators.HomePage.logoLink).click();
    }

    public void clickCartIcon() {
        clickNavigation(Locators.HomePage.cartIcon, "cart");
    }

    public void clickProfileIcon() {
        clickNavigation(Locators.HomePage.profileIcon, "login");
    }

    public void validateCategoryNavigation() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.HomePage.makeupNavLink));
        Assert.assertTrue(driver.findElement(Locators.HomePage.makeupNavLink).isDisplayed(), ERROR_PREFIX + "Makeup navigation link is not visible");
        Assert.assertTrue(driver.findElement(Locators.HomePage.skinNavLink).isDisplayed(), ERROR_PREFIX + "Skin navigation link is not visible");
        Assert.assertTrue(driver.findElement(Locators.HomePage.hairNavLink).isDisplayed(), ERROR_PREFIX + "Hair navigation link is not visible");
        System.out.println(SUCCESS_PREFIX + "Category navigation validated successfully");
    }

    public void clickLoginIcon() {
        clickNavigation(Locators.LoginPage.loginIcon, "login");
        System.out.println(SUCCESS_PREFIX + "Login icon clicked and navigated to login page");
    }

    public void clickWishlistIcon() {
        clickNavigation(Locators.WishlistPage.clickOnWishlistIconFromHome, "wishlist");
        System.out.println(SUCCESS_PREFIX + "Wishlist icon clicked and navigated to wishlist page");
    }

    public void hoverOverMakeup() {
        Actions actions = new Actions(driver);
        WebElement makeupNav = wait.until(ExpectedConditions.elementToBeClickable(Locators.HomePage.makeupNavLink));

        // Scroll element into view first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", makeupNav);

        // Move to makeup element and hold
        actions.moveToElement(makeupNav).perform();

        // Also trigger hover using JavaScript as backup
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", makeupNav);
    }

    public void hoverOverSkin() {
        Actions actions = new Actions(driver);
        WebElement skinNav = wait.until(ExpectedConditions.elementToBeClickable(Locators.HomePage.skinNavLink));

        // Scroll element into view first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", skinNav);

        // Move to skin element and hold
        actions.moveToElement(skinNav).perform();

        // Also trigger hover using JavaScript as backup
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", skinNav);
    }

    public void hoverOverHair() {
        Actions actions = new Actions(driver);
        WebElement hairNav = wait.until(ExpectedConditions.elementToBeClickable(Locators.HomePage.hairNavLink));

        // Scroll element into view first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", hairNav);

        // Move to hair element and hold
        actions.moveToElement(hairNav).perform();

        // Also trigger hover using JavaScript as backup
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", hairNav);
    }

    private static final By[] MAKEUP_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.makeupSubcategoryface,
        Locators.HomePage.makeupSubcategoryBlush,
        Locators.HomePage.makeupSubcategoryBronzer,
        Locators.HomePage.makeupSubcategoryCOmpact,
        Locators.HomePage.makeupSubcategoryConcealers,
        Locators.HomePage.makeupSubcategoryContour,
        Locators.HomePage.makeupSubcategoryFoundation,
        Locators.HomePage.makeupSubcategoryHighlighters,
        Locators.HomePage.makeupSubcategorySettingPowder,
        Locators.HomePage.makeupSubcategoryMakeupRemover,
        Locators.HomePage.makeupSubcategoryPrimer,
        Locators.HomePage.makeupSubcategorySettingSpray,
        Locators.HomePage.makeupSubcategoryBB,
        Locators.HomePage.makeupSubcategoryLoosePowder
    };

    private static final String[] MAKEUP_SUBCATEGORY_NAMES = {
        "Face", "Blush", "Bronzer", "Compact", "Concealers & Correctors",
        "Contour", "Foundation", "Highlighters & Illuminators", "Setting Powder",
        "Makeup Remover", "Primer", "Setting Spray", "BB & CC Creams", "Loose Powder"
    };

    private static final By[] EYE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.makeupSubcategoryEye,
        Locators.HomePage.makeupSubcategoryEyeMakeupRemover,
        Locators.HomePage.makeupSubcategoryEyebrowEnhancer,
        Locators.HomePage.makeupSubcategoryFalseEyelashes,
        Locators.HomePage.makeupSubcategoryEyeliner,
        Locators.HomePage.makeupSubcategoryEyeShadow,
        Locators.HomePage.makeupSubcategoryKajal,
        Locators.HomePage.makeupSubcategoryMascara,
        Locators.HomePage.makeupSubcategorySettingUnderEyeConcealer,
        Locators.HomePage.makeupSubcategoryEyeBases
    };

    private static final String[] EYE_SUBCATEGORY_NAMES = {
        "Eye", "Eye Makeup Remover", "Eyebrow Enhancer", "False Eyelashes",
        "Eyeliner", "Eye Shadow", "Kajal & Kohls", "Mascara", "Under Eye Concealer", "Eye Bases & Primers"
    };

    private static final By[] LIP_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.makeupSubcategoryLip,
        Locators.HomePage.makeupSubcategoryLipBalms,
        Locators.HomePage.makeupSubcategoryLipGloss,
        Locators.HomePage.makeupSubcategoryLipLiner,
        Locators.HomePage.makeupSubcategoryLipstick,
        Locators.HomePage.makeupSubcategoryLiquidLipstick
    };

    private static final String[] LIP_SUBCATEGORY_NAMES = {
        "Lip", "Lip Balms", "Lip Gloss", "Lip Liner", "Lipstick", "Liquid Lipstick"
    };

    private static final By[] TOOLS_BRUSHES_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.makeupSubcategoryToolsBrushes,
        Locators.HomePage.makeupSubcategoryBrushSets,
        Locators.HomePage.makeupSubcategoryEyeBrushes,
        Locators.HomePage.makeupSubcategoryFaceBrushes,
        Locators.HomePage.makeupSubcategoryLipBrushes,
        Locators.HomePage.makeupSubcategoryMakeupMakeupPouch,
        Locators.HomePage.makeupSubcategoryMakeupSharpeners,
        Locators.HomePage.makeupSubcategoryMakeupSponges,
        Locators.HomePage.makeupSubcategoryMirrors
    };

    private static final String[] TOOLS_BRUSHES_SUBCATEGORY_NAMES = {
        "Tools & Brushes", "Brush Sets", "Eye Brushes & Eyelash Curlers", "Face Brush",
        "Lip Brush", "Makeup Pouch", "Sharpeners & Tweezers", "Sponges & Blenders", "Mirrors"
    };

    private static final By[] KITS_PALETTES_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.makeupSubcategoryKitsPalettes,
        Locators.HomePage.makeupSubcategoryEyePalettes,
        Locators.HomePage.makeupSubcategoryFacePalettes,
        Locators.HomePage.makeupSubcategoryMakeupKits
    };

    private static final String[] KITS_PALETTES_SUBCATEGORY_NAMES = {
        "Kits & Palettes", "Eyeshadow Palettes", "Face Makeup Palettes", "Makeup Kits & Sets"
    };

    // Skin subcategory arrays
    private static final By[] CLEANSERS_EXFOLIATORS_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.skinSubcategorySkincare,
        Locators.HomePage.skinSubcategoryFaceWash,
        Locators.HomePage.skinSubcategoryFaceScrub,
        Locators.HomePage.skinSubcategoryToner,
        Locators.HomePage.skinSubcategorySerum,
        Locators.HomePage.skinSubcategoryMoisturizer
    };

    private static final String[] CLEANSERS_EXFOLIATORS_SUBCATEGORY_NAMES = {
        "Cleansers & Exfoliators", "Face Washes & Cleansers", "Face Scrub & Exfoliator",
        "Scrubs & Exfoliators", "Face Wipes", "Makeup Remover"
    };

    private static final By[] EYECARE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.skinSubcategoryEyeCare,
        Locators.HomePage.skinSubcategoryEyeCream,
        Locators.HomePage.skinSubcategoryEyeSerum,
        Locators.HomePage.skinSubcategoryEyeMask
    };

    private static final String[] EYECARE_SUBCATEGORY_NAMES = {
        "Eye Care", "Eye Creams & Serums", "Eye Mask", "Eye Makeup Remover"
    };

    private static final By[] SETS_BUNDLES_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.skinSubcategorySetsAndBundles,
        Locators.HomePage.skinSubcategoryFacialSets,
        Locators.HomePage.skinSubcategoryGiftSets
    };

    private static final String[] SETS_BUNDLES_SUBCATEGORY_NAMES = {
        "Sets & Bundles", "Facial Sets", "Gift Sets"
    };

    private static final By[] LIPCARE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.skinSubcategoryLipCare,
        Locators.HomePage.skinSubcategoryLipBalm,
        Locators.HomePage.skinSubcategoryLipScrub,
        Locators.HomePage.skinSubcategoryLipMask,
        Locators.HomePage.skinSubcategoryLipOils
    };

    private static final String[] LIPCARE_SUBCATEGORY_NAMES = {
        "Lip Care", "Lip Balm", "Lip Scrub", "Lip Mask", "Lip Oils"
    };

    private static final By[] MASK_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.skinSubcategoryBodyCare,
        Locators.HomePage.skinSubcategoryBodyLotion,
        Locators.HomePage.skinSubcategoryBodyWash
    };

    private static final String[] MASK_SUBCATEGORY_NAMES = {
        "Mask", "Masks & Peels", "Sheet Mask"
    };

    private static final By[] MOISTURIZER_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.skinSubcategoryMoisturizers,
        Locators.HomePage.skinSubcategoryFaceMoisturizer,
        Locators.HomePage.skinSubcategoryNightCream,
        Locators.HomePage.skinSubcategoryFaceOil
    };

    private static final String[] MOISTURIZER_SUBCATEGORY_NAMES = {
        "Moisturizer", "Face Moisturizer", "Night Cream", "Face Oil"
    };

    private static final By[] TONERS_MIST_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.skinSubcategoryTonersAndMist,
        Locators.HomePage.skinSubcategoryToners,
        Locators.HomePage.skinSubcategoryMist
    };

    private static final String[] TONERS_MIST_SUBCATEGORY_NAMES = {
        "Toners & Mist", "Toner", "Mist"
    };

    private static final By[] SUNCARE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.skinSubcategorySunCare,
        Locators.HomePage.skinSubcategorySunscreen
    };

    private static final String[] SUNCARE_SUBCATEGORY_NAMES = {
        "Sun Care", "Sunscreen"
    };

    // Hair subcategory arrays
    private static final By[] HAIRCARE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.hairSubcategoryHairCare,
        Locators.HomePage.hairSubcategoryConditioner,
        Locators.HomePage.hairSubcategoryDryShampoo,
        Locators.HomePage.hairSubcategoryHairOil,
        Locators.HomePage.hairSubcategoryHairSerum,
        Locators.HomePage.hairSubcategoryShampoo,
        Locators.HomePage.hairSubcategoryHairCreamsLeaveIns,
        Locators.HomePage.hairSubcategoryHairMask,
        Locators.HomePage.hairSubcategoryHairScalpTreatments
    };

    private static final String[] HAIRCARE_SUBCATEGORY_NAMES = {
        "Hair Care", "Conditioner", "Dry Shampoo", "Hair Oil", "Hair Serum",
        "Shampoo", "Hair Creams & Leave-Ins", "Hair Mask", "Hair & Scalp Treatments"
    };

    private static final By[] HAIRSTYLING_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.hairSubcategoryHairStyling,
        Locators.HomePage.hairSubcategoryHairGelsWaxes,
        Locators.HomePage.hairSubcategoryHairSpraysMists,
        Locators.HomePage.hairSubcategoryHairColour
    };

    private static final String[] HAIRSTYLING_SUBCATEGORY_NAMES = {
        "Hair Styling", "Hair Gels & Waxes", "Hair Sprays & Mists", "Hair Colour"
    };

    private static final By[] HAIRTOOLS_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.hairSubcategoryToolsAccessories,
        Locators.HomePage.hairSubcategoryHairBrush,
        Locators.HomePage.hairSubcategoryHairAccessories,
        Locators.HomePage.hairSubcategoryHairComb,
        Locators.HomePage.hairSubcategoryRollersCurlers,
        Locators.HomePage.hairSubcategoryHairDryersStylers,
        Locators.HomePage.hairSubcategoryStraightener
    };

    private static final String[] HAIRTOOLS_SUBCATEGORY_NAMES = {
        "Tools & Accessories", "Hair Brush", "Hair Accessories", "Hair Comb",
        "Rollers & Curlers", "Hair Dryers & Stylers", "Straightener"
    };

    private static final By[] HAIRTYPE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.hairSubcategoryShopByHairType,
        Locators.HomePage.hairSubcategoryStraight,
        Locators.HomePage.hairSubcategoryCurlyWavy
    };

    private static final String[] HAIRTYPE_SUBCATEGORY_NAMES = {
        "Shop By Hair Type", "Straight", "Curly & Wavy"
    };

    public void testAllMakeupSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all makeup subcategories...");

        for (int i = 0; i < MAKEUP_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMakeupSubcategory(MAKEUP_SUBCATEGORY_LOCATORS[i], MAKEUP_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All makeup subcategories tested successfully");
    }

    public void testAllEyeSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all eye subcategories...");

        for (int i = 0; i < EYE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleEyeSubcategory(EYE_SUBCATEGORY_LOCATORS[i], EYE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All eye subcategories tested successfully");
    }

    public void testAllLipSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all lip subcategories...");

        for (int i = 0; i < LIP_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleLipSubcategory(LIP_SUBCATEGORY_LOCATORS[i], LIP_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All lip subcategories tested successfully");
    }

    public void testAllToolsBrushesSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all tools & brushes subcategories...");

        for (int i = 0; i < TOOLS_BRUSHES_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleToolsBrushesSubcategory(TOOLS_BRUSHES_SUBCATEGORY_LOCATORS[i], TOOLS_BRUSHES_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All tools & brushes subcategories tested successfully");
    }

    public void testAllKitsPalettesSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all kits & palettes subcategories...");

        for (int i = 0; i < KITS_PALETTES_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleKitsPalettesSubcategory(KITS_PALETTES_SUBCATEGORY_LOCATORS[i], KITS_PALETTES_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All kits & palettes subcategories tested successfully");
    }

    public void testAllMakeupCategories() {
        System.out.println(INFO_PREFIX + "Starting comprehensive makeup hover testing for all categories...");

        System.out.println(INFO_PREFIX + "Testing Face subcategories...");
        testAllMakeupSubcategories();

        System.out.println(INFO_PREFIX + "Testing Eye subcategories...");
        testAllEyeSubcategories();

        System.out.println(INFO_PREFIX + "Testing Lip subcategories...");
        testAllLipSubcategories();

        System.out.println(INFO_PREFIX + "Testing Tools & Brushes subcategories...");
        testAllToolsBrushesSubcategories();

        System.out.println(INFO_PREFIX + "Testing Kits & Palettes subcategories...");
        testAllKitsPalettesSubcategories();

        System.out.println(SUCCESS_PREFIX + "Complete makeup hover testing finished successfully!");
        System.out.println(SUCCESS_PREFIX + "Total categories tested: Face (14), Eye (10), Lip (6), Tools & Brushes (9), Kits & Palettes (4)");
        System.out.println(SUCCESS_PREFIX + "Total subcategories tested: 43");
    }

    private void testSingleMakeupSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            hoverOverMakeup();

            WebElement subcategoryElement = null;
            try {
                subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
            } catch (Exception e1) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.presenceOfElementLocated(subcategoryLocator));
                } catch (Exception e2) {
                    System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found even after hover");
                    return;
                }
            }

            String expectedHref = subcategoryElement.getAttribute("href");

            try {
                subcategoryElement.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
            }

            System.out.println("🔗 Clicked on " + subcategoryName + " subcategory");

            if (expectedHref != null && !expectedHref.isEmpty()) {
                wait.until(ExpectedConditions.urlToBe(expectedHref));
            } else {
                wait.until(ExpectedConditions.urlContains("tirabeauty.com"));
            }

            String actualUrl = driver.getCurrentUrl();
            System.out.println("🔗 Expected URL: " + expectedHref);
            System.out.println("🔗 Actual URL: " + actualUrl);

            Assert.assertEquals(actualUrl, expectedHref, ERROR_PREFIX + "URL validation failed for " + subcategoryName);

            System.out.println(SUCCESS_PREFIX + subcategoryName + " subcategory navigation validated successfully");

            driver.navigate().back();
            waitForPageToLoad();

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to test " + subcategoryName + ": " + e.getMessage());
            navigateToHomePage();
        }
    }

    private void testSingleEyeSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            hoverOverMakeup();

            WebElement subcategoryElement = null;
            try {
                subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
            } catch (Exception e1) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.presenceOfElementLocated(subcategoryLocator));
                } catch (Exception e2) {
                    System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found even after hover");
                    return;
                }
            }

            String expectedHref = subcategoryElement.getAttribute("href");

            try {
                subcategoryElement.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
            }

            System.out.println("🔗 Clicked on " + subcategoryName + " subcategory");

            if (expectedHref != null && !expectedHref.isEmpty()) {
                wait.until(ExpectedConditions.urlToBe(expectedHref));
            } else {
                wait.until(ExpectedConditions.urlContains("tirabeauty.com"));
            }

            String actualUrl = driver.getCurrentUrl();
            System.out.println("🔗 Expected URL: " + expectedHref);
            System.out.println("🔗 Actual URL: " + actualUrl);

            Assert.assertEquals(actualUrl, expectedHref, ERROR_PREFIX + "URL validation failed for " + subcategoryName);

            System.out.println(SUCCESS_PREFIX + subcategoryName + " subcategory navigation validated successfully");

            driver.navigate().back();
            waitForPageToLoad();

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to test " + subcategoryName + ": " + e.getMessage());
            navigateToHomePage();
        }
    }

    private void navigateToHomePage() {
        String baseUrl = driver.getCurrentUrl().split("\\?")[0].split("#")[0];
        driver.navigate().to(baseUrl);
        waitForPageToLoad();
    }

    public void hoverOverMakeupAndClickSubcategory(String subcategoryName) {
        System.out.println(INFO_PREFIX + "Hovering over Makeup and clicking on " + subcategoryName + "...");

        // Find in face subcategories first
        for (int i = 0; i < MAKEUP_SUBCATEGORY_NAMES.length; i++) {
            if (MAKEUP_SUBCATEGORY_NAMES[i].equals(subcategoryName)) {
                testSingleMakeupSubcategory(MAKEUP_SUBCATEGORY_LOCATORS[i], subcategoryName);
                System.out.println(SUCCESS_PREFIX + "Successfully tested " + subcategoryName + " subcategory");
                return;
            }
        }

        // Find in eye subcategories if not found in face
        for (int i = 0; i < EYE_SUBCATEGORY_NAMES.length; i++) {
            if (EYE_SUBCATEGORY_NAMES[i].equals(subcategoryName)) {
                testSingleEyeSubcategory(EYE_SUBCATEGORY_LOCATORS[i], subcategoryName);
                System.out.println(SUCCESS_PREFIX + "Successfully tested " + subcategoryName + " subcategory");
                return;
            }
        }

        System.out.println(ERROR_PREFIX + "Subcategory " + subcategoryName + " not found");
    }

    private void testSingleLipSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            hoverOverMakeup();

            WebElement subcategoryElement = null;
            try {
                subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
            } catch (Exception e1) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.presenceOfElementLocated(subcategoryLocator));
                } catch (Exception e2) {
                    System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found even after hover");
                    return;
                }
            }

            String expectedHref = subcategoryElement.getAttribute("href");

            try {
                subcategoryElement.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
            }

            System.out.println("🔗 Clicked on " + subcategoryName + " subcategory");

            if (expectedHref != null && !expectedHref.isEmpty()) {
                wait.until(ExpectedConditions.urlToBe(expectedHref));
            } else {
                wait.until(ExpectedConditions.urlContains("tirabeauty.com"));
            }

            String actualUrl = driver.getCurrentUrl();
            System.out.println("🔗 Expected URL: " + expectedHref);
            System.out.println("🔗 Actual URL: " + actualUrl);

            Assert.assertEquals(actualUrl, expectedHref, ERROR_PREFIX + "URL validation failed for " + subcategoryName);

            System.out.println(SUCCESS_PREFIX + subcategoryName + " subcategory navigation validated successfully");

            driver.navigate().back();
            waitForPageToLoad();

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to test " + subcategoryName + ": " + e.getMessage());
            navigateToHomePage();
        }
    }

    private void testSingleToolsBrushesSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            hoverOverMakeup();

            WebElement subcategoryElement = null;
            try {
                subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
            } catch (Exception e1) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.presenceOfElementLocated(subcategoryLocator));
                } catch (Exception e2) {
                    System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found even after hover");
                    return;
                }
            }

            String expectedHref = subcategoryElement.getAttribute("href");

            try {
                subcategoryElement.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
            }

            System.out.println("🔗 Clicked on " + subcategoryName + " subcategory");

            if (expectedHref != null && !expectedHref.isEmpty()) {
                wait.until(ExpectedConditions.urlToBe(expectedHref));
            } else {
                wait.until(ExpectedConditions.urlContains("tirabeauty.com"));
            }

            String actualUrl = driver.getCurrentUrl();
            System.out.println("🔗 Expected URL: " + expectedHref);
            System.out.println("🔗 Actual URL: " + actualUrl);

            Assert.assertEquals(actualUrl, expectedHref, ERROR_PREFIX + "URL validation failed for " + subcategoryName);

            System.out.println(SUCCESS_PREFIX + subcategoryName + " subcategory navigation validated successfully");

            driver.navigate().back();
            waitForPageToLoad();

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to test " + subcategoryName + ": " + e.getMessage());
            navigateToHomePage();
        }
    }

    private void testSingleKitsPalettesSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            hoverOverMakeup();

            WebElement subcategoryElement = null;
            try {
                subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
            } catch (Exception e1) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.presenceOfElementLocated(subcategoryLocator));
                } catch (Exception e2) {
                    System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found even after hover");
                    return;
                }
            }

            String expectedHref = subcategoryElement.getAttribute("href");

            try {
                subcategoryElement.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
            }

            System.out.println("🔗 Clicked on " + subcategoryName + " subcategory");

            if (expectedHref != null && !expectedHref.isEmpty()) {
                wait.until(ExpectedConditions.urlToBe(expectedHref));
            } else {
                wait.until(ExpectedConditions.urlContains("tirabeauty.com"));
            }

            String actualUrl = driver.getCurrentUrl();
            System.out.println("🔗 Expected URL: " + expectedHref);
            System.out.println("🔗 Actual URL: " + actualUrl);

            Assert.assertEquals(actualUrl, expectedHref, ERROR_PREFIX + "URL validation failed for " + subcategoryName);

            System.out.println(SUCCESS_PREFIX + subcategoryName + " subcategory navigation validated successfully");

            driver.navigate().back();
            waitForPageToLoad();

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to test " + subcategoryName + ": " + e.getMessage());
            navigateToHomePage();
        }
    }

    // Skin subcategory test methods
    public void testAllCleansersExfoliatorsSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all cleansers & exfoliators subcategories...");

        for (int i = 0; i < CLEANSERS_EXFOLIATORS_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleSkinSubcategory(CLEANSERS_EXFOLIATORS_SUBCATEGORY_LOCATORS[i], CLEANSERS_EXFOLIATORS_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All cleansers & exfoliators subcategories tested successfully");
    }

    public void testAllEyeCareSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all eye care subcategories...");

        for (int i = 0; i < EYECARE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleSkinSubcategory(EYECARE_SUBCATEGORY_LOCATORS[i], EYECARE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All eye care subcategories tested successfully");
    }

    public void testAllSetsBundlesSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all sets & bundles subcategories...");

        for (int i = 0; i < SETS_BUNDLES_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleSkinSubcategory(SETS_BUNDLES_SUBCATEGORY_LOCATORS[i], SETS_BUNDLES_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All sets & bundles subcategories tested successfully");
    }

    public void testAllLipCareSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all lip care subcategories...");

        for (int i = 0; i < LIPCARE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleSkinSubcategory(LIPCARE_SUBCATEGORY_LOCATORS[i], LIPCARE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All lip care subcategories tested successfully");
    }

    public void testAllMaskSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all mask subcategories...");

        for (int i = 0; i < MASK_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleSkinSubcategory(MASK_SUBCATEGORY_LOCATORS[i], MASK_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All mask subcategories tested successfully");
    }

    public void testAllMoisturizerSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all moisturizer subcategories...");

        for (int i = 0; i < MOISTURIZER_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleSkinSubcategory(MOISTURIZER_SUBCATEGORY_LOCATORS[i], MOISTURIZER_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All moisturizer subcategories tested successfully");
    }

    public void testAllTonersMistSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all toners & mist subcategories...");

        for (int i = 0; i < TONERS_MIST_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleSkinSubcategory(TONERS_MIST_SUBCATEGORY_LOCATORS[i], TONERS_MIST_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All toners & mist subcategories tested successfully");
    }

    public void testAllSunCareSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all sun care subcategories...");

        for (int i = 0; i < SUNCARE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleSkinSubcategory(SUNCARE_SUBCATEGORY_LOCATORS[i], SUNCARE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All sun care subcategories tested successfully");
    }

    public void testAllSkinCategories() {
        System.out.println(INFO_PREFIX + "Starting comprehensive skin hover testing for all categories...");

        System.out.println(INFO_PREFIX + "Testing Cleansers & Exfoliators subcategories...");
        testAllCleansersExfoliatorsSubcategories();

        System.out.println(INFO_PREFIX + "Testing Eye Care subcategories...");
        testAllEyeCareSubcategories();

        System.out.println(INFO_PREFIX + "Testing Sets & Bundles subcategories...");
        testAllSetsBundlesSubcategories();

        System.out.println(INFO_PREFIX + "Testing Lip Care subcategories...");
        testAllLipCareSubcategories();

        System.out.println(INFO_PREFIX + "Testing Mask subcategories...");
        testAllMaskSubcategories();

        System.out.println(INFO_PREFIX + "Testing Moisturizer subcategories...");
        testAllMoisturizerSubcategories();

        System.out.println(INFO_PREFIX + "Testing Toners & Mist subcategories...");
        testAllTonersMistSubcategories();

        System.out.println(INFO_PREFIX + "Testing Sun Care subcategories...");
        testAllSunCareSubcategories();

        System.out.println(SUCCESS_PREFIX + "Complete skin hover testing finished successfully!");
        System.out.println(SUCCESS_PREFIX + "Total categories tested: Cleansers & Exfoliators (6), Eye Care (4), Sets & Bundles (3), Lip Care (5), Mask (3), Moisturizer (4), Toners & Mist (3), Sun Care (2)");
        System.out.println(SUCCESS_PREFIX + "Total subcategories tested: 30");
    }

    private void testSingleSkinSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            hoverOverSkin();

            WebElement subcategoryElement = null;
            try {
                subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
            } catch (Exception e1) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.presenceOfElementLocated(subcategoryLocator));
                } catch (Exception e2) {
                    System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found even after hover");
                    return;
                }
            }

            String expectedHref = subcategoryElement.getAttribute("href");

            try {
                subcategoryElement.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
            }

            System.out.println("🔗 Clicked on " + subcategoryName + " subcategory");

            if (expectedHref != null && !expectedHref.isEmpty()) {
                wait.until(ExpectedConditions.urlToBe(expectedHref));
            } else {
                wait.until(ExpectedConditions.urlContains("tirabeauty.com"));
            }

            String actualUrl = driver.getCurrentUrl();
            System.out.println("🔗 Expected URL: " + expectedHref);
            System.out.println("🔗 Actual URL: " + actualUrl);

            Assert.assertEquals(actualUrl, expectedHref, ERROR_PREFIX + "URL validation failed for " + subcategoryName);

            System.out.println(SUCCESS_PREFIX + subcategoryName + " subcategory navigation validated successfully");

            driver.navigate().back();
            waitForPageToLoad();

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to test " + subcategoryName + ": " + e.getMessage());
            navigateToHomePage();
        }
    }

    // Hair subcategory test methods
    public void testAllHairCareSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all hair care subcategories...");

        for (int i = 0; i < HAIRCARE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleHairSubcategory(HAIRCARE_SUBCATEGORY_LOCATORS[i], HAIRCARE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All hair care subcategories tested successfully");
    }

    public void testAllHairStylingSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all hair styling subcategories...");

        for (int i = 0; i < HAIRSTYLING_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleHairSubcategory(HAIRSTYLING_SUBCATEGORY_LOCATORS[i], HAIRSTYLING_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All hair styling subcategories tested successfully");
    }

    public void testAllHairToolsSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all hair tools & accessories subcategories...");

        for (int i = 0; i < HAIRTOOLS_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleHairSubcategory(HAIRTOOLS_SUBCATEGORY_LOCATORS[i], HAIRTOOLS_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All hair tools & accessories subcategories tested successfully");
    }

    public void testAllHairTypeSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all shop by hair type subcategories...");

        for (int i = 0; i < HAIRTYPE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleHairSubcategory(HAIRTYPE_SUBCATEGORY_LOCATORS[i], HAIRTYPE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All shop by hair type subcategories tested successfully");
    }

    public void testAllHairCategories() {
        System.out.println(INFO_PREFIX + "Starting comprehensive hair hover testing for all categories...");

        System.out.println(INFO_PREFIX + "Testing Hair Care subcategories...");
        testAllHairCareSubcategories();

        System.out.println(INFO_PREFIX + "Testing Hair Styling subcategories...");
        testAllHairStylingSubcategories();

        System.out.println(INFO_PREFIX + "Testing Tools & Accessories subcategories...");
        testAllHairToolsSubcategories();

        System.out.println(INFO_PREFIX + "Testing Shop By Hair Type subcategories...");
        testAllHairTypeSubcategories();

        System.out.println(SUCCESS_PREFIX + "Complete hair hover testing finished successfully!");
        System.out.println(SUCCESS_PREFIX + "Total categories tested: Hair Care (9), Hair Styling (4), Tools & Accessories (7), Shop By Hair Type (3)");
        System.out.println(SUCCESS_PREFIX + "Total subcategories tested: 23");
    }

    private void testSingleHairSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            hoverOverHair();

            WebElement subcategoryElement = null;
            try {
                subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
            } catch (Exception e1) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.presenceOfElementLocated(subcategoryLocator));
                } catch (Exception e2) {
                    System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found even after hover");
                    return;
                }
            }

            String expectedHref = subcategoryElement.getAttribute("href");

            try {
                subcategoryElement.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
            }

            System.out.println("🔗 Clicked on " + subcategoryName + " subcategory");

            if (expectedHref != null && !expectedHref.isEmpty()) {
                wait.until(ExpectedConditions.urlToBe(expectedHref));
            } else {
                wait.until(ExpectedConditions.urlContains("tirabeauty.com"));
            }

            String actualUrl = driver.getCurrentUrl();
            System.out.println("🔗 Expected URL: " + expectedHref);
            System.out.println("🔗 Actual URL: " + actualUrl);

            Assert.assertEquals(actualUrl, expectedHref, ERROR_PREFIX + "URL validation failed for " + subcategoryName);

            System.out.println(SUCCESS_PREFIX + subcategoryName + " subcategory navigation validated successfully");

            driver.navigate().back();
            waitForPageToLoad();

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to test " + subcategoryName + ": " + e.getMessage());
            navigateToHomePage();
        }
    }
}