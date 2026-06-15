package com.framework.tests;

import com.framework.pages.InventoryPage;
import com.framework.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    @Test(description = "Standard user can log in successfully")
    @Story("Valid login")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage();
        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded after login");
        Assert.assertTrue(inventoryPage.getItemCount() > 0, "Inventory should have items");
    }

    @Test(description = "Locked out user sees error message")
    @Story("Invalid login")
    @Severity(SeverityLevel.NORMAL)
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"),
            "Error should mention locked out");
    }

    @Test(dataProvider = "invalidCredentials", description = "Invalid credentials show error")
    @Story("Invalid login")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidCredentials(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage().open();
        loginPage.login(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError));
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
            {"wrong_user", "secret_sauce", "Username and password do not match"},
            {"standard_user", "wrong_pass", "Username and password do not match"},
            {"", "", "Username is required"},
            {"standard_user", "", "Password is required"}
        };
    }
}
