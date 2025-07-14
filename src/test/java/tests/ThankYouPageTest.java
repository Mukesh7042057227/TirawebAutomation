package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

@Listeners(TestListener.class)
public class ThankYouPageTest extends BaseTest {

    @Test
    public void thankYouPageTestCase() throws InterruptedException {
        //Home Page calling
        HomePage.assertHomePageLoaded();
        HomePage.validateCategoryNavigation();
       //Login Page calling
        LoginPage.loginPageValidation();
        LoginPage.mockLogin();
        //Category Page calling
        CategoryPage.navigateToLipstickCategory();
       //PLP page calling
        PlpPage.validatePlpPage();
        PlpPage.sortBy();
        PlpPage.clickOnProduct();
        //PDP Page calling
        ProductPage.validatePdpPage();
        ProductPage.setAddToCartBtn();
        //checkout Page calling
        CheckoutPage.validateCheckoutPage();
        CheckoutPage.reviewOrder();
        CheckoutPage.scrollSidebarTillElementVisible();
        CheckoutPage.clickOnBuyNow();
        //Thank you page calling
        ThankYouPage.isThankYouPageLoaded();
        ThankYouPage.myOrderClick();
    }
}

