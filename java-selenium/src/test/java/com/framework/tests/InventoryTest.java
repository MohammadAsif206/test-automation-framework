package com.framework.tests;

import com.framework.pages.InventoryPage;
import com.framework.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Shopping")
@Feature("Inventory")
public class InventoryTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void login() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.login("standard_user", "secret_sauce");
    }

    @Test(description = "Inventory page loads with 6 items")
    @Story("Page load")
    @Severity(SeverityLevel.CRITICAL)
    public void testInventoryPageLoads() {
        InventoryPage inventoryPage = new InventoryPage();
        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded");
        Assert.assertEquals(inventoryPage.getItemCount(), 6, "Should show 6 inventory items");
    }

    @Test(description = "First item can be added to cart")
    @Story("Add to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddItemToCart() {
        InventoryPage inventoryPage = new InventoryPage();
        Assert.assertTrue(inventoryPage.isLoaded());

        String itemName = inventoryPage.getFirstItemName();
        Assert.assertNotNull(itemName, "Item name should not be null");
        Assert.assertFalse(itemName.isBlank(), "Item name should not be blank");

        inventoryPage.addFirstItemToCart();

        // Cart badge should appear with count 1
        driver.findElement(org.openqa.selenium.By.cssSelector(".shopping_cart_badge"));
        String badge = driver.findElement(
            org.openqa.selenium.By.cssSelector(".shopping_cart_badge")
        ).getText();
        Assert.assertEquals(badge, "1", "Cart badge should show 1 item");
    }

    @Test(description = "Navigate to cart after adding item")
    @Story("Cart navigation")
    @Severity(SeverityLevel.NORMAL)
    public void testNavigateToCart() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.addFirstItemToCart();
        inventoryPage.goToCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"),
            "Should navigate to cart page");
    }
}
