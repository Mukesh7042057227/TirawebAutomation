package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.File;

public class EmailUtility {

    private static final String SUCCESS_PREFIX = "✅ ";
    private static final String ERROR_PREFIX = "❌ ";
    private static final String INFO_PREFIX = "🔍 ";
    private static final Set<String> emailsSent = ConcurrentHashMap.newKeySet();

    public static void sendTestNotification(String testClassName, String testMethodName,
                                          String status, String errorMessage, long duration) {
        sendTestNotification(testClassName, testMethodName, status, errorMessage, duration, null);
    }

    public static void sendTestNotification(String testClassName, String testMethodName,
                                          String status, String errorMessage, long duration, Throwable throwable) {
        sendTestNotification(testClassName, testMethodName, status, errorMessage, duration, throwable, null);
    }

    public static void sendTestNotification(String testClassName, String testMethodName,
                                          String status, String errorMessage, long duration, Throwable throwable, String currentUrl) {
        try {
            // Create unique key to prevent duplicate emails
            String emailKey = testClassName + "." + testMethodName + "_" + status;
            if (emailsSent.contains(emailKey)) {
                System.out.println(INFO_PREFIX + "Duplicate email prevented for: " + testClassName + "." + testMethodName);
                return;
            }
            emailsSent.add(emailKey);

            // Ensure config is loaded
            ConfigReader.loadConfig();

            // Check if email is enabled
            if (!Boolean.parseBoolean(ConfigReader.get("email.enabled"))) {
                System.out.println(INFO_PREFIX + "Email notifications are disabled");
                return;
            }

            // Check if we should send email for this status
            boolean shouldSend = false;
            if ("PASSED".equalsIgnoreCase(status) && Boolean.parseBoolean(ConfigReader.get("email.send.on.success"))) {
                shouldSend = true;
            } else if ("FAILED".equalsIgnoreCase(status) && Boolean.parseBoolean(ConfigReader.get("email.send.on.failure"))) {
                shouldSend = true;
            }

            if (!shouldSend) {
                System.out.println(INFO_PREFIX + "Email notification skipped for status: " + status);
                return;
            }

            // Setup email properties
            Properties emailProps = new Properties();
            emailProps.put("mail.smtp.host", ConfigReader.get("email.smtp.host"));
            emailProps.put("mail.smtp.port", ConfigReader.get("email.smtp.port"));
            emailProps.put("mail.smtp.auth", ConfigReader.get("email.smtp.auth"));
            emailProps.put("mail.smtp.starttls.enable", ConfigReader.get("email.smtp.starttls.enable"));

            // Create authenticator
            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        ConfigReader.get("email.username"),
                        ConfigReader.get("email.password")
                    );
                }
            };

            // Create session
            Session session = Session.getInstance(emailProps, auth);

            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConfigReader.get("email.from")));

            // Add recipients
            String[] recipients = ConfigReader.get("email.to").split(",");
            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addressTo[i] = new InternetAddress(recipients[i].trim());
            }
            message.setRecipients(Message.RecipientType.TO, addressTo);

            // Set subject
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String subject = ConfigReader.get("email.subject.prefix") + " - " + status + " - " + testClassName + "." + testMethodName;
            message.setSubject(subject);

            // Create email content
            String content = createDetailedEmailContent(testClassName, testMethodName, status, errorMessage, duration, timestamp, throwable, currentUrl);
            message.setContent(content, "text/html; charset=utf-8");

            // Send email
            Transport.send(message);
            System.out.println(SUCCESS_PREFIX + "Email notification sent successfully for " + testClassName + "." + testMethodName);

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to send email notification: " + e.getMessage());
        }
    }

    public static void sendTestSuiteNotification(String suiteName, int totalTests, int passedTests,
                                               int failedTests, int skippedTests, long totalDuration) {
        sendTestSuiteNotification(suiteName, totalTests, passedTests, failedTests, skippedTests, totalDuration, null);
    }

    public static void sendTestSuiteNotification(String suiteName, int totalTests, int passedTests,
                                               int failedTests, int skippedTests, long totalDuration, Map<String, Object> testDetails) {
        try {
            // Ensure config is loaded
            ConfigReader.loadConfig();

            // Check if email is enabled
            if (!Boolean.parseBoolean(ConfigReader.get("email.enabled"))) {
                return;
            }

            // Setup email properties
            Properties emailProps = new Properties();
            emailProps.put("mail.smtp.host", ConfigReader.get("email.smtp.host"));
            emailProps.put("mail.smtp.port", ConfigReader.get("email.smtp.port"));
            emailProps.put("mail.smtp.auth", ConfigReader.get("email.smtp.auth"));
            emailProps.put("mail.smtp.starttls.enable", ConfigReader.get("email.smtp.starttls.enable"));

            // Create authenticator
            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        ConfigReader.get("email.username"),
                        ConfigReader.get("email.password")
                    );
                }
            };

            // Create session
            Session session = Session.getInstance(emailProps, auth);

            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConfigReader.get("email.from")));

            // Add recipients
            String[] recipients = ConfigReader.get("email.to").split(",");
            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addressTo[i] = new InternetAddress(recipients[i].trim());
            }
            message.setRecipients(Message.RecipientType.TO, addressTo);

            // Set subject
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            double passPercentage = totalTests > 0 ? (double) passedTests / totalTests * 100 : 0;
            String subject = String.format("%s - Suite Complete - %s (%.1f%% Pass Rate)",
                ConfigReader.get("email.subject.prefix"), suiteName, passPercentage);
            message.setSubject(subject);

            // Create dashboard-style email content
            String dashboardContent = createDashboardEmailContent(suiteName, totalTests, passedTests, failedTests, skippedTests, totalDuration, timestamp);
            message.setContent(dashboardContent, "text/html; charset=utf-8");

            // Send email
            Transport.send(message);
            System.out.println(SUCCESS_PREFIX + "Test suite email notification sent successfully for " + suiteName);

        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + "Failed to send test suite email notification: " + e.getMessage());
        }
    }

    private static String createEmailContent(String testClassName, String testMethodName,
                                           String status, String errorMessage, long duration, String timestamp) {
        StringBuilder content = new StringBuilder();

        content.append("<!DOCTYPE html>");
        content.append("<html><head><style>");
        content.append("body { font-family: Arial, sans-serif; margin: 20px; }");
        content.append(".header { background-color: #f0f0f0; padding: 10px; border-radius: 5px; }");
        content.append(".passed { color: #28a745; font-weight: bold; }");
        content.append(".failed { color: #dc3545; font-weight: bold; }");
        content.append(".info { background-color: #e9ecef; padding: 10px; margin: 10px 0; border-radius: 5px; }");
        content.append(".error { background-color: #f8d7da; padding: 10px; margin: 10px 0; border-radius: 5px; color: #721c24; }");
        content.append("</style></head><body>");

        content.append("<div class='header'>");
        content.append("<h2>🧪 Tira Beauty Test Execution Report</h2>");
        content.append("</div>");

        content.append("<div class='info'>");
        content.append("<h3>Test Details</h3>");
        content.append("<p><strong>Test Class:</strong> ").append(testClassName).append("</p>");
        content.append("<p><strong>Test Method:</strong> ").append(testMethodName).append("</p>");

        if ("PASSED".equalsIgnoreCase(status)) {
            content.append("<p><strong>Status:</strong> <span class='passed'>✅ PASSED</span></p>");
        } else {
            content.append("<p><strong>Status:</strong> <span class='failed'>❌ FAILED</span></p>");
        }

        content.append("<p><strong>Duration:</strong> ").append(duration).append(" ms</p>");
        content.append("<p><strong>Execution Time:</strong> ").append(timestamp).append("</p>");
        content.append("</div>");

        if (errorMessage != null && !errorMessage.isEmpty()) {
            content.append("<div class='error'>");
            content.append("<h3>Error Details</h3>");
            content.append("<pre>").append(errorMessage).append("</pre>");
            content.append("</div>");
        }

        content.append("<div class='info'>");
        content.append("<p><strong>Environment:</strong> ").append(ConfigReader.get("baseUrl")).append("</p>");
        content.append("<p><strong>Browser:</strong> ").append(ConfigReader.get("browser")).append("</p>");
        content.append("</div>");

        content.append("<p><em>This is an automated notification from Tira Beauty Test Framework</em></p>");
        content.append("</body></html>");

        return content.toString();
    }


    private static String createDetailedEmailContent(String testClassName, String testMethodName,
                                                   String status, String errorMessage, long duration,
                                                   String timestamp, Throwable throwable, String currentUrl) {
        StringBuilder content = new StringBuilder();

        content.append("<!DOCTYPE html>");
        content.append("<html><head><style>");
        content.append("body { font-family: Arial, sans-serif; margin: 20px; line-height: 1.6; }");
        content.append(".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 8px; text-align: center; }");
        content.append(".status-passed { background: #d4edda; border: 1px solid #c3e6cb; color: #155724; padding: 15px; border-radius: 5px; margin: 10px 0; }");
        content.append(".status-failed { background: #f8d7da; border: 1px solid #f5c6cb; color: #721c24; padding: 15px; border-radius: 5px; margin: 10px 0; }");
        content.append(".status-skipped { background: #fff3cd; border: 1px solid #ffeaa7; color: #856404; padding: 15px; border-radius: 5px; margin: 10px 0; }");
        content.append(".info-box { background: #e9ecef; padding: 15px; margin: 10px 0; border-radius: 5px; border-left: 4px solid #007bff; }");
        content.append(".error-box { background: #f8d7da; padding: 15px; margin: 10px 0; border-radius: 5px; border-left: 4px solid #dc3545; }");
        content.append(".warning-box { background: #fff3cd; padding: 15px; margin: 10px 0; border-radius: 5px; border-left: 4px solid #ffc107; }");
        content.append(".success-box { background: #d4edda; padding: 15px; margin: 10px 0; border-radius: 5px; border-left: 4px solid #28a745; }");
        content.append(".code-block { background: #f8f9fa; border: 1px solid #e9ecef; border-radius: 4px; padding: 12px; font-family: 'Courier New', monospace; font-size: 12px; overflow-x: auto; white-space: pre-wrap; word-wrap: break-word; }");
        content.append(".stack-trace { max-height: 300px; overflow-y: auto; }");
        content.append(".badge { display: inline-block; padding: 4px 8px; border-radius: 12px; font-size: 12px; font-weight: bold; }");
        content.append(".badge-success { background: #28a745; color: white; }");
        content.append(".badge-danger { background: #dc3545; color: white; }");
        content.append(".badge-warning { background: #ffc107; color: #212529; }");
        content.append(".meta-info { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; margin: 15px 0; }");
        content.append(".meta-item { background: #f8f9fa; padding: 10px; border-radius: 5px; text-align: center; }");
        content.append(".meta-label { font-size: 12px; color: #6c757d; margin-bottom: 5px; }");
        content.append(".meta-value { font-size: 16px; font-weight: bold; color: #495057; }");
        content.append("</style></head><body>");

        // Header
        content.append("<div class='header'>");
        content.append("<h1>🧪 Tira Beauty Test Report</h1>");
        content.append("<p>Automated Test Execution Notification</p>");
        content.append("</div>");

        // Status Section
        if ("PASSED".equalsIgnoreCase(status)) {
            content.append("<div class='status-passed'>");
            content.append("<h2>✅ TEST PASSED</h2>");
            content.append("<p>The test executed successfully without any issues.</p>");
            content.append("</div>");
        } else if ("FAILED".equalsIgnoreCase(status)) {
            content.append("<div class='status-failed'>");
            content.append("<h2>❌ TEST FAILED</h2>");
            content.append("<p><strong>Immediate attention required!</strong> This test failed during execution.</p>");
            content.append("</div>");
        } else if ("SKIPPED".equalsIgnoreCase(status)) {
            content.append("<div class='status-skipped'>");
            content.append("<h2>⚠️ TEST SKIPPED</h2>");
            content.append("<p>This test was skipped during execution.</p>");
            content.append("</div>");
        }

        // Test Details
        content.append("<div class='info-box'>");
        content.append("<h3>📋 Test Information</h3>");
        content.append("<div class='meta-info'>");
        content.append("<div class='meta-item'>");
        content.append("<div class='meta-label'>Test Class</div>");
        content.append("<div class='meta-value'>").append(testClassName).append("</div>");
        content.append("</div>");
        content.append("<div class='meta-item'>");
        content.append("<div class='meta-label'>Test Method</div>");
        content.append("<div class='meta-value'>").append(testMethodName).append("</div>");
        content.append("</div>");
        content.append("<div class='meta-item'>");
        content.append("<div class='meta-label'>Execution Time</div>");
        content.append("<div class='meta-value'>").append(String.format("%.2f", duration / 1000.0)).append("s</div>");
        content.append("</div>");
        content.append("<div class='meta-item'>");
        content.append("<div class='meta-label'>Status</div>");
        if ("PASSED".equalsIgnoreCase(status)) {
            content.append("<div class='meta-value'><span class='badge badge-success'>").append(status).append("</span></div>");
        } else if ("FAILED".equalsIgnoreCase(status)) {
            content.append("<div class='meta-value'><span class='badge badge-danger'>").append(status).append("</span></div>");
        } else {
            content.append("<div class='meta-value'><span class='badge badge-warning'>").append(status).append("</span></div>");
        }
        content.append("</div>");
        content.append("</div>");
        content.append("</div>");

        // Failure Details (only for failed tests)
        if ("FAILED".equalsIgnoreCase(status) && (errorMessage != null || throwable != null)) {
            content.append("<div class='error-box'>");
            content.append("<h3>🔍 Failure Analysis</h3>");

            // Error categorization
            String errorCategory = categorizeError(errorMessage, throwable);
            content.append("<p><strong>Error Category:</strong> ").append(errorCategory).append("</p>");

            // Primary error message
            if (errorMessage != null && !errorMessage.isEmpty()) {
                content.append("<h4>Primary Error Message:</h4>");
                content.append("<div class='code-block'>").append(escapeHtml(errorMessage)).append("</div>");
            }

            // Detailed stack trace
            if (throwable != null) {
                content.append("<h4>Complete Stack Trace:</h4>");
                content.append("<div class='code-block stack-trace'>");
                content.append(escapeHtml(getStackTraceAsString(throwable)));
                content.append("</div>");

                // Root cause analysis
                Throwable rootCause = getRootCause(throwable);
                if (rootCause != throwable) {
                    content.append("<h4>Root Cause:</h4>");
                    content.append("<div class='code-block'>");
                    content.append("<strong>").append(escapeHtml(rootCause.getClass().getSimpleName())).append(":</strong> ");
                    content.append(escapeHtml(rootCause.getMessage() != null ? rootCause.getMessage() : "No message available"));
                    content.append("</div>");
                }
            }

            // Suggested solutions
            content.append("<h4>💡 Suggested Solutions:</h4>");
            content.append("<ul>");
            content.append(getSuggestedSolutions(errorMessage, throwable));
            content.append("</ul>");
            content.append("</div>");
        }

        // Environment Information
        content.append("<div class='info-box'>");
        content.append("<h3>🌐 Environment Details</h3>");
        content.append("<div class='meta-info'>");
        content.append("<div class='meta-item'>");
        content.append("<div class='meta-label'>Base URL</div>");
        content.append("<div class='meta-value'>").append(ConfigReader.get("baseUrl")).append("</div>");
        content.append("</div>");
        if (currentUrl != null && !currentUrl.isEmpty() && !"PASSED".equalsIgnoreCase(status)) {
            content.append("<div class='meta-item'>");
            content.append("<div class='meta-label'>Current URL</div>");
            content.append("<div class='meta-value'><a href='").append(currentUrl).append("' target='_blank' style='color: #007bff; text-decoration: underline;'>").append(currentUrl).append("</a></div>");
            content.append("</div>");
        }
        content.append("<div class='meta-item'>");
        content.append("<div class='meta-label'>Browser</div>");
        content.append("<div class='meta-value'>").append(ConfigReader.get("browser")).append("</div>");
        content.append("</div>");
        content.append("<div class='meta-item'>");
        content.append("<div class='meta-label'>Execution Date</div>");
        content.append("<div class='meta-value'>").append(timestamp).append("</div>");
        content.append("</div>");
        content.append("<div class='meta-item'>");
        content.append("<div class='meta-label'>Timeout Setting</div>");
        content.append("<div class='meta-value'>").append(ConfigReader.get("timeout")).append("s</div>");
        content.append("</div>");
        content.append("</div>");
        content.append("</div>");

        // "No Products Found" Detection
        if ("FAILED".equalsIgnoreCase(status) && currentUrl != null && detectNoProductsFound(currentUrl, errorMessage, throwable)) {
            content.append("<div class='warning-box'>");
            content.append("<h3>🛍️ No Products Found Detection</h3>");
            content.append("<p><strong>⚠️ Detected Issue:</strong> This subcategory appears to have no products available</p>");
            content.append("<p><strong>🔗 Failed URL:</strong> <a href='").append(currentUrl).append("' target='_blank' style='color: #dc3545;'>").append(currentUrl).append("</a></p>");
            content.append("<p><strong>📋 Recommended Actions:</strong></p>");
            content.append("<ul>");
            content.append("<li>Verify if this subcategory should have products</li>");
            content.append("<li>Check if products are temporarily out of stock</li>");
            content.append("<li>Review category mapping and URL structure</li>");
            content.append("<li>Consider updating test expectations if this is intentional</li>");
            content.append("</ul>");
            content.append("</div>");
        }

        // Screenshot Information (if available)
        if ("FAILED".equalsIgnoreCase(status)) {
            content.append("<div class='warning-box'>");
            content.append("<h3>📸 Screenshot Information</h3>");
            content.append("<p><strong>Screenshot captured:</strong> Yes (saved locally as ").append(testMethodName).append(".png)</p>");
            content.append("<p><em>Note: Screenshots are saved in the project's screenshot directory and can be attached to investigate the failure.</em></p>");
            content.append("</div>");
        }

        // Footer
        content.append("<div style='margin-top: 30px; padding-top: 20px; border-top: 1px solid #dee2e6; text-align: center; color: #6c757d; font-size: 12px;'>");
        content.append("<p>This is an automated notification from the Tira Beauty Test Automation Framework</p>");
        content.append("<p>For technical support, please contact the QA team</p>");
        content.append("</div>");

        content.append("</body></html>");
        return content.toString();
    }

    private static String categorizeError(String errorMessage, Throwable throwable) {
        if (throwable != null) {
            String className = throwable.getClass().getSimpleName();
            if (className.contains("NoSuchElement")) return "🎯 Element Not Found";
            if (className.contains("Timeout") || className.contains("Wait")) return "⏱️ Timeout Issue";
            if (className.contains("WebDriver") || className.contains("Selenium")) return "🌐 WebDriver Issue";
            if (className.contains("Assert")) return "🔍 Assertion Failure";
            if (className.contains("Network") || className.contains("Connection")) return "🌐 Network Issue";
            if (className.contains("Javascript") || className.contains("Script")) return "📜 JavaScript Error";
        }

        if (errorMessage != null) {
            String msg = errorMessage.toLowerCase();
            if (msg.contains("element not found") || msg.contains("no such element")) return "🎯 Element Not Found";
            if (msg.contains("timeout") || msg.contains("wait")) return "⏱️ Timeout Issue";
            if (msg.contains("assertion") || msg.contains("expected")) return "🔍 Assertion Failure";
            if (msg.contains("network") || msg.contains("connection")) return "🌐 Network Issue";
        }

        return "❓ Unknown Error";
    }

    private static String getSuggestedSolutions(String errorMessage, Throwable throwable) {
        StringBuilder solutions = new StringBuilder();

        if (throwable != null && throwable.getClass().getSimpleName().contains("NoSuchElement")) {
            solutions.append("<li>Check if the element locator is correct</li>");
            solutions.append("<li>Verify if the element is present on the page</li>");
            solutions.append("<li>Add explicit wait before interacting with the element</li>");
            solutions.append("<li>Check if the page has fully loaded</li>");
        } else if (throwable != null && (throwable.getClass().getSimpleName().contains("Timeout") || throwable.getClass().getSimpleName().contains("Wait"))) {
            solutions.append("<li>Increase the wait timeout value</li>");
            solutions.append("<li>Check if the page is loading slowly</li>");
            solutions.append("<li>Verify network connectivity</li>");
            solutions.append("<li>Check if there are JavaScript errors blocking the page</li>");
        } else if (throwable != null && throwable.getClass().getSimpleName().contains("Assert")) {
            solutions.append("<li>Review the expected vs actual values</li>");
            solutions.append("<li>Check if the test data is correct</li>");
            solutions.append("<li>Verify if the application behavior has changed</li>");
            solutions.append("<li>Update the test assertion if business logic changed</li>");
        } else {
            solutions.append("<li>Check the full stack trace for more details</li>");
            solutions.append("<li>Verify the test environment is stable</li>");
            solutions.append("<li>Review recent code changes</li>");
            solutions.append("<li>Check application logs for related errors</li>");
        }

        return solutions.toString();
    }

    private static String getStackTraceAsString(Throwable throwable) {
        if (throwable == null) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(throwable.getClass().getName()).append(": ").append(throwable.getMessage()).append("\n");

        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append("\tat ").append(element.toString()).append("\n");
        }

        if (throwable.getCause() != null && throwable.getCause() != throwable) {
            sb.append("Caused by: ").append(getStackTraceAsString(throwable.getCause()));
        }

        return sb.toString();
    }

    private static Throwable getRootCause(Throwable throwable) {
        if (throwable == null) return null;

        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&#x27;");
    }

    private static boolean detectNoProductsFound(String currentUrl, String errorMessage, Throwable throwable) {
        if (currentUrl == null) return false;

        // Check URL patterns that might indicate a category/subcategory page
        boolean isCategoryPage = currentUrl.contains("/c/") ||
                               currentUrl.contains("/category/") ||
                               currentUrl.contains("/makeup") ||
                               currentUrl.contains("/skin") ||
                               currentUrl.contains("/hair") ||
                               currentUrl.contains("/fragrance") ||
                               currentUrl.contains("/men") ||
                               currentUrl.contains("/bath-body") ||
                               currentUrl.contains("/bath-and-body");

        if (!isCategoryPage) return false;

        // Check error messages that might indicate "no products found"
        if (errorMessage != null) {
            String msg = errorMessage.toLowerCase();
            if (msg.contains("nothing to find here") ||
                msg.contains("no results found") ||
                msg.contains("no products found") ||
                msg.contains("0 results") ||
                msg.contains("empty category") ||
                msg.contains("no items available")) {
                return true;
            }
        }

        // Check throwable messages
        if (throwable != null && throwable.getMessage() != null) {
            String throwableMsg = throwable.getMessage().toLowerCase();
            if (throwableMsg.contains("nothing to find") ||
                throwableMsg.contains("no results") ||
                throwableMsg.contains("empty") ||
                throwableMsg.contains("no products")) {
                return true;
            }
        }

        // URL-based detection for common "no products" indicators
        if (currentUrl.contains("search?q=") && errorMessage != null) {
            return errorMessage.toLowerCase().contains("no results");
        }

        return false;
    }

    private static String createSimpleEmailContent(String suiteName, int totalTests, int passedTests,
                                                  int failedTests, int skippedTests, long totalDuration, String timestamp) {
        StringBuilder content = new StringBuilder();
        double passPercentage = totalTests > 0 ? (double) passedTests / totalTests * 100 : 0;

        content.append("<!DOCTYPE html>");
        content.append("<html><head><style>");
        content.append("body { font-family: Arial, sans-serif; margin: 20px; line-height: 1.6; }");
        content.append(".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 8px; text-align: center; margin-bottom: 20px; }");
        content.append(".summary { background: #e9ecef; padding: 15px; margin: 15px 0; border-radius: 5px; border-left: 4px solid #007bff; }");
        content.append(".attachment-note { background: #d1ecf1; padding: 15px; margin: 15px 0; border-radius: 5px; border-left: 4px solid #0c5460; }");
        content.append("</style></head><body>");

        content.append("<div class='header'>");
        content.append("<h1>🎯 Tira Beauty Test Suite Complete</h1>");
        content.append("<p>Test Execution Summary Report</p>");
        content.append("</div>");

        content.append("<div class='summary'>");
        content.append("<h3>📊 Execution Summary</h3>");
        content.append("<p><strong>Suite:</strong> ").append(suiteName).append("</p>");
        content.append("<p><strong>Total Tests:</strong> ").append(totalTests).append("</p>");
        content.append("<p><strong>Passed:</strong> ").append(passedTests).append(" (").append(String.format("%.1f%%", passPercentage)).append(")</p>");
        content.append("<p><strong>Failed:</strong> ").append(failedTests).append("</p>");
        content.append("<p><strong>Skipped:</strong> ").append(skippedTests).append("</p>");
        content.append("<p><strong>Duration:</strong> ").append(String.format("%.2f", totalDuration / 1000.0)).append(" seconds</p>");
        content.append("<p><strong>Execution Time:</strong> ").append(timestamp).append("</p>");
        content.append("</div>");

        content.append("<div class='attachment-note'>");
        content.append("<h3>📸 Detailed Report</h3>");
        content.append("<p><strong>Weekly Regression Report Dashboard:</strong> Please see the attached screenshot for detailed test results and visual statistics.</p>");
        content.append("<p><em>The attached report provides comprehensive insights including pass/fail trends, test coverage, and performance metrics.</em></p>");
        content.append("</div>");

        content.append("<div style='margin-top: 30px; padding-top: 20px; border-top: 1px solid #dee2e6; text-align: center; color: #6c757d; font-size: 12px;'>");
        content.append("<p>This is an automated notification from the Tira Beauty Test Automation Framework</p>");
        content.append("</div>");

        content.append("</body></html>");
        return content.toString();
    }

    private static String createDashboardEmailContent(String suiteName, int totalTests, int passedTests,
                                                     int failedTests, int skippedTests, long totalDuration, String timestamp) {
        StringBuilder content = new StringBuilder();
        double passPercentage = totalTests > 0 ? (double) passedTests / totalTests * 100 : 0;
        double failedPercentage = totalTests > 0 ? (double) failedTests / totalTests * 100 : 0;
        double skippedPercentage = totalTests > 0 ? (double) skippedTests / totalTests * 100 : 0;

        content.append("<!DOCTYPE html>");
        content.append("<html><head>");
        content.append("<meta charset='UTF-8'>");
        content.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        content.append("<style>");

        // Email-compatible CSS using tables instead of advanced CSS
        content.append("body { margin: 0; padding: 20px; font-family: Arial, sans-serif; background-color: #f4f5f7; color: #333; }");
        content.append(".container { max-width: 800px; margin: 0 auto; background: #f4f5f7; }");
        content.append(".header { background: #2E8B57; color: white; padding: 30px; text-align: center; border-radius: 10px; margin-bottom: 30px; }");
        content.append(".header h1 { margin: 0 0 10px 0; font-size: 36px; font-weight: normal; }");
        content.append(".header p { margin: 0; font-size: 18px; opacity: 0.9; }");

        // Card styles using tables for email compatibility
        content.append(".card-table { width: 100%; margin-bottom: 20px; }");
        content.append(".card { background: white; padding: 25px; border-radius: 10px; border: 1px solid #e1e5e9; vertical-align: top; }");
        content.append(".card h3 { margin: 0 0 20px 0; font-size: 18px; color: #5a6c7d; text-align: center; font-weight: normal; }");

        // Simple progress circles using background colors
        content.append(".progress-container { text-align: center; margin-bottom: 20px; }");
        content.append(".circle-progress { width: 120px; height: 120px; border-radius: 50%; margin: 0 auto 15px; position: relative; display: inline-block; }");
        content.append(".circle-inner { position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); text-align: center; background: white; border-radius: 50%; width: 80px; height: 80px; display: flex; flex-direction: column; justify-content: center; }");
        content.append(".circle-number { font-size: 24px; font-weight: bold; color: #2c3e50; display: block; }");
        content.append(".circle-label { font-size: 10px; color: #7f8c8d; text-transform: uppercase; letter-spacing: 1px; }");

        // Statistics list
        content.append(".stats-list { margin-top: 15px; }");
        content.append(".stat-row { padding: 8px 0; border-bottom: 1px solid #ecf0f1; }");
        content.append(".stat-row table { width: 100%; }");
        content.append(".stat-label { font-weight: 500; font-size: 14px; }");
        content.append(".stat-dot { width: 10px; height: 10px; border-radius: 50%; margin-right: 8px; display: inline-block; }");
        content.append(".stat-value { font-weight: bold; color: #2c3e50; font-size: 14px; text-align: right; }");
        content.append(".passed { background: #28a745; }");
        content.append(".failed { background: #dc3545; }");
        content.append(".skipped { background: #ffc107; }");
        content.append(".queued { background: #6c757d; }");
        content.append(".aborted { background: #6f42c1; }");

        // Table styles for weekly details
        content.append(".details-section { background: white; padding: 30px; border-radius: 10px; border: 1px solid #e1e5e9; margin-top: 20px; }");
        content.append(".details-section h2 { margin: 0 0 25px 0; color: #2c3e50; font-size: 22px; font-weight: normal; }");
        content.append(".details-table { width: 100%; border-collapse: collapse; }");
        content.append(".details-table th { background: #f8f9fa; color: #5a6c7d; padding: 12px 8px; text-align: left; font-size: 11px; font-weight: 600; text-transform: uppercase; border-bottom: 1px solid #ecf0f1; }");
        content.append(".details-table td { padding: 12px 8px; color: #2c3e50; border-bottom: 1px solid #ecf0f1; font-size: 14px; }");
        content.append(".passed-pct { color: #28a745; font-weight: bold; }");
        content.append(".failed-pct { color: #dc3545; font-weight: bold; }");

        // Footer
        content.append(".footer { text-align: center; padding: 30px 0; color: #7f8c8d; font-size: 14px; }");

        content.append("</style></head><body>");

        content.append("<div class='container'>");

        // Header section
        content.append("<div class='header'>");
        content.append("<h1>Weekly Regression</h1>");
        content.append("<p>Web AutomationSuite Dashboard Report</p>");
        content.append("</div>");

        // Main statistics using table layout for email compatibility
        content.append("<table class='card-table' cellpadding='0' cellspacing='10'>");
        content.append("<tr>");

        // Weekly Total Card
        content.append("<td class='card' width='33%'>");
        content.append("<h3>WEEKLY TOTAL</h3>");
        content.append("<div class='progress-container'>");
        // Simple circular design using background color based on pass rate
        String circleColor = passPercentage > 80 ? "#28a745" : passPercentage > 60 ? "#ffc107" : "#dc3545";
        content.append("<div class='circle-progress' style='background: ").append(circleColor).append(";'>");
        content.append("<div class='circle-inner'>");
        content.append("<span class='circle-number'>").append(totalTests).append("</span>");
        content.append("<span class='circle-label'>TOTAL</span>");
        content.append("</div>");
        content.append("</div>");
        content.append("</div>");
        content.append("<div class='stats-list'>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot passed'></span>PASSED</td><td class='stat-value'>").append(passedTests).append("</td></tr></table>");
        content.append("</div>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot failed'></span>FAILED</td><td class='stat-value'>").append(failedTests).append("</td></tr></table>");
        content.append("</div>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot skipped'></span>SKIPPED</td><td class='stat-value'>").append(skippedTests).append("</td></tr></table>");
        content.append("</div>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot queued'></span>QUEUED</td><td class='stat-value'>0</td></tr></table>");
        content.append("</div>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot aborted'></span>ABORTED</td><td class='stat-value'>0</td></tr></table>");
        content.append("</div>");
        content.append("</div>");
        content.append("</td>");

        // Weekly Total Percentage Card
        content.append("<td class='card' width='33%'>");
        content.append("<h3>WEEKLY TOTAL (%)</h3>");
        content.append("<div class='progress-container'>");
        content.append("<div class='circle-progress' style='background: ").append(circleColor).append(";'>");
        content.append("<div class='circle-inner'>");
        content.append("<span class='circle-number'>").append(String.format("%.0f", passPercentage)).append("%</span>");
        content.append("<span class='circle-label'>PASS RATE</span>");
        content.append("</div>");
        content.append("</div>");
        content.append("</div>");
        content.append("<div class='stats-list'>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot passed'></span>PASSED</td><td class='stat-value'>").append(String.format("%.1f", passPercentage)).append("%</td></tr></table>");
        content.append("</div>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot failed'></span>FAILED</td><td class='stat-value'>").append(String.format("%.1f", failedPercentage)).append("%</td></tr></table>");
        content.append("</div>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot skipped'></span>SKIPPED</td><td class='stat-value'>").append(String.format("%.1f", skippedPercentage)).append("%</td></tr></table>");
        content.append("</div>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot queued'></span>QUEUED</td><td class='stat-value'>0%</td></tr></table>");
        content.append("</div>");
        content.append("<div class='stat-row'>");
        content.append("<table><tr><td class='stat-label'><span class='stat-dot aborted'></span>ABORTED</td><td class='stat-value'>0%</td></tr></table>");
        content.append("</div>");
        content.append("</div>");
        content.append("</td>");

        // Test Results Card
        content.append("<td class='card' width='33%'>");
        content.append("<h3>TEST RESULTS (LAST 7 DAYS)</h3>");
        content.append("<div style='text-align: center; padding: 20px 0;'>");
        content.append("<div style='font-size: 48px; color: #28a745; margin-bottom: 15px;'>📊</div>");
        content.append("<div style='color: #5a6c7d; font-size: 16px; font-weight: bold;'>").append(String.format("%.1f", passPercentage)).append("% Success Rate</div>");
        content.append("<div style='color: #7f8c8d; font-size: 14px; margin-top: 8px;'>").append(totalTests).append(" tests executed</div>");
        content.append("<div style='color: #7f8c8d; font-size: 12px; margin-top: 5px;'>Latest execution</div>");
        content.append("</div>");
        content.append("</td>");

        content.append("</tr>");
        content.append("</table>");

        // Weekly Details Section
        content.append("<div class='details-section'>");
        content.append("<h2>WEEKLY DETAILS</h2>");

        content.append("<table class='details-table'>");
        content.append("<thead>");
        content.append("<tr>");
        content.append("<th>OWNER</th>");
        content.append("<th>REPORT</th>");
        content.append("<th>PASSED</th>");
        content.append("<th>FAILED</th>");
        content.append("<th>KNOWN ISSUE</th>");
        content.append("<th>SKIPPED</th>");
        content.append("<th>QUEUED</th>");
        content.append("<th>TOTAL</th>");
        content.append("<th>PASSED (%)</th>");
        content.append("<th>FAILED (%)</th>");
        content.append("<th>KNOWN ISSUE (%)</th>");
        content.append("<th>SKIPPED (%)</th>");
        content.append("<th>QUEUED (%)</th>");
        content.append("</tr>");
        content.append("</thead>");
        content.append("<tbody>");
        content.append("<tr>");
        content.append("<td><strong>").append(suiteName.contains("@") ? suiteName.split("@")[0] : "Mukesh Misra").append("</strong></td>");
        content.append("<td><span style='color: #2E8B57; font-weight: bold;'>").append(suiteName).append("</span></td>");
        content.append("<td>").append(passedTests).append("</td>");
        content.append("<td>").append(failedTests).append("</td>");
        content.append("<td>0</td>");
        content.append("<td>").append(skippedTests).append("</td>");
        content.append("<td>0</td>");
        content.append("<td><strong>").append(totalTests).append("</strong></td>");
        content.append("<td><span class='passed-pct'>").append(String.format("%.0f", passPercentage)).append("</span></td>");
        content.append("<td><span class='failed-pct'>").append(String.format("%.0f", failedPercentage)).append("</span></td>");
        content.append("<td>0</td>");
        content.append("<td>").append(String.format("%.0f", skippedPercentage)).append("</td>");
        content.append("<td>0</td>");
        content.append("</tr>");
        content.append("</tbody>");
        content.append("</table>");
        content.append("</div>");

        // Footer
        content.append("<div class='footer'>");
        content.append("<p><strong>Execution Time:</strong> ").append(timestamp).append(" | <strong>Duration:</strong> ").append(String.format("%.2f", totalDuration / 1000.0)).append(" seconds</p>");
        content.append("<p style='margin-top: 10px;'>Generated by Tira Beauty Test Automation Framework</p>");
        content.append("</div>");

        content.append("</div>");
        content.append("</body></html>");

        return content.toString();
    }
}