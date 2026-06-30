package com.framework.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public class AlertUtil {

    private final WebDriver driver;

    public AlertUtil(WebDriver driver) {
        this.driver = driver;
    }

    public Alert switchToAlert() {
        return driver.switchTo().alert();
    }

    public void acceptAlert() {
        switchToAlert().accept();
    }

    public void dismissAlert() {
        switchToAlert().dismiss();
    }

    public String getAlertText() {
       return switchToAlert().getText();
    }

    public void sendKeysToAlert(String text) {
        switchToAlert().sendKeys(text);
    }
}
