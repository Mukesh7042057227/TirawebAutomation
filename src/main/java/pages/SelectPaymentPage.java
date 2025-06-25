package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SelectPaymentPage {
    WebDriver driver;
    WebDriverWait wait;

    By sidebarLocator = By.cssSelector("div.sidebar-container"); // You may adjust based on your HTML

    public SelectPaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void scrollSidebarToBottom() {
        try {
            WebElement sidebar = wait.until(ExpectedConditions.visibilityOfElementLocated(sidebarLocator));
            System.out.println("✅ Sidebar element found.");

            // Hover to activate scrollable behavior
            new Actions(driver).moveToElement(sidebar, 5, 2).click().perform();
            Thread.sleep(800);

            // Scroll to bottom gradually
            JavascriptExecutor js = (JavascriptExecutor) driver;
            long lastScrollTop = -1;
            for (int i = 0; i < 15; i++) {
                long currentScrollTop = (long) js.executeScript("return arguments[0].scrollTop;", sidebar);
                long scrollHeight = (long) js.executeScript("return arguments[0].scrollHeight;", sidebar);
                long clientHeight = (long) js.executeScript("return arguments[0].clientHeight;", sidebar);

                if (currentScrollTop + clientHeight >= scrollHeight) {
                    System.out.println("✅ Sidebar fully scrolled.");
                    break;
                }

                js.executeScript("arguments[0].scrollTop += 150;", sidebar);
                Thread.sleep(300);

                if (lastScrollTop == currentScrollTop) {
                    System.out.println("🛑 ScrollTop did not increase, stopping.");
                    break;
                }
                lastScrollTop = currentScrollTop;
            }

        } catch (Exception e) {
            System.out.println("❌ Scroll failed: " + e.getMessage());
        }
    }
}
