package com.framework.tests;

import com.framework.config.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (!result.isSuccess()) {
            byte[] screenshot = com.framework.utils.ScreenshotUtil.capture(driver);
            Allure.getLifecycle().addAttachment(
                "Screenshot on failure: " + result.getName(),
                "image/png", "png", screenshot
            );
        }
        DriverFactory.quitDriver();
    }
}
