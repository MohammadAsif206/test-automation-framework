package com.framework.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.framework.utils.AlertUtil;

public class SpecialFeaturesPage extends BasePage {

    @FindBy(id = "btn-simple-alert")
    private WebElement simpleAlertButton;

    @FindBy(id = "btn-confirm-alert")
    private WebElement confirmAlertButton;

    @FindBy(css = "[data-testid='result-confirm']")
    private WebElement confirmResult;

    // Youtue automation
    @FindBy(css = "[name='search_query']")
    private WebElement searchFeild;

    @FindBy(css = "[class='ytd-item-section-renderer lockup ytLockupViewModelWrapper']")
    private WebElement vedio;

    // end of Youttube automaiton
    @FindBy(id = "file-upload")
    private WebElement fileUploadButton;

    @FindBy(id = "file-path-display")
    private WebElement fileUploadedMsg;

    AlertUtil alertUtil = new AlertUtil(driver);

    public SpecialFeaturesPage open(String... value) {
        if (isEmptyOrNull(value[0])) {
            navigateTo("https://qaplayground.com/practice/alerts-dialogs");
        } else {
            navigateTo(value[0]);
        }
        return this;

    }

    @Step("Click the simple alert button")
    public void clickSimpleAlert() {
        click(simpleAlertButton);

    }

    @Step("Verify page before accepting the alert")
    public boolean isPageInteractable() {
        if (simpleAlertButton.isDisplayed())
            return true;
        else
            return false;
    }

    @Step("Switch to alert to accept it")
    public void switchToAlert() {
        alertUtil.switchToAlert();

    }

    @Step("Accept alert")
    public void acceptAlert() {
        alertUtil.acceptAlert();
    }

    @Step("Dismiss alert")
    public void dismissAlert() {
        alertUtil.dismissAlert();
    }

    @Step("Provide the prompt text")
    public void sendAlertText() {
        alertUtil.sendKeysToAlert("");
    }

    @Step("Get alert text")
    public String getAlertText() {
        return alertUtil.getAlertText();
    }

    @Step("Verify confirm result")
    public String confirmResult() {
        return getText(confirmResult);
    }

    @Step("Click confirm alert button")
    public void clickConfirmAlert() {
        click(confirmAlertButton);
    }

    @Step("Search whatever wanted in Youtue search feild")
    public void searchForVideo() {
        type(searchFeild, "Taylor Swift");
    }

    @Step("Select the value matching the queried value")
    public void selctTheMatchValue() {
        //searchForVideo();
        String text;
        //searchFeild.clear();
        do {

            searchFeild.sendKeys(Keys.ARROW_DOWN);
            text = searchFeild.getAttribute("value");
            System.out.println("==================> The text is: "+text);
            if (text.equalsIgnoreCase("Taylor Swift")) {
                searchFeild.sendKeys(Keys.ENTER);
                break;

            }

        } while (!text.isEmpty());
    }

    @Step("Verify the artist is selected")
    public String getCurrentUrl1() {
        return getCurrentUrl();

    }

    @Step("Click on File upload button")
    public void fileUpload() {
        type(fileUploadButton, "C:\\Users\\moham\\Downloads\\android-chrome-512x512.png");
    }
    @Step("Verify file upload message")
    public String fileUploadMessage(){
        return getText(fileUploadedMsg);
    }

}
