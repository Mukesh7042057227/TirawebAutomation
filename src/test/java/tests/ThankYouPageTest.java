package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ThankYouPage;

@Listeners(TestListener.class)
public class ThankYouPageTest extends BaseTest {

    @Test
    public void testLoginToOrderFlow() throws InterruptedException {
        ThankYouPage thankyou = new ThankYouPage(driver);
        boolean isLoaded = thankyou.isThankYouPageLoaded();

       Assert.assertTrue(isLoaded, "❌ Could not find thankyou page");
        System.out.println("Thank You Page loaded successfully.");
        thankyou.myOrderClick();
    }
}

