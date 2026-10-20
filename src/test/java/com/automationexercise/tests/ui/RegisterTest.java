package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.listeners.TestNGListeners;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.pages.components.SignupLoginPage;
import com.automationexercise.pages.components.SignupPage;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.*;


@Listeners(TestNGListeners.class)

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Registration")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohamed Hassan")
@UITest
public class RegisterTest extends BaseTest {






    // Tests
@Description("Verify user can sign up with valid data")
@Test

    // test case  using UI to create new user account
    public void ValidSignUpTC(){ //
    String timeStamp = TimeManager.getSimpleTimeStamp();
    String email = testData.getJsonData("email") + timeStamp + "@gmail.com";
    String password = testData.getJsonData("password");

        new SignupLoginPage(driver).navigateToSignupLoginPage()
                .enterSignupName(testData.getJsonData("name"))
                .enterSignupEmail(email)
                .clickSignupButton();
             new SignupPage(driver)
                .fillRegistrationForm(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")
                )
                .clickCreateAccountButton()
                .verifyAccountCreated();

    new UserManagementAPI().deleteUserAccount(
                    email,
                   password)
            .verifyUserAccountDeletedSuccessfully();

    }

    @Description("Verify user cannot sign up with an email that is already registered")
@Test

// create user account using API then try to create same account using UI and verify error message
    public void verifyErrorMessageWhenAccountCreatedBefore(){
    String timeStamp = TimeManager.getSimpleTimeStamp();
    String email = testData.getJsonData("email") + timeStamp + "@gmail.com";
    String password = testData.getJsonData("password");

        // precondition > create user account using API

        new UserManagementAPI().createRegisterUserAccount(testData.getJsonData("name"),
                        email ,
                       password,
                testData.getJsonData("titleMale"),

                testData.getJsonData("day"),
                testData.getJsonData("month"),
                testData.getJsonData("year"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName"),
                testData.getJsonData("company"),
                testData.getJsonData("address1"),
                testData.getJsonData("address2"),
                testData.getJsonData("country"),
                testData.getJsonData("state"),
                testData.getJsonData("city"),
                testData.getJsonData("zipcode"),
                testData.getJsonData("mobileNumber"))
                .verifyUserAccountCreatedSuccessfully();

    new SignupLoginPage(driver).navigateToSignupLoginPage()
            .enterSignupName(testData.getJsonData("name"))
            .enterSignupEmail(email)
            .clickSignupButton()
            .verifySignupErrorMessageVisible(testData.getJsonData("messages.errorSignup"));


    new UserManagementAPI().deleteUserAccount(
                    email,
                    password)
            .verifyUserAccountDeletedSuccessfully();

    }




    // Configurations
   /* @BeforeMethod(dependsOnMethods = "baseSetup")*/


    @BeforeClass
    public void preCondition() {

        testData = new JsonReader("register-data");
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
