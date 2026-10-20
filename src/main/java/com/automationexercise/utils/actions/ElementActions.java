package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementActions {
    private final WebDriver driver;
    private WaitManager waitManager;




    public ElementActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Clicking
    public ElementActions click(By locator) {

        waitManager.fluentWait().until(d->


        {
            try {

                 WebElement element = d.findElement(locator);
                 scrollToElementJS(locator);
                 // wait until the element is stable (not moving or changing) before clicking
                Point initialLocation = element.getLocation();
                LogsManager.info("initialLocation: " + initialLocation);
                Point finalLocation = element.getLocation();
                LogsManager.info("finalLocation: " + finalLocation);
                if (!initialLocation.equals(finalLocation)){
                    return false; // still moving, wait longer
                }
                element.click();
                LogsManager.info("Clicked on Element:"+locator);
                return true;
            }
            catch (Exception e){
                return false;
            }
        }
        );
        return this;
    }





    //Typing

    public ElementActions type(By locator, String text) {

        waitManager.fluentWait().until(d->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.clear();  //  عشان نضمن ان المكان فاضي قبل ما نكتب فيه
                        element.sendKeys(text);
                        LogsManager.info("Typed text:" + text + "into element"+locator);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
        return this;
    }


    // hovering
    public ElementActions hover(By locator) {

        waitManager.fluentWait().until(d->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        new Actions(d).moveToElement(element).perform();
                        LogsManager.info("Hovered over element:"+locator);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
        return this;

    }









    //Getting text


    public String getText(By locator){
        return  waitManager.fluentWait().until(d->
        {
            try {

                WebElement element = d.findElement(locator);
               scrollToElementJS(locator);
                //*element.clear();*//*      //ممنوع نحطها في getText لانها هتمسح النص من العنصر
                String msg = element.getText();
                LogsManager.info("Retrieved text from element:" + locator + "text" + msg);
                return !msg.isEmpty() ? msg : null;

            } catch (Exception e) {
                LogsManager.error("Failed to retrieve text from element: " + locator,e.getMessage());
                return null;

            }
        }
        );
    }



   /* public String getText(By locator) {

        try {

            return waitManager.fluentWait().until(d -> {

                try {

                    WebElement element = d.findElement(locator);
                    String msg = element.getText();

                    LogsManager.info(
                            "Retrieved text from element: " + locator + " | text: " + msg
                    );

                    return !msg.isEmpty() ? msg : null;

                } catch (NoSuchElementException | StaleElementReferenceException e) {

                    return null;
                }
            });

        } catch (TimeoutException e) {

            LogsManager.error(
                    "Timeout while retrieving text from element: " + locator,
                    e.getMessage()
            );

            String pageSource = driver.getPageSource();

            LogsManager.info("Current URL: " + driver.getCurrentUrl());

            LogsManager.info(
                    "Contains signup-form: " + pageSource.contains("signup-form")
            );

            LogsManager.info(
                    "Contains duplicate email message: "
                            + pageSource.contains("Email Address already exist!")
            );

            LogsManager.info(
                    "Contains email already exist: "
                            + pageSource.toLowerCase().contains("email address already exist")
            );

            return null;
        }


    }*/










































    //upload file
    public ElementActions uploadFile(By locator, String filePath)
    {
     String fileAbsolute = System.getProperty("user.dir") + File.separator +  filePath ;

        waitManager.fluentWait().until(d->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolute);
                        LogsManager.info("Uploaded file:"+fileAbsolute+"to element:"+locator);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
        return this;


    }


    //function to find an element and return it
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }


    //function to scroll to an element using js (javascript executor)
    public void scrollToElementJS(By locator){
        WebElement element = driver.findElement(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("""
                        arguments[0].scrollIntoView({behavior : "auto" , block : "center" , inline : "center"});""", findElement(locator));
    }


    // select from dropdown
    public  ElementActions selectFromDropdown(By locator, String value) {
        waitManager.fluentWait().until(d->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        Select select = new  Select(element);
                        select.selectByVisibleText(value);
                        LogsManager.info("Selected value:  '" + value + "' from dropdown element: " + locator );
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
        return this;
    }
}
