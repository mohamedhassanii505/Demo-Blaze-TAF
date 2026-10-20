package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.ContactUsPage;
import com.automationexercise.pages.DeleteAccountPage;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {


    private final GUIDriver driver;

   public NavigationBarComponent(GUIDriver driver){
       this.driver = driver;

   }


    // locators

    private final By homeButton = By.xpath("//a[.=\" Home\"]");
   private final By productsButton = By.cssSelector("a[href=\"/products\"]");
   private final By cartButton = By.xpath("//a[.=\" Cart\"]");
   private final By logoutButton = By.xpath("//a[.=\" Logout\"]");
   private final By signup_loginButton = By.cssSelector("a[href=\"/login\"]");
   private final By testCasesButton = By.xpath("//a[.=\" Test Cases\"]");
   private final By deleteAccountButton = By.xpath("//a[.=\" Delete Account\"]");
   private final By apiTestingButton = By.xpath("//a[.=\" API Testing\"]");
   private final By videoTutorialButton = By.xpath("//a[.=\" Video Tutorials\"]");
   private final By contactUsButton = By.xpath("//a[.=\" Contact us\"]");
   private final By homePageLabel = By.cssSelector("h1 > span");
   private final By userLabel = By.tagName("b");


    // actions
    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on Home Button")
    public NavigationBarComponent clickHomeButton(){
    driver.element().click(homeButton);
    return this;
    }
    @Step("Click on Products Button")
    public ProductsPage clickOnProductsButton(){
        driver.element().click(productsButton);
        return new ProductsPage(driver);
    }
    @Step("Click on Cart Button")
    public CartPage clickOnCartButton() {
        driver.element().click(cartButton);
        return new CartPage(driver);
    }
    @Step("Click on Logout Button")
    public LogoutPage clickOnLogoutButton(){
        driver.element().click(logoutButton);
        return new LogoutPage(driver);
    }
    @Step("Click on Signup/Login Button")
    public SignupLoginPage clickOnSignupLoginButton() {
        driver.element().click(signup_loginButton);
        return new SignupLoginPage(driver);
    }
    @Step("Click on Test Cases Button")
    public TestCasesPage clickTestCasePageButton(){
        driver.element().click(testCasesButton);
        return new TestCasesPage(driver);

    }

    @Step("Click on Delete Account Button")
    public DeleteAccountPage clickDeleteAccountButton(){
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }





    @Step("Click on Contact Us Button")
    public ContactUsPage clickContactUsButton(){
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }












    // validations

    @Step("Verify Home Page Label")
    public NavigationBarComponent verifyHomePageLabel(){
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }

    @Step("Verify User Label")
    public NavigationBarComponent verifyUserLabel(String expectedName){
        String actualName = driver.element().getText(userLabel);
        driver.verification().Equals(actualName,expectedName,"User label does not match. Expected: "+expectedName+"Actual"+actualName);
        return this;
    }









}
