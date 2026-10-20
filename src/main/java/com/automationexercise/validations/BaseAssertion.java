package com.automationexercise.validations;

import com.automationexercise.FileUtils;
import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseAssertion  {




    protected   WebDriver driver;
    protected  WaitManager waitManager;
    protected  ElementActions elementActions;

    protected BaseAssertion(){


    }


    protected BaseAssertion(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
        this.elementActions = new ElementActions(driver);
    }


    protected abstract void assertTrue(boolean condition,String message);
    protected abstract void assertFalse(boolean condition,String message);
    protected abstract void assertEquals(String actual,String expected,String message);

    public BaseAssertion Equals(String actual,String expected,String message){
        assertEquals(actual,expected,message);
        return this;
    }

    public void isElementVisible(By locator){
       boolean flag =  waitManager.fluentWait().until(d->
                {
                    try {

                         d.findElement(locator).isDisplayed();
                        return true;

                    }
                    catch (Exception e){
                       return false;
                    }

                }

                );
        assertTrue(flag,"Element is not visible: "+locator);
    }
//verify page url
    public void assertPageUrl(String expectedUrl){
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl,expectedUrl,"URL does not match .ExpectedUrl :" + expectedUrl + "ActualUrl :"+actualUrl);
    }
//verify page title
   public void assertPageTitle(String expectedTitle){
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle,expectedTitle,"Title does not match. ExpectedTitle: "+expectedTitle+"ActualTitle: "+actualTitle);
    }

    // verify the file exists
    public void assertFileExists(String fileName,String message) {
        waitManager.fluentWait().until(
                driver1 -> FileUtils.isFileExists(fileName)
        );

        assertTrue(FileUtils.isFileExists(fileName), message);
    }
}
