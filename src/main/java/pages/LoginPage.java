package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class LoginPage {
    WebDriver driver;

    // Sample locators (replace with actual ones from the DOM)
    By mobileInput = By.xpath("//input[@name='mobile-number']"); // replace with correct locator
    By selectCheckbox = By.xpath("//img[@alt='checkbox']");
    By clickSendOtpButton = By.xpath("//button[text()=' Send OTP ']");
    By enterOtp= By.xpath("//div/input[@class='otp-input']");
    By verifyOtp = By.xpath("//button[text()=' Verify OTP ']");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void mockLogin() {
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
