package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Listeners(TestListener.class)
public class HomePageTest extends BaseTest {

    @Test
    public void homePageTestCase() {
        HomePage home = new HomePage(driver);
        home.assertHomePageLoaded();
        home.validateCategoryNavigation();
    }
}
