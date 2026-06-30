package com.framework.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.pages.SpecialFeaturesPage;

@Epic("Special Features")
@Feature("Handle Alerts")
public class SpecialFeatureTest extends BaseTest {
    SpecialFeaturesPage sPage;// = new SpecialFeaturesPage().open();

    @Test(description = "Handle simple alert")
    @Story("Accept simple alert")
    @Severity(SeverityLevel.CRITICAL)
    public void handleSimpleAllert() {
        sPage = new SpecialFeaturesPage().open("https://qaplayground.com/practice/alerts-dialogs");
        sPage.clickSimpleAlert();
        sPage.switchToAlert();
        Assert.assertTrue(sPage.getAlertText().contains("Welcome to QA PlayGround!"));
        sPage.acceptAlert();
    }

    @Test(description = "Handle confirm alert")
    @Story("Accept confirm alert")
    @Severity(SeverityLevel.CRITICAL)
    public void confirmAllertAccept() {
        sPage = new SpecialFeaturesPage().open("https://qaplayground.com/practice/alerts-dialogs");
        sPage.clickConfirmAlert();
        sPage.switchToAlert();
        Assert.assertTrue(sPage.getAlertText().contains("Do you know QA Playground?"));
        sPage.acceptAlert();
        System.out.println("Confirm result text: " + sPage.confirmResult());
        Assert.assertTrue(sPage.confirmResult().contains("Accepted"));
    }

    @Test(description = "Handle confirm alert")
    @Story("Dismiss confirm alert")
    @Severity(SeverityLevel.CRITICAL)
    public void confirmAllertDismiss() {
        sPage = new SpecialFeaturesPage().open("https://qaplayground.com/practice/alerts-dialogs");
        sPage.clickConfirmAlert();
        sPage.switchToAlert();
        Assert.assertTrue(sPage.getAlertText().contains("Do you know QA Playground?"));

        sPage.dismissAlert();
        System.out.println("Confirm result text: " + sPage.confirmResult());
        Assert.assertTrue(sPage.confirmResult().contains("Dismissed"));
    }

    @Test(description = "Handle sahre link")
    @Story("Share link")
    @Severity(SeverityLevel.CRITICAL)
    public void sharePageLink() throws InterruptedException {
        sPage = new SpecialFeaturesPage().open("https://www.youtube.com");
        sPage.searchForVideo();
        Thread.sleep(3000);
        sPage.selctTheMatchValue();
        Assert.assertEquals(sPage.getCurrentUrl1(), "https://www.youtube.com/results?search_query=taylor+swift");
    }
    @Test(description = "File Uplaod")
    @Story("Upload file")
    @Severity(SeverityLevel.CRITICAL)
    public void fileUplaod() {
        sPage = new SpecialFeaturesPage().open("https://qaplayground.com/practice/file-upload");
        sPage.fileUpload();
        System.out.println("File Upload Statu: "+sPage.fileUploadMessage());
        Assert.assertEquals(sPage.fileUploadMessage(),"File selected: android-chrome-512x512.png");
    }

}
