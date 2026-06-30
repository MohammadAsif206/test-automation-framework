package com.framework.pages;



import com.framework.config.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object for the Home page (https://automationexercise.com/)
 * and the shared header / footer that appear on every page.
 */
public class HomePage extends BasePage {

    // ----- Header navigation -----
    @FindBy(xpath = "//a[@href='/login']")
    private WebElement signupLoginLink; 
    @FindBy(xpath = "//a[@href='/logout']")
    private WebElement logoutLink;
    @FindBy(xpath = "//a[@href='/delete_account']")
    private WebElement deleteAccountLink;
    @FindBy(xpath = "//a[@href='/products']")
    private  WebElement productsLink;
    @FindBy(xpath = "//a[@href='/view_cart']")
    private  WebElement cartLink;
    @FindBy(xpath = "//a[@href='/contact_us']")
    private  WebElement contactUsLink;
    @FindBy(xpath = "//a[@href='/test_cases']")
    private  WebElement testCasesLink;
    @FindBy(css = ".logo img")
    private  WebElement homeLogo;
    @FindBy(xpath = "//a[contains(text(),'Logged in as')]")
    private  WebElement loggedInAsText;

    // ----- Hero / slider text (used to verify "scrolled to top") -----
    @FindBy(xpath = "//*[contains(text(),'Full-Fledged practice website for Automation Engineers')]")
    private  WebElement heroFullFledgedText;

    // ----- Subscription (footer) -----
    @FindBy(id = "susbscribe_input")
    private  WebElement subscriptionTitle; // anchor, used to confirm footer reached
    @FindBy(id = "susbscribe_email")
    private  WebElement subscriptionEmailInput;
    @FindBy(id = "subscribe")
    private  WebElement subscriptionArrowButton;
    @FindBy(id = "success-subscribe")
    private  WebElement subscriptionSuccessAlert;

    // ----- Scroll up arrow (jQuery scrollUp plugin) -----
    @FindBy(id = "scrollUp")
    private  WebElement scrollUpArrow;

    // ----- Recommended items (lazy-loaded via AJAX near footer) -----
    @FindBy(xpath = "//h2[contains(text(),'Recommended Items')]")
    private  WebElement recommendedItemsTitle;
    @FindBy(xpath = "//div[@class='recommended_items']//a[contains(@class,'add-to-cart')]")
    private WebElement recommendedAddToCartButtons;

    // ----- "Added to cart" modal -----
    @FindBy(id = "cartModal")
    private  WebElement cartModal;
    @FindBy(xpath = "//div[@id='cartModal']//a[text()='View Cart']")
    private  WebElement viewCartLinkInModal;
    @FindBy(xpath = "//button[text()='Continue Shopping']")
    private  WebElement continueShoppingButton;

    

    /** Navigates the browser to the application base URL. */
    public HomePage open() {
        driver.get(ConfigManager.getBaseUrl() + "/");
        return this;
    }

    public boolean isHomePageVisible() {
        return isVisible(homeLogo) && driver.getCurrentUrl().contains("automationexercise.com");
    }

    // ----- Navigation actions -----
    public void clickSignupLogin() {
        click(signupLoginLink);
    }

    public void clickProducts() {
        click(productsLink);
    }

    public void clickCart() {
        click(cartLink);
    }

    public void clickContactUs() {
        click(contactUsLink);
    }

    public void clickTestCases() {
        click(testCasesLink);
    }

    public void clickLogo() {
        click(homeLogo);
    }

    public void clickLogout() {
        click(logoutLink);
    }

    public void clickDeleteAccount() {
        click(deleteAccountLink);
    }

    // ----- Login state assertions -----
    public boolean isLoggedInAsVisible(String username) {
        String text = getText(loggedInAsText);
        return text.contains("Logged in as") && text.contains(username);
    }

    public boolean isUserLoggedIn() {
        return isVisible(loggedInAsText);
    }

    // ----- Subscription (works on Home page AND Cart page footer) -----
    public void scrollToFooter() {
        scrollIntoView(subscriptionEmailInput);
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public boolean isSubscriptionTextVisible() {
        return isVisible(driver.findElement(By.xpath("//h2[text()='Subscription']")));
    }

    public void enterSubscriptionEmail(String email) {
        type(subscriptionEmailInput, email);
    }

    public void clickSubscribeArrow() {
        click(subscriptionArrowButton);
    }

    public String getSubscriptionSuccessMessage() {
        return getText(subscriptionSuccessAlert);
    }

    public HomePage subscribe(String email) {
        scrollToFooter();
        enterSubscriptionEmail(email);
        clickSubscribeArrow();
        wait.until(ExpectedConditions.visibilityOf(subscriptionSuccessAlert));
        return this;
    }

    // ----- Scroll behaviors (TC25 / TC26) -----
    public boolean isScrollUpArrowVisible() {
        return isVisible(scrollUpArrow);
    }

    public void clickScrollUpArrow() {
        wait.until(ExpectedConditions.elementToBeClickable(scrollUpArrow)).click();
    }

    public boolean isHeroTextVisible() {
        return isVisible(heroFullFledgedText);
    }

    // ----- Recommended items (TC22) -----
    public void scrollToRecommendedItems() {
        scrollIntoView(recommendedItemsTitle);
    }

    public boolean isRecommendedItemsVisible() {
        return isVisible(recommendedItemsTitle);
    }

    /** Clicks "Add to cart" on the first product inside the Recommended Items carousel. */
    public void addFirstRecommendedItemToCart() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='recommended_items']//a[contains(@class,'add-to-cart')]")));
        scrollIntoView(recommendedItemsTitle);
        List<WebElement> buttons = driver.findElements(By.xpath("//div[@class='recommended_items']//a[contains(@class,'add-to-cart')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttons.get(0));
    }

    public boolean isCartModalVisible() {
        return isVisible(cartModal);
    }

    public void clickViewCartInModal() {
        click(viewCartLinkInModal);
    }

    public void clickContinueShopping() {
        click(continueShoppingButton);
    }
}