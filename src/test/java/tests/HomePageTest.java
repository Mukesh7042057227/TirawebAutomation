package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import java.util.Arrays;
import java.util.List;

@Listeners({ExtentReportListener.class, TestListener.class, listeners.TestSummaryListener.class})
public class HomePageTest extends BaseTest {

    @Test(priority = 1)
    public void verifyHomepageLoadsSuccessfully() {
        HomePage homePage = new HomePage(driver);
        homePage.assertHomePageLoaded();
        homePage.waitForPageToLoad();
    }

    @Test(priority = 2)
    public void testSearchWithResults() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();

        SearchPage searchPage = new SearchPage(driver);
        searchPage.search("lipstick");
        searchPage.assertSearchResultsPresent();
    }

    @Test(priority = 3)
    public void testSearchWithNoResults() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();

        SearchPage searchPage = new SearchPage(driver);
        searchPage.search("invalid-search-123");
        searchPage.assertNoResultMessageDisplayed();
    }

    @Test(priority = 4)
    public void testSearchFunctionality() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();

        List<String> keywords = Arrays.asList("lipstick", "moisturizer", "kajal", "foundation");
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchMultipleKeywords(keywords);
    }

    @Test(priority = 5)
    public void testNavigationMenuLinks() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();

        // Test all main navigation links with URL validation
        homePage.clickTiraRedNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickOffersNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickTopShelfNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickForYouNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickWhatsNewNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickMakeupNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickSkinNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickHairNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickFragranceNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickMenNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickBathBodyNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickToolsAppliancesNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickMomBabyNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickWellnessNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickMinisNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickHomegrownNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();

        homePage.clickGiftsNavigation();
        driver.navigate().back();
        homePage.waitForPageToLoad();
    }

    @Test(priority = 6)
    public void testHeaderActions() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();

        // Test header elements
        homePage.clickLogo();
        homePage.waitForPageToLoad();

        homePage.clickCartIcon();
        driver.navigate().back();
        homePage.waitForPageToLoad();
    }

    @Test(priority = 7)
    public void testMakeupSubcategoriesHrefValidation() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        homePage.testAllMakeupCategories();
    }

    @Test(priority = 8)
    public void testSkinSubcategoriesHrefValidation() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        homePage.testAllSkinCategories();
    }

    @Test(priority = 9)
    public void testHairSubcategoriesHrefValidation() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        homePage.testAllHairCategories();
    }

    @Test(priority = 10)
    public void testFragranceSubcategoriesHrefValidation() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        homePage.testAllFragranceCategories();
    }

    @Test(priority = 11)
    public void testMenSubcategoriesHrefValidation() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        homePage.testAllMenCategories();
    }

    @Test(priority = 12)
    public void testBathBodySubcategoriesHrefValidation() {
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        homePage.testAllBathBodyCategories();
    }

}

