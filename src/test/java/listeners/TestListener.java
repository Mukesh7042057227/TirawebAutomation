package listeners;

import base.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import utils.ScreenshotUtil;
import utils.TestResultTracker;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class TestListener implements ITestListener {

    private static final Map<String, Long> testStartTimes = new ConcurrentHashMap<>();

    @Override
    public void onTestStart(ITestResult result) {
        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        String testKey = className + "." + methodName;

        testStartTimes.put(testKey, System.currentTimeMillis());
        TestResultTracker.recordTestStart(className, methodName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        String testKey = className + "." + methodName;

        long executionTime = System.currentTimeMillis() - testStartTimes.getOrDefault(testKey, System.currentTimeMillis());
        TestResultTracker.recordTestResult(className, methodName, true, null, executionTime);
        testStartTimes.remove(testKey);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        String testKey = className + "." + methodName;

        long executionTime = System.currentTimeMillis() - testStartTimes.getOrDefault(testKey, System.currentTimeMillis());
        String failureReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error";

        TestResultTracker.recordTestResult(className, methodName, false, failureReason, executionTime);
        testStartTimes.remove(testKey);

        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).getDriver();

        if (driver != null) {
            ScreenshotUtil.takeScreenshot(result.getName());
        } else {
            System.out.println("⚠️ WebDriver is null. Screenshot not captured.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        String testKey = className + "." + methodName;

        long executionTime = System.currentTimeMillis() - testStartTimes.getOrDefault(testKey, System.currentTimeMillis());
        String skipReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "Test skipped";

        TestResultTracker.recordTestResult(className, methodName, false, "SKIPPED: " + skipReason, executionTime);
        testStartTimes.remove(testKey);
    }
}
