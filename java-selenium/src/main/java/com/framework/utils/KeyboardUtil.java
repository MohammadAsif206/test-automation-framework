package com.framework.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class KeyboardUtil {

    private final WebDriver driver;
    private final Actions action;

    public KeyboardUtil(WebDriver driver){
        this.driver = driver;
        this.action = new Actions(driver);
    }

    public void copy(){
        action.keyDown(Keys.CONTROL)
            .sendKeys("c")
            .keyUp(Keys.CONTROL)
            .perform();
    }

    public void paste(){
        action.keyDown(Keys.CONTROL)
            .sendKeys("v")
            .keyUp(Keys.CONTROL)
            .perform();
    }

    public String getText() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            return (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}
