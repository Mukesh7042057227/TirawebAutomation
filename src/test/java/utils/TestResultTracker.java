package utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.text.DecimalFormat;

public class TestResultTracker {
    private static final Map<String, TestClassResult> testClassResults = new ConcurrentHashMap<>();
    private static final Map<String, TestMethodResult> allTestResults = new ConcurrentHashMap<>();
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static class TestMethodResult {
        private String methodName;
        private String className;
        private boolean passed;
        private String failureReason;
        private long executionTime;

        public TestMethodResult(String className, String methodName, boolean passed, String failureReason, long executionTime) {
            this.className = className;
            this.methodName = methodName;
            this.passed = passed;
            this.failureReason = failureReason;
            this.executionTime = executionTime;
        }

        // Getters
        public String getMethodName() { return methodName; }
        public String getClassName() { return className; }
        public boolean isPassed() { return passed; }
        public String getFailureReason() { return failureReason; }
        public long getExecutionTime() { return executionTime; }
    }

    public static class TestClassResult {
        private String className;
        private int totalTests = 0;
        private int passedTests = 0;
        private int failedTests = 0;
        private List<TestMethodResult> methodResults = new ArrayList<>();

        public TestClassResult(String className) {
            this.className = className;
        }

        public void addTestResult(TestMethodResult result) {
            methodResults.add(result);
            totalTests++;
            if (result.isPassed()) {
                passedTests++;
            } else {
                failedTests++;
            }
        }

        public double getPassPercentage() {
            if (totalTests == 0) return 0.0;
            return (double) passedTests / totalTests * 100;
        }

        public double getFailPercentage() {
            if (totalTests == 0) return 0.0;
            return (double) failedTests / totalTests * 100;
        }

        // Getters
        public String getClassName() { return className; }
        public int getTotalTests() { return totalTests; }
        public int getPassedTests() { return passedTests; }
        public int getFailedTests() { return failedTests; }
        public List<TestMethodResult> getMethodResults() { return methodResults; }
    }

    // Record test start
    public static void recordTestStart(String className, String methodName) {
        System.out.println("🔍 Starting test: " + methodName + " in " + className);
    }

    // Record test result
    public static void recordTestResult(String className, String methodName, boolean passed, String failureReason, long executionTime) {
        TestMethodResult methodResult = new TestMethodResult(className, methodName, passed, failureReason, executionTime);

        // Store individual test result
        String testKey = className + "." + methodName;
        allTestResults.put(testKey, methodResult);

        // Update class-level results
        TestClassResult classResult = testClassResults.computeIfAbsent(className, TestClassResult::new);
        classResult.addTestResult(methodResult);

        // Print immediate result
        if (passed) {
            System.out.println("✅ Test PASSED: " + methodName + " (" + executionTime + "ms)");
        } else {
            System.out.println("❌ Test FAILED: " + methodName + " - " + failureReason + " (" + executionTime + "ms)");
        }
    }

    // Get results for a specific test class
    public static TestClassResult getClassResult(String className) {
        return testClassResults.get(className);
    }

    // Print summary for a specific test class
    public static void printClassSummary(String className) {
        TestClassResult result = testClassResults.get(className);
        if (result == null) {
            System.out.println("⚠️ No test results found for class: " + className);
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("📊 TEST SUMMARY FOR: " + className);
        System.out.println("=".repeat(60));
        System.out.println("Total Tests: " + result.getTotalTests());
        System.out.println("✅ Passed: " + result.getPassedTests() + " (" + df.format(result.getPassPercentage()) + "%)");
        System.out.println("❌ Failed: " + result.getFailedTests() + " (" + df.format(result.getFailPercentage()) + "%)");
        System.out.println("=".repeat(60));

        if (!result.getMethodResults().isEmpty()) {
            System.out.println("\n📝 DETAILED RESULTS:");
            for (TestMethodResult methodResult : result.getMethodResults()) {
                String status = methodResult.isPassed() ? "✅ PASS" : "❌ FAIL";
                System.out.println("  " + status + " | " + methodResult.getMethodName() + " (" + methodResult.getExecutionTime() + "ms)");
                if (!methodResult.isPassed() && methodResult.getFailureReason() != null) {
                    System.out.println("    └── Reason: " + methodResult.getFailureReason());
                }
            }
        }
        System.out.println("=".repeat(60) + "\n");
    }

    // Print overall summary across all test classes
    public static void printOverallSummary() {
        if (testClassResults.isEmpty()) {
            System.out.println("⚠️ No test results available.");
            return;
        }

        int totalTests = 0;
        int totalPassed = 0;
        int totalFailed = 0;

        System.out.println("\n" + "=".repeat(80));
        System.out.println("🎯 OVERALL TEST EXECUTION SUMMARY");
        System.out.println("=".repeat(80));

        for (TestClassResult classResult : testClassResults.values()) {
            totalTests += classResult.getTotalTests();
            totalPassed += classResult.getPassedTests();
            totalFailed += classResult.getFailedTests();

            System.out.println(String.format("%-30s | Total: %2d | ✅ Pass: %2d (%.1f%%) | ❌ Fail: %2d (%.1f%%)",
                classResult.getClassName(),
                classResult.getTotalTests(),
                classResult.getPassedTests(),
                classResult.getPassPercentage(),
                classResult.getFailedTests(),
                classResult.getFailPercentage()
            ));
        }

        System.out.println("=".repeat(80));
        double overallPassPercentage = totalTests == 0 ? 0.0 : (double) totalPassed / totalTests * 100;
        double overallFailPercentage = totalTests == 0 ? 0.0 : (double) totalFailed / totalTests * 100;

        System.out.println(String.format("🏆 GRAND TOTAL: %d tests | ✅ %d passed (%.1f%%) | ❌ %d failed (%.1f%%)",
            totalTests, totalPassed, overallPassPercentage, totalFailed, overallFailPercentage));
        System.out.println("=".repeat(80) + "\n");
    }

    // Clear all results (useful for fresh test runs)
    public static void clearResults() {
        testClassResults.clear();
        allTestResults.clear();
    }

    // Get specific test method result
    public static TestMethodResult getMethodResult(String className, String methodName) {
        return allTestResults.get(className + "." + methodName);
    }
}