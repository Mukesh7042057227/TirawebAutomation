package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;

@Listeners({ExtentReportListener.class, TestListener.class})
public class HomePageTest extends BaseTest {

//    @Test(priority = 1)
//    public void verifyHomepageLoadsSuccessfully() {
//        System.out.println("🔍 Starting test: verifyHomepageLoadsSuccessfully");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.assertHomePageLoaded();
//            homePage.waitForPageToLoad();
//            System.out.println("✅ Test PASSED: verifyHomepageLoadsSuccessfully");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: verifyHomepageLoadsSuccessfully - " + e.getMessage());
//            throw e;
//        }
//    }
//
//    @Test(priority = 2)
//    public void testSearchWithResults() {
//        System.out.println("🔍 Starting test: testSearchWithResults");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//
//            SearchPage searchPage = new SearchPage(driver);
//            searchPage.search("lipstick");
//            searchPage.assertSearchResultsPresent();
//
//            System.out.println("✅ Test PASSED: testSearchWithResults");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testSearchWithResults - " + e.getMessage());
//            throw e;
//        }
//    }
//
//    @Test(priority = 2)
//    public void testSearchWithNoResults() {
//        System.out.println("🔍 Starting test: testSearchWithNoResults");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//
//            SearchPage searchPage = new SearchPage(driver);
//            searchPage.search("invalid-search-123");
//            searchPage.assertNoResultMessageDisplayed();
//
//            System.out.println("✅ Test PASSED: testSearchWithNoResults");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testSearchWithNoResults - " + e.getMessage());
//            throw e;
//        }
//    }
//
//    @Test(priority = 2)
//    public void testSearchFunctionality() {
//        System.out.println("🔍 Starting test: testSearchFunctionality");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//
//            List<String> keywords = Arrays.asList("lipstick", "moisturizer", "kajal", "foundation");
//            SearchPage searchPage = new SearchPage(driver);
//            searchPage.searchMultipleKeywords(keywords);
//
//            System.out.println("✅ Test PASSED: testSearchFunctionality");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testSearchFunctionality - " + e.getMessage());
//            throw e;
//        }
//    }
//
//    @Test(priority = 3)
//    public void testNavigationMenuLinks() {
//        System.out.println("🔍 Starting test: testNavigationMenuLinks");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//
//            // Test all main navigation links with URL validation
//            System.out.println("🔗 Testing Tira Red navigation with href validation");
//            homePage.clickTiraRedNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Offers navigation with href validation");
//            homePage.clickOffersNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Top Shelf navigation with href validation");
//            homePage.clickTopShelfNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing For You navigation with href validation");
//            homePage.clickForYouNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing What's New navigation with href validation");
//            homePage.clickWhatsNewNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Makeup navigation with href validation");
//            homePage.clickMakeupNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Skin navigation with href validation");
//            homePage.clickSkinNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Hair navigation with href validation");
//            homePage.clickHairNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Fragrance navigation with href validation");
//            homePage.clickFragranceNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Men navigation with href validation");
//            homePage.clickMenNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Bath & Body navigation with href validation");
//            homePage.clickBathBodyNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Tools & Appliances navigation with href validation");
//            homePage.clickToolsAppliancesNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Mom & Baby navigation with href validation");
//            homePage.clickMomBabyNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Wellness navigation with href validation");
//            homePage.clickWellnessNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Minis navigation with href validation");
//            homePage.clickMinisNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Homegrown navigation with href validation");
//            homePage.clickHomegrownNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("🔗 Testing Gifts navigation with href validation");
//            homePage.clickGiftsNavigation();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("✅ Test PASSED: testNavigationMenuLinks");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testNavigationMenuLinks - " + e.getMessage());
//            throw e;
//        }
//    }
//
//    @Test(priority = 4)
//    public void testHeaderActions() {
//        System.out.println("🔍 Starting test: testHeaderActions");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//
//            // Test header elements
//            homePage.clickLogo();
//            homePage.waitForPageToLoad();
//
//            homePage.clickCartIcon();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            homePage.clickProfileIconAndValidateLoginRedirect();
//            driver.navigate().back();
//            homePage.waitForPageToLoad();
//
//            System.out.println("✅ Test PASSED: testHeaderActions");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testHeaderActions - " + e.getMessage());
//            throw e;
//        }
//    }

//    @Test(priority = 5)
//    public void testMakeupSubcategoriesHrefValidation() {
//        System.out.println("🔍 Starting test: testMakeupSubcategoriesHrefValidation");
//        System.out.println("🔍 Testing all makeup subcategories:");
//        System.out.println("Face: Blush, Bronzer, Compact, Concealers & Correctors, Contour, Foundation, Highlighters & Illuminators, Setting Powder, Makeup Remover, Primer, Setting Spray, BB & CC Creams, Loose Powder");
//        System.out.println("Eye: Eye Makeup Remover, Eyebrow Enhancer, False Eyelashes, Eyeliner, Eye Shadow, Kajal & Kohls, Mascara, Under Eye Concealer, Eye Bases & Primers");
//        System.out.println("Lip: Lip Balms, Lip Gloss, Lip Liner, Lipstick, Liquid Lipstick");
//        System.out.println("Tools & Brushes: Brush Sets, Eye Brushes & Eyelash Curlers, Face Brush, Lip Brush, Makeup Pouch, Sharpeners & Tweezers, Sponges & Blenders, Mirrors");
//        System.out.println("Kits & Palettes: Eyeshadow Palettes, Face Makeup Palettes, Makeup Kits & Sets");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//            homePage.testAllMakeupCategories();
//            System.out.println("✅ Test PASSED: testMakeupSubcategoriesHrefValidation - Complete makeup hover testing completed successfully");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testMakeupSubcategoriesHrefValidation - " + e.getMessage());
//            throw e;
//        }
//    }

