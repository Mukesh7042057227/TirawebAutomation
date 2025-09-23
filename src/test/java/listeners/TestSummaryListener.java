package listeners;

import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestContext;
import utils.TestResultTracker;

public class TestSummaryListener implements ITestListener, ISuiteListener {

    @Override
    public void onFinish(ITestContext context) {
        String className = context.getCurrentXmlTest().getClasses().get(0).getName();
        System.out.println("\n🏁 Test execution completed for: " + className);

        // Print class-specific summary
        TestResultTracker.printClassSummary(className);
    }

    @Override
    public void onFinish(org.testng.ISuite suite) {
        System.out.println("\n🎯 All test suites completed!");

        // Print overall summary across all test classes
        TestResultTracker.printOverallSummary();
    }
}