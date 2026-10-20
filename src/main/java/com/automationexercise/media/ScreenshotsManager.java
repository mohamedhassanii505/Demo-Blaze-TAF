package com.automationexercise.media;

import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotsManager {
    public static final String SCREENSHOTS_PATH = "test-output/screenshots/";



    // take full page screenshot

public static void  takeFullPageScreenshot(WebDriver driver,String screenshotName){
    try {
        //capture  screenshot using TakesScreenshot

        File screenshotSrc = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        // Save screenshot to a file if needed

        File screenshotFile = new File(SCREENSHOTS_PATH+screenshotName+"-"+ TimeManager.getTimeStamp()+".png");
        FileUtils.copyFile(screenshotSrc,screenshotFile);

        // TODO: Attach the screenshot to Allure if needed (done todo here )

        AllureAttachmentManager.attachScreenshot(screenshotName,screenshotFile.getAbsolutePath());
        
        LogsManager.info("Capturing Screenshot Succeeded");


    }
    catch (Exception e){

        LogsManager.error("Capturing Screenshot Failed",e.getMessage());
    }

}

// take screenshot of a specific element

    public static void takeElementScreenshot(WebDriver driver, By elementSelector){
        try {
            //capture  screenshot using TakesScreenshot
            String accessibleName = driver.findElement(elementSelector).getAccessibleName();
            File screenshotSrc = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed

            File screenshotFile = new File(SCREENSHOTS_PATH+accessibleName+"-"+ TimeManager.getTimeStamp()+".png");
            FileUtils.copyFile(screenshotSrc,screenshotFile);

            // TODO: Attach the screenshot to Allure if needed

            LogsManager.info("Capturing Screenshot Succeeded");


        }
        catch (Exception e){

            LogsManager.error("Capturing Screenshot Failed",e.getMessage());
        }




    }


}
