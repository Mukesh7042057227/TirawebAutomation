package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.CheckoutPage;

public class CheckoutPageTest extends BaseTest {

    @Test
    public void checkoutPageTestCase() throws InterruptedException {


        CheckoutPage.validateCheckoutPage();
        CheckoutPage.reviewOrder();
        CheckoutPage.scrollSidebarTillElementVisible();
        CheckoutPage.clickOnBuyNow();
    }
}
