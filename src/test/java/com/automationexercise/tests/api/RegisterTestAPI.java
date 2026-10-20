package com.automationexercise.tests.api;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.listeners.TestNGListeners;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestNGListeners.class)
public class RegisterTestAPI extends BaseTest {
    String timeStamp = TimeManager.getSimpleTimeStamp();

    @Test
    public void registerTest() {


        new UserManagementAPI().createRegisterUserAccount(testData.getJsonData("name"),
                testData.getJsonData("email") + timeStamp + "@gmail.com",
                testData.getJsonData("password")
,
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


    }















    @BeforeClass
    public void setup() {

        testData = new JsonReader("register-data");
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
    }



    @AfterClass
    public void tearDown(){
        driver.quitDriver();

    }
}
