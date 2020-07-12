package com.paulszabo.airbnb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverManager {

    /**
     * This method checks the OS and selects the according chromedriver, maximizez the window and sets waits
     * and timeouts
     *
     * @return the driver
     */
    public static WebDriver getWebDriver() {
        WebDriver driver;

        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
        } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        }

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(45, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

        return driver;
    }
}
