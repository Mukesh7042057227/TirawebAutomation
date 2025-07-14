package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;

import java.time.Duration;

import static locators.Locators.LoginPage.*;

public class LoginPage {
    private static final WebDriver driver;
    private static final WebDriverWait wait;

    static {
        driver = BaseTest.driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public static void mockLogin() {

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
    public static void loginPageValidation()
    {
        driver.findElement(loginIcon).click(); //login icon click
        //Login page validation
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateLoginPage));
        String expectedText = "Phone Number";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }
}
