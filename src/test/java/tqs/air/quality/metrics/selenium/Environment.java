package tqs.air.quality.metrics.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Environment {
    private WebDriver driver;
    private boolean headless = true;

    public Environment() {
//        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
        if (headless) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        } else {
            driver = new FirefoxDriver();
        }
    }

    public void shutDownDriver() {
        driver.quit();
        driver = null;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
