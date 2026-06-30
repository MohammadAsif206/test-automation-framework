package com.framework.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.framework.utils.RandomDataGenerator;
import io.qameta.allure.Step;

public class RegisterPage extends BasePage {

    @FindBy(css = "a[href*='/login']")
    private WebElement login;

    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement name;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement email;

    @FindBy(css = "[data-qa='signup-button']")
    private WebElement signUpbButton;

    @FindBy(css = "#id_gender1")
    private WebElement selectGenderM;

    @FindBy(css = "#id_gender2")
    private WebElement selectGenderF;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "uniform-days")
    private WebElement dayDropdown;

    @FindBy(id = "days")
    private WebElement days;

    @FindBy(id = "uniform-months")
    private WebElement monthDropdown;

    @FindBy(id = "months")
    private WebElement months;

    @FindBy(id = "uniform-years")
    private WebElement yearDropdown;

    @FindBy(id = "years")
    private WebElement years;

    @FindBy(id = "first_name")
    private WebElement firstName;

    @FindBy(id = "last_name")
    private WebElement lastName;

    @FindBy(id = "company")
    private WebElement company;

    @FindBy(id = "country")
    private WebElement country;

    @FindBy(id = "address1")
    private WebElement address1;

    @FindBy(id = "address2")
    private WebElement address2;

    @FindBy(id = "state")
    private WebElement state;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "zipcode")
    private WebElement zipcode;

    @FindBy(id = "mobile_number")
    private WebElement mobileNumber;

    @FindBy(css = "[data-qa='create-account']")
    private WebElement createAccountButton;

    @FindBy(css = "[data-qa='account-created']")
    public WebElement accountCreated;

    public RegisterPage open() {
        navigateTo("https://automationexercise.com");
        return this;
    }

    @Step("Click on Signup/Signin link")
    public void clickOnSignup() {
        click(login);
    }

    @Step("Enter user name")
    public void enterUserName() {
        type(name, RandomDataGenerator.getFirstName());
    }

    @Step("Enter user email")
    public void enterUserEmail() {
        type(email, RandomDataGenerator.getEmail());
    }

    @Step("Click signup button")
    public void clickSignUpButton() {
       
        click(signUpbButton);
    }

    @Step("Select Gender")
    public void selectGender(String val) {
        if (val.equals("Mr."))
            click(selectGenderM);
        if (val.equals("Mrs."))
            click(selectGenderF);
    }

    @Step("Enter user password")
    public void enterUserPassword() {
        type(password, "mypassword");
    }

    @Step("Click on day dropdown menu")
    public void clickOnDayDropdown() {
        click(dayDropdown);

    }

    @Step("Select day of DOB")
    public void enterDayOfDoB(String day) {
        selectFromNormalDropdown(days, day);
    }

    @Step("Click on day dropdown menu")
    public void clickOnMonthDropdown() {
        click(monthDropdown);

    }

    @Step("Select month of DOB")
    public void enterMonthOfDoB(String month) {
        selectFromNormalDropdown(months, month);
    }

    @Step("Click on year dropdown menu")
    public void clickOnYearDropdown() {
        click(yearDropdown);

    }

    @Step("Select year of DOB")
    public void enterYearOfDoB(String year) {
        selectFromNormalDropdown(years, year);
    }

    @Step("Enter user first name")
    public void enterUserFirstName() {
        type(firstName, RandomDataGenerator.getFirstName());
    }

    @Step("Enter user last name")
    public void enterUserLasstName() {
        type(lastName, RandomDataGenerator.getLastName());
    }

    @Step("Enter user company name")
    public void enterUserCompanyName(String companyName) {
        type(company, companyName);
    }

    @Step("Enter user street address ")
    public void enterUserAddress() {
        type(address1, RandomDataGenerator.getAddress());
    }

    @Step("Select user country")
    public void selectUserCountry(String countryName) {
        selectFromNormalDropdown(country, countryName);
    }

    @Step("Enter user state")
    public void enterUserState() {
        type(state, "Texas");
    }

    @Step("Enter user city")
    public void enterUserCity() {
        type(city, RandomDataGenerator.getCity());
    }

    @Step("Enter user zipcode")
    public void enterUserZipCode() {
        type(zipcode, RandomDataGenerator.getZipcode());
    }

    @Step("Enter user mobile number")
    public void enterUserMobile() {
        type(mobileNumber, RandomDataGenerator.getPhoneNumber());
    }


    @Step("Click on Create Account button")
    public void clickOnCreateAccountButton() {
        click(createAccountButton);
    }
    @Step("Verify account created")
    public String accountCreated(){
        return getText(accountCreated);
    }

}
