package com.automationexercise;

import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.sun.jdi.PathSearchingVirtualMachine;

import java.io.File;

import static org.apache.commons.io.FileUtils.copyFile;

public class FileUtils {


    private static final String USER_DIR = PropertyReader.getProperty("user.dir")+File.separator;
    private FileUtils(){

        // prevent instantiation
    }


    // Renaming

    public static void renameFile(String oldName,String newName){
        try {

            var targetFile = new File(oldName);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();
            File newFile = new File(  targetDirectory + File.separator + newName);
            if (!targetFile.getPath().equals(newFile.getPath())) {
                copyFile(targetFile, newFile);
                org.apache.commons.io.FileUtils.deleteQuietly(targetFile);
                LogsManager.info("Target File Path: \"" + oldName + "\", file was renamed to \"" + newName + "\".");
            } else {
                LogsManager.info(("Target File Path: \"" + oldName + "\", already has the desired name \"" + newName + "\"."));

            } }
        catch (Exception e){
            LogsManager.error(e.getMessage() );
        }



    }


   /* public static void main(String[] args) {
        String oldFileName = "oldFile.txt";
        String newFileName = "newFile.txt";
        renameFile(oldFileName,newFileName);
    }*/








    // creating directory
    public static void createDirectory(String path){

      try {

          File file = new File(USER_DIR + path);
          if (!file.exists())
          {
              file.mkdirs();
              LogsManager.info("Directory created:" + path);
          }
      }
      catch (Exception e){
          LogsManager.error("failed to create directory: " +path , e.getMessage());
      }






    }



    // force delete directory
    public static void forceDelete(File file){

        try {
            org.apache.commons.io.FileUtils.forceDelete(file);
            LogsManager.info("File deleted successfully: "+file.getAbsolutePath());
        }
        catch (Exception e){
            LogsManager.error("Failed to delete file : "+file.getAbsoluteFile(),e.getMessage());
        }
    }




    //cleaning directory

    public static  void cleanDirectory(File file){
        try {
            org.apache.commons.io.FileUtils.deleteQuietly(file);  // in use > skipped
            LogsManager.info("Directory cleaned successfully: "+file.getAbsolutePath());

        }
        catch (Exception e){
            LogsManager.error("failed to clean directory : "+file.getAbsoluteFile(),e.getMessage());
        }
    }




    /**
     * Copy a directory to another location.
     *
     * @param sourceDir      Source directory
     * @param destinationDir Destination directory
     */
    public static void copyDirectory(File sourceDir, File destinationDir) {

        if (!sourceDir.exists()) {
            System.out.println("Source directory does not exist: " + sourceDir.getAbsolutePath());
            return;
        }

        try {

            org.apache.commons.io.FileUtils.copyDirectory(sourceDir, destinationDir);
            LogsManager.info("Directory copied successfully."+destinationDir);

        } catch (Exception e) {
           LogsManager.error("Failed to copy directory.", e.getMessage());
        }
    }



    // check the file exists
    public static boolean isFileExists(String fileName) {
        String downloadsPath = System.getProperty("user.dir") + File.separator + "/src/main/resources/downloads/";
        File file = new File(downloadsPath + fileName);
        return file.exists();
    }
}
