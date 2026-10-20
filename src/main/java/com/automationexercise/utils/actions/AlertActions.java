package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;

public class AlertActions {
    private final WebDriver driver;
    private final WaitManager waitManager;


    public AlertActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }


    /**
     * accept alert
     */

    public void acceptAlert() {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        d.switchTo().alert().accept();
                        return true;

                    } catch (Exception e) {
                        LogsManager.error("Failed to accept alert:", e.getMessage());
                        return false;
                    }
                }
        );

    }


    /**
     * dismiss alert
     */
    public void dismissAlert() {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        d.switchTo().alert().dismiss();
                        return true;

                    } catch (Exception e) {
                        LogsManager.error("Failed to dismiss alert:", e.getMessage());
                        return false;
                    }

                }
        );

    }

    /**
     * get text from the alert
     *
     * @return the text of the alert
     */


    public String getAlertText() {

        waitManager.fluentWait().until(d ->

                {
                    try {
                        String text = d.switchTo().alert().getText();
                        return !text.isEmpty() ? text : null;
                    } catch (Exception e) {
                        LogsManager.error("Failed to get alert text:", e.getMessage());
                        return null;
                    }
                }


        );


        return "";
    }


    /**
     * sets the text in the alert
     *
     * @param text the text to set in the alert
     */


    public void setAlertText(String text) {
        waitManager.fluentWait().until(d ->
        {
            try {
                d.switchTo().alert().sendKeys(text);
                return true;

            } catch (Exception e) {
                LogsManager.error("Failed to set alert text:", e.getMessage());
                return false;
            }


        }
        );
    }


}
