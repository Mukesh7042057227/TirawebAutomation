package pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.ConfigReader;

import static locators.Locators.LoginPage.*;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void mockLogin() {
        String mobile = ConfigReader.get("mobile");
        String otp = ConfigReader.get("otp");

        driver.findElement(mobileInput).sendKeys(mobile);
        driver.findElement(selectCheckbox).click();
        driver.findElement(clickSendOtpButton).click();
        driver.findElement(enterOtp).sendKeys(otp);
        driver.findElement(verifyOtp).click();

        System.out.println("✅ Login flow completed.");
    }

    public void loginPageValidation() {
        driver.findElement(loginIcon).click();

         String actualText = driver.findElement(validateLoginPage).getText();
         Assert.assertTrue(actualText.contains("Phone Number"), "Text mismatch");
    }
}
