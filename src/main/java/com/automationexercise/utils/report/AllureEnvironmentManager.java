package com.automationexercise.utils.report;

import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.google.common.collect.ImmutableMap;

import java.io.File;

import static com.automationexercise.utils.dataReader.PropertyReader.getProperty;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureEnvironmentManager {


   public static void setEnvironmentVariables() {
        allureEnvironmentWriter(
                        ImmutableMap.<String, String>builder()
                                .put("OS", getProperty("os.name"))
                                .put("JDK Version", getProperty("java.runtime.version"))
                                .put("Browser",getProperty("browserType"))
                                .put("Execution Type",getProperty("executionType"))
                                .put("URL", getProperty("baseUrlWeb"))
                                .build(),String.valueOf(AllureConstants.RESULTS_FOLDER)+File.separator


                );

       LogsManager.info("Allure environment variables set");
       AllureBinaryManager.downloadAndExtract();
    }


}
