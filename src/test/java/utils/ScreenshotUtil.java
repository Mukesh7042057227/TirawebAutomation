package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static void takeScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dirPath = "screenshots";
        String filePath = dirPath + "/" + testName + "_" + timestamp + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File folder = new File(dirPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            FileUtils.copyFile(src, new File(filePath));
            System.out.println("📸 Screenshot saved at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
