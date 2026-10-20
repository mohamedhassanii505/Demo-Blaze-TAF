package com.automationexercise.utils.report;

import com.automationexercise.FileUtils;
import com.automationexercise.media.ScreenRecordManager;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.apache.logging.log4j.LogManager.getContext;

public class AllureAttachmentManager {
    // attach Screenshots  , attach Logs ,  attach records


    public static void attachScreenshot(String name ,String path){

      try {
         Path screenshot = Path.of(path);
         if (Files.exists(screenshot)){
             Allure.addAttachment(name, Files.newInputStream(screenshot));
         }
         else {
             LogsManager.error("Screenshot not found",path);
         }

      } catch (Exception e){
          LogsManager.error("Error attaching screenshot",e.getMessage());
      }
    }




  /*  public static void attachLogs() {

      try
      {
         LogsManager.shutdown();
        File logFile = new File(LogsManager.LOGS_PATH  + "logs.log");
        ((LoggerContext) LogManager.getContext(false)).getLoggerRegistry();
        if (logFile.exists()) {
            Allure.addAttachment("logs.log", Files.readString(logFile.toPath()));
        }
    }catch (Exception e){
          LogsManager.error("Error attaching logs",e.getMessage());
      }
    }*/


    public static void attachLogs() {
        try {

            /*LogsManager.shutdown();*/

            File logFile = new File(LogsManager.getLogFilePath());

            if (logFile.exists() && logFile.length() > 0) {

                Allure.addAttachment(
                        "logs.log",
                        Files.readString(logFile.toPath())
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public static void attachRecords(String testMethodName){
        if(PropertyReader.getProperty("recordTests").equalsIgnoreCase("true")){
            try {
                File record = new File(ScreenRecordManager.RECORDINGS_PATH+ testMethodName);
                if(record != null && record.getName().endsWith(".mp4")){
                    Allure.addAttachment(testMethodName,"video/mp4",Files.newInputStream(record.toPath()),".mp4");
                }

            }catch (Exception e){
                LogsManager.error("Error attaching records",e.getMessage());
            }
        }

    }

}
