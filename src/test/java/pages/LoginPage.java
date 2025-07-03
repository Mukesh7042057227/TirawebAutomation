package pages;

import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

import static locators.Locators.LoginPage.*;

public class LoginPage {
    WebDriver driver;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void mockLogin() {
        driver.findElement(loginIcon).click();

        String mobile = ConfigReader.get("mobile");
        String otp = ConfigReader.get("otp");

        System.out.println("Entering mobile number: " + mobile);
        driver.findElement(mobileInput).sendKeys(mobile);

        System.out.println("Select the permission checkbox: ");
        driver.findElement(selectCheckbox).click();

        System.out.println("click on send otp button ");
        driver.findElement(clickSendOtpButton).click();

        System.out.println("Entering OTP: " + otp);
        driver.findElement(enterOtp).sendKeys(otp);

        driver.findElement(verifyOtp).click();
        System.out.println("✅ Submitted login form.");
    }
}
