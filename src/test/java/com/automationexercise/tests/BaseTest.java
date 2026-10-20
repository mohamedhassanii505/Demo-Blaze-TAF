package com.automationexercise.tests;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.listeners.TestNGListeners;
import com.automationexercise.utils.dataReader.JsonReader;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

/*@Listeners({AllureTestNg.class})*/

public class BaseTest implements WebDriverProvider {

    protected GUIDriver driver;
    protected JsonReader testData;










    @Override
    public WebDriver getWebDriver() {

        return driver.get();
    }
}



