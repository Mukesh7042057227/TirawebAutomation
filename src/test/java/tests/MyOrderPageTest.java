package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

@Listeners({ExtentReportListener.class, TestListener.class})
public class MyOrderPageTest extends BaseTest {

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
        login.loginWithValidDetail();
    }
    @Test(priority = 3)
    public void categoryPageTestCase() throws InterruptedException {
        //Calling Category page
        CategoryPage category= new CategoryPage(driver);
        category.navigateToLipstickCategory();

    }
    @Test(priority = 4)
    public void plpPageTestCase() throws InterruptedException {
        //Calling PLP page
        PlpPage plp = new PlpPage(driver);
        plp.validatePlpPage();
        plp.sortBy();
        plp.clickOnProduct();
    }
    @Test(priority = 5)
    public void productPageTestCase() throws InterruptedException {
        ProductPage product = new ProductPage(driver);
        product.validatePdpPage();
        product.setAddToCartBtn();
    }
    @Test(priority = 6)
    public void cartPageTestCase() throws InterruptedException {
        //calling Cart Page
        CartPage cart= new CartPage(driver);
        cart.openCart();
        cart.validateCartPage();
        cart.proceedToCheckout();

    }
    @Test(priority = 7)
    public void checkoutPageTestCase() throws InterruptedException {

        CheckoutPage checkout = new CheckoutPage(driver);
        //Calling checkout page
        checkout.validateCheckoutPage();
        checkout.reviewOrder();
        checkout.scrollSidebarTillElementVisible();
        checkout.clickOnBuyNow();
    }
    @Test(priority = 8)
    public void thankYouPageTestCase() throws InterruptedException {
        //Thank you page calling
        ThankYouPage thankYou = new ThankYouPage(driver);
        thankYou.isThankYouPageLoaded();
        thankYou.myOrderClick();
    }
    @Test(priority = 9)
    public void myOrderPageTestCase() throws InterruptedException {
        //Myorder page calling
        MyOrderPage myOrder = new MyOrderPage(driver);
        myOrder.validateMyOrderPage();
        myOrder.shipmentClick();
        myOrder.validateShipmentDetailPage();
        myOrder.cancelButtonClick();
    }
}