    @Test(priority = 6)
    public void testSkinSubcategoriesHrefValidation() {
        System.out.println("🔍 Starting test: testSkinSubcategoriesHrefValidation");
        System.out.println("🔍 Testing all skin subcategories:");
        System.out.println("Cleansers & Exfoliators: Face Washes & Cleansers, Face Scrub & Exfoliator, Scrubs & Exfoliators, Face Wipes, Makeup Remover");
        System.out.println("Eye Care: Eye Creams & Serums, Eye Mask, Eye Makeup Remover");
        System.out.println("Sets & Bundles: Facial Sets, Gift Sets");
        System.out.println("Lip Care: Lip Balm, Lip Scrub, Lip Mask, Lip Oils");
        System.out.println("Mask: Masks & Peels, Sheet Mask");
        System.out.println("Moisturizer: Face Moisturizer, Night Cream, Face Oil");
        System.out.println("Toners & Mist: Toner, Mist");
        System.out.println("Sun Care: Sunscreen");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.waitForPageToLoad();
            homePage.testAllSkinCategories();
            System.out.println("✅ Test PASSED: testSkinSubcategoriesHrefValidation - Complete skin hover testing completed successfully");
        } catch (Exception e) {
            System.out.println("❌ Test FAILED: testSkinSubcategoriesHrefValidation - " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 7)
    public void testHairSubcategoriesHrefValidation() {
        System.out.println("🔍 Starting test: testHairSubcategoriesHrefValidation");
        System.out.println("🔍 Testing all hair subcategories:");
        System.out.println("Hair Care: Shampoo, Conditioner, Hair Mask, Hair Oil, Hair Serum, Scalp Care, Dry Shampoo");
        System.out.println("Hair Styling: Hair Gel, Hair Wax, Hair Spray, Hair Cream, Hair Mousse, Heat Protectant");
        System.out.println("Hair Color: Permanent Hair Color, Semi-Permanent Color, Hair Dye, Root Touch Up, Color Protection");
        System.out.println("Hair Tools: Hair Brushes, Combs, Hair Dryer, Hair Straightener, Curling Iron, Hair Accessories");
        System.out.println("Hair Treatments: Anti Hair Fall, Dandruff Treatment, Hair Growth, Hair Repair");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.waitForPageToLoad();
            homePage.testAllHairCategories();
            System.out.println("✅ Test PASSED: testHairSubcategoriesHrefValidation - Complete hair hover testing completed successfully");
        } catch (Exception e) {
            System.out.println("❌ Test FAILED: testHairSubcategoriesHrefValidation - " + e.getMessage());
            throw e;
        }
    }

}

