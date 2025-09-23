package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;

@Listeners({ExtentReportListener.class, TestListener.class, listeners.TestSummaryListener.class})
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

//    @Test(priority = 6)
//    public void testSkinSubcategoriesHrefValidation() {
//        System.out.println("🔍 Starting test: testSkinSubcategoriesHrefValidation");
//        System.out.println("🔍 Testing all skin subcategories:");
//        System.out.println("Cleansers & Exfoliators: Face Washes & Cleansers, Face Scrub & Exfoliator, Scrubs & Exfoliators, Face Wipes, Makeup Remover");
//        System.out.println("Eye Care: Eye Creams & Serums, Eye Mask, Eye Makeup Remover");
//        System.out.println("Sets & Bundles: Facial Sets, Gift Sets");
//        System.out.println("Lip Care: Lip Balm, Lip Scrub, Lip Mask, Lip Oils");
//        System.out.println("Mask: Masks & Peels, Sheet Mask");
//        System.out.println("Moisturizer: Face Moisturizer, Night Cream, Face Oil");
//        System.out.println("Toners & Mist: Toner, Mist");
//        System.out.println("Sun Care: Sunscreen");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//            homePage.testAllSkinCategories();
//            System.out.println("✅ Test PASSED: testSkinSubcategoriesHrefValidation - Complete skin hover testing completed successfully");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testSkinSubcategoriesHrefValidation - " + e.getMessage());
//            throw e;
//        }
//    }

    @Test(priority = 7)
    public void testHairSubcategoriesHrefValidation() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        homePage.testAllHairCategories();
    }
//
//    @Test(priority = 8)
//    public void testFragranceSubcategoriesHrefValidation() {
//        System.out.println("🔍 Starting test: testFragranceSubcategoriesHrefValidation");
//        System.out.println("🔍 Testing all fragrance subcategories:");
//        System.out.println("Women's Fragrance: Perfume (EDT & EDP), Body Mists & Sprays, Deodorants & Roll-Ons");
//        System.out.println("Men's Fragrance: Perfume (EDT & EDP), Body Mists & Sprays, Deodorants & Roll-Ons, Colognes & After Shaves");
//        System.out.println("Unisex Fragrance: Unisex Perfumes, Unisex Mists & Sprays, Unisex Deodorants & Roll-Ons");
//        System.out.println("Fragrance Family: Floral, Fruity, Spicy, Woody, Fresh, Aqua, Citrus, Musky");
//        System.out.println("Home Fragrance: Candle, Diffuser");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//            homePage.testAllFragranceCategories();
//            System.out.println("✅ Test PASSED: testFragranceSubcategoriesHrefValidation - Complete fragrance hover testing completed successfully");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testFragranceSubcategoriesHrefValidation - " + e.getMessage());
//            throw e;
//        }
//    }
////
//    @Test(priority = 9)
//    public void testMenSubcategoriesHrefValidation() {
//        System.out.println("🔍 Starting test: testMenSubcategoriesHrefValidation");
//        System.out.println("🔍 Testing all men subcategories:");
//        System.out.println("Beard Care: Beard Oil, Beard Wash, Beard Balm, Moustache Wax, Beard Trimmer");
//        System.out.println("Hair Care: Shampoo, Conditioner, Hair Oil, Hair Wax, Hair Gel");
//        System.out.println("Fragrance: Perfume, Deodorant, Body Spray, Attar");
//        System.out.println("Shaving: Razor, Shaving Cream, After Shave, Pre Shave, Shaving Brush");
//        System.out.println("Skincare: Face Wash, Moisturizer, Sunscreen, Face Mask, Eye Cream");
//        System.out.println("Bath & Body: Body Wash, Body Lotions, Soaps, Body Scrubs, Talcum Powder");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//            homePage.testAllMenCategories();
//            System.out.println("✅ Test PASSED: testMenSubcategoriesHrefValidation - Complete men hover testing completed successfully");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testMenSubcategoriesHrefValidation - " + e.getMessage());
//            throw e;
//        }
//    }
////
//    @Test(priority = 10)
//    public void testBathBodySubcategoriesHrefValidation() {
//        System.out.println("🔍 Starting test: testBathBodySubcategoriesHrefValidation");
//        System.out.println("🔍 Testing all bath & body subcategories:");
//        System.out.println("Bath & Shower: Body Wash, Body Scrubs, Bath Salts, Bubble Bath, Bath Bombs, Shower Gel");
//        System.out.println("Body Care: Body Lotions, Body Butters, Body Oils, Body Cream, Body Mist, Body Mousse");
//        System.out.println("Hands & Feet: Hand Cream, Foot Cream, Hand Lotion, Cuticle Care, Foot Scrub, Hand Wash");
//        System.out.println("Hygiene Essentials: Intimate Wash, Deodorant, Sanitizer, Wet Wipes, Talcum Powder");
//        System.out.println("Shaving & Hair Removal: Shaving Cream, After Shave, Hair Removal Cream, Shaving Gel, Razors");
//        System.out.println("Brands To Know: Treaclemoon, Vaseline, The Body Shop, Aveeno, L'Occitane, Dream Immerse Play, The Love Co, OUAI");
//        try {
//            HomePage homePage = new HomePage(driver);
//            homePage.waitForPageToLoad();
//            homePage.testAllBathBodyCategories();
//            System.out.println("✅ Test PASSED: testBathBodySubcategoriesHrefValidation - Complete bath & body hover testing completed successfully");
//        } catch (Exception e) {
//            System.out.println("❌ Test FAILED: testBathBodySubcategoriesHrefValidation - " + e.getMessage());
//            throw e;
//        }
//    }

}

