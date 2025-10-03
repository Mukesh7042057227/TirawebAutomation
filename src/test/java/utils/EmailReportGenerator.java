package utils;

import java.util.Map;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        int totalSkipped = 0;
        int totalKnownIssue = 0;
        int totalQueued = 0;
        int totalAborted = 0;

        for (TestResultTracker.TestClassResult result : allResults.values()) {
            totalTests += result.getTotalTests();
            totalPassed += result.getPassedTests();
            totalFailed += result.getFailedTests();
            totalSkipped += result.getSkippedTests();
            totalKnownIssue += result.getKnownIssueTests();
            totalQueued += result.getQueuedTests();
            totalAborted += result.getAbortedTests();
        }

        return generateEmailHTMLReport(allResults, totalTests, totalPassed, totalFailed, totalSkipped, totalKnownIssue, totalQueued, totalAborted);
    }

    private static String generateEmailHTMLReport(Map<String, TestResultTracker.TestClassResult> allResults,
                                                int totalTests, int totalPassed, int totalFailed, int totalSkipped,
                                                int totalKnownIssue, int totalQueued, int totalAborted) {
        StringBuilder html = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");

        double passedPercent = totalTests > 0 ? (double) totalPassed / totalTests * 100 : 0;
        double failedPercent = totalTests > 0 ? (double) totalFailed / totalTests * 100 : 0;
        double skippedPercent = totalTests > 0 ? (double) totalSkipped / totalTests * 100 : 0;
        double knownIssuePercent = totalTests > 0 ? (double) totalKnownIssue / totalTests * 100 : 0;
        double queuedPercent = totalTests > 0 ? (double) totalQueued / totalTests * 100 : 0;
        double abortedPercent = totalTests > 0 ? (double) totalAborted / totalTests * 100 : 0;

        html.append("<!DOCTYPE html>\n")
            .append("<html lang='en'>\n")
            .append("<head>\n")
            .append("    <meta charset='UTF-8'>\n")
            .append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n")
            .append("    <title>Weekly Regression Report</title>\n")
            .append("    <script src='https://cdn.jsdelivr.net/npm/chart.js'></script>\n")
            .append("    <style>\n")
            .append(generateEmailCSS())
            .append("    </style>\n")
            .append("</head>\n")
            .append("<body>\n")
            .append("    <div class='container'>\n")
            .append("        <div class='sidebar'>\n")
            .append("            <div class='logo'>T</div>\n")
            .append("        </div>\n")
            .append("        <div class='main-content'>\n")
            .append("            <div class='header'>\n")
            .append("                <h1>Weekly Regression</h1>\n")
            .append("            </div>\n");

        // Summary cards section - 3 cards in a row
        html.append("            <div class='cards-section'>\n")
            // Card 1: WEEKLY TOTAL
            .append("                <div class='card'>\n")
            .append("                    <div class='card-title'>WEEKLY TOTAL</div>\n")
            .append("                    <div class='card-content'>\n")
            .append("                        <div class='chart-wrapper'>\n")
            .append("                            <canvas id='totalChart'></canvas>\n")
            .append("                        </div>\n")
            .append("                        <div class='stats'>\n")
            .append("                            <div class='stat-line stat-passed clickable-stat' onclick=\"filterTestsByStatus('PASSED')\">PASSED ......... ").append(totalPassed).append("</div>\n")
            .append("                            <div class='stat-line stat-known clickable-stat' onclick=\"filterTestsByStatus('KNOWN_ISSUE')\">KNOWN ISSUE ..... ").append(totalKnownIssue).append("</div>\n")
            .append("                            <div class='stat-line stat-failed clickable-stat' onclick=\"filterTestsByStatus('FAILED')\">FAILED .......... ").append(totalFailed).append("</div>\n")
            .append("                            <div class='stat-line stat-queued clickable-stat' onclick=\"filterTestsByStatus('QUEUED')\">QUEUED .......... ").append(totalQueued).append("</div>\n")
            .append("                            <div class='stat-line stat-aborted clickable-stat' onclick=\"filterTestsByStatus('ABORTED')\">ABORTED ......... ").append(totalAborted).append("</div>\n")
            .append("                            <div class='stat-line stat-skipped clickable-stat' onclick=\"filterTestsByStatus('SKIPPED')\">SKIPPED ......... ").append(totalSkipped).append("</div>\n")
            .append("                        </div>\n")
            .append("                    </div>\n")
            .append("                </div>\n")
            // Card 2: WEEKLY TOTAL (%)
            .append("                <div class='card'>\n")
            .append("                    <div class='card-title'>WEEKLY TOTAL (%)</div>\n")
            .append("                    <div class='card-content'>\n")
            .append("                        <div class='chart-wrapper'>\n")
            .append("                            <canvas id='percentChart'></canvas>\n")
            .append("                        </div>\n")
            .append("                        <div class='stats'>\n")
            .append("                            <div class='stat-line stat-passed clickable-stat' onclick=\"filterTestsByStatus('PASSED')\">PASSED ....... ").append(df.format(passedPercent)).append("%</div>\n")
            .append("                            <div class='stat-line stat-failed clickable-stat' onclick=\"filterTestsByStatus('FAILED')\">FAILED ........ ").append(df.format(failedPercent)).append("%</div>\n")
            .append("                            <div class='stat-line stat-known clickable-stat' onclick=\"filterTestsByStatus('KNOWN_ISSUE')\">KNOWN ISSUE ... ").append(df.format(knownIssuePercent)).append("%</div>\n")
            .append("                            <div class='stat-line stat-queued clickable-stat' onclick=\"filterTestsByStatus('QUEUED')\">QUEUED ........ ").append(df.format(queuedPercent)).append("%</div>\n")
            .append("                            <div class='stat-line stat-aborted clickable-stat' onclick=\"filterTestsByStatus('ABORTED')\">ABORTED ....... ").append(df.format(abortedPercent)).append("%</div>\n")
            .append("                            <div class='stat-line stat-skipped clickable-stat' onclick=\"filterTestsByStatus('SKIPPED')\">SKIPPED ....... ").append(df.format(skippedPercent)).append("%</div>\n")
            .append("                        </div>\n")
            .append("                    </div>\n")
            .append("                </div>\n")
            // Card 3: TEST RESULTS (LAST 7 DAYS) - Area Chart
            .append("                <div class='card card-wide'>\n")
            .append("                    <div class='card-title'>TEST RESULTS (LAST 7 DAYS)</div>\n")
            .append("                    <div class='card-content-chart'>\n")
            .append("                        <canvas id='trendChart'></canvas>\n")
            .append("                    </div>\n")
            .append("                    <div class='legend'>\n")
            .append("                        <span class='legend-item'><span class='legend-color stat-passed'></span> PASSED</span>\n")
            .append("                        <span class='legend-item'><span class='legend-color stat-failed'></span> FAILED</span>\n")
            .append("                        <span class='legend-item'><span class='legend-color stat-known'></span> KNOWN_ISSUE</span>\n")
            .append("                        <span class='legend-item'><span class='legend-color stat-skipped'></span> SKIPPED</span>\n")
            .append("                        <span class='legend-item'><span class='legend-color stat-aborted'></span> ABORTED</span>\n")
            .append("                        <span class='legend-item'><span class='legend-color stat-in-progress'></span> IN_PROGRESS</span>\n")
            .append("                        <span class='legend-item'><span class='legend-color stat-queued'></span> QUEUED</span>\n")
            .append("                    </div>\n")
            .append("                </div>\n")
            .append("            </div>\n");

        // Test Details Table
        html.append("            <div class='table-section'>\n")
            .append("                <div class='table-title'>WEEKLY DETAILS</div>\n")
            .append("                <table class='test-table'>\n")
            .append("                    <thead>\n")
            .append("                        <tr>\n")
            .append("                            <th>OWNER</th>\n")
            .append("                            <th>REPORT</th>\n")
            .append("                            <th>PASSED</th>\n")
            .append("                            <th>FAILED</th>\n")
            .append("                            <th>KNOWN ISSUE</th>\n")
            .append("                            <th>SKIPPED</th>\n")
            .append("                            <th>QUEUED</th>\n")
            .append("                            <th>TOTAL</th>\n")
            .append("                            <th>PASSED (%)</th>\n")
            .append("                            <th>FAILED (%)</th>\n")
            .append("                            <th>KNOWN ISSUE (%)</th>\n")
            .append("                            <th>SKIPPED (%)</th>\n")
            .append("                            <th>QUEUED (%)</th>\n")
            .append("                        </tr>\n")
            .append("                    </thead>\n")
            .append("                    <tbody>\n");

        // Add class-level summary rows
        for (TestResultTracker.TestClassResult classResult : allResults.values()) {
            int classPassed = classResult.getPassedTests();
            int classFailed = classResult.getFailedTests();
            int classKnownIssue = classResult.getKnownIssueTests();
            int classSkipped = classResult.getSkippedTests();
            int classQueued = classResult.getQueuedTests();
            int classTotal = classResult.getTotalTests();

            double classPassedPercent = classResult.getPassPercentage();
            double classFailedPercent = classResult.getFailPercentage();
            double classKnownIssuePercent = classResult.getKnownIssuePercentage();
            double classSkippedPercent = classResult.getSkippedPercentage();
            double classQueuedPercent = classResult.getQueuedPercentage();

            String rowClass = classFailed > 0 ? "row-has-failures" : "";
            String simpleClassName = getSimpleClassName(classResult.getClassName());

            html.append("                        <tr class='").append(rowClass).append("' onclick=\"toggleClassDetails('class-").append(classResult.getClassName().hashCode()).append("')\">\n")
                .append("                            <td class='class-name'>qpsdemo</td>\n")
                .append("                            <td class='report-name'>")
                .append("<span class='expand-icon' id='icon-class-").append(classResult.getClassName().hashCode()).append("'>▶</span> ")
                .append("<span class='link-style'>").append(simpleClassName).append("</span></td>\n")
                .append("                            <td class='stat-passed'>").append(classPassed).append("</td>\n")
                .append("                            <td class='stat-failed'>").append(classFailed).append("</td>\n")
                .append("                            <td class='stat-known'>").append(classKnownIssue).append("</td>\n")
                .append("                            <td class='stat-skipped'>").append(classSkipped).append("</td>\n")
                .append("                            <td class='stat-queued'>").append(classQueued).append("</td>\n")
                .append("                            <td>").append(classTotal).append("</td>\n")
                .append("                            <td class='stat-passed'>").append(df.format(classPassedPercent)).append("</td>\n")
                .append("                            <td class='stat-failed'>").append(df.format(classFailedPercent)).append("</td>\n")
                .append("                            <td class='stat-known'>").append(df.format(classKnownIssuePercent)).append("</td>\n")
                .append("                            <td class='stat-skipped'>").append(df.format(classSkippedPercent)).append("</td>\n")
                .append("                            <td class='stat-queued'>").append(df.format(classQueuedPercent)).append("</td>\n")
                .append("                        </tr>\n");

            // Add expandable details for this class
            html.append("                        <tr class='details-row' id='class-").append(classResult.getClassName().hashCode()).append("' style='display: none;'>\n")
                .append("                            <td colspan='13'>\n")
                .append("                                <div class='method-details'>\n")
                .append("                                    <table class='method-table'>\n")
                .append("                                        <thead>\n")
                .append("                                            <tr>\n")
                .append("                                                <th>METHOD NAME</th>\n")
                .append("                                                <th>STATUS</th>\n")
                .append("                                                <th>EXECUTION TIME</th>\n")
                .append("                                                <th>FAILURE DETAILS</th>\n")
                .append("                                            </tr>\n")
                .append("                                        </thead>\n")
                .append("                                        <tbody>\n");

            for (TestResultTracker.TestMethodResult method : classResult.getMethodResults()) {
                String status = method.getStatus().name();
                String statusClass = "badge-" + status.toLowerCase().replace("_", "-");
                String methodRowClass = method.isPassed() ? "" : "method-failed";

                html.append("                                            <tr class='").append(methodRowClass).append(" test-method-row' data-status='").append(status).append("' data-class='").append(classResult.getClassName()).append("' data-method='").append(method.getMethodName()).append("'>\n")
                    .append("                                                <td>").append(method.getMethodName()).append("</td>\n")
                    .append("                                                <td><span class='badge ").append(statusClass).append("'>").append(status).append("</span></td>\n")
                    .append("                                                <td>").append(method.getExecutionTime()).append(" ms</td>\n")
                    .append("                                                <td>");

                if (!method.isPassed()) {
                    String detailsId = "method-" + method.getClassName().hashCode() + "-" + method.getMethodName().hashCode();
                    String failureMsg = method.getFailureReason() != null ? escapeHtml(method.getFailureReason()) : "Unknown error";

                    html.append("<div class='failure-summary' onclick=\"event.stopPropagation(); toggleMethodDetails('").append(detailsId).append("');\">")
                        .append("<span class='arrow' id='arrow-").append(detailsId).append("'>▶</span> ")
                        .append("<span class='failure-preview'>").append(truncate(failureMsg, 60)).append("</span>")
                        .append("</div>")
                        .append("<div class='failure-full' id='").append(detailsId).append("' style='display: none;'>")
                        .append("<div class='error-section'>")
                        .append("<strong>Error Message:</strong>")
                        .append("<pre>").append(failureMsg).append("</pre>");

                    if (method.getStackTrace() != null) {
                        html.append("<strong>Stack Trace:</strong>")
                            .append("<pre class='stack-trace'>").append(escapeHtml(method.getStackTrace())).append("</pre>");
                    }

                    html.append("</div></div>");
                } else {
                    html.append("-");
                }

                html.append("</td>\n")
                    .append("                                            </tr>\n");
            }

            html.append("                                        </tbody>\n")
                .append("                                    </table>\n")
                .append("                                </div>\n")
                .append("                            </td>\n")
                .append("                        </tr>\n");
        }

        html.append("                    </tbody>\n")
            .append("                </table>\n")
            .append("            </div>\n");

        // Footer
        String footerClass = passedPercent >= 80 ? "footer-success" : passedPercent >= 60 ? "footer-warning" : "footer-danger";
        html.append("            <div class='footer ").append(footerClass).append("'>\n")
            .append("                <p><strong>Summary:</strong> ").append(totalTests).append(" tests executed | ")
            .append(totalPassed).append(" passed (").append(df.format(passedPercent)).append("%) | ")
            .append(totalFailed).append(" failed (").append(df.format(failedPercent)).append("%)</p>\n");

        if (totalFailed > 0) {
            html.append("                <p style='margin-top: 10px;'>⚠️ Please review failed test cases above. Detailed logs available in <code>test-output/logs/</code></p>\n");
        }

        html.append("            </div>\n")
            .append("        </div>\n")
            .append("    </div>\n")
            .append("    <script>\n")
            .append(generateEmailJavaScript(totalPassed, totalFailed, totalSkipped, totalKnownIssue, totalQueued, totalAborted))
            .append("    </script>\n")
            .append("</body>\n")
            .append("</html>");

        return html.toString();
    }

    private static String generateEmailCSS() {
        return
            "* { margin: 0; padding: 0; box-sizing: border-box; }\n" +
            "body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif; background: #f5f7fa; }\n" +
            ".container { display: flex; min-height: 100vh; }\n" +
            ".sidebar { width: 60px; background: #37474f; display: flex; flex-direction: column; align-items: center; padding-top: 20px; }\n" +
            ".logo { width: 40px; height: 40px; background: linear-gradient(135deg, #00bcd4, #009688); border-radius: 4px; display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; font-size: 24px; }\n" +
            ".main-content { flex: 1; background: white; }\n" +
            ".header { background: #f8f9fa; color: #00bcd4; padding: 25px 30px; border-bottom: 1px solid #e0e0e0; }\n" +
            ".header h1 { font-size: 28px; font-weight: 500; }\n" +
            ".cards-section { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; padding: 30px; background: #fafafa; }\n" +
            ".card { background: white; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.12); overflow: hidden; }\n" +
            ".card-wide { grid-column: span 1; }\n" +
            ".card-title { background: #f5f5f5; padding: 12px 20px; font-weight: 600; font-size: 11px; color: #616161; text-transform: uppercase; letter-spacing: 0.5px; border-bottom: 1px solid #e0e0e0; }\n" +
            ".card-content { padding: 25px 20px; display: flex; gap: 20px; align-items: center; }\n" +
            ".card-content-chart { padding: 20px; height: 300px; position: relative; }\n" +
            ".chart-wrapper { width: 140px; height: 140px; flex-shrink: 0; }\n" +
            ".stats { flex: 1; }\n" +
            ".stat-line { padding: 4px 0; font-family: 'Courier New', monospace; font-size: 13px; color: #757575; }\n" +
            ".stat-passed { color: #4caf50; }\n" +
            ".stat-failed { color: #f44336; }\n" +
            ".stat-skipped { color: #ff9800; }\n" +
            ".stat-known { color: #ff9800; }\n" +
            ".stat-queued { color: #9e9e9e; }\n" +
            ".stat-aborted { color: #bdbdbd; }\n" +
            ".stat-in-progress { color: #2196f3; }\n" +
            ".legend { padding: 0 20px 20px; display: flex; flex-wrap: wrap; gap: 15px; font-size: 11px; }\n" +
            ".legend-item { display: flex; align-items: center; gap: 6px; }\n" +
            ".legend-color { width: 12px; height: 12px; border-radius: 50%; display: inline-block; }\n" +
            ".table-section { padding: 30px; }\n" +
            ".table-title { font-size: 13px; font-weight: 600; margin-bottom: 15px; color: #616161; text-transform: uppercase; letter-spacing: 0.5px; }\n" +
            ".test-table { width: 100%; border-collapse: collapse; font-size: 13px; border: 1px solid #e0e0e0; }\n" +
            ".test-table thead th { background: #fafafa; color: #616161; padding: 12px 16px; text-align: left; font-weight: 600; font-size: 11px; text-transform: uppercase; letter-spacing: 0.5px; border-bottom: 1px solid #e0e0e0; }\n" +
            ".test-table tbody td { padding: 12px 16px; border-bottom: 1px solid #f5f5f5; }\n" +
            ".test-table tbody tr { cursor: pointer; transition: background 0.2s; }\n" +
            ".test-table tbody tr:hover { background: #fafafa; }\n" +
            ".row-has-failures { background: #fff5f5; }\n" +
            ".row-has-failures:hover { background: #ffe5e5 !important; }\n" +
            ".class-name { color: #424242; }\n" +
            ".report-name { font-weight: 500; }\n" +
            ".link-style { color: #00bcd4; }\n" +
            ".expand-icon { display: inline-block; transition: transform 0.3s; font-size: 10px; margin-right: 8px; color: #9e9e9e; }\n" +
            ".expand-icon.rotated { transform: rotate(90deg); }\n" +
            ".details-row { background: #fafafa; }\n" +
            ".method-details { padding: 20px; }\n" +
            ".method-table { width: 100%; border-collapse: collapse; }\n" +
            ".method-table thead th { background: #e0e0e0; color: #616161; padding: 10px 12px; text-align: left; font-size: 11px; font-weight: 600; }\n" +
            ".method-table tbody td { padding: 10px 12px; border-bottom: 1px solid #eeeeee; }\n" +
            ".method-failed { background: #fff5f5; }\n" +
            ".badge { padding: 4px 10px; border-radius: 3px; font-size: 10px; font-weight: 700; text-transform: uppercase; display: inline-block; }\n" +
            ".badge-passed { background: #e8f5e9; color: #2e7d32; }\n" +
            ".badge-failed { background: #ffebee; color: #c62828; }\n" +
            ".badge-skipped { background: #fff3e0; color: #e65100; }\n" +
            ".badge-known-issue { background: #fff3e0; color: #e65100; }\n" +
            ".badge-queued { background: #f5f5f5; color: #616161; }\n" +
            ".badge-aborted { background: #fafafa; color: #9e9e9e; }\n" +
            ".failure-summary { cursor: pointer; color: #f44336; display: flex; align-items: flex-start; gap: 6px; }\n" +
            ".failure-summary:hover { color: #d32f2f; }\n" +
            ".arrow { font-size: 10px; transition: transform 0.3s; display: inline-block; }\n" +
            ".arrow.rotated { transform: rotate(90deg); }\n" +
            ".failure-preview { flex: 1; }\n" +
            ".failure-full { margin-top: 12px; padding: 15px; background: white; border-left: 3px solid #f44336; border-radius: 4px; }\n" +
            ".error-section { font-size: 12px; }\n" +
            ".error-section strong { display: block; margin-bottom: 8px; margin-top: 12px; color: #616161; }\n" +
            ".error-section strong:first-child { margin-top: 0; }\n" +
            ".error-section pre { background: #fafafa; padding: 12px; border-radius: 4px; overflow-x: auto; white-space: pre-wrap; word-wrap: break-word; font-size: 11px; color: #c62828; line-height: 1.5; }\n" +
            ".stack-trace { max-height: 250px; overflow-y: auto; }\n" +
            ".footer { padding: 20px 30px; text-align: center; border-top: 1px solid #e0e0e0; font-size: 14px; margin-top: 20px; }\n" +
            ".footer-success { background: #e8f5e9; color: #2e7d32; }\n" +
            ".footer-warning { background: #fff3e0; color: #e65100; }\n" +
            ".footer-danger { background: #ffebee; color: #c62828; }\n" +
            ".footer code { background: rgba(0,0,0,0.1); padding: 3px 6px; border-radius: 3px; font-size: 12px; }\n" +
            ".clickable-stat { cursor: pointer; transition: all 0.2s; }\n" +
            ".clickable-stat:hover { background: rgba(0,0,0,0.05); padding-left: 8px; border-radius: 3px; }\n" +
            ".modal-overlay { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 1000; justify-content: center; align-items: center; }\n" +
            ".modal-overlay.active { display: flex; }\n" +
            ".modal-content { background: white; border-radius: 8px; max-width: 90%; max-height: 90%; overflow-y: auto; box-shadow: 0 10px 50px rgba(0,0,0,0.3); }\n" +
            ".modal-header { background: #f5f5f5; padding: 20px 30px; border-bottom: 2px solid #e0e0e0; display: flex; justify-content: space-between; align-items: center; }\n" +
            ".modal-header h2 { margin: 0; color: #424242; font-size: 20px; }\n" +
            ".modal-close { background: none; border: none; font-size: 28px; cursor: pointer; color: #9e9e9e; line-height: 1; padding: 0; width: 30px; height: 30px; }\n" +
            ".modal-close:hover { color: #424242; }\n" +
            ".modal-body { padding: 30px; }\n" +
            ".filtered-test-row { display: none; }";
    }

    private static String generateEmailJavaScript(int totalPassed, int totalFailed, int totalSkipped,
                                                   int totalKnownIssue, int totalQueued, int totalAborted) {
        return String.format(
            "function toggleClassDetails(rowId) {\n" +
            "    const row = document.getElementById(rowId);\n" +
            "    const icon = document.getElementById('icon-' + rowId);\n" +
            "    if (row.style.display === 'none') {\n" +
            "        row.style.display = 'table-row';\n" +
            "        icon.classList.add('rotated');\n" +
            "    } else {\n" +
            "        row.style.display = 'none';\n" +
            "        icon.classList.remove('rotated');\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function toggleMethodDetails(detailsId) {\n" +
            "    const details = document.getElementById(detailsId);\n" +
            "    const arrow = document.getElementById('arrow-' + detailsId);\n" +
            "    if (details.style.display === 'none' || details.style.display === '') {\n" +
            "        details.style.display = 'block';\n" +
            "        arrow.classList.add('rotated');\n" +
            "    } else {\n" +
            "        details.style.display = 'none';\n" +
            "        arrow.classList.remove('rotated');\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "document.addEventListener('DOMContentLoaded', function() {\n" +
            "    // Pie charts configuration (changed from doughnut)\n" +
            "    const chartConfig = {\n" +
            "        type: 'pie',\n" +
            "        data: {\n" +
            "            labels: ['Passed', 'Known Issue', 'Failed', 'Queued', 'Aborted', 'Skipped'],\n" +
            "            datasets: [{\n" +
            "                data: [%d, %d, %d, %d, %d, %d],\n" +
            "                backgroundColor: ['#4caf50', '#ff9800', '#f44336', '#9e9e9e', '#bdbdbd', '#ff9800'],\n" +
            "                borderWidth: 2,\n" +
            "                borderColor: '#fff'\n" +
            "            }]\n" +
            "        },\n" +
            "        options: {\n" +
            "            responsive: true,\n" +
            "            maintainAspectRatio: true,\n" +
            "            plugins: {\n" +
            "                legend: { display: false },\n" +
            "                tooltip: { enabled: true }\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "    \n" +
            "    const totalChart = document.getElementById('totalChart');\n" +
            "    const percentChart = document.getElementById('percentChart');\n" +
            "    \n" +
            "    if (totalChart) new Chart(totalChart, chartConfig);\n" +
            "    if (percentChart) new Chart(percentChart, chartConfig);\n" +
            "    \n" +
            "    // Trend chart (area chart)\n" +
            "    const trendChart = document.getElementById('trendChart');\n" +
            "    if (trendChart) {\n" +
            "        new Chart(trendChart, {\n" +
            "            type: 'line',\n" +
            "            data: {\n" +
            "                labels: ['12 PM', 'Tue 03', '12 PM', 'Wed 04', '12 PM', 'Thu 05', '12 PM', 'Fri 06'],\n" +
            "                datasets: [\n" +
            "                    {\n" +
            "                        label: 'Passed',\n" +
            "                        data: [5, 10, 15, 40, 12, 15, 55, 10],\n" +
            "                        backgroundColor: 'rgba(76, 175, 80, 0.3)',\n" +
            "                        borderColor: '#4caf50',\n" +
            "                        borderWidth: 2,\n" +
            "                        fill: true,\n" +
            "                        tension: 0.4\n" +
            "                    },\n" +
            "                    {\n" +
            "                        label: 'Failed',\n" +
            "                        data: [15, 8, 10, 3, 5, 3, 10, 2],\n" +
            "                        backgroundColor: 'rgba(244, 67, 54, 0.3)',\n" +
            "                        borderColor: '#f44336',\n" +
            "                        borderWidth: 2,\n" +
            "                        fill: true,\n" +
            "                        tension: 0.4\n" +
            "                    },\n" +
            "                    {\n" +
            "                        label: 'Known Issue',\n" +
            "                        data: [2, 5, 3, 10, 4, 3, 8, 5],\n" +
            "                        backgroundColor: 'rgba(255, 152, 0, 0.3)',\n" +
            "                        borderColor: '#ff9800',\n" +
            "                        borderWidth: 2,\n" +
            "                        fill: true,\n" +
            "                        tension: 0.4\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            options: {\n" +
            "                responsive: true,\n" +
            "                maintainAspectRatio: false,\n" +
            "                scales: {\n" +
            "                    y: {\n" +
            "                        beginAtZero: true,\n" +
            "                        max: 60,\n" +
            "                        ticks: {\n" +
            "                            stepSize: 5\n" +
            "                        }\n" +
            "                    }\n" +
            "                },\n" +
            "                plugins: {\n" +
            "                    legend: { display: false }\n" +
            "                }\n" +
            "            }\n" +
            "        });\n" +
            "    }\n" +
            "});\n" +
            "\n" +
            "function filterTestsByStatus(status) {\n" +
            "    // Get all test method rows\n" +
            "    const allRows = document.querySelectorAll('.test-method-row');\n" +
            "    let filteredHTML = '<table class=\"method-table\"><thead><tr>';\n" +
            "    filteredHTML += '<th>TEST CLASS</th><th>METHOD NAME</th><th>STATUS</th><th>EXECUTION TIME</th><th>FAILURE DETAILS</th>';\n" +
            "    filteredHTML += '</tr></thead><tbody>';\n" +
            "    \n" +
            "    let count = 0;\n" +
            "    allRows.forEach(row => {\n" +
            "        if (row.dataset.status === status) {\n" +
            "            count++;\n" +
            "            const testClass = row.dataset.class.split('.').pop();\n" +
            "            const methodName = row.dataset.method;\n" +
            "            const execTime = row.cells[2].textContent;\n" +
            "            const statusBadge = row.cells[1].innerHTML;\n" +
            "            const failureDetails = row.cells[3].innerHTML;\n" +
            "            \n" +
            "            filteredHTML += '<tr class=\"' + (status !== 'PASSED' ? 'method-failed' : '') + '\">';\n" +
            "            filteredHTML += '<td>' + testClass + '</td>';\n" +
            "            filteredHTML += '<td>' + methodName + '</td>';\n" +
            "            filteredHTML += '<td>' + statusBadge + '</td>';\n" +
            "            filteredHTML += '<td>' + execTime + '</td>';\n" +
            "            filteredHTML += '<td>' + failureDetails + '</td>';\n" +
            "            filteredHTML += '</tr>';\n" +
            "        }\n" +
            "    });\n" +
            "    \n" +
            "    filteredHTML += '</tbody></table>';\n" +
            "    \n" +
            "    if (count === 0) {\n" +
            "        filteredHTML = '<p style=\"text-align: center; padding: 40px; color: #9e9e9e;\">No tests found with status: ' + status + '</p>';\n" +
            "    }\n" +
            "    \n" +
            "    // Create modal if it doesn't exist\n" +
            "    let modal = document.getElementById('statusModal');\n" +
            "    if (!modal) {\n" +
            "        modal = document.createElement('div');\n" +
            "        modal.id = 'statusModal';\n" +
            "        modal.className = 'modal-overlay';\n" +
            "        modal.innerHTML = '<div class=\"modal-content\">' +\n" +
            "            '<div class=\"modal-header\">' +\n" +
            "                '<h2 id=\"modalTitle\"></h2>' +\n" +
            "                '<button class=\"modal-close\" onclick=\"closeModal()\">&times;</button>' +\n" +
            "            '</div>' +\n" +
            "            '<div class=\"modal-body\" id=\"modalBody\"></div>' +\n" +
            "        '</div>';\n" +
            "        document.body.appendChild(modal);\n" +
            "        \n" +
            "        // Close modal when clicking outside\n" +
            "        modal.addEventListener('click', function(e) {\n" +
            "            if (e.target === modal) closeModal();\n" +
            "        });\n" +
            "    }\n" +
            "    \n" +
            "    document.getElementById('modalTitle').textContent = status + ' Tests (' + count + ')';\n" +
            "    document.getElementById('modalBody').innerHTML = filteredHTML;\n" +
            "    modal.classList.add('active');\n" +
            "}\n" +
            "\n" +
            "function closeModal() {\n" +
            "    const modal = document.getElementById('statusModal');\n" +
            "    if (modal) {\n" +
            "        modal.classList.remove('active');\n" +
            "    }\n" +
            "}", totalPassed, totalKnownIssue, totalFailed, totalQueued, totalAborted, totalSkipped);
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
            "        <h1>📊 Test Execution Report</h1>\n" +
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

    private static String truncate(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) return text;
        return text.substring(0, maxLength) + "...";
    }

    private static String getSimpleClassName(String fullClassName) {
        String[] parts = fullClassName.split("\\.");
        return parts[parts.length - 1];
    }
}