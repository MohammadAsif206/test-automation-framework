package com.framework.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            initDriver();
        }
        return driverThreadLocal.get();
    }

    private static void initDriver() {
        String browser   = ConfigManager.get("BROWSER", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigManager.get("HEADLESS", "true"));
        String gridUrl   = ConfigManager.get("GRID_URL", "");

        WebDriver driver;

        if (!gridUrl.isEmpty()) {
            driver = createRemoteDriver(browser, headless, gridUrl);
        } else {
            driver = createLocalDriver(browser, headless);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driverThreadLocal.set(driver);
        log.info("Driver initialised: {} | headless={} | grid={}", browser, headless, !gridUrl.isEmpty());
    }

    private static WebDriver createLocalDriver(String browser, boolean headless) {
        return switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions opts = new FirefoxOptions();
                if (headless) opts.addArguments("--headless");
                yield new FirefoxDriver(opts);
            }
            case "edge" -> {
                //WebDriverManager.edgedriver().setup();
                WebDriverManager.edgedriver().browserVersion("149").setup();
                EdgeOptions opts = new EdgeOptions();
                if (headless) opts.addArguments("--headless");
                yield new EdgeDriver(opts);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions opts = new ChromeOptions();
                opts.addArguments("--incognito");
                opts.addArguments("--no-sandbox");           // fixes crashes in CI/Linux
                opts.addArguments("--disable-dev-shm-usage"); // prevents memory issues
                opts.addArguments("--remote-allow-origins=*"); // fixes Selenium 4 comms
                if (headless) opts.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
                yield new ChromeDriver(opts);
            }
        };
    }

    private static WebDriver createRemoteDriver(String browser, boolean headless, String gridUrl) {
        try {
            return switch (browser) {
                case "firefox" -> {
                    FirefoxOptions opts = new FirefoxOptions();
                    if (headless) opts.addArguments("--headless");
                    yield new RemoteWebDriver(new URL(gridUrl), opts);
                }
                case "edge" -> {
                     WebDriverManager.edgedriver().browserVersion("149").setup();
                    EdgeOptions opts = new EdgeOptions();
                    if (headless) opts.addArguments("--headless");
                    yield new RemoteWebDriver(new URL(gridUrl), opts);
                }
                default -> {
                    ChromeOptions opts = new ChromeOptions();
                    if (headless) opts.addArguments("--headless=new");
                    yield new RemoteWebDriver(new URL(gridUrl), opts);
                }
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid URL: " + gridUrl, e);
        }
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            log.info("Driver quit and removed from thread");
        }
    }
}
