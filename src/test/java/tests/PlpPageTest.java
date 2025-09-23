package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;
import pages.LoginPage;
import pages.PlpPage;

@Listeners({ExtentReportListener.class, TestListener.class, listeners.TestSummaryListener.class})
public class PlpPageTest extends BaseTest {

//    @Test(priority = 1)
//    public void homePageTestCase() throws InterruptedException {
//        HomePage home = new HomePage(driver);
//        home.assertHomePageLoaded();
//        home.validateCategoryNavigation();
//        home.clickLoginIcon();
//    }
//    @Test(priority = 2)
//    public void loginPageTestCase() throws InterruptedException
//    {
//        LoginPage login = new LoginPage(driver);
//        login.loginPageValidation();
//        login.loginWithValidDetail();
//    }
//    @Test(priority = 3)
//    public void categoryPageTestCase() throws InterruptedException {
//        //Calling Category page
//        CategoryPage category= new CategoryPage(driver);
//        category.navigateToLipstickCategory();
//
//    }
//    @Test(priority = 1)
//    public void plpPageTestCase() throws InterruptedException {
//        HomePage home = new HomePage(driver);
//        home.assertHomePageLoaded();
//        home.clickLoginIcon();
//        LoginPage login = new LoginPage(driver);
//        login.loginPageValidation();
//        login.loginWithValidDetail();
//        CategoryPage category= new CategoryPage(driver);
//        category.navigateToLipstickCategory();
//        PlpPage plp = new PlpPage(driver);
//        plp.validatePlpPage();
//        plp.sortBy();
//        plp.clickOnProduct();
//    }
//    @Test(priority = 2)
//    public void allSortingzCondition() throws InterruptedException {
//        HomePage home = new HomePage(driver);
//        home.assertHomePageLoaded();
//        CategoryPage category= new CategoryPage(driver);
//        category.navigateToLipstickCategory();
//        PlpPage plp = new PlpPage(driver);
//        plp.validatePlpPage();
//        //plp.AnkushCode();
//        plp.validateSortOptions();
//       //plp.getSortOptionCount(driver);
//      // System.out.println(plp.getSortOptionCount(driver));
//    }

    @Test(priority = 3)
    public void validatePincodeSidebarFunctionality() {
        System.out.println("🔍 Starting test: validatePincodeSidebarFunctionality");
        try {
            HomePage home = new HomePage(driver);
            home.assertHomePageLoaded();
            CategoryPage category= new CategoryPage(driver);
            category.navigateToLipstickCategory();
            PlpPage plp = new PlpPage(driver);
            plp.validatePlpPage();
            plp.clickOnPincodeAndVerifySidebar();

            System.out.println("✅ Test PASSED: validatePincodeSidebarFunctionality");
        } catch (Exception e) {
            System.out.println("❌ Test FAILED: validatePincodeSidebarFunctionality - " + e.getMessage());
            throw e;
        }
    }
}