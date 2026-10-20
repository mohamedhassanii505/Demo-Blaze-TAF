package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {

    public NavigationBarComponent navigationBarComponent;


    private  GUIDriver driver;



    private final String signupLoginEndpoint = "/login";

    public SignupLoginPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }


    // locators


    private final By signupNameField = By.cssSelector("[data-qa='signup-name']");
    private final By signupEmailField = By.cssSelector("[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("[data-qa='signup-button']");
    private final By loginEmailField = By.cssSelector("[data-qa='login-email']");
    private final By loginPasswordField = By.xpath("//*[@data-qa='login-password']") ;
    private final By loginButton = By.cssSelector("[data-qa='login-button']");

    private final By signupLabel = By.cssSelector("signup-form > h2");
    private final By loginError = By.cssSelector(".login-form  p");
    private final By signupError = By.cssSelector(".signup-form  p");







    // actions
@Step("Navigate to Signup/Login page")
    public SignupLoginPage navigateToSignupLoginPage(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + signupLoginEndpoint);
        return this;
    }

@Step("Enter name in signup field")
    public SignupLoginPage enterSignupName(String name){
        driver.element().type(signupNameField,name);
        return this;
    }
@Step("Enter email address in signup field")
    public SignupLoginPage enterSignupEmail(String email){
        driver.element().type(signupEmailField,email);
        return this;
}
@Step("Click on Signup button")
    public SignupLoginPage clickSignupButton(){
        driver.element().click(signupButton);
        return new SignupLoginPage(driver);
}


@Step("Enter email address in login field")
    public SignupLoginPage enterLoginEmail(String email){
        driver.element().type(loginEmailField,email);
        return this;
}
@Step("Enter password in login field")
    public SignupLoginPage enterLoginPassword(String password){
        driver.element().type(loginPasswordField,password);
        return this;
}
@Step("Click on Login button")
    public SignupLoginPage clickLoginButton(){
        driver.element().click(loginButton);
        return this;
}






    // validations
    @Step("validate that user is on Signup/Login page / verify New user signup! visible")
    public SignupLoginPage verifyUserIsOnSignupLoginPage(){
        driver.verification().isElementVisible(signupLabel);
        return this;
    }



    @Step("verify signup error message is visible {expectedErrorMassage}")
    public SignupLoginPage verifySignupErrorMessageVisible(String expectedErrorMassage){
        String actualErrorMessage = driver.element().getText(signupError);
        driver.verification().Equals(actualErrorMessage,expectedErrorMassage,"Signup error message does not match. Expected: "+ expectedErrorMassage+"Actual: "+ actualErrorMessage);
    return this;
    }

   @Step("verify login error message is visible {expectedErrorMessage}")
    public SignupLoginPage verifyLoginErrorMessageVisible(String expectedErrorMessage){
      String actualErrorMessage = driver.element().getText(loginError);
      driver.verification().Equals(actualErrorMessage,expectedErrorMessage,"Login error message does not match. Expected: "+ expectedErrorMessage+"Actual: "+ actualErrorMessage);
    return this;
    }


}
