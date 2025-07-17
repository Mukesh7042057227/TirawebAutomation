package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;
import pages.LoginPage;

@Listeners({ExtentReportListener.class, TestListener.class})
public class CategoryPageTest extends BaseTest {
    @Test()
    public void homePageTestCase() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.assertHomePageLoaded();
        home.validateCategoryNavigation();
    }
    @Test(dependsOnMethods = {"homePageTestCase"})
    public void loginPageTestCase() throws InterruptedException
    {
        LoginPage login = new LoginPage(driver);
        login.loginPageValidation();
        login.mockLogin();
    }
    @Test(dependsOnMethods = {"loginPageTestCase"})
    public void categoryPageTestCase() throws InterruptedException {
        //Calling Category page
        CategoryPage category= new CategoryPage(driver);
        category.navigateToLipstickCategory();

    }
}