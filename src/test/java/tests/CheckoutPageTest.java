package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.CheckoutPage;

public class CheckoutPageTest extends BaseTest {

    @Test
    public void checkoutPageTestCase() throws InterruptedException {
        // ✅ Pass the shared driver from BaseTest
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.validateCheckoutPage();
        checkoutPage.reviewOrder();

        checkoutPage.scrollSidebarTillElementVisible();
        checkoutPage.clickOnBuyNow();
    }
}
