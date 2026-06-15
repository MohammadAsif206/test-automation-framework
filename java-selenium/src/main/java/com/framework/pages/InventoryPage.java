package com.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item")
    private List<WebElement> items;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartLink;

    @FindBy(css = "[data-test='product-sort-container']")
    private WebElement sortDropdown;

    @Step("Verify inventory page is loaded")
    public boolean isLoaded() {
        waitForUrl("inventory");
        return !itemNames.isEmpty();
    }

    @Step("Get inventory item count")
    public int getItemCount() {
        return items.size();
    }

    @Step("Add first item to cart")
    public InventoryPage addFirstItemToCart() {
        WebElement addButton = items.get(0).findElement(
            org.openqa.selenium.By.cssSelector("button[id^='add-to-cart']")
        );
        click(addButton);
        return this;
    }

    @Step("Navigate to cart")
    public void goToCart() {
        click(cartLink);
    }

    @Step("Get first item name")
    public String getFirstItemName() {
        return getText(itemNames.get(0));
    }
}
