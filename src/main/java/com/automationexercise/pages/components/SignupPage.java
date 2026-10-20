package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    private final GUIDriver driver;
    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }



    // locators


    private final By nameField = By.id("name");
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By dayField = By.id("days");
    private final By monthField = By.id("months");
    private final By yearField = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By specialOffersCheckbox = By.id("optin");

    private final By firstNameField = By.cssSelector("[data-qa='first_name']");
    private final By lastNameField = By.cssSelector("[data-qa='last_name']");
    private final By companyField = By.cssSelector("[data-qa='company']");
    private final By address1Field = By.cssSelector("[data-qa='address']");
    private final By address2Field = By.cssSelector("[data-qa='address2']");
    private final By countryField = By.cssSelector("[data-qa='country']");
    private final By stateField = By.cssSelector("[data-qa='state']");
    private final By cityField = By.cssSelector("[data-qa='city']");
    private final By zipcodeField = By.cssSelector("[data-qa='zipcode']");
    private final By mobileNumberField = By.cssSelector("[data-qa='mobile_number']");
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");

    private final By accountCreatedLabel = By.tagName("b");
    private final By continueButton = By.cssSelector("[data-qa='continue-button']");


    // actions


  @Step("Choose title {title}") // Mr - Mrs
    private SignupPage chooseTitle(String title){
        By titleLocator = By.xpath("//input[@value='"+title+"']");
        driver.element().click(titleLocator);
        return this;
    }

    @Step("Fill registration form")
    public SignupPage fillRegistrationForm(String title ,  String password, String day, String month, String year, String firstName, String lastName,
                                       String company, String address1, String address2, String country,
                                        String state, String city, String zipcode, String mobileNumber){
      chooseTitle(title);

        driver.element().type(passwordField,password);
        driver.element().selectFromDropdown(dayField,day);
        driver.element().selectFromDropdown(monthField,month);
        driver.element().selectFromDropdown(yearField,year);
        driver.element().click(newsletterCheckbox);
        driver.element().click(specialOffersCheckbox);

        driver.element().type(firstNameField,firstName);
        driver.element().type(lastNameField,lastName);
        driver.element().type(companyField,company);
        driver.element().type(address1Field,address1);
        driver.element().type(address2Field,address2);
        driver.element().selectFromDropdown(countryField,country);
        driver.element().type(stateField,state);
        driver.element().type(cityField,city);
        driver.element().type(zipcodeField,zipcode);
        driver.element().type(mobileNumberField,mobileNumber);

        return this;

    }

    @Step("Click on Create Account button")
   public SignupPage clickCreateAccountButton(){
        driver.element().click(createAccountButton);
        return this;
    }

    @Step("Click on Continue button")
    public NavigationBarComponent clickContinueButton(){
      driver.element().click(continueButton);
      return new NavigationBarComponent(driver);
    }

    
    // validations

    @Step("Verify account created successfully")

    public SignupPage verifyAccountCreated(){
      driver.verification().isElementVisible(accountCreatedLabel);
      return this;
    }
}
