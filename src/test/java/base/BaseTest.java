package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import utils.ConfigReader;

import java.time.Duration;

public class BaseTest {
    public static WebDriver driver;

    @BeforeSuite
    public void setUp() {
        // ✅ Load config
        ConfigReader.loadConfig();

        // ✅ Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // ✅ Set up browser
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // ✅ Launch base URL
        String baseUrl = ConfigReader.get("baseUrl");
        System.out.println("Launching: " + baseUrl);
        driver.get(baseUrl);
    }

    public WebDriver getDriver() {
        return driver;
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
