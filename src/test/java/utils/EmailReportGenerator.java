package utils;

import java.util.Map;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class EmailReportGenerator {
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static String generateEnhancedEmailReport() {
        Map<String, TestResultTracker.TestClassResult> allResults = TestResultTracker.getAllClassResults();

        if (allResults.isEmpty()) {
            return generateEmptyEmailReport();
        }

        int totalTests = 0;
        int totalPassed = 0;
        int totalFailed = 0;

        for (TestResultTracker.TestClassResult result : allResults.values()) {
            totalTests += result.getTotalTests();
            totalPassed += result.getPassedTests();
            totalFailed += result.getFailedTests();
        }

        return generateEmailHTMLReport(allResults, totalTests, totalPassed, totalFailed);
    }

    private static String generateEmailHTMLReport(Map<String, TestResultTracker.TestClassResult> allResults,
                                                int totalTests, int totalPassed, int totalFailed) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n")
            .append("<html lang='en'>\n")
            .append("<head>\n")
            .append("    <meta charset='UTF-8'>\n")
            .append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n")
            .append("    <title>Enhanced Test Report</title>\n")
            .append("    <script src='https://cdn.jsdelivr.net/npm/chart.js'></script>\n")
            .append("    <style>\n")
            .append(generateEmailCSS())
            .append("    </style>\n")
            .append("</head>\n")
            .append("<body>\n")
            .append("    <div class='container'>\n")
            .append("        <header>\n")
            .append("            <h1>🎯 Tira Beauty Test Execution Report</h1>\n")
            .append("            <div class='timestamp'>Generated on: ").append(new java.util.Date().toString()).append("</div>\n")
            .append("        </header>\n");

        // Executive Summary
        html.append("        <section class='executive-summary'>\n")
            .append("            <h2>📊 Executive Summary</h2>\n")
            .append("            <div class='summary-grid'>\n")
            .append("                <div class='stats-cards'>\n")
            .append("                    <div class='stat-card total'>\n")
            .append("                        <h3>").append(totalTests).append("</h3>\n")
            .append("                        <p>Total Tests</p>\n")
            .append("                    </div>\n")
            .append("                    <div class='stat-card passed'>\n")
            .append("                        <h3>").append(totalPassed).append("</h3>\n")
            .append("                        <p>Passed (").append(df.format((double) totalPassed / totalTests * 100)).append("%)</p>\n")
            .append("                    </div>\n")
            .append("                    <div class='stat-card failed'>\n")
            .append("                        <h3>").append(totalFailed).append("</h3>\n")
            .append("                        <p>Failed (").append(df.format((double) totalFailed / totalTests * 100)).append("%)</p>\n")
            .append("                    </div>\n")
            .append("                </div>\n")
            .append("                <div class='chart-container'>\n")
            .append("                    <canvas id='pieChart' width='200' height='200'></canvas>\n")
            .append("                </div>\n")
            .append("            </div>\n")
            .append("        </section>\n");

        // Test Results by Class
        html.append("        <section class='results-section'>\n")
            .append("            <h2>📋 Test Results by Class</h2>\n");

        for (TestResultTracker.TestClassResult classResult : allResults.values()) {
            html.append("            <div class='class-result'>\n")
                .append("                <div class='class-header'>\n")
                .append("                    <h3>").append(classResult.getClassName()).append("</h3>\n")
                .append("                    <div class='class-stats'>\n")
                .append("                        <span class='stat passed'>✅ ").append(classResult.getPassedTests()).append("</span>\n")
                .append("                        <span class='stat failed'>❌ ").append(classResult.getFailedTests()).append("</span>\n")
                .append("                        <span class='stat total'>📊 ").append(classResult.getTotalTests()).append("</span>\n")
                .append("                    </div>\n")
                .append("                </div>\n");

            // Progress bar
            double passPercentage = classResult.getPassPercentage();
            html.append("                <div class='progress-bar'>\n")
                .append("                    <div class='progress-fill' style='width: ").append(passPercentage).append("%'></div>\n")
                .append("                    <span class='progress-text'>").append(df.format(passPercentage)).append("% Pass Rate</span>\n")
                .append("                </div>\n");

            // Method results - Passed tests
            html.append("                <div class='method-results'>\n");
            for (TestResultTracker.TestMethodResult method : classResult.getMethodResults()) {
                if (method.isPassed()) {
                    html.append("                    <div class='method-item passed'>\n")
                        .append("                        <span class='method-status'>✅</span>\n")
                        .append("                        <span class='method-name'>").append(method.getMethodName()).append("</span>\n")
                        .append("                        <span class='method-time'>").append(method.getExecutionTime()).append("ms</span>\n")
                        .append("                    </div>\n");
                }
            }
            html.append("                </div>\n")
                .append("            </div>\n");
        }
        html.append("        </section>\n");

        // Failed tests section
        List<TestResultTracker.TestMethodResult> failedTests = allResults.values().stream()
            .flatMap(classResult -> classResult.getMethodResults().stream())
            .filter(method -> !method.isPassed())
            .collect(Collectors.toList());

        if (!failedTests.isEmpty()) {
            html.append("        <section class='failed-section'>\n")
                .append("            <h2>❌ Failed Test Cases - Requires Attention</h2>\n")
                .append("            <div class='failed-tests'>\n");

            for (TestResultTracker.TestMethodResult failedTest : failedTests) {
                html.append("                <div class='failed-test-item'>\n")
                    .append("                    <div class='failed-test-header'>\n")
                    .append("                        <span class='failed-icon'>❌</span>\n")
                    .append("                        <span class='failed-class'>").append(failedTest.getClassName()).append("</span>\n")
                    .append("                        <span class='failed-method'>").append(failedTest.getMethodName()).append("</span>\n")
                    .append("                        <span class='failed-time'>").append(failedTest.getExecutionTime()).append("ms</span>\n")
                    .append("                    </div>\n")
                    .append("                    <div class='failed-reason'>")
                    .append("                        <strong>Error:</strong> ")
                    .append(failedTest.getFailureReason() != null ? escapeHtml(failedTest.getFailureReason()) : "No failure reason available")
                    .append("                    </div>\n")
                    .append("                    <div class='failed-action'>\n")
                    .append("                        <p><strong>📝 Log File:</strong> ").append(failedTest.getClassName()).append("_").append(failedTest.getMethodName()).append(".log</p>\n")
                    .append("                        <p><em>Check the test-output/logs/ directory for detailed failure logs</em></p>\n")
                    .append("                    </div>\n")
                    .append("                </div>\n");
            }

            html.append("            </div>\n")
                .append("        </section>\n");
        }

        // Summary and Actions
        double passRate = (double) totalPassed / totalTests * 100;
        html.append("        <section class='action-section'>\n")
            .append("            <h2>🎯 Summary & Next Steps</h2>\n")
            .append("            <div class='summary-box'>\n");

        if (passRate >= 95) {
            html.append("                <div class='status-excellent'>\n")
                .append("                    <h3>🎉 Excellent Results!</h3>\n")
                .append("                    <p>Outstanding pass rate of ").append(df.format(passRate)).append("%. The test suite is performing excellently.</p>\n")
                .append("                </div>\n");
        } else if (passRate >= 80) {
            html.append("                <div class='status-good'>\n")
                .append("                    <h3>✅ Good Results</h3>\n")
                .append("                    <p>Good pass rate of ").append(df.format(passRate)).append("%. Consider reviewing failed tests for improvements.</p>\n")
                .append("                </div>\n");
        } else if (passRate >= 60) {
            html.append("                <div class='status-warning'>\n")
                .append("                    <h3>⚠️ Needs Attention</h3>\n")
                .append("                    <p>Pass rate of ").append(df.format(passRate)).append("% indicates several issues. Please review failed tests urgently.</p>\n")
                .append("                </div>\n");
        } else {
            html.append("                <div class='status-critical'>\n")
                .append("                    <h3>🚨 Critical Issues</h3>\n")
                .append("                    <p>Low pass rate of ").append(df.format(passRate)).append("%. Immediate attention required to investigate test failures.</p>\n")
                .append("                </div>\n");
        }

        if (totalFailed > 0) {
            html.append("                <div class='action-items'>\n")
                .append("                    <h4>📋 Recommended Actions:</h4>\n")
                .append("                    <ul>\n")
                .append("                        <li>Review failed test logs in <code>test-output/logs/</code> directory</li>\n")
                .append("                        <li>Check screenshots for visual failures</li>\n")
                .append("                        <li>Verify environment stability and data integrity</li>\n")
                .append("                        <li>Update test cases if application behavior has changed</li>\n")
                .append("                    </ul>\n")
                .append("                </div>\n");
        }

        html.append("            </div>\n")
            .append("        </section>\n");

        html.append("    </div>\n")
            .append("    <script>\n")
            .append(generateEmailJavaScript(totalPassed, totalFailed))
            .append("    </script>\n")
            .append("</body>\n")
            .append("</html>");

        return html.toString();
    }

    private static String generateEmailCSS() {
        return "* { margin: 0; padding: 0; box-sizing: border-box; }\n" +
            "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: #333; padding: 20px; }\n" +
            ".container { max-width: 800px; margin: 0 auto; background: white; border-radius: 15px; padding: 30px; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }\n" +
            "header { text-align: center; margin-bottom: 30px; padding: 20px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border-radius: 10px; }\n" +
            "header h1 { font-size: 2em; margin-bottom: 10px; }\n" +
            ".timestamp { font-size: 1em; opacity: 0.9; }\n" +
            ".executive-summary { margin-bottom: 30px; padding: 20px; background: #f8f9fa; border-radius: 10px; border-left: 5px solid #007bff; }\n" +
            ".summary-grid { display: grid; grid-template-columns: 2fr 1fr; gap: 30px; align-items: center; }\n" +
            ".stats-cards { display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; }\n" +
            ".stat-card { text-align: center; padding: 15px; border-radius: 8px; color: white; box-shadow: 0 3px 10px rgba(0,0,0,0.1); }\n" +
            ".stat-card.total { background: linear-gradient(45deg, #4facfe, #00f2fe); }\n" +
            ".stat-card.passed { background: linear-gradient(45deg, #43e97b, #38f9d7); }\n" +
            ".stat-card.failed { background: linear-gradient(45deg, #fa709a, #fee140); }\n" +
            ".stat-card h3 { font-size: 2em; margin-bottom: 5px; }\n" +
            ".stat-card p { font-size: 0.9em; opacity: 0.9; }\n" +
            ".chart-container { display: flex; justify-content: center; align-items: center; }\n" +
            ".results-section { margin-bottom: 30px; }\n" +
            ".results-section h2 { color: #333; font-size: 1.5em; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 2px solid #eee; }\n" +
            ".class-result { background: #f8f9fa; border-radius: 8px; padding: 15px; margin-bottom: 15px; border-left: 4px solid #007bff; }\n" +
            ".class-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }\n" +
            ".class-header h3 { color: #333; font-size: 1.1em; }\n" +
            ".class-stats { display: flex; gap: 10px; }\n" +
            ".class-stats .stat { padding: 3px 8px; border-radius: 12px; font-weight: bold; font-size: 0.8em; }\n" +
            ".class-stats .stat.passed { background: #e8f5e8; color: #2e7d32; }\n" +
            ".class-stats .stat.failed { background: #ffebee; color: #c62828; }\n" +
            ".class-stats .stat.total { background: #e3f2fd; color: #1565c0; }\n" +
            ".progress-bar { position: relative; background: #e0e0e0; border-radius: 8px; height: 16px; margin-bottom: 10px; overflow: hidden; }\n" +
            ".progress-fill { background: linear-gradient(90deg, #43e97b, #38f9d7); height: 100%; border-radius: 8px; transition: width 0.5s ease; }\n" +
            ".progress-text { position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-weight: bold; color: #333; font-size: 0.7em; }\n" +
            ".method-results { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 8px; }\n" +
            ".method-item { display: flex; justify-content: space-between; align-items: center; padding: 8px 12px; border-radius: 6px; background: white; border-left: 3px solid; }\n" +
            ".method-item.passed { border-left-color: #28a745; }\n" +
            ".method-name { flex: 1; margin-left: 8px; font-weight: 500; font-size: 0.9em; }\n" +
            ".method-time { color: #666; font-size: 0.8em; }\n" +
            ".failed-section { margin-bottom: 30px; padding: 20px; background: #fff5f5; border-radius: 10px; border-left: 5px solid #dc3545; }\n" +
            ".failed-section h2 { color: #dc3545; font-size: 1.5em; margin-bottom: 20px; }\n" +
            ".failed-tests { display: flex; flex-direction: column; gap: 15px; }\n" +
            ".failed-test-item { background: white; border-radius: 8px; padding: 15px; border-left: 4px solid #dc3545; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }\n" +
            ".failed-test-header { display: flex; align-items: center; gap: 10px; margin-bottom: 10px; }\n" +
            ".failed-icon { font-size: 1.1em; }\n" +
            ".failed-class { font-weight: bold; color: #333; }\n" +
            ".failed-method { color: #666; font-style: italic; }\n" +
            ".failed-time { color: #999; font-size: 0.8em; margin-left: auto; }\n" +
            ".failed-reason { background: #f8f9fa; padding: 10px; border-radius: 5px; color: #666; font-family: monospace; font-size: 0.85em; border-left: 3px solid #dc3545; margin-bottom: 10px; }\n" +
            ".failed-action { font-size: 0.85em; color: #555; }\n" +
            ".failed-action code { background: #e9ecef; padding: 2px 4px; border-radius: 3px; font-family: monospace; }\n" +
            ".action-section { padding: 20px; background: #f8f9fa; border-radius: 10px; border-left: 5px solid #28a745; }\n" +
            ".action-section h2 { color: #28a745; font-size: 1.5em; margin-bottom: 20px; }\n" +
            ".summary-box { background: white; padding: 20px; border-radius: 8px; }\n" +
            ".status-excellent { padding: 15px; background: #d4edda; border: 1px solid #c3e6cb; border-radius: 5px; color: #155724; margin-bottom: 15px; }\n" +
            ".status-good { padding: 15px; background: #d1ecf1; border: 1px solid #bee5eb; border-radius: 5px; color: #0c5460; margin-bottom: 15px; }\n" +
            ".status-warning { padding: 15px; background: #fff3cd; border: 1px solid #ffeaa7; border-radius: 5px; color: #856404; margin-bottom: 15px; }\n" +
            ".status-critical { padding: 15px; background: #f8d7da; border: 1px solid #f5c6cb; border-radius: 5px; color: #721c24; margin-bottom: 15px; }\n" +
            ".action-items { padding: 15px; background: #e9ecef; border-radius: 5px; }\n" +
            ".action-items h4 { margin-bottom: 10px; color: #495057; }\n" +
            ".action-items ul { margin-left: 20px; }\n" +
            ".action-items li { margin-bottom: 8px; line-height: 1.4; }\n" +
            ".action-items code { background: #f8f9fa; padding: 2px 4px; border-radius: 3px; font-family: monospace; border: 1px solid #dee2e6; }";
    }

    private static String generateEmailJavaScript(int totalPassed, int totalFailed) {
        return String.format(
            "document.addEventListener('DOMContentLoaded', function() {\n" +
            "    const ctx = document.getElementById('pieChart');\n" +
            "    if (ctx) {\n" +
            "        const pieChart = new Chart(ctx, {\n" +
            "            type: 'pie',\n" +
            "            data: {\n" +
            "                labels: ['Passed', 'Failed'],\n" +
            "                datasets: [{\n" +
            "                    data: [%d, %d],\n" +
            "                    backgroundColor: ['#28a745', '#dc3545'],\n" +
            "                    borderWidth: 2,\n" +
            "                    borderColor: '#fff'\n" +
            "                }]\n" +
            "            },\n" +
            "            options: {\n" +
            "                responsive: true,\n" +
            "                maintainAspectRatio: false,\n" +
            "                plugins: {\n" +
            "                    legend: {\n" +
            "                        position: 'bottom',\n" +
            "                        labels: {\n" +
            "                            padding: 15,\n" +
            "                            font: { size: 12, weight: 'bold' }\n" +
            "                        }\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        });\n" +
            "    }\n" +
            "});", totalPassed, totalFailed);
    }

    private static String generateEmptyEmailReport() {
        return "<!DOCTYPE html>\n" +
            "<html lang='en'>\n" +
            "<head>\n" +
            "    <meta charset='UTF-8'>\n" +
            "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
            "    <title>Test Report</title>\n" +
            "    <style>\n" +
            "        body { font-family: Arial, sans-serif; text-align: center; padding: 50px; background: #f5f5f5; }\n" +
            "        .container { max-width: 600px; margin: 0 auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); }\n" +
            "        h1 { color: #333; margin-bottom: 20px; }\n" +
            "        p { color: #666; font-size: 1.1em; }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class='container'>\n" +
            "        <h1>📊 Tira Beauty Test Report</h1>\n" +
            "        <p>No test results available. Please run tests first.</p>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&#x27;");
    }
}