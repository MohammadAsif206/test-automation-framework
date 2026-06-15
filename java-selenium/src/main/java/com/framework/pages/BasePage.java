package com.framework.pages;

import com.framework.config.DriverFactory;
import com.framework.utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);
    private static final int DEFAULT_TIMEOUT = 15;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    @Step("Navigate to {url}")
    protected void navigateTo(String url) {
        log.info("Navigating to {}", url);
        driver.get(url);
    }

    @Step("Click element")
    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    @Step("Type '{text}' into element")
    protected void type(WebElement element, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOf(element));
        el.clear();
        el.sendKeys(text);
    }

    @Step("Get text from element")
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    @Step("Check element visibility")
    protected boolean isVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForUrl(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void takeScreenshot(String name) {
        byte[] screenshot = ScreenshotUtil.capture(driver);
        Allure.getLifecycle().addAttachment(name, "image/png", "png", screenshot);
    }
}
