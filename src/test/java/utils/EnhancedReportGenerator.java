package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class EnhancedReportGenerator {
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static void generateEnhancedReport(String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            Map<String, TestResultTracker.TestClassResult> allResults = TestResultTracker.getAllClassResults();

            if (allResults.isEmpty()) {
                writer.write(generateEmptyReport());
                return;
            }

            int totalTests = 0;
            int totalPassed = 0;
            int totalFailed = 0;

            for (TestResultTracker.TestClassResult result : allResults.values()) {
                totalTests += result.getTotalTests();
                totalPassed += result.getPassedTests();
                totalFailed += result.getFailedTests();
            }

            writer.write(generateHTMLReport(allResults, totalTests, totalPassed, totalFailed));

        } catch (IOException e) {
            System.err.println("Failed to generate enhanced report: " + e.getMessage());
        }
    }

    private static String generateHTMLReport(Map<String, TestResultTracker.TestClassResult> allResults,
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
            .append(generateCSS())
            .append("    </style>\n")
            .append("</head>\n")
            .append("<body>\n")
            .append("    <div class='container'>\n")
            .append("        <header>\n")
            .append("            <h1>🎯 Test Execution Report</h1>\n")
            .append("            <div class='timestamp'>Generated on: ").append(new java.util.Date().toString()).append("</div>\n")
            .append("        </header>\n");

        // Summary section with pie chart
        html.append("        <section class='summary-section'>\n")
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
            .append("                    <canvas id='pieChart' width='300' height='300'></canvas>\n")
            .append("                </div>\n")
            .append("            </div>\n")
            .append("        </section>\n");

        // Class results section
        html.append("        <section class='results-section'>\n")
            .append("            <h2>📊 Test Results by Class</h2>\n");

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

            // Method results
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

        // Failed tests section at bottom
        List<TestResultTracker.TestMethodResult> failedTests = allResults.values().stream()
            .flatMap(classResult -> classResult.getMethodResults().stream())
            .filter(method -> !method.isPassed())
            .collect(Collectors.toList());

        if (!failedTests.isEmpty()) {
            html.append("        <section class='failed-section'>\n")
                .append("            <h2>❌ Failed Test Cases</h2>\n")
                .append("            <div class='failed-tests'>\n");

            for (TestResultTracker.TestMethodResult failedTest : failedTests) {
                String logFileName = failedTest.getClassName() + "_" + failedTest.getMethodName() + ".log";
                html.append("                <div class='failed-test-item' onclick='viewLog(\"").append(logFileName).append("\")'>\n")
                    .append("                    <div class='failed-test-header'>\n")
                    .append("                        <span class='failed-icon'>❌</span>\n")
                    .append("                        <span class='failed-class'>").append(failedTest.getClassName()).append("</span>\n")
                    .append("                        <span class='failed-method'>").append(failedTest.getMethodName()).append("</span>\n")
                    .append("                        <span class='failed-time'>").append(failedTest.getExecutionTime()).append("ms</span>\n")
                    .append("                        <span class='view-log-btn'>View Log</span>\n")
                    .append("                    </div>\n")
                    .append("                    <div class='failed-reason'>").append(failedTest.getFailureReason() != null ? failedTest.getFailureReason() : "No failure reason available").append("</div>\n")
                    .append("                </div>\n");
            }

            html.append("            </div>\n")
                .append("        </section>\n");
        }

        html.append("    </div>\n")
            .append("    <script>\n")
            .append(generateJavaScript(totalPassed, totalFailed))
            .append("    </script>\n")
            .append("</body>\n")
            .append("</html>");

        return html.toString();
    }

    private static String generateCSS() {
        return "* {\n" +
            "    margin: 0;\n" +
            "    padding: 0;\n" +
            "    box-sizing: border-box;\n" +
            "}\n" +
            "\n" +
            "body {\n" +
            "    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
            "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
            "    min-height: 100vh;\n" +
            "    color: #333;\n" +
            "}\n" +
            "\n" +
            ".container {\n" +
            "    max-width: 1200px;\n" +
            "    margin: 0 auto;\n" +
            "    padding: 20px;\n" +
            "}\n" +
            "\n" +
            "header {\n" +
            "    text-align: center;\n" +
            "    margin-bottom: 30px;\n" +
            "    color: white;\n" +
            "}\n" +
            "\n" +
            "header h1 {\n" +
            "    font-size: 2.5em;\n" +
            "    margin-bottom: 10px;\n" +
            "    text-shadow: 2px 2px 4px rgba(0,0,0,0.3);\n" +
            "}\n" +
            "\n" +
            ".timestamp {\n" +
            "    font-size: 1.1em;\n" +
            "    opacity: 0.9;\n" +
            "}\n" +
            "\n" +
            ".summary-section {\n" +
            "    background: white;\n" +
            "    border-radius: 15px;\n" +
            "    padding: 30px;\n" +
            "    margin-bottom: 30px;\n" +
            "    box-shadow: 0 10px 30px rgba(0,0,0,0.1);\n" +
            "}\n" +
            "\n" +
            ".summary-grid {\n" +
            "    display: grid;\n" +
            "    grid-template-columns: 2fr 1fr;\n" +
            "    gap: 30px;\n" +
            "    align-items: center;\n" +
            "}\n" +
            "\n" +
            ".stats-cards {\n" +
            "    display: grid;\n" +
            "    grid-template-columns: repeat(3, 1fr);\n" +
            "    gap: 20px;\n" +
            "}\n" +
            "\n" +
            ".stat-card {\n" +
            "    text-align: center;\n" +
            "    padding: 20px;\n" +
            "    border-radius: 10px;\n" +
            "    color: white;\n" +
            "    box-shadow: 0 5px 15px rgba(0,0,0,0.1);\n" +
            "}\n" +
            "\n" +
            ".stat-card.total {\n" +
            "    background: linear-gradient(45deg, #4facfe, #00f2fe);\n" +
            "}\n" +
            "\n" +
            ".stat-card.passed {\n" +
            "    background: linear-gradient(45deg, #43e97b, #38f9d7);\n" +
            "}\n" +
            "\n" +
            ".stat-card.failed {\n" +
            "    background: linear-gradient(45deg, #fa709a, #fee140);\n" +
            "}\n" +
            "\n" +
            ".stat-card h3 {\n" +
            "    font-size: 2.5em;\n" +
            "    margin-bottom: 10px;\n" +
            "}\n" +
            "\n" +
            ".stat-card p {\n" +
            "    font-size: 1.1em;\n" +
            "    opacity: 0.9;\n" +
            "}\n" +
            "\n" +
            ".chart-container {\n" +
            "    display: flex;\n" +
            "    justify-content: center;\n" +
            "    align-items: center;\n" +
            "}\n" +
            "\n" +
            ".results-section {\n" +
            "    margin-bottom: 30px;\n" +
            "}\n" +
            "\n" +
            ".results-section h2 {\n" +
            "    color: white;\n" +
            "    font-size: 2em;\n" +
            "    margin-bottom: 20px;\n" +
            "    text-shadow: 2px 2px 4px rgba(0,0,0,0.3);\n" +
            "}\n" +
            "\n" +
            ".class-result {\n" +
            "    background: white;\n" +
            "    border-radius: 10px;\n" +
            "    padding: 20px;\n" +
            "    margin-bottom: 20px;\n" +
            "    box-shadow: 0 5px 15px rgba(0,0,0,0.1);\n" +
            "}\n" +
            "\n" +
            ".class-header {\n" +
            "    display: flex;\n" +
            "    justify-content: space-between;\n" +
            "    align-items: center;\n" +
            "    margin-bottom: 15px;\n" +
            "}\n" +
            "\n" +
            ".class-header h3 {\n" +
            "    color: #333;\n" +
            "    font-size: 1.3em;\n" +
            "}\n" +
            "\n" +
            ".class-stats {\n" +
            "    display: flex;\n" +
            "    gap: 15px;\n" +
            "}\n" +
            "\n" +
            ".class-stats .stat {\n" +
            "    padding: 5px 10px;\n" +
            "    border-radius: 15px;\n" +
            "    font-weight: bold;\n" +
            "    font-size: 0.9em;\n" +
            "}\n" +
            "\n" +
            ".class-stats .stat.passed {\n" +
            "    background: #e8f5e8;\n" +
            "    color: #2e7d32;\n" +
            "}\n" +
            "\n" +
            ".class-stats .stat.failed {\n" +
            "    background: #ffebee;\n" +
            "    color: #c62828;\n" +
            "}\n" +
            "\n" +
            ".class-stats .stat.total {\n" +
            "    background: #e3f2fd;\n" +
            "    color: #1565c0;\n" +
            "}\n" +
            "\n" +
            ".progress-bar {\n" +
            "    position: relative;\n" +
            "    background: #f0f0f0;\n" +
            "    border-radius: 10px;\n" +
            "    height: 20px;\n" +
            "    margin-bottom: 15px;\n" +
            "    overflow: hidden;\n" +
            "}\n" +
            "\n" +
            ".progress-fill {\n" +
            "    background: linear-gradient(90deg, #43e97b, #38f9d7);\n" +
            "    height: 100%;\n" +
            "    border-radius: 10px;\n" +
            "    transition: width 0.5s ease;\n" +
            "}\n" +
            "\n" +
            ".progress-text {\n" +
            "    position: absolute;\n" +
            "    top: 50%;\n" +
            "    left: 50%;\n" +
            "    transform: translate(-50%, -50%);\n" +
            "    font-weight: bold;\n" +
            "    color: #333;\n" +
            "    font-size: 0.8em;\n" +
            "}\n" +
            "\n" +
            ".method-results {\n" +
            "    display: grid;\n" +
            "    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));\n" +
            "    gap: 10px;\n" +
            "}\n" +
            "\n" +
            ".method-item {\n" +
            "    display: flex;\n" +
            "    justify-content: space-between;\n" +
            "    align-items: center;\n" +
            "    padding: 10px 15px;\n" +
            "    border-radius: 8px;\n" +
            "    background: #f8f9fa;\n" +
            "    border-left: 4px solid;\n" +
            "}\n" +
            "\n" +
            ".method-item.passed {\n" +
            "    border-left-color: #28a745;\n" +
            "    background: #f8fff8;\n" +
            "}\n" +
            "\n" +
            ".method-name {\n" +
            "    flex: 1;\n" +
            "    margin-left: 10px;\n" +
            "    font-weight: 500;\n" +
            "}\n" +
            "\n" +
            ".method-time {\n" +
            "    color: #666;\n" +
            "    font-size: 0.9em;\n" +
            "}\n" +
            "\n" +
            ".failed-section {\n" +
            "    margin-top: 40px;\n" +
            "}\n" +
            "\n" +
            ".failed-section h2 {\n" +
            "    color: white;\n" +
            "    font-size: 2em;\n" +
            "    margin-bottom: 20px;\n" +
            "    text-shadow: 2px 2px 4px rgba(0,0,0,0.3);\n" +
            "}\n" +
            "\n" +
            ".failed-tests {\n" +
            "    display: flex;\n" +
            "    flex-direction: column;\n" +
            "    gap: 15px;\n" +
            "}\n" +
            "\n" +
            ".failed-test-item {\n" +
            "    background: white;\n" +
            "    border-radius: 10px;\n" +
            "    padding: 20px;\n" +
            "    box-shadow: 0 5px 15px rgba(0,0,0,0.1);\n" +
            "    cursor: pointer;\n" +
            "    transition: transform 0.2s, box-shadow 0.2s;\n" +
            "    border-left: 5px solid #dc3545;\n" +
            "}\n" +
            "\n" +
            ".failed-test-item:hover {\n" +
            "    transform: translateY(-2px);\n" +
            "    box-shadow: 0 8px 25px rgba(0,0,0,0.15);\n" +
            "}\n" +
            "\n" +
            ".failed-test-header {\n" +
            "    display: flex;\n" +
            "    align-items: center;\n" +
            "    gap: 15px;\n" +
            "    margin-bottom: 10px;\n" +
            "}\n" +
            "\n" +
            ".failed-icon {\n" +
            "    font-size: 1.2em;\n" +
            "}\n" +
            "\n" +
            ".failed-class {\n" +
            "    font-weight: bold;\n" +
            "    color: #333;\n" +
            "}\n" +
            "\n" +
            ".failed-method {\n" +
            "    color: #666;\n" +
            "    font-style: italic;\n" +
            "}\n" +
            "\n" +
            ".failed-time {\n" +
            "    color: #999;\n" +
            "    font-size: 0.9em;\n" +
            "}\n" +
            "\n" +
            ".view-log-btn {\n" +
            "    margin-left: auto;\n" +
            "    background: #dc3545;\n" +
            "    color: white;\n" +
            "    padding: 5px 15px;\n" +
            "    border-radius: 15px;\n" +
            "    font-size: 0.8em;\n" +
            "    font-weight: bold;\n" +
            "}\n" +
            "\n" +
            ".failed-reason {\n" +
            "    background: #f8f9fa;\n" +
            "    padding: 10px;\n" +
            "    border-radius: 5px;\n" +
            "    color: #666;\n" +
            "    font-family: monospace;\n" +
            "    font-size: 0.9em;\n" +
            "    border-left: 3px solid #dc3545;\n" +
            "}";
    }

    private static String generateJavaScript(int totalPassed, int totalFailed) {
        return String.format(
            "// Pie Chart Configuration\n" +
            "const ctx = document.getElementById('pieChart').getContext('2d');\n" +
            "const pieChart = new Chart(ctx, {\n" +
            "    type: 'pie',\n" +
            "    data: {\n" +
            "        labels: ['Passed', 'Failed'],\n" +
            "        datasets: [{\n" +
            "            data: [%d, %d],\n" +
            "            backgroundColor: [\n" +
            "                '#28a745',\n" +
            "                '#dc3545'\n" +
            "            ],\n" +
            "            borderWidth: 3,\n" +
            "            borderColor: '#fff'\n" +
            "        }]\n" +
            "    },\n" +
            "    options: {\n" +
            "        responsive: true,\n" +
            "        maintainAspectRatio: false,\n" +
            "        plugins: {\n" +
            "            legend: {\n" +
            "                position: 'bottom',\n" +
            "                labels: {\n" +
            "                    padding: 20,\n" +
            "                    font: {\n" +
            "                        size: 14,\n" +
            "                        weight: 'bold'\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "});\n" +
            "\n" +
            "// Function to handle log viewing\n" +
            "function viewLog(logFileName) {\n" +
            "    // Check if log file exists in logs directory\n" +
            "    const logPath = 'logs/' + logFileName;\n" +
            "\n" +
            "    // Try to open the log file\n" +
            "    const newWindow = window.open(logPath, '_blank');\n" +
            "    if (!newWindow) {\n" +
            "        // If popup blocked or file doesn't exist, show alert\n" +
            "        alert('Log file: ' + logFileName + '\\\\nLocation: ' + logPath + '\\\\n\\\\nPlease check the logs directory for detailed error information.');\n" +
            "    }\n" +
            "\n" +
            "    // Alternative: redirect to log viewer page\n" +
            "    // window.location.href = 'log-viewer.html?file=' + logFileName;\n" +
            "}", totalPassed, totalFailed);
    }

    private static String generateEmptyReport() {
        return "<!DOCTYPE html>\n" +
            "<html lang='en'>\n" +
            "<head>\n" +
            "    <meta charset='UTF-8'>\n" +
            "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
            "    <title>Enhanced Test Report</title>\n" +
            "    <style>\n" +
            "        body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }\n" +
            "        .message { background: #f0f0f0; padding: 30px; border-radius: 10px; }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class='message'>\n" +
            "        <h1>📊 Test Report</h1>\n" +
            "        <p>No test results available. Please run tests first.</p>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";
    }
}