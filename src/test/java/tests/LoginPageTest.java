package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

//@Listeners(TestListener.class)
public class LoginPageTest extends BaseTest {

    @Test
    public void loginPageTestCase() throws InterruptedException {
        LoginPage login = new LoginPage(driver);
        HomePage home = new HomePage(driver);
        login.loginPageValidation();
        login.mockLogin();
        home.assertHomePageLoaded();
    }
}