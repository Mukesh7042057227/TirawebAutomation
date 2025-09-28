package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import utils.ConfigReader;

import java.time.Duration;

public class BaseTest {
    public static WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ConfigReader.loadConfig();
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Check if running in CI/CD environment or headless mode
        String headless = System.getProperty("headless");
        if ("true".equals(headless) || System.getenv("CI") != null) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(ConfigReader.get("baseUrl"));
    }

    @AfterMethod
   public void tearDown() {
       if (driver != null) {
           driver.quit();
        }
   }

    public WebDriver getDriver() {
        return driver;
    }
}
