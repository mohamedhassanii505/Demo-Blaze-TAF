package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {

    private final WebDriver driver;
    private final WaitManager waitManager = new WaitManager(null);


    public BrowserActions(WebDriver driver){
        this.driver = driver;
    }


    /**
     * Maximize window.
      */

    public void maximizeWindow(){
        driver.manage().window().maximize();
    }

    // get current web page's URL
    public String getCurrentUrl(){
        String url = driver.getCurrentUrl();
        LogsManager.info("Current URL : " + url);
        return url;
    }

    // Navigate to a specific url
    public void navigateTo(String url){
        driver.get(url);
        LogsManager.info("Navigated to URL : " + url);
    }

    // refresh the current page
    public void refreshPage(){
        driver.navigate().refresh();
    }

    // close current window
    public void closeCurrentWindow(){
        driver.close();
    }
  // open a new window
    public void openNewWindow(){
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    // close extension tab
    public void closeExtensionTab(){
        String currentWindowHandle = driver.getWindowHandle(); // 0 1
        waitManager.fluentWait().until(
                d ->
                {
                     return d.getWindowHandles().size();  // Wait until extension tab is opened
                }
        );
       for (String windowHandle : driver.getWindowHandles()) { // extension tab is opened
            if (!windowHandle.equals(currentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                driver.close(); // Close the extension tab
                LogsManager.info("extension tab closed");

            }
        }
        driver.switchTo().window(currentWindowHandle); // Switch back to the main window
        LogsManager.info("extension tab closed and switched back to main window");

    }
}
