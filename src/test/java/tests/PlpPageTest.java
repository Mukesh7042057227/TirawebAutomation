package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;
import pages.LoginPage;
import pages.PlpPage;

@Listeners({ExtentReportListener.class, TestListener.class})
public class PlpPageTest extends BaseTest {

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
}