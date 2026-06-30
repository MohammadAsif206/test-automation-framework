package com.framework.tests;
import com.framework.pages.RegisterPage;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;


@Epic("Registration")
@Feature("Registration")
public class RegisterTest extends BaseTest{
    @Test(description="Register User")
    @Story("Valid Registeration")
    @Severity(SeverityLevel.CRITICAL)
    public void succesfullRegistration(){
        RegisterPage registerPage = new RegisterPage().open();
        registerPage.clickOnSignup();
        registerPage.enterUserName();
        registerPage.enterUserEmail();
        registerPage.clickSignUpButton();

        registerPage.selectGender("Mr.");
        registerPage.enterUserPassword();

        registerPage.enterDayOfDoB("10");
        registerPage.enterMonthOfDoB("September");
        registerPage.enterYearOfDoB("2001");

        registerPage.enterUserFirstName();
        registerPage.enterUserLasstName();
        registerPage.enterUserCompanyName("META");
        registerPage.enterUserAddress();
        registerPage.enterUserCity();
        registerPage.selectUserCountry("United States");
        registerPage.enterUserState();
        registerPage.enterUserCity();
        registerPage.enterUserZipCode();
        registerPage.enterUserMobile();
        registerPage.clickOnCreateAccountButton();
        System.out.println("========================>"+registerPage.accountCreated());
        Assert.assertTrue(registerPage.accountCreated().equalsIgnoreCase("Account Created!"));
    

        


    }

    
}
