package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

@Listeners({ExtentReportListener.class, TestListener.class})
public class CartPageTest extends BaseTest {

    @Test(priority = 1)
    public void homePageTestCase() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.assertHomePageLoaded();
        home.validateCategoryNavigation();
        home.clickLoginIcon();
    }
    @Test(priority = 2)
    public void loginPageTestCase() throws InterruptedException
    {
        LoginPage login = new LoginPage(driver);
        login.loginPageValidation();
        login.mockLogin();
    }
    @Test(priority = 3)
    public void plpPageTestCase() throws InterruptedException {
        //Calling PLP page
        PlpPage plp = new PlpPage(driver);
        plp.validatePlpPage();
        plp.sortBy();
        plp.clickOnProduct();
    }
    @Test(priority = 4)
    public void productPageTestCase() throws InterruptedException {
        ProductPage product = new ProductPage(driver);
        product.validatePdpPage();
        product.setAddToCartBtn();
    }
    @Test(priority = 5)
    public void cartPageTestCase() throws InterruptedException {
        //calling Cart Page
        CartPage cart= new CartPage(driver);
        cart.openCart();
        cart.validateCartPage();
        cart.proceedToCheckout();
    }
}