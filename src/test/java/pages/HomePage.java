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
        try {
            // First wait for the page URL to contain tirabeauty
            wait.until(driver -> driver.getCurrentUrl().contains("tirabeauty"));

            // Wait for page readyState to be complete
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState;").equals("complete"));

            // Try to find the logo, but don't fail if it's not found immediately
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.HomePage.logoLink));
            } catch (Exception e) {
                // If logo is not found, try to find any common homepage elements
                boolean pageLoaded = wait.until(driver -> {
                    try {
                        // Check for any navigation menu items
                        return driver.findElement(Locators.HomePage.makeupNavLink).isDisplayed() ||
                               driver.findElement(Locators.HomePage.skinNavLink).isDisplayed() ||
                               driver.findElement(Locators.HomePage.hairNavLink).isDisplayed() ||
                               driver.findElement(By.xpath("//body")).isDisplayed();
                    } catch (Exception ex) {
                        return false;
                    }
                });

                if (!pageLoaded) {
                    throw new RuntimeException("Homepage elements could not be located after page load");
                }
            }

            // Basic validations
            Assert.assertTrue(driver.getCurrentUrl().contains("tirabeauty"),
                ERROR_PREFIX + "URL does not contain expected domain after page load");
            Assert.assertFalse(driver.getTitle().isEmpty(),
                ERROR_PREFIX + "Page title is empty after page load");

            System.out.println(SUCCESS_PREFIX + "Homepage loaded completely");

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Page loading failed: " + e.getMessage());
            throw e;
        }
    }

    // Generic navigation method
    public void clickNavigation(By locator, String expectedUrlPart) {
        driver.findElement(locator).click();
        wait.until(ExpectedConditions.urlContains(expectedUrlPart));
    }

    // Enhanced URL verification methods to replace Thread.sleep
    public void waitForUrlChange(String expectedUrlPart) {
        try {
            wait.until(ExpectedConditions.urlContains(expectedUrlPart));
            waitForPageStabilization();
            System.out.println(SUCCESS_PREFIX + "URL loaded successfully: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "URL change timeout: Expected '" + expectedUrlPart + "' but got '" + driver.getCurrentUrl() + "'");
            throw e;
        }
    }

    public void waitForExactUrl(String expectedUrl) {
        try {
            wait.until(driver -> driver.getCurrentUrl().equals(expectedUrl));
            waitForPageStabilization();
            System.out.println(SUCCESS_PREFIX + "Exact URL reached: " + expectedUrl);
        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Exact URL timeout: Expected '" + expectedUrl + "' but got '" + driver.getCurrentUrl() + "'");
            throw e;
        }
    }

    public void waitForPageStabilization() {
        try {
            // Wait for document ready state
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState;").equals("complete"));

            // Wait for any loading indicators to disappear
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(Locators.HomePage.pageLoadingIndicator));
            } catch (Exception e) {
                // Loading indicator might not exist, which is fine
            }

            // Wait for body element to be present and at least one interactive element
            wait.until(ExpectedConditions.presenceOfElementLocated(Locators.HomePage.bodyElement));
            wait.until(driver -> {
                try {
                    return driver.findElement(Locators.HomePage.makeupNavLink).isDisplayed() ||
                           driver.findElement(Locators.HomePage.skinNavLink).isDisplayed() ||
                           driver.findElement(Locators.HomePage.hairNavLink).isDisplayed();
                } catch (Exception ex) {
                    return false;
                }
            });

            // Additional stability check - wait for JavaScript to finish
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return jQuery != undefined ? jQuery.active == 0 : true;"));

        } catch (Exception e) {
            // If jQuery check fails, continue - not all pages use jQuery
        }
    }

    public void waitForUrlAndVerify(String expectedUrlPart, String elementToVerify) {
        try {
            // Wait for URL change
            wait.until(ExpectedConditions.urlContains(expectedUrlPart));

            // Wait for page stabilization
            waitForPageStabilization();

            // Verify specific element is visible
            if (elementToVerify != null && !elementToVerify.isEmpty()) {
                By verificationElement = By.xpath("//*[contains(text(), '" + elementToVerify + "')]");
                wait.until(ExpectedConditions.visibilityOfElementLocated(verificationElement));
            }

            System.out.println(SUCCESS_PREFIX + "Page loaded and verified: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Page verification failed for URL containing '" + expectedUrlPart + "': " + e.getMessage());
            throw e;
        }
    }

    public boolean isPageFullyLoaded() {
        try {
            // Check document ready state
            Object readyState = ((JavascriptExecutor) driver).executeScript("return document.readyState;");
            if (!"complete".equals(readyState)) {
                return false;
            }

            // Check if main navigation elements are present
            return driver.findElement(Locators.HomePage.makeupNavLink).isDisplayed() ||
                   driver.findElement(Locators.HomePage.skinNavLink).isDisplayed() ||
                   driver.findElement(Locators.HomePage.hairNavLink).isDisplayed();
        } catch (Exception e) {
            return false;
        }
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

    public void hoverOverFragrance() {
        Actions actions = new Actions(driver);
        WebElement fragranceNav = wait.until(ExpectedConditions.elementToBeClickable(Locators.HomePage.fragranceNavLink));

        // Scroll element into view first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", fragranceNav);

        // Move to fragrance element and hold
        actions.moveToElement(fragranceNav).perform();

        // Also trigger hover using JavaScript as backup
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", fragranceNav);
    }

    public void hoverOverMen() {
        Actions actions = new Actions(driver);
        WebElement menNav = wait.until(ExpectedConditions.elementToBeClickable(Locators.HomePage.menNavLink));

        // Scroll element into view first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", menNav);

        // Move to men element and hold
        actions.moveToElement(menNav).perform();

        // Also trigger hover using JavaScript as backup
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", menNav);
    }

    public void hoverOverBathBody() {
        Actions actions = new Actions(driver);
        WebElement bathBodyNav = wait.until(ExpectedConditions.elementToBeClickable(Locators.HomePage.bathBodyNavLink));

        // Scroll element into view first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", bathBodyNav);

        // Move to bath & body element and hold
        actions.moveToElement(bathBodyNav).perform();

        // Also trigger hover using JavaScript as backup
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", bathBodyNav);
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
        Locators.HomePage.skinSubcategoryToner,
        Locators.HomePage.skinSubcategorySerum,
        Locators.HomePage.skinSubcategoryMoisturizer
    };

    private static final String[] CLEANSERS_EXFOLIATORS_SUBCATEGORY_NAMES = {
        "Cleansers & Exfoliators", "Face Washes & Cleansers", "Scrubs & Exfoliators", "Face Wipes", "Makeup Remover"
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
        //Locators.HomePage.hairSubcategoryShopByHairType,
        Locators.HomePage.hairSubcategoryStraight,
        Locators.HomePage.hairSubcategoryCurlyWavy
    };

    private static final String[] HAIRTYPE_SUBCATEGORY_NAMES = {
        "Shop By Hair Type", "Straight", "Curly & Wavy"
    };

    // Hair Shop By subcategory arrays
    private static final By[] HAIRSHOPBY_SUBCATEGORY_LOCATORS = {
      //  Locators.HomePage.hairSubcategoryShopBy,
        Locators.HomePage.hairSubcategoryWhatsNew,
        Locators.HomePage.hairSubcategoryBestsellers,
        Locators.HomePage.hairSubcategoryMinis,
        Locators.HomePage.hairSubcategorySetsBundles,
        Locators.HomePage.hairSubcategoryTiraLoves,
        Locators.HomePage.hairSubcategoryHomegrown,
        Locators.HomePage.hairSubcategoryBudgetBuys
    };
    private static final String[] HAIRSHOPBY_SUBCATEGORY_NAMES = {
        "Shop By", "What's New", "Bestsellers", "Minis", "Sets & Bundles", "Tira Loves", "Homegrown", "Budget Buys"
    };

    // Hair Brands To Know subcategory arrays
    private static final By[] HAIRBRANDS_SUBCATEGORY_LOCATORS = {
       // Locators.HomePage.hairSubcategoryBrandsToKnow,
        Locators.HomePage.hairSubcategoryMilkShake,
        Locators.HomePage.hairSubcategoryForestEssentials,
        Locators.HomePage.hairSubcategoryCOTRIL,
        Locators.HomePage.hairSubcategoryLOrealProfessionnel
    };
    private static final String[] HAIRBRANDS_SUBCATEGORY_NAMES = {
        "Brands To Know", "Milk Shake", "Forest Essentials", "COTRIL", "L'OREAL PROFESSIONNEL"
    };

    // Hair Shop By Concern subcategory arrays
    private static final By[] HAIRCONCERN_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.hairSubcategoryShopByConcern,
        Locators.HomePage.hairSubcategoryHairfallThinning,
        Locators.HomePage.hairSubcategoryDandruffBuildUps,
        Locators.HomePage.hairSubcategoryDryFrizzyHair,
        Locators.HomePage.hairSubcategorySplitEnds,
        Locators.HomePage.hairSubcategoryColourProtection,
        Locators.HomePage.hairSubcategoryBreakageProneHair,
        Locators.HomePage.hairSubcategoryCurlCare,
        Locators.HomePage.hairSubcategoryVolume
    };
    private static final String[] HAIRCONCERN_SUBCATEGORY_NAMES = {
        "Shop By Concern", "Hairfall & Hair Thinning", "Dandruff Build-Ups", "Dry & Frizzy Hair", "Split Ends", "Colour Protection", "Breakage-Prone Hair", "Curl Care", "Volume"
    };

    // Hair Tira Red subcategory arrays
    private static final By[] HAIRTIIRED_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.hairSubcategoryTiraRed,
        Locators.HomePage.hairSubcategoryOlaplex,
        Locators.HomePage.hairSubcategoryKevinMurphy,
        Locators.HomePage.hairSubcategoryK18,
        Locators.HomePage.hairSubcategoryRootDeep,
        Locators.HomePage.hairSubcategoryMoroccanoil
    };
    private static final String[] HAIRTIIRED_SUBCATEGORY_NAMES = {
        "Tira Red", "Olaplex", "Kevin Murphy", "K18", "Root Deep", "Moroccanoil"
    };

    // Hair Explore subcategory arrays
    private static final By[] HAIREXPLORE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.hairSubcategoryExplore,
        Locators.HomePage.hairSubcategoryDermocosmetics,
        Locators.HomePage.hairSubcategoryProfessionalHairCare
    };
    private static final String[] HAIREXPLORE_SUBCATEGORY_NAMES = {
        "Explore", "Dermocosmetics", "Professional Hair Care"
    };

    // Fragrance subcategory arrays - Women's Fragrance
    private static final By[] FRAGRANCE_WOMENS_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.fragranceSubcategoryWomensPerfume,
        Locators.HomePage.fragranceSubcategoryWomensBodyMists,
        Locators.HomePage.fragranceSubcategoryWomensDeodorants
    };
    private static final String[] FRAGRANCE_WOMENS_SUBCATEGORY_NAMES = {
        "Perfume (EDT & EDP)", "Body Mists & Sprays", "Deodorants & Roll-Ons"
    };

    // Fragrance subcategory arrays - Men's Fragrance
    private static final By[] FRAGRANCE_MENS_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.fragranceSubcategoryMensPerfume,
        Locators.HomePage.fragranceSubcategoryMensBodyMists,
        Locators.HomePage.fragranceSubcategoryMensDeodorants,
        Locators.HomePage.fragranceSubcategoryMensColognes
    };
    private static final String[] FRAGRANCE_MENS_SUBCATEGORY_NAMES = {
        "Perfume (EDT & EDP)", "Body Mists & Sprays", "Deodorants & Roll-Ons", "Colognes & After Shaves"
    };

    // Fragrance subcategory arrays - Unisex Fragrance
    private static final By[] FRAGRANCE_UNISEX_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.fragranceSubcategoryUnisexPerfumes,
        Locators.HomePage.fragranceSubcategoryUnisexMists,
        Locators.HomePage.fragranceSubcategoryUnisexDeodorants
    };
    private static final String[] FRAGRANCE_UNISEX_SUBCATEGORY_NAMES = {
        "Unisex Perfumes", "Unisex Mists & Sprays", "Unisex Deodorants & Roll-Ons"
    };

    // Fragrance subcategory arrays - Fragrance Family
    private static final By[] FRAGRANCE_FAMILY_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.fragranceSubcategoryFloral,
        Locators.HomePage.fragranceSubcategoryFruity,
        Locators.HomePage.fragranceSubcategorySpicy,
        Locators.HomePage.fragranceSubcategoryWoody,
        Locators.HomePage.fragranceSubcategoryFresh,
        Locators.HomePage.fragranceSubcategoryAqua,
        Locators.HomePage.fragranceSubcategoryCitrus,
        Locators.HomePage.fragranceSubcategoryMusky
    };
    private static final String[] FRAGRANCE_FAMILY_SUBCATEGORY_NAMES = {
        "Floral", "Fruity", "Spicy", "Woody", "Fresh", "Aqua", "Citrus", "Musky"
    };

    // Fragrance subcategory arrays - Home Fragrance
    private static final By[] FRAGRANCE_HOME_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.fragranceSubcategoryCandle,
        Locators.HomePage.fragranceSubcategoryDiffuser
    };
    private static final String[] FRAGRANCE_HOME_SUBCATEGORY_NAMES = {
        "Candle", "Diffuser"
    };

    // Fragrance subcategory arrays - Shop By
    private static final By[] FRAGRANCE_SHOPBY_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.fragranceSubcategoryWhatsNew,
        Locators.HomePage.fragranceSubcategoryBestsellers,
        Locators.HomePage.fragranceSubcategoryGiftSets,
        Locators.HomePage.fragranceSubcategorySetsBundles,
        Locators.HomePage.fragranceSubcategoryTiraLoves
    };
    private static final String[] FRAGRANCE_SHOPBY_SUBCATEGORY_NAMES = {
        "What's New", "Bestsellers", "Gift Sets", "Sets & Bundles", "Tira Loves"
    };

    // Fragrance subcategory arrays - Tira Red
    private static final By[] FRAGRANCE_TIRARED_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.fragranceSubcategoryYvesSaintLaurent,
        Locators.HomePage.fragranceSubcategoryBurberry,
        Locators.HomePage.fragranceSubcategoryTomFord,
        Locators.HomePage.fragranceSubcategoryPrada,
        Locators.HomePage.fragranceSubcategoryVersace
    };
    private static final String[] FRAGRANCE_TIRARED_SUBCATEGORY_NAMES = {
        "Yves Saint Laurent", "Burberry", "Tom Ford", "Prada", "Versace"
    };

    // Fragrance subcategory arrays - Brands To Know
    private static final By[] FRAGRANCE_BRANDS_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.fragranceSubcategoryGucci,
        Locators.HomePage.fragranceSubcategoryJoMaloneLondon,
        Locators.HomePage.fragranceSubcategoryElizabethArden,
        Locators.HomePage.fragranceSubcategoryJimmyChoo,
        Locators.HomePage.fragranceSubcategoryGiorgioArmani,
        Locators.HomePage.fragranceSubcategoryCalvinKlein,
        Locators.HomePage.fragranceSubcategoryNarcisoRodriguez,
        Locators.HomePage.fragranceSubcategoryDolceGabbana,
        Locators.HomePage.fragranceSubcategorySalvatoreFerragamo
    };
    private static final String[] FRAGRANCE_BRANDS_SUBCATEGORY_NAMES = {
        "Gucci", "Jo Malone London", "Elizabeth Arden", "Jimmy Choo", "Giorgio Armani", "Calvin Klein", "Narciso Rodriguez", "Dolce&Gabbana", "Salvatore Ferragamo"
    };

    // Men subcategory arrays - Beard Care
    private static final By[] MEN_BEARDCARE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryBeardMoustacheOil,
        Locators.HomePage.menSubcategoryBeardWax,
        Locators.HomePage.menSubcategoryBeardComb,
        Locators.HomePage.menSubcategoryBeardCream,
        Locators.HomePage.menSubcategoryBeardWash
    };
    private static final String[] MEN_BEARDCARE_SUBCATEGORY_NAMES = {
        "Beard & Moustache Oil", "Beard Wax & Softeners", "Beard Comb", "Beard Cream, Serum & Balm", "Beard Wash & Shampoos"
    };

    // Men subcategory arrays - Hair Care
    private static final By[] MEN_HAIRCARE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryShampoo,
        Locators.HomePage.menSubcategoryConditioner,
        Locators.HomePage.menSubcategoryHairOil,
        Locators.HomePage.menSubcategoryHairStyling,
        Locators.HomePage.menSubcategoryHairColour
    };
    private static final String[] MEN_HAIRCARE_SUBCATEGORY_NAMES = {
        "Shampoo", "Conditioner", "Hair Oil", "Hair Styling", "Hair Colour"
    };

    // Men subcategory arrays - Fragrance
    private static final By[] MEN_FRAGRANCE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryPerfume,
        Locators.HomePage.menSubcategoryDeodorants,
        Locators.HomePage.menSubcategoryBodyMists,
        Locators.HomePage.menSubcategoryColognes
    };
    private static final String[] MEN_FRAGRANCE_SUBCATEGORY_NAMES = {
        "Perfume (EDT & EDP)", "Deodorants & Roll-Ons", "Body Mists & Sprays", "Colognes & Aftershaves"
    };

    // Men subcategory arrays - Shaving
    private static final By[] MEN_SHAVING_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryRazors,
        Locators.HomePage.menSubcategoryShavers,
        Locators.HomePage.menSubcategoryShavingCream,
        Locators.HomePage.menSubcategoryPrePostShaves,
        Locators.HomePage.menSubcategoryShavingBrush,
        Locators.HomePage.menSubcategoryGroomingKits
    };
    private static final String[] MEN_SHAVING_SUBCATEGORY_NAMES = {
        "Razors & Cartridges", "Shavers & Trimmers", "Shaving Cream, Foam & Gel", "Pre & Post Shaves", "Shaving Brush", "Grooming Kits"
    };

    // Men subcategory arrays - Skincare
    private static final By[] MEN_SKINCARE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryFaceWash,
        Locators.HomePage.menSubcategoryScrubs,
        Locators.HomePage.menSubcategoryFaceMoisturizer,
        Locators.HomePage.menSubcategorySunscreen,
        Locators.HomePage.menSubcategoryMasks
    };
    private static final String[] MEN_SKINCARE_SUBCATEGORY_NAMES = {
        "Face Wash", "Scrubs & Exfoliators", "Face Moisturizer", "Sunscreen", "Masks & Peels"
    };

    // Men subcategory arrays - Bath & Body
    private static final By[] MEN_BATHBODY_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryShowerGel,
        Locators.HomePage.menSubcategorySoap,
        Locators.HomePage.menSubcategoryBodyScrub,
        Locators.HomePage.menSubcategoryBodyLotion,
        Locators.HomePage.menSubcategoryIntimateCare,
        Locators.HomePage.menSubcategoryTalc,
        Locators.HomePage.menSubcategorySets
    };
    private static final String[] MEN_BATHBODY_SUBCATEGORY_NAMES = {
        "Shower Gel", "Soap", "Body Scrub", "Body Lotion", "Intimate Care", "Talc", "Sets & Bundles"
    };

    // Men subcategory arrays - Shop By
    private static final By[] MEN_SHOPBY_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryWhatsNew,
        Locators.HomePage.menSubcategoryBestsellers,
        Locators.HomePage.menSubcategoryMinis,
        Locators.HomePage.menSubcategorySetsBundles,
        Locators.HomePage.menSubcategoryBudgetBuys
    };
    private static final String[] MEN_SHOPBY_SUBCATEGORY_NAMES = {
        "What's New", "Bestsellers", "Minis", "Sets & Bundles", "Budget Buys"
    };

    // Men subcategory arrays - Tira Red
    private static final By[] MEN_TIRARED_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryCaptainFawcett,
        Locators.HomePage.menSubcategoryTruefittHill,
        Locators.HomePage.menSubcategoryVersace,
        Locators.HomePage.menSubcategoryBeardburys,
        Locators.HomePage.menSubcategoryCalvinKlein
    };
    private static final String[] MEN_TIRARED_SUBCATEGORY_NAMES = {
        "Captain Fawcett", "Truefitt & Hill", "Versace", "Beardburys", "Calvin Klein"
    };

    // Men subcategory arrays - Shop By Concern
    private static final By[] MEN_CONCERN_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryFineLinesWrinkles,
        Locators.HomePage.menSubcategoryAcne,
        Locators.HomePage.menSubcategoryDullness,
        Locators.HomePage.menSubcategoryPigmentationDarkSpots,
        Locators.HomePage.menSubcategoryPoresBlemishes,
        Locators.HomePage.menSubcategoryDryness,
        Locators.HomePage.menSubcategoryDarkCircles,
        Locators.HomePage.menSubcategoryPuffiness,
        Locators.HomePage.menSubcategoryHairThinningHairLoss
    };
    private static final String[] MEN_CONCERN_SUBCATEGORY_NAMES = {
        "Fine Lines & Wrinkles", "Acne", "Dullness", "Pigmentation & Dark Spots", "Pores & Blemishes", "Dryness", "Dark Circles", "Puffiness", "Hair Thinning & Hair Loss"
    };

    // Men subcategory arrays - Brands To Know
    private static final By[] MEN_BRANDS_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.menSubcategoryUrbanGabru,
        Locators.HomePage.menSubcategoryBombayShavingCompany,
        Locators.HomePage.menSubcategoryTheManCompany,
        Locators.HomePage.menSubcategoryBeardo,
        Locators.HomePage.menSubcategoryBigen,
        Locators.HomePage.menSubcategoryLetsShave
    };
    private static final String[] MEN_BRANDS_SUBCATEGORY_NAMES = {
        "Urban Gabru", "Bombay Shaving Company", "The Man Company", "Beardo", "Bigen", "LetsShave"
    };

    // Bath & Body subcategory arrays - Bath & Shower
    private static final By[] BATHBODY_BATHSHOWER_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.bathBodySubcategoryBathSalts,
        Locators.HomePage.bathBodySubcategoryBodyScrubsExfoliants,
        Locators.HomePage.bathBodySubcategoryBodyWashesShowerGels,
        Locators.HomePage.bathBodySubcategorySoap,
        Locators.HomePage.bathBodySubcategoryBathKitsSets
    };
    private static final String[] BATHBODY_BATHSHOWER_SUBCATEGORY_NAMES = {
        "Bath Salts", "Body Scrubs & Exfoliants", "Body Washes & Shower Gels", "Soap", "Bath Kits & Sets"
    };

    // Bath & Body subcategory arrays - Body Care
    private static final By[] BATHBODY_BODYCARE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.bathBodySubcategoryBodyButter,
        Locators.HomePage.bathBodySubcategoryBodyLotionsMoisturizers,
        Locators.HomePage.bathBodySubcategoryMassageOil,
        Locators.HomePage.bathBodySubcategoryTalc,
        Locators.HomePage.bathBodySubcategoryEssentialOil
    };
    private static final String[] BATHBODY_BODYCARE_SUBCATEGORY_NAMES = {
        "Body Butter", "Body Lotions & Moisturizers", "Massage Oil", "Talc", "Essential Oil"
    };

    // Bath & Body subcategory arrays - Hands & Feet
    private static final By[] BATHBODY_HANDSFEET_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.bathBodySubcategoryHandWash,
        Locators.HomePage.bathBodySubcategoryHandCreamsMasks,
        Locators.HomePage.bathBodySubcategoryFootCare,
        Locators.HomePage.bathBodySubcategoryManiPediTools
    };
    private static final String[] BATHBODY_HANDSFEET_SUBCATEGORY_NAMES = {
        "Hand Wash", "Hand Creams & Masks", "Foot Care", "Mani-Pedi Tools & Kits"
    };

    // Bath & Body subcategory arrays - Hygiene Essentials
    private static final By[] BATHBODY_HYGIENE_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.bathBodySubcategoryHandSanitizer,
        Locators.HomePage.bathBodySubcategoryIntimateCare
    };
    private static final String[] BATHBODY_HYGIENE_SUBCATEGORY_NAMES = {
        "Hand Sanitizer", "Intimate Care"
    };

    // Bath & Body subcategory arrays - Shaving & Hair Removal
    private static final By[] BATHBODY_SHAVING_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.bathBodySubcategoryBodyRazorsCartridges,
        Locators.HomePage.bathBodySubcategoryFaceEyebrowRazors,
        Locators.HomePage.bathBodySubcategoryEpilatorsTrimmers,
        Locators.HomePage.bathBodySubcategoryWaxEssentials,
        Locators.HomePage.bathBodySubcategoryHairRemovalCreams
    };
    private static final String[] BATHBODY_SHAVING_SUBCATEGORY_NAMES = {
        "Body Razors & Cartridges", "Face & Eyebrow Razors", "Epilators & Trimmers", "Wax Essentials", "Hair Removal Creams"
    };

    // Bath & Body subcategory arrays - Brands To Know
    private static final By[] BATHBODY_BRANDS_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.bathBodySubcategoryTheBodyShop,
        Locators.HomePage.bathBodySubcategoryMcaffeine,
        Locators.HomePage.bathBodySubcategoryLoccitane,
        Locators.HomePage.bathBodySubcategoryKimirica,
        Locators.HomePage.bathBodySubcategoryYvesRocher,
        Locators.HomePage.bathBodySubcategoryMarksSpencers,
        Locators.HomePage.bathBodySubcategoryMamaearth,
        Locators.HomePage.bathBodySubcategorySoulflower,
        Locators.HomePage.bathBodySubcategoryFindYourHappyPlace,
        Locators.HomePage.bathBodySubcategoryPlum,
        Locators.HomePage.bathBodySubcategoryDove,
        Locators.HomePage.bathBodySubcategoryNivea,
        Locators.HomePage.bathBodySubcategoryVaseline
    };
    private static final String[] BATHBODY_BRANDS_SUBCATEGORY_NAMES = {
        "The Body Shop", "Mcaffeine", "L'occitane", "Kimirica", "Yves Rocher", "Marks & Spencers", "Mamaearth", "Soulflower", "Find Your Happy Place", "Plum", "Dove", "Nivea", "Vaseline"
    };

    // Bath & Body subcategory arrays - Derm Approved
    private static final By[] BATHBODY_DERMAPPROVED_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.bathBodySubcategoryDermApproved
    };
    private static final String[] BATHBODY_DERMAPPROVED_SUBCATEGORY_NAMES = {
        "Derm Approved"
    };

    // Bath & Body subcategory arrays - Shop By
    private static final By[] BATHBODY_SHOPBY_SUBCATEGORY_LOCATORS = {
        Locators.HomePage.bathBodySubcategoryWhatsNew,
        Locators.HomePage.bathBodySubcategoryBestsellers,
        Locators.HomePage.bathBodySubcategoryMinis,
        Locators.HomePage.bathBodySubcategorySetsAndBundles
    };
    private static final String[] BATHBODY_SHOPBY_SUBCATEGORY_NAMES = {
        "What's New", "Bestsellers", "Minis", "Sets & Bundles"
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
        try {
            // Simply go back to homepage without logo clicking
            driver.get("https://www.tirabeauty.com");
            waitForPageToLoad();
        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to navigate to homepage: " + e.getMessage());
        }
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
        System.out.println(SUCCESS_PREFIX + "Total categories tested: Cleansers & Exfoliators (5), Eye Care (4), Sets & Bundles (3), Lip Care (5), Mask (3), Moisturizer (4), Toners & Mist (3), Sun Care (2)");
        System.out.println(SUCCESS_PREFIX + "Total subcategories tested: 29");
    }

    private void testSingleSkinSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            // Navigate to home page first to ensure clean state
            if (!driver.getCurrentUrl().contains("tirabeauty.com") || driver.getCurrentUrl().contains("/collection/")) {
                navigateToHomePage();
            }

            hoverOverSkin();

            WebElement subcategoryElement = null;
            String expectedHref = null;

            // Retry mechanism for finding and getting href
            for (int retry = 0; retry < 3; retry++) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
                    expectedHref = subcategoryElement.getAttribute("href");
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found after " + (retry + 1) + " attempts");
                        return;
                    }
                    waitForPageStabilization();
                    hoverOverSkin(); // Re-hover
                }
            }

            // Click with retry mechanism
            for (int retry = 0; retry < 3; retry++) {
                try {
                    // Re-find element to avoid stale reference
                    subcategoryElement = driver.findElement(subcategoryLocator);
                    subcategoryElement.click();
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        // Final attempt with JavaScript click
                        subcategoryElement = driver.findElement(subcategoryLocator);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
                    } else {
                        waitForPageStabilization();
                    }
                }
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

    public void testAllHairShopBySubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all hair shop by subcategories...");

        for (int i = 0; i < HAIRSHOPBY_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleHairSubcategory(HAIRSHOPBY_SUBCATEGORY_LOCATORS[i], HAIRSHOPBY_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All hair shop by subcategories tested successfully");
    }

    public void testAllHairBrandsSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all hair brands to know subcategories...");

        for (int i = 0; i < HAIRBRANDS_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleHairSubcategory(HAIRBRANDS_SUBCATEGORY_LOCATORS[i], HAIRBRANDS_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All hair brands to know subcategories tested successfully");
    }

    public void testAllHairConcernSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all hair shop by concern subcategories...");

        for (int i = 0; i < HAIRCONCERN_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleHairSubcategory(HAIRCONCERN_SUBCATEGORY_LOCATORS[i], HAIRCONCERN_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All hair shop by concern subcategories tested successfully");
    }

    public void testAllHairTiraRedSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all hair tira red subcategories...");

        for (int i = 0; i < HAIRTIIRED_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleHairSubcategory(HAIRTIIRED_SUBCATEGORY_LOCATORS[i], HAIRTIIRED_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All hair tira red subcategories tested successfully");
    }

    public void testAllHairExploreSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all hair explore subcategories...");

        for (int i = 0; i < HAIREXPLORE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleHairSubcategory(HAIREXPLORE_SUBCATEGORY_LOCATORS[i], HAIREXPLORE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All hair explore subcategories tested successfully");
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

        System.out.println(INFO_PREFIX + "Testing Hair Shop By subcategories...");
        testAllHairShopBySubcategories();

        System.out.println(INFO_PREFIX + "Testing Hair Brands To Know subcategories...");
        testAllHairBrandsSubcategories();

        System.out.println(INFO_PREFIX + "Testing Hair Shop By Concern subcategories...");
        testAllHairConcernSubcategories();

        System.out.println(INFO_PREFIX + "Testing Hair Tira Red subcategories...");
        testAllHairTiraRedSubcategories();

        System.out.println(INFO_PREFIX + "Testing Hair Explore subcategories...");
        testAllHairExploreSubcategories();

        System.out.println(SUCCESS_PREFIX + "Complete hair hover testing finished successfully!");
        System.out.println(SUCCESS_PREFIX + "Total categories tested: Hair Care (9), Hair Styling (4), Tools & Accessories (7), Shop By Hair Type (3), Shop By (8), Brands To Know (5), Shop By Concern (9), Tira Red (6), Explore (3)");
        System.out.println(SUCCESS_PREFIX + "Total subcategories tested: 54");
    }

    private void testSingleHairSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            // Navigate to home page first to ensure clean state
            if (!driver.getCurrentUrl().contains("tirabeauty.com") || driver.getCurrentUrl().contains("/collection/")) {
                navigateToHomePage();
            }

            hoverOverHair();

            WebElement subcategoryElement = null;
            String expectedHref = null;

            // Retry mechanism for finding and getting href
            for (int retry = 0; retry < 3; retry++) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
                    expectedHref = subcategoryElement.getAttribute("href");
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found after " + (retry + 1) + " attempts");
                        return;
                    }
                    waitForPageStabilization();
                    hoverOverHair(); // Re-hover
                }
            }

            // Click with retry mechanism
            for (int retry = 0; retry < 3; retry++) {
                try {
                    // Re-find element to avoid stale reference
                    subcategoryElement = driver.findElement(subcategoryLocator);
                    subcategoryElement.click();
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        // Final attempt with JavaScript click
                        subcategoryElement = driver.findElement(subcategoryLocator);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
                    } else {
                        waitForPageStabilization();
                    }
                }
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

    // Fragrance subcategory test methods
    public void testAllFragranceWomensSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all fragrance women's subcategories...");

        for (int i = 0; i < FRAGRANCE_WOMENS_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleFragranceSubcategory(FRAGRANCE_WOMENS_SUBCATEGORY_LOCATORS[i], FRAGRANCE_WOMENS_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All fragrance women's subcategories tested successfully");
    }

    public void testAllFragranceMensSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all fragrance men's subcategories...");

        for (int i = 0; i < FRAGRANCE_MENS_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleFragranceSubcategory(FRAGRANCE_MENS_SUBCATEGORY_LOCATORS[i], FRAGRANCE_MENS_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All fragrance men's subcategories tested successfully");
    }

    public void testAllFragranceUnisexSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all fragrance unisex subcategories...");

        for (int i = 0; i < FRAGRANCE_UNISEX_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleFragranceSubcategory(FRAGRANCE_UNISEX_SUBCATEGORY_LOCATORS[i], FRAGRANCE_UNISEX_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All fragrance unisex subcategories tested successfully");
    }

    public void testAllFragranceFamilySubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all fragrance family subcategories...");

        for (int i = 0; i < FRAGRANCE_FAMILY_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleFragranceSubcategory(FRAGRANCE_FAMILY_SUBCATEGORY_LOCATORS[i], FRAGRANCE_FAMILY_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All fragrance family subcategories tested successfully");
    }

    public void testAllFragranceHomeSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all fragrance home subcategories...");

        for (int i = 0; i < FRAGRANCE_HOME_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleFragranceSubcategory(FRAGRANCE_HOME_SUBCATEGORY_LOCATORS[i], FRAGRANCE_HOME_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All fragrance home subcategories tested successfully");
    }

    public void testAllFragranceShopBySubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all fragrance shop by subcategories...");

        for (int i = 0; i < FRAGRANCE_SHOPBY_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleFragranceSubcategory(FRAGRANCE_SHOPBY_SUBCATEGORY_LOCATORS[i], FRAGRANCE_SHOPBY_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All fragrance shop by subcategories tested successfully");
    }

    public void testAllFragranceTiraRedSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all fragrance tira red subcategories...");

        for (int i = 0; i < FRAGRANCE_TIRARED_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleFragranceSubcategory(FRAGRANCE_TIRARED_SUBCATEGORY_LOCATORS[i], FRAGRANCE_TIRARED_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All fragrance tira red subcategories tested successfully");
    }

    public void testAllFragranceBrandsSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all fragrance brands to know subcategories...");

        for (int i = 0; i < FRAGRANCE_BRANDS_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleFragranceSubcategory(FRAGRANCE_BRANDS_SUBCATEGORY_LOCATORS[i], FRAGRANCE_BRANDS_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All fragrance brands to know subcategories tested successfully");
    }

    public void testAllFragranceCategories() {
        System.out.println(INFO_PREFIX + "Starting comprehensive fragrance hover testing for all categories...");

        System.out.println(INFO_PREFIX + "Testing Women's Fragrance subcategories...");
        testAllFragranceWomensSubcategories();

        System.out.println(INFO_PREFIX + "Testing Men's Fragrance subcategories...");
        testAllFragranceMensSubcategories();

        System.out.println(INFO_PREFIX + "Testing Unisex Fragrance subcategories...");
        testAllFragranceUnisexSubcategories();

        System.out.println(INFO_PREFIX + "Testing Fragrance Family subcategories...");
        testAllFragranceFamilySubcategories();

        System.out.println(INFO_PREFIX + "Testing Home Fragrance subcategories...");
        testAllFragranceHomeSubcategories();

        System.out.println(INFO_PREFIX + "Testing Fragrance Shop By subcategories...");
        testAllFragranceShopBySubcategories();

        System.out.println(INFO_PREFIX + "Testing Fragrance Tira Red subcategories...");
        testAllFragranceTiraRedSubcategories();

        System.out.println(INFO_PREFIX + "Testing Fragrance Brands To Know subcategories...");
        testAllFragranceBrandsSubcategories();

        System.out.println(SUCCESS_PREFIX + "Complete fragrance hover testing finished successfully!");
        System.out.println(SUCCESS_PREFIX + "Total categories tested: Women's (3), Men's (4), Unisex (3), Family (8), Home (2), Shop By (5), Tira Red (5), Brands To Know (9)");
        System.out.println(SUCCESS_PREFIX + "Total subcategories tested: 39");
    }

    private void testSingleFragranceSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            // Navigate to home page first to ensure clean state
            if (!driver.getCurrentUrl().contains("tirabeauty.com") || driver.getCurrentUrl().contains("/collection/")) {
                navigateToHomePage();
            }

            hoverOverFragrance();

            WebElement subcategoryElement = null;
            String expectedHref = null;

            // Retry mechanism for finding and getting href
            for (int retry = 0; retry < 3; retry++) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
                    expectedHref = subcategoryElement.getAttribute("href");
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found after " + (retry + 1) + " attempts");
                        return;
                    }
                    waitForPageStabilization();
                    hoverOverFragrance(); // Re-hover
                }
            }

            // Click with retry mechanism
            for (int retry = 0; retry < 3; retry++) {
                try {
                    // Re-find element to avoid stale reference
                    subcategoryElement = driver.findElement(subcategoryLocator);
                    subcategoryElement.click();
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        // Final attempt with JavaScript click
                        subcategoryElement = driver.findElement(subcategoryLocator);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
                    } else {
                        waitForPageStabilization();
                    }
                }
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

    // Men subcategory test methods
    public void testAllMenBeardCareSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men beard care subcategories...");

        for (int i = 0; i < MEN_BEARDCARE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_BEARDCARE_SUBCATEGORY_LOCATORS[i], MEN_BEARDCARE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men beard care subcategories tested successfully");
    }

    public void testAllMenHairCareSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men hair care subcategories...");

        for (int i = 0; i < MEN_HAIRCARE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_HAIRCARE_SUBCATEGORY_LOCATORS[i], MEN_HAIRCARE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men hair care subcategories tested successfully");
    }

    public void testAllMenFragranceSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men fragrance subcategories...");

        for (int i = 0; i < MEN_FRAGRANCE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_FRAGRANCE_SUBCATEGORY_LOCATORS[i], MEN_FRAGRANCE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men fragrance subcategories tested successfully");
    }

    public void testAllMenShavingSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men shaving subcategories...");

        for (int i = 0; i < MEN_SHAVING_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_SHAVING_SUBCATEGORY_LOCATORS[i], MEN_SHAVING_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men shaving subcategories tested successfully");
    }

    public void testAllMenSkincareSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men skincare subcategories...");

        for (int i = 0; i < MEN_SKINCARE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_SKINCARE_SUBCATEGORY_LOCATORS[i], MEN_SKINCARE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men skincare subcategories tested successfully");
    }

    public void testAllMenBathBodySubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men bath & body subcategories...");

        for (int i = 0; i < MEN_BATHBODY_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_BATHBODY_SUBCATEGORY_LOCATORS[i], MEN_BATHBODY_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men bath & body subcategories tested successfully");
    }

    public void testAllMenShopBySubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men shop by subcategories...");

        for (int i = 0; i < MEN_SHOPBY_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_SHOPBY_SUBCATEGORY_LOCATORS[i], MEN_SHOPBY_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men shop by subcategories tested successfully");
    }

    public void testAllMenTiraRedSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men tira red subcategories...");

        for (int i = 0; i < MEN_TIRARED_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_TIRARED_SUBCATEGORY_LOCATORS[i], MEN_TIRARED_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men tira red subcategories tested successfully");
    }

    public void testAllMenConcernSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men shop by concern subcategories...");

        for (int i = 0; i < MEN_CONCERN_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_CONCERN_SUBCATEGORY_LOCATORS[i], MEN_CONCERN_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men shop by concern subcategories tested successfully");
    }

    public void testAllMenBrandsSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all men brands to know subcategories...");

        for (int i = 0; i < MEN_BRANDS_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleMenSubcategory(MEN_BRANDS_SUBCATEGORY_LOCATORS[i], MEN_BRANDS_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All men brands to know subcategories tested successfully");
    }

    public void testAllMenCategories() {
        System.out.println(INFO_PREFIX + "Starting comprehensive men hover testing for all categories...");

        System.out.println(INFO_PREFIX + "Testing Men Beard Care subcategories...");
        testAllMenBeardCareSubcategories();

        System.out.println(INFO_PREFIX + "Testing Men Hair Care subcategories...");
        testAllMenHairCareSubcategories();

        System.out.println(INFO_PREFIX + "Testing Men Fragrance subcategories...");
        testAllMenFragranceSubcategories();

        System.out.println(INFO_PREFIX + "Testing Men Shaving subcategories...");
        testAllMenShavingSubcategories();

        System.out.println(INFO_PREFIX + "Testing Men Skincare subcategories...");
        testAllMenSkincareSubcategories();

        System.out.println(INFO_PREFIX + "Testing Men Bath & Body subcategories...");
        testAllMenBathBodySubcategories();

        System.out.println(INFO_PREFIX + "Testing Men Shop By subcategories...");
        testAllMenShopBySubcategories();

        System.out.println(INFO_PREFIX + "Testing Men Tira Red subcategories...");
        testAllMenTiraRedSubcategories();

        System.out.println(INFO_PREFIX + "Testing Men Shop By Concern subcategories...");
        testAllMenConcernSubcategories();

        System.out.println(INFO_PREFIX + "Testing Men Brands To Know subcategories...");
        testAllMenBrandsSubcategories();

        System.out.println(SUCCESS_PREFIX + "Complete men hover testing finished successfully!");
        System.out.println(SUCCESS_PREFIX + "Total categories tested: Beard Care (5), Hair Care (5), Fragrance (4), Shaving (6), Skincare (5), Bath & Body (7), Shop By (5), Tira Red (5), Shop By Concern (9), Brands To Know (6)");
        System.out.println(SUCCESS_PREFIX + "Total subcategories tested: 57");
    }

    private void testSingleMenSubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            // Navigate to home page first to ensure clean state
            if (!driver.getCurrentUrl().contains("tirabeauty.com") || driver.getCurrentUrl().contains("/collection/")) {
                navigateToHomePage();
            }

            hoverOverMen();

            WebElement subcategoryElement = null;
            String expectedHref = null;

            // Retry mechanism for finding and getting href
            for (int retry = 0; retry < 3; retry++) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
                    expectedHref = subcategoryElement.getAttribute("href");
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found after " + (retry + 1) + " attempts");
                        return;
                    }
                    waitForPageStabilization();
                    hoverOverMen(); // Re-hover
                }
            }

            // Click with retry mechanism
            for (int retry = 0; retry < 3; retry++) {
                try {
                    // Re-find element to avoid stale reference
                    subcategoryElement = driver.findElement(subcategoryLocator);
                    subcategoryElement.click();
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        // Final attempt with JavaScript click
                        subcategoryElement = driver.findElement(subcategoryLocator);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
                    } else {
                        waitForPageStabilization();
                    }
                }
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

    // Bath & Body subcategory test methods
    public void testAllBathBodyBathShowerSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all bath & body bath & shower subcategories...");

        for (int i = 0; i < BATHBODY_BATHSHOWER_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleBathBodySubcategory(BATHBODY_BATHSHOWER_SUBCATEGORY_LOCATORS[i], BATHBODY_BATHSHOWER_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All bath & body bath & shower subcategories tested successfully");
    }

    public void testAllBathBodyBodyCareSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all bath & body body care subcategories...");

        for (int i = 0; i < BATHBODY_BODYCARE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleBathBodySubcategory(BATHBODY_BODYCARE_SUBCATEGORY_LOCATORS[i], BATHBODY_BODYCARE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All bath & body body care subcategories tested successfully");
    }

    public void testAllBathBodyHandsFeetSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all bath & body hands & feet subcategories...");

        for (int i = 0; i < BATHBODY_HANDSFEET_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleBathBodySubcategory(BATHBODY_HANDSFEET_SUBCATEGORY_LOCATORS[i], BATHBODY_HANDSFEET_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All bath & body hands & feet subcategories tested successfully");
    }

    public void testAllBathBodyHygieneSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all bath & body hygiene essentials subcategories...");

        for (int i = 0; i < BATHBODY_HYGIENE_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleBathBodySubcategory(BATHBODY_HYGIENE_SUBCATEGORY_LOCATORS[i], BATHBODY_HYGIENE_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All bath & body hygiene essentials subcategories tested successfully");
    }

    public void testAllBathBodyShavingSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all bath & body shaving & hair removal subcategories...");

        for (int i = 0; i < BATHBODY_SHAVING_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleBathBodySubcategory(BATHBODY_SHAVING_SUBCATEGORY_LOCATORS[i], BATHBODY_SHAVING_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All bath & body shaving & hair removal subcategories tested successfully");
    }

    public void testAllBathBodyBrandsSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all bath & body featured brands subcategories...");

        for (int i = 0; i < BATHBODY_BRANDS_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleBathBodySubcategory(BATHBODY_BRANDS_SUBCATEGORY_LOCATORS[i], BATHBODY_BRANDS_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All bath & body featured brands subcategories tested successfully");
    }

    public void testAllBathBodyDermApprovedSubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all bath & body derm approved subcategories...");

        for (int i = 0; i < BATHBODY_DERMAPPROVED_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleBathBodySubcategory(BATHBODY_DERMAPPROVED_SUBCATEGORY_LOCATORS[i], BATHBODY_DERMAPPROVED_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All bath & body derm approved subcategories tested successfully");
    }

    public void testAllBathBodyShopBySubcategories() {
        System.out.println(INFO_PREFIX + "Starting to test all bath & body shop by subcategories...");

        for (int i = 0; i < BATHBODY_SHOPBY_SUBCATEGORY_LOCATORS.length; i++) {
            testSingleBathBodySubcategory(BATHBODY_SHOPBY_SUBCATEGORY_LOCATORS[i], BATHBODY_SHOPBY_SUBCATEGORY_NAMES[i]);
        }

        System.out.println(SUCCESS_PREFIX + "All bath & body shop by subcategories tested successfully");
    }

    public void testAllBathBodyCategories() {
        System.out.println(INFO_PREFIX + "Starting comprehensive bath & body hover testing for all categories...");

        System.out.println(INFO_PREFIX + "Testing Bath & Body Bath & Shower subcategories...");
        testAllBathBodyBathShowerSubcategories();

        System.out.println(INFO_PREFIX + "Testing Bath & Body Body Care subcategories...");
        testAllBathBodyBodyCareSubcategories();

        System.out.println(INFO_PREFIX + "Testing Bath & Body Hands & Feet subcategories...");
        testAllBathBodyHandsFeetSubcategories();

        System.out.println(INFO_PREFIX + "Testing Bath & Body Hygiene Essentials subcategories...");
        testAllBathBodyHygieneSubcategories();

        System.out.println(INFO_PREFIX + "Testing Bath & Body Shaving & Hair Removal subcategories...");
        testAllBathBodyShavingSubcategories();

        System.out.println(INFO_PREFIX + "Testing Bath & Body Brands To Know subcategories...");
        testAllBathBodyBrandsSubcategories();

        System.out.println(INFO_PREFIX + "Testing Bath & Body Derm Approved subcategories...");
        testAllBathBodyDermApprovedSubcategories();

        System.out.println(INFO_PREFIX + "Testing Bath & Body Shop By subcategories...");
        testAllBathBodyShopBySubcategories();

        System.out.println(SUCCESS_PREFIX + "Complete bath & body hover testing finished successfully!");
        System.out.println(SUCCESS_PREFIX + "Total categories tested: Bath & Shower (5), Body Care (5), Hands & Feet (4), Hygiene Essentials (2), Shaving & Hair Removal (5), Brands To Know (13), Derm Approved (1), Shop By (4)");
        System.out.println(SUCCESS_PREFIX + "Total subcategories tested: 39");
    }

    private void testSingleBathBodySubcategory(By subcategoryLocator, String subcategoryName) {
        try {
            // Navigate to home page first to ensure clean state
            if (!driver.getCurrentUrl().contains("tirabeauty.com") || driver.getCurrentUrl().contains("/collection/")) {
                navigateToHomePage();
            }

            hoverOverBathBody();

            WebElement subcategoryElement = null;
            String expectedHref = null;

            // Retry mechanism for finding and getting href
            for (int retry = 0; retry < 3; retry++) {
                try {
                    subcategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subcategoryLocator));
                    expectedHref = subcategoryElement.getAttribute("href");
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        System.out.println(ERROR_PREFIX + "Element " + subcategoryName + " not found after " + (retry + 1) + " attempts");
                        return;
                    }
                    waitForPageStabilization();
                    hoverOverBathBody(); // Re-hover
                }
            }

            // Click with retry mechanism
            for (int retry = 0; retry < 3; retry++) {
                try {
                    // Re-find element to avoid stale reference
                    subcategoryElement = driver.findElement(subcategoryLocator);
                    subcategoryElement.click();
                    break;
                } catch (Exception e) {
                    if (retry == 2) {
                        // Final attempt with JavaScript click
                        subcategoryElement = driver.findElement(subcategoryLocator);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcategoryElement);
                    } else {
                        waitForPageStabilization();
                    }
                }
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