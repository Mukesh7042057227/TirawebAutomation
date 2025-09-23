package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailUtility {

    private static final String SUCCESS_PREFIX = "✅ ";
    private static final String ERROR_PREFIX = "❌ ";
    private static final String INFO_PREFIX = "🔍 ";

    public static void sendTestNotification(String testClassName, String testMethodName,
                                          String status, String errorMessage, long duration) {
        try {
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
            String content = createEmailContent(testClassName, testMethodName, status, errorMessage, duration, timestamp);
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
        try {
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

            // Create email content
            String content = createSuiteEmailContent(suiteName, totalTests, passedTests, failedTests, skippedTests, totalDuration, timestamp);
            message.setContent(content, "text/html; charset=utf-8");

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

    private static String createSuiteEmailContent(String suiteName, int totalTests, int passedTests,
                                                int failedTests, int skippedTests, long totalDuration, String timestamp) {
        StringBuilder content = new StringBuilder();
        double passPercentage = totalTests > 0 ? (double) passedTests / totalTests * 100 : 0;

        content.append("<!DOCTYPE html>");
        content.append("<html><head><style>");
        content.append("body { font-family: Arial, sans-serif; margin: 20px; }");
        content.append(".header { background-color: #f0f0f0; padding: 10px; border-radius: 5px; }");
        content.append(".summary { background-color: #e9ecef; padding: 15px; margin: 10px 0; border-radius: 5px; }");
        content.append(".passed { color: #28a745; font-weight: bold; }");
        content.append(".failed { color: #dc3545; font-weight: bold; }");
        content.append(".skipped { color: #ffc107; font-weight: bold; }");
        content.append(".stats { display: flex; justify-content: space-around; margin: 15px 0; }");
        content.append(".stat-box { text-align: center; padding: 10px; border-radius: 5px; background-color: #f8f9fa; }");
        content.append("</style></head><body>");

        content.append("<div class='header'>");
        content.append("<h2>🎯 Tira Beauty Test Suite Execution Report</h2>");
        content.append("</div>");

        content.append("<div class='summary'>");
        content.append("<h3>Suite: ").append(suiteName).append("</h3>");
        content.append("<p><strong>Execution Time:</strong> ").append(timestamp).append("</p>");
        content.append("<p><strong>Total Duration:</strong> ").append(String.format("%.2f", totalDuration / 1000.0)).append(" seconds</p>");
        content.append("</div>");

        content.append("<div class='stats'>");
        content.append("<div class='stat-box'>");
        content.append("<h3>").append(totalTests).append("</h3>");
        content.append("<p>Total Tests</p>");
        content.append("</div>");
        content.append("<div class='stat-box'>");
        content.append("<h3 class='passed'>").append(passedTests).append("</h3>");
        content.append("<p>Passed</p>");
        content.append("</div>");
        content.append("<div class='stat-box'>");
        content.append("<h3 class='failed'>").append(failedTests).append("</h3>");
        content.append("<p>Failed</p>");
        content.append("</div>");
        content.append("<div class='stat-box'>");
        content.append("<h3 class='skipped'>").append(skippedTests).append("</h3>");
        content.append("<p>Skipped</p>");
        content.append("</div>");
        content.append("</div>");

        content.append("<div class='summary'>");
        content.append("<h3>Overall Results</h3>");
        content.append("<p><strong>Pass Rate:</strong> ").append(String.format("%.1f%%", passPercentage)).append("</p>");
        if (passPercentage >= 90) {
            content.append("<p class='passed'>🎉 Excellent! High success rate achieved.</p>");
        } else if (passPercentage >= 70) {
            content.append("<p style='color: #fd7e14;'>⚠️ Good, but room for improvement.</p>");
        } else {
            content.append("<p class='failed'>🚨 Attention needed - Low success rate.</p>");
        }
        content.append("</div>");

        content.append("<div class='summary'>");
        content.append("<p><strong>Environment:</strong> ").append(ConfigReader.get("baseUrl")).append("</p>");
        content.append("<p><strong>Browser:</strong> ").append(ConfigReader.get("browser")).append("</p>");
        content.append("</div>");

        content.append("<p><em>This is an automated notification from Tira Beauty Test Framework</em></p>");
        content.append("</body></html>");

        return content.toString();
    }
}