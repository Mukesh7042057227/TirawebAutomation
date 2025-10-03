package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

@Listeners({ExtentReportListener.class, TestListener.class, listeners.TestSummaryListener.class})
public class ProductPageTest extends BaseTest {

//    @Test(priority = 1)
//    public void productPageTestCaseAddtoBagWithLogin() throws InterruptedException {
//        HomePage home = new HomePage(driver);
//        ProductPage product = new ProductPage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        LoginPage login = new LoginPage(driver);
//        home.assertHomePageLoaded();
//        home.validateCategoryNavigation();
//        home.clickLoginIcon();
//        login.loginWithValidDetail();
//        category.navigateToLipstickCategory();
//        plp.validatePlpPage();
//        plp.sortBy();
//        plp.clickOnProduct();
//        product.validatePdpPage();
//        product.setAddToCartBtn();
//    }
//    @Test(priority = 2)
//    public void productPageTestCaseAddtoBagWithOutLogin() throws InterruptedException {
//        HomePage home = new HomePage(driver);
//        ProductPage product = new ProductPage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        home.assertHomePageLoaded();
//        home.validateCategoryNavigation();
//        category.navigateToLipstickCategory();
//        plp.validatePlpPage();
//        plp.sortBy();
//        plp.clickOnProduct();
//        product.validatePdpPage();
//        product.setAddToCartBtn();
//    }
    @Test(priority = 3)
    public void productPageTestCaseWishlistWithLogin() throws InterruptedException {
        HomePage home = new HomePage(driver);
        ProductPage product = new ProductPage(driver);
        PlpPage plp = new PlpPage(driver);
        CategoryPage category= new CategoryPage(driver);
        LoginPage login = new LoginPage(driver);
        home.assertHomePageLoaded();
        home.validateCategoryNavigation();
        home.clickLoginIcon();
        login.loginWithValidDetail();
        category.navigateToLipstickCategory();
        plp.validatePlpPage();
        plp.sortBy();
        plp.clickOnProduct();
        product.validatePdpPage();
       product.setSaveToWishlist();
       product.validateSaveToWishlist();
    }
//    @Test(priority = 4)
//    public void productPageTestCaseWishlistWithOutLogin() throws InterruptedException {
//        HomePage home = new HomePage(driver);
//        ProductPage product = new ProductPage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        LoginPage login = new LoginPage(driver);
//        home.assertHomePageLoaded();
//        home.validateCategoryNavigation();
//        home.clickLoginIcon();
//        login.loginWithValidDetail();
//        category.navigateToLipstickCategory();
//        plp.validatePlpPage();
//        plp.sortBy();
//        plp.clickOnProduct();
//        product.validatePdpPage();
//        product.setAddToCartBtn();
//    }
}