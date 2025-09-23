package listeners;

import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestContext;
import org.testng.ISuite;
import utils.TestResultTracker;
import utils.EmailUtility;

public class TestSummaryListener implements ITestListener, ISuiteListener {

    @Override
    public void onFinish(ITestContext context) {
        String className = context.getCurrentXmlTest().getClasses().get(0).getName();
        System.out.println("\n🏁 Test execution completed for: " + className);

        // Print class-specific summary
        TestResultTracker.printClassSummary(className);
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("\n🎯 All test suites completed!");

        // Print overall summary across all test classes
        TestResultTracker.printOverallSummary();

        // Send suite-level email notification
        try {
            int totalTests = suite.getResults().values().stream()
                .mapToInt(result -> result.getTestContext().getAllTestMethods().length)
                .sum();

            int passedTests = suite.getResults().values().stream()
                .mapToInt(result -> result.getTestContext().getPassedTests().size())
                .sum();

            int failedTests = suite.getResults().values().stream()
                .mapToInt(result -> result.getTestContext().getFailedTests().size())
                .sum();

            int skippedTests = suite.getResults().values().stream()
                .mapToInt(result -> result.getTestContext().getSkippedTests().size())
                .sum();

            long totalDuration = suite.getResults().values().stream()
                .mapToLong(result -> result.getTestContext().getEndDate().getTime() - result.getTestContext().getStartDate().getTime())
                .sum();

            EmailUtility.sendTestSuiteNotification(suite.getName(), totalTests, passedTests, failedTests, skippedTests, totalDuration);
        } catch (Exception e) {
            System.out.println("❌ Failed to send suite email notification: " + e.getMessage());
        }
    }
}