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

@Listeners({ExtentReportListener.class, TestListener.class, listeners.TestSummaryListener.class})
public class LoginPageTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(LoginPageTest.class);

    @BeforeClass()
    public void setupLoginPage() throws InterruptedException {
        // This will be called before any test method, after driver is initialized
    }

    private void navigateToLoginPage() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.assertHomePageLoaded();
        home.validateCategoryNavigation();
        home.clickLoginIcon();
    }


    @Test(priority = 1)
    public void loginWithInValidPhone() throws InterruptedException
    {
        navigateToLoginPage();
        LoginPage login = new LoginPage(driver);
        login.loginPageValidation();
        login.loginWithInValidPhone();
        login.invalidPhoneValidation();
        login.clearPhoneNo();
    }

    @Test(priority = 2)
    public void loginWithInValidOtp() throws InterruptedException
    {
        navigateToLoginPage();
        LoginPage login = new LoginPage(driver);
        login.loginWithInValidOtp();
        login.invalidOtpVaidation();
        login.editMobileNo();
    }

    @Test(priority = 3)
    public void loginWithValidDetail() throws InterruptedException
    {
        navigateToLoginPage();
        LoginPage login = new LoginPage(driver);
        login.loginPageValidation();
        login.loginWithValidDetail();
        login.validationAfterLogin();
    }
}