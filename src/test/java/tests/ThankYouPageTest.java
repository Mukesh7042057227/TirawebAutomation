package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

@Listeners({ExtentReportListener.class, TestListener.class, listeners.TestSummaryListener.class})
public class ThankYouPageTest extends BaseTest {

//    @Test(priority = 1)
//    public void homePageTestCase() throws InterruptedException {
//        HomePage home = new HomePage(driver);
//        home.assertHomePageLoaded();
//        home.validateCategoryNavigation();
//        home.clickLoginIcon();
//
//    }
//    @Test(priority = 2)
//    public void loginPageTestCase() throws InterruptedException
//    {
//        LoginPage login = new LoginPage(driver);
//        HomePage home = new HomePage(driver);
//        home.clickLoginIcon();
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
//    @Test(priority = 4)
//    public void plpPageTestCase() throws InterruptedException {
//        //Calling PLP page
//        PlpPage plp = new PlpPage(driver);
//        LoginPage login = new LoginPage(driver);
//        HomePage home = new HomePage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        home.clickLoginIcon();
//        login.loginPageValidation();
//        login.loginWithValidDetail();
//        category.navigateToLipstickCategory();
//        plp.validatePlpPage();
//        plp.sortBy();
//        plp.clickOnProduct();
//    }
//    @Test(priority = 5)
//    public void productPageTestCase() throws InterruptedException {
//        ProductPage product = new ProductPage(driver);
//        LoginPage login = new LoginPage(driver);
//        HomePage home = new HomePage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        home.clickLoginIcon();
//        login.loginWithValidDetail();
//        category.navigateToLipstickCategory();
//        plp.sortBy();
//        plp.clickOnProduct();
//        plp.sortBy();
//        plp.clickOnProduct();
//        product.validatePdpPage();
//        product.setAddToCartBtn();
//    }
//    @Test(priority = 6)
//    public void cartPageTestCase() throws InterruptedException {
//        //calling Cart Page
//        CartPage cart= new CartPage(driver);
//        ProductPage product = new ProductPage(driver);
//        LoginPage login = new LoginPage(driver);
//        HomePage home = new HomePage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        home.clickLoginIcon();
//        login.loginWithValidDetail();
//        category.navigateToLipstickCategory();
//        plp.sortBy();
//        plp.clickOnProduct();
//        plp.sortBy();
//        plp.clickOnProduct();
//        product.setAddToCartBtn();
//        cart.openCart();
//        cart.validateCartPage();
//        cart.proceedToCheckout();
//
//    }
    @Test(priority = 7)
    public void checkoutPageTestCase() throws InterruptedException {

        CheckoutPage checkout = new CheckoutPage(driver);
        CartPage cart= new CartPage(driver);
        ProductPage product = new ProductPage(driver);
        LoginPage login = new LoginPage(driver);
        HomePage home = new HomePage(driver);
        PlpPage plp = new PlpPage(driver);
        CategoryPage category= new CategoryPage(driver);
        ThankYouPage thankYou = new ThankYouPage(driver);
        home.assertHomePageLoaded();
        category.navigateToLipstickCategory();
        plp.sortBy();
        plp.clickOnProduct();
        plp.sortBy();
        plp.clickOnProduct();
        product.setAddToCartBtn();
        cart.openCart();
        cart.proceedToCheckout();
       // home.clickLoginIcon();
        login.loginWithValidDetail();
        cart.proceedToCheckout();
        checkout.validateCheckoutPage();
        checkout.reviewOrder();
        checkout.scrollSidebarTillElementVisible();
        checkout.clickOnBuyNow();
        thankYou.isThankYouPageLoaded();
        thankYou.myOrderClick();
    }
//    @Test(priority = 8)
//    public void thankYouPageTestCase() throws InterruptedException {
//        //Thank you page calling
//        ThankYouPage thankYou = new ThankYouPage(driver);
//        thankYou.isThankYouPageLoaded();
//        thankYou.myOrderClick();
//    }
}

