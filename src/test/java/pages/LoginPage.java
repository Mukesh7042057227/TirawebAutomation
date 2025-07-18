package pages;

import locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;

import java.time.Duration;

import static locators.Locators.LoginPage.*;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void loginWithValidDetail()
    {
        String mobile = ConfigReader.get("mobile");
        String otp = ConfigReader.get("otp");
        WebElement outSideCLick = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileInput));
        outSideCLick.sendKeys(mobile);
        WebElement selectCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileInput));
        selectCheckbox.sendKeys(mobile);
        driver.findElement(clickSendOtpButton).click();
        WebElement submitOtp = wait.until(ExpectedConditions.visibilityOfElementLocated(enterOtp));
        submitOtp.sendKeys(otp);
        driver.findElement(verifyOtp).click();
        System.out.println("✅ Login flow completed.");
    }

    public void loginWithInValidPhone() {
        String mobile = ConfigReader.get("invalid_mobile");
        WebElement enterPhoneNo = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileInput));
        enterPhoneNo.click();
        enterPhoneNo.sendKeys(mobile);
        WebElement outSideCLick = wait.until(ExpectedConditions.visibilityOfElementLocated(outSideClickOnLoginPage));
        outSideCLick.click();
    }

    public void loginWithInValidOtp() throws InterruptedException {
        String mobile = ConfigReader.get("mobile");
        String otp = ConfigReader.get("invalid_otp");
        WebElement outSideCLick = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileInput));
        outSideCLick.sendKeys(mobile);
        driver.findElement(selectCheckbox).click();
        driver.findElement(clickSendOtpButton).click();
        WebElement submitOtp = wait.until(ExpectedConditions.visibilityOfElementLocated(enterOtp));
        submitOtp.sendKeys(otp);
        driver.findElement(verifyOtp).click();
    }

    public void loginPageValidation() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(validateLoginPage));
        String actualText = element.getText();
        Assert.assertTrue(actualText.contains("Sign up now"), "❌ Text mismatch: Expected 'Sign up now'");
        System.out.println("✅ Verified text: " + actualText);
    }

    public void invalidOtpVaidation() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(validateInvalidOtp));
        String actualText = element.getText();
        Assert.assertTrue(actualText.contains("Entered OTP is invalid"), "Text mismatch");
    }

    public void invalidPhoneValidation() {
        String actualText = driver.findElement(validateInvalidPhone).getText();
        Assert.assertTrue(actualText.contains("Invalid Phone Number"), "Text mismatch");
    }

    public void clearPhoneNo() {
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileInput));
        phoneInput.click();
        phoneInput.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        phoneInput.sendKeys(Keys.BACK_SPACE);
        System.out.println("✅ Cleared phone number field.");
    }

    public void editMobileNo() {
        WebElement editMobile = wait.until(ExpectedConditions.visibilityOfElementLocated(clickOnEditMobile));
        editMobile.click();
    }
    public void validationAfterLogin() {
        WebElement iconElement = wait.until(ExpectedConditions.visibilityOfElementLocated(wishlistIcon));
        Assert.assertTrue(iconElement.isDisplayed(), "❌ Wishlist icon is not visible.");
        System.out.println("✅ Wishlist icon is visible.");
    }
}
