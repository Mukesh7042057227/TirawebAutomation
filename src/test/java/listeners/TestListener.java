package listeners;

import base.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ Test failed: " + result.getName());

        Object testClass = result.getInstance();
        WebDriver driver = BaseTest.driver;  // Safe typecast

        if (driver != null) {
            ScreenshotUtil.takeScreenshot(driver, result.getName());
        } else {
            System.out.println("⚠️ Driver was null. Could not capture screenshot.");
        }
    }
}
