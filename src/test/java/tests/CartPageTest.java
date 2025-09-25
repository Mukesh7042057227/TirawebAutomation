package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

@Listeners({ExtentReportListener.class, TestListener.class, listeners.TestSummaryListener.class})
public class CartPageTest extends BaseTest {

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
//    public void plpPageTestCase() throws InterruptedException {
//        //Calling PLP page
//        PlpPage plp = new PlpPage(driver);
//        plp.validatePlpPage();
//        plp.sortBy();
//        plp.clickOnProduct();
//    }
//    @Test(priority = 4)
//    public void productPageTestCase() throws InterruptedException {
//        ProductPage product = new ProductPage(driver);
//        product.validatePdpPage();
//        product.setAddToCartBtn();
//    }
//    @Test(priority = 1)
//    public void cartPageTestCaseWithoutLogin() throws InterruptedException {
//        //calling Cart Page
//        CartPage cart= new CartPage(driver);
//        ProductPage product = new ProductPage(driver);
//        LoginPage login = new LoginPage(driver);
//        HomePage home = new HomePage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        home.assertHomePageLoaded();
//        category.navigateToLipstickCategory();
//        plp.clickOnProduct();
//        product.setAddToCartBtn();
//        cart.openCart();
//        cart.validateCartPageWithLogin();
//        cart.proceedToCheckout();
//        login.loginWithValidDetail();
//        cart.validateCartPageWithLogin();
//    }
//    @Test(priority = 2)
//    public void cartPageTestCaseWithLogin() throws InterruptedException {
//        //calling Cart Page
//        CartPage cart= new CartPage(driver);
//        ProductPage product = new ProductPage(driver);
//        LoginPage login = new LoginPage(driver);
//        HomePage home = new HomePage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        home.assertHomePageLoaded();
//        home.clickLoginIcon();
//        login.loginWithValidDetail();
//        category.navigateToLipstickCategory();
//        plp.clickOnProduct();
//        product.setAddToCartBtn();
//        cart.openCart();
//        cart.validateCartPageWithLogin();
//    }
    @Test(priority = 3)
    public void cartPageTestCaseIncreaseQty() throws InterruptedException {
        //calling Cart Page
        CartPage cart= new CartPage(driver);
        ProductPage product = new ProductPage(driver);
        HomePage home = new HomePage(driver);
        PlpPage plp = new PlpPage(driver);
        CategoryPage category= new CategoryPage(driver);
        home.assertHomePageLoaded();
        category.navigateToLipstickCategory();
        plp.clickOnProduct();
        product.setAddToCartBtn();
        cart.openCart();
        cart.InncreaseQtyOnCart();
    }
//    @Test(priority = 2)
//    public void cartPageTestCaseDecreaseQty() throws InterruptedException {
//        //calling Cart Page
//        CartPage cart= new CartPage(driver);
//        ProductPage product = new ProductPage(driver);
//        LoginPage login = new LoginPage(driver);
//        HomePage home = new HomePage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        home.assertHomePageLoaded();
//        category.navigateToLipstickCategory();
//        plp.sortBy();
//        plp.clickOnProduct();
//        product.setAddToCartBtn();
//        cart.openCart();
//        cart.validateCartPageWithOutLogin();
//        cart.proceedToCheckout();
//    }
//    @Test(priority = 2)
//    public void cartPageTestCaseRemoveProduct() throws InterruptedException {
//        //calling Cart Page
//        CartPage cart= new CartPage(driver);
//        ProductPage product = new ProductPage(driver);
//        LoginPage login = new LoginPage(driver);
//        HomePage home = new HomePage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        home.assertHomePageLoaded();
//        category.navigateToLipstickCategory();
//        plp.sortBy();
//        plp.clickOnProduct();
//        product.setAddToCartBtn();
//        cart.openCart();
//        cart.validateCartPageWithOutLogin();
//        cart.proceedToCheckout();
//    }
//    @Test(priority = 2)
//    public void cartPageTestCaseMoveToWishlist() throws InterruptedException {
//        //calling Cart Page
//        CartPage cart= new CartPage(driver);
//        ProductPage product = new ProductPage(driver);
//        LoginPage login = new LoginPage(driver);
//        HomePage home = new HomePage(driver);
//        PlpPage plp = new PlpPage(driver);
//        CategoryPage category= new CategoryPage(driver);
//        home.assertHomePageLoaded();
//        category.navigateToLipstickCategory();
//        plp.sortBy();
//        plp.clickOnProduct();
//        product.setAddToCartBtn();
//        cart.openCart();
//        cart.validateCartPageWithOutLogin();
//        cart.proceedToCheckout();
//    }
}