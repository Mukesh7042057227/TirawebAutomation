package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

//@Listeners(TestListener.class)
public class LoginPageTest extends BaseTest {


    @Test
    public void testLogin() throws InterruptedException {

        LoginPage login = new LoginPage(driver);
        login.mockLogin();
    }
}