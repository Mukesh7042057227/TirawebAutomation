package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.CheckoutPage;

public class CheckoutPageTest extends BaseTest {

    @Test
    public void testLoginToOrderFlow() {
        // ✅ Pass the shared driver from BaseTest
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        // ✅ Call the reviewOrder method
        checkoutPage.reviewOrder();
    }
}
