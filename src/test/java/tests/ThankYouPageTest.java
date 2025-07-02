package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ThankYouPage;

@Listeners(TestListener.class)
public class ThankYouPageTest extends BaseTest {

    @Test
    public void testLoginToOrderFlow() throws InterruptedException {
        ThankYouPage thankyou = new ThankYouPage(driver);
        thankyou.isThankYouPageLoaded();

       // Assert.assertEquals(thankyou.getText(), "Welcome to TIRA", "❌ Unexpected homepage heading");

        System.out.println("Thank You Page loaded successfully.");
    }
}

