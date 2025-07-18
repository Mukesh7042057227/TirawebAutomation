package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

@Listeners({ExtentReportListener.class, TestListener.class})
public class LoginPageTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(LoginPageTest.class);

    @BeforeClass()
    public void homePageTestCase() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.assertHomePageLoaded();
       // home.validateCategoryNavigation();
         home.clickLoginIcon();
    }


    @Test(priority = 2)
    public void loginWithInValidOtp() throws InterruptedException
    {
        LoginPage login = new LoginPage(driver);
        login.loginWithInValidOtp();
        login.invalidOtpVaidation();
        login.editMobileNo();
    }
    @Test(priority = 1)
    public void loginWithInValidPhone() throws InterruptedException
    {
        LoginPage login = new LoginPage(driver);
        login.loginPageValidation();
        login.loginWithInValidPhone();
        login.invalidPhoneValidation();
        login.clearPhoneNo();
    }
    @Test(priority = 3)
    public void loginWithValidDetail() throws InterruptedException
    {
        LoginPage login = new LoginPage(driver);
        login.loginPageValidation();
        login.loginWithValidDetail();
        login.validationAfterLogin();
    }
}