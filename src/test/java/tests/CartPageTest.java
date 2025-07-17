package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

@Listeners({ExtentReportListener.class, TestListener.class})
public class CartPageTest extends BaseTest {

    @Test()
    public void homePageTestCase() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.assertHomePageLoaded();
        home.validateCategoryNavigation();
    }
    @Test(dependsOnMethods = {"homePageTestCase"})
    public void loginPageTestCase() throws InterruptedException
    {
        LoginPage login = new LoginPage(driver);
        login.loginPageValidation();
        login.mockLogin();
    }
    @Test(dependsOnMethods = {"loginPageTestCase"})
    public void categoryPageTestCase() throws InterruptedException {
        //Calling Category page
        CategoryPage category= new CategoryPage(driver);
        category.navigateToLipstickCategory();

    }
    @Test(dependsOnMethods = {"categoryPageTestCase"})
    public void plpPageTestCase() throws InterruptedException {
        //Calling PLP page
        PlpPage plp = new PlpPage(driver);
        plp.validatePlpPage();
        plp.sortBy();
        plp.clickOnProduct();
    }
    @Test(dependsOnMethods = {"plpPageTestCase"})
    public void productPageTestCase() throws InterruptedException {
        ProductPage product = new ProductPage(driver);
        product.validatePdpPage();
        product.setAddToCartBtn();
    }
    @Test(dependsOnMethods = {"productPageTestCase"})
    public void cartPageTestCase() throws InterruptedException {
        //calling Cart Page
        CartPage cart= new CartPage(driver);
        cart.openCart();
        cart.validateCartPage();
        cart.proceedToCheckout();

    }
}