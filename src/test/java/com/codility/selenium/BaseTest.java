package com.codility.selenium;

import com.titusfortner.logging.SeleniumLogger;
import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.NoSuchDriverException;

import java.util.logging.Level;

public class BaseTest {

    public static WebDriver webDriver;

    private static WebDriver init() {

        SeleniumLogger seleniumLogger = new SeleniumLogger();
        seleniumLogger.setLevel(Level.FINE);

        seleniumLogger.enable("RemoteWebDriver", "SeleniumManager");

        if (webDriver == null) {
            if (Config.browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
            } else if (Config.browser.equalsIgnoreCase("chrome headless")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                webDriver = new ChromeDriver(options);
            } else if (Config.browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
            } else if (Config.browser.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
            } else {
                throw new NoSuchDriverException("Driver name not recognized");
            }
        }

        webDriver.manage().deleteAllCookies();
        webDriver.manage().window().maximize();

        return webDriver;
    }

    @Before
    public void setup() {
        webDriver = this.init();
        webDriver.get(Config.baseUrl);
    }

    @After
    public void tearDown() {
        webDriver.close();
        webDriver.quit();
        webDriver = null;
    }
}
