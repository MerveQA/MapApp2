package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.time.Duration;


public class Driver {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();


    public static WebDriver getDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = createDriver(browser);
            driverThreadLocal.set(driver);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driverThreadLocal.get();
    }


    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return new ChromeDriver();

            case "adblockchrome":
                ChromeOptions options = new ChromeOptions();
                options.addExtensions(new File("./Extensions/AdblockChrome.crx"));
                return new ChromeDriver(options);

            case "firefox":
                return new FirefoxDriver();

            case "edge":
                return new EdgeDriver();

            case "safari":
                return new SafariDriver();

            default:
                throw new IllegalArgumentException("Geçersiz tarayıcı türü: " + browser);
        }

    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }


}
