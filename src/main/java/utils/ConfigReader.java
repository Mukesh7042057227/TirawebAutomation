package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    public static void loadConfig() {
        try {
            FileInputStream fis = new FileInputStream("src/test/config.properties");
            prop = new Properties();
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}

