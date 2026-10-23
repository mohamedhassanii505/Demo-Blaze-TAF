package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.listeners.TestNGListeners;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.pages.components.SignupLoginPage;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.*;

@Listeners(TestNGListeners.class)
@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohamed Hassan")
@UITest
public class LoginTest extends BaseTest {

    String timeStamp = TimeManager.getSimpleTimeStamp();
    @Description("Verify user can login with valid credentials")
  @Test
  public void validLoginTC(){
      new UserManagementAPI().createRegisterUserAccount(
              testData.getJsonData("name"),
              testData.getJsonData("email")+ timeStamp + "@gmail.com",
              testData.getJsonData("password"),
              testData.getJsonData("firstName"),
              testData.getJsonData("lastName"))
              .verifyUserAccountCreatedSuccessfully();

      new SignupLoginPage(driver).navigateToSignupLoginPage()
              .enterLoginEmail(testData.getJsonData("email")+ timeStamp + "@gmail.com")
              .enterLoginPassword(testData.getJsonData("password"))
              .clickLoginButton()
              .navigationBarComponent
              .verifyUserLabel(testData.getJsonData("name"));

      new UserManagementAPI().deleteUserAccount(
              testData.getJsonData("email")+ timeStamp + "@gmail.com",
              testData.getJsonData("password"))
              .verifyUserAccountDeletedSuccessfully();
  }



@Description("Verify user cannot login with invalid Email")
    @Test
    public void inValidLoginUsingInvalidEmailTC(){
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email")+ timeStamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserAccountCreatedSuccessfully();

        new SignupLoginPage(driver).navigateToSignupLoginPage()
                .enterLoginEmail(testData.getJsonData("email") + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .verifyLoginErrorMessageVisible(testData.getJsonData("messages.error"));


        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email")+ timeStamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserAccountDeletedSuccessfully();
    }


@Description("Verify user cannot login with invalid Password")
    @Test
    public void inValidLoginUsingInvalidPasswordTC(){
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email")+ timeStamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserAccountCreatedSuccessfully();

        new SignupLoginPage(driver).navigateToSignupLoginPage()
                .enterLoginEmail(testData.getJsonData("email")+ timeStamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password")+timeStamp)
                .clickLoginButton()
                .verifyLoginErrorMessageVisible(testData.getJsonData("messages.error"));

        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email")+ timeStamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserAccountDeletedSuccessfully();
    }












    // configuration methods


    @BeforeClass
    public void preCondition() {

        testData = new JsonReader("login-data");
    }


    @BeforeMethod(alwaysRun = true)
    public void setup(){
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();

        /* driver.browser().closeExtensionTab();*/



    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quitDriver();

    }
}
