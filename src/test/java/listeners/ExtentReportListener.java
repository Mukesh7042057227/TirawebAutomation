package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtil;
import utils.EnhancedReportGenerator;
import utils.TestResultTracker;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Tester", "QA Automation");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);

        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        TestResultTracker.recordTestStart(className, methodName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "✅ Test Passed");

        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        long executionTime = result.getEndMillis() - result.getStartMillis();
        TestResultTracker.recordTestResult(className, methodName, true, null, executionTime);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "❌ Test Failed: " + result.getThrowable());
        String screenshotPath = ScreenshotUtil.takeScreenshot(result.getMethod().getMethodName());
        test.get().addScreenCaptureFromPath(screenshotPath);

        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        long executionTime = result.getEndMillis() - result.getStartMillis();
        String failureReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error";

        TestResultTracker.recordTestResult(className, methodName, false, failureReason, executionTime);
        generateFailureLog(className, methodName, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "⚠️ Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

        // Generate enhanced report with pie chart and clickable failed tests
        System.out.println("🎨 Generating enhanced test report with pie chart...");
        EnhancedReportGenerator.generateEnhancedReport("test-output/EnhancedReport.html");
        System.out.println("✅ Enhanced report generated: test-output/EnhancedReport.html");
    }

    private void generateFailureLog(String className, String methodName, Throwable throwable) {
        try {
            // Create logs directory if it doesn't exist
            File logsDir = new File("test-output/logs");
            if (!logsDir.exists()) {
                logsDir.mkdirs();
            }

            // Generate log file name
            String logFileName = className + "_" + methodName + ".log";
            File logFile = new File(logsDir, logFileName);

            try (FileWriter writer = new FileWriter(logFile)) {
                writer.write("=".repeat(80) + "\n");
                writer.write("FAILURE LOG\n");
                writer.write("=".repeat(80) + "\n");
                writer.write("Test Class: " + className + "\n");
                writer.write("Test Method: " + methodName + "\n");
                writer.write("Timestamp: " + new java.util.Date().toString() + "\n");
                writer.write("=".repeat(80) + "\n");
                writer.write("ERROR DETAILS:\n");
                writer.write("=".repeat(80) + "\n");

                if (throwable != null) {
                    writer.write("Exception Type: " + throwable.getClass().getSimpleName() + "\n");
                    writer.write("Error Message: " + throwable.getMessage() + "\n\n");
                    writer.write("STACK TRACE:\n");
                    writer.write("-".repeat(40) + "\n");

                    for (StackTraceElement element : throwable.getStackTrace()) {
                        writer.write(element.toString() + "\n");
                    }

                    // Include cause if available
                    Throwable cause = throwable.getCause();
                    if (cause != null) {
                        writer.write("\nCaused by:\n");
                        writer.write("-".repeat(40) + "\n");
                        writer.write(cause.getClass().getSimpleName() + ": " + cause.getMessage() + "\n");
                        for (StackTraceElement element : cause.getStackTrace()) {
                            writer.write("\tat " + element.toString() + "\n");
                        }
                    }
                } else {
                    writer.write("No detailed error information available.\n");
                }

                writer.write("\n" + "=".repeat(80) + "\n");
                writer.write("END OF LOG\n");
                writer.write("=".repeat(80) + "\n");
            }

            System.out.println("📝 Failure log generated: " + logFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("❌ Failed to generate failure log: " + e.getMessage());
        }
    }
}
