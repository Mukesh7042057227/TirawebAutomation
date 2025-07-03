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
        thankyou.isThankYouPageLoaded();
        thankyou.myOrderClick();
    }
}

