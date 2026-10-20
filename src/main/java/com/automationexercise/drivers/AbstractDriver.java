package com.automationexercise.drivers;

import com.automationexercise.utils.dataReader.PropertyReader;
import org.openqa.selenium.WebDriver;

import java.io.File;

public abstract class AbstractDriver {

    protected final String remoteHost = PropertyReader.getProperty("remoteHost");
    protected final String remotePort = PropertyReader.getProperty("remotePort");
    protected String downloadsPath = System.getProperty("user.dir")+"\\src\\main\\resources\\downloads";

    /*protected File haramBlurExtension = new File("src/main/resources/extensions/HaramBlur.crx");*/


    public abstract WebDriver createDriver();
}
