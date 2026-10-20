package com.automationexercise.utils.dataReader;

import com.automationexercise.utils.logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    public static void loadProperties() {

        try {
            Collection<File> propertiesFiles =
                    FileUtils.listFiles(
                            new File("src/main/resources"),
                            new String[]{"properties"},
                            true
                    );

            for (File file : propertiesFiles) {

                try {
                    properties.load(FileUtils.openInputStream(file));

                    LogsManager.info(
                            "Loaded properties file: " + file.getAbsolutePath()
                    );

                } catch (Exception e) {
                    LogsManager.error(
                            "Error loading properties file:",
                            file.getName(),
                            e.getMessage()
                    );
                }
            }

            System.out.println("Properties loaded successfully");

        } catch (Exception e) {
            System.err.println(
                    "Error loading properties files:"+
                    e.getMessage()
            );
        }
    }

    public static String getProperty(String key) {

        String value = properties.getProperty(key);


        if (value == null) {
            value = System.getProperty(key);
        }

        return value;
    }
}









/*
public class PropertyReader {


    public static Properties loadProperties() {


        try {
            Properties properties = new Properties();
            Collection<File> propertiesFiles;
            propertiesFiles = FileUtils.listFiles(new File("src/main/resources"), new String[]{"properties"}, true);
            propertiesFiles.forEach(file -> {
                        try {

                            properties.load(FileUtils.openInputStream(file));
                        } catch (Exception e) {
                            LogsManager.error("Error loading properties file :", file.getName(), e.getMessage());
                        }


                        properties.putAll(System.getProperties());
                        System.getProperties().putAll(properties);
                    });
            LogsManager.info("Properties loaded successfully");
            return properties;
        } catch (Exception e) {
            LogsManager.error("Error loading properties file :",e.getMessage());
            return null;
        }
    }



    public static String getProperty(String key){
        try{

          return System.getProperty(key);
        }
        catch (Exception e){
          LogsManager.error("Error getting property for key:",key,e.getMessage());
            return "";
        }
    }
}
*/
