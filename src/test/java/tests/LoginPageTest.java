package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

//@Listeners(TestListener.class)
public class LoginPageTest extends BaseTest {
//    LoginPage login = new LoginPage(driver);
//    HomePage home = new HomePage(driver);

    @Test
    public void testUserLogin() throws InterruptedException {
        LoginPage login = new LoginPage(driver);
        HomePage home = new HomePage(driver);
        login.mockLogin();
        home.assertHomePageLoaded();
    }
}