package com.automationexercise.drivers;

import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.ENABLE_DOWNLOADS;

public class FirefoxFactory extends AbstractDriver {



    private FirefoxOptions getOptions(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<>();
        String userDir = System.getProperty("user.dir");
        String downloadPath = userDir + "\\src\\main\\resources\\downloads";
        prefs.put("profile.default_content_settings.popups", 0);

        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR,UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(ENABLE_DOWNLOADS,true);

        options.setAcceptInsecureCerts(true);


       /* FirefoxProfile profile = new FirefoxProfile();
        profile.addExtension(haramBlurExtension);
        options.setProfile(profile);
*/
        switch (PropertyReader.getProperty("executionType"))
        {
            case "LocalHeadless" -> options.addArguments("--headless=new");
            case "Remote" ->
            {
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-gpu");
                options.addArguments("--headless=new");
            }
        }





        options.setPageLoadStrategy(PageLoadStrategy.EAGER);


        return options;

    }

    public WebDriver createDriver(){
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local")||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")){
            return new FirefoxDriver(getOptions());
        } else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")) {

            try{
                return new RemoteWebDriver(
                        new URI("http://"+remoteHost+":"+remotePort+"/wd/hub").toURL(),getOptions()
                );
            }
            catch (Exception e){
                LogsManager.error("Error creating RemoteWebDriver: ",e.getMessage());
                throw new RuntimeException("failed to create RemoteWebDriver: " + e);
            }



        }
        else {
            LogsManager.error("Invalid execution type: ",PropertyReader.getProperty("executionType"));
            throw new IllegalArgumentException("Invalid execution type: " +PropertyReader.getProperty("executionType"));
        }
    }
}
