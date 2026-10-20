package com.automationexercise.utils.report;

import com.automationexercise.utils.OSUtils;
import com.automationexercise.utils.TerminalUtils;
import com.automationexercise.utils.logs.LogsManager;
import org.jsoup.Jsoup;
import ws.schild.jave.utils.RBufferedReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AllureBinaryManager {

    private static final class LazyHolder{

         static final String VERSION = resolveVersion();// example 2.44.0


        private static String resolveVersion() {
     try {
         String url = Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest")
                 .followRedirects(true).execute().url().toString();
         return   url.split("/tag/")[1];
     }
     catch (Exception e){
         throw new IllegalArgumentException("Unable to resolve Allure version ",e);
     }


        }
    }


    public static void downloadAndExtract() {
        try {
            String version = LazyHolder.VERSION;
            // C:\Users\mohamed hassan\.m2\repository\allure-2.34.1
            Path extractionDir = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version);

            if (Files.exists(extractionDir)) {
                LogsManager.info("Allure binaries already exist");
                return;
            }
            // give execute permissions to the binary if not on Windows
            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand("chmod", "u+x", AllureConstants.USER_DIR.toString());

            }
            Path zipPath = downloadZip(version);

            if (zipPath == null || !Files.exists(zipPath)) {

                LogsManager.error(
                        "Allure zip file was not downloaded."
                );

                return;
            }
            extractZip(zipPath);
            if (!Files.exists(getExecutable())) {

                LogsManager.error(
                        "Allure executable was not found:",
                        getExecutable().toString()
                );

                return;
            }
            LogsManager.info(
                    "Allure binaries downloaded and extracted"
            );

            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)) {

                TerminalUtils.executeTerminalCommand(
                        "chmod",
                        "u+x",
                        getExecutable().toString()
                );

            }
            Files.deleteIfExists(zipPath);
        }catch (Exception e) {
            LogsManager.error("Error downloading or extracting binaries:",
                    e.getMessage());
        }
    }



/*
            // give execute permissions to the binary if not on Windows
            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)){
                TerminalUtils.executeTerminalCommand("chmod","u+x" ,getExecutable().toString());
            }
            // clean up the zip file after extraction
            Files.deleteIfExists(Files.list(AllureConstants.EXTRACTION_DIR).filter(p-> p.toString().endsWith(".zip")).findFirst().orElseThrow());

        }
        catch (Exception e){
            LogsManager.error("Error downloading or extracting binaries" , e.getMessage());
        }
    }*/















    public static Path getExecutable(){
        String version = LazyHolder.VERSION;
        // C:\Users\mohamed hassan\.m2\repository\allure-2.34.1\bin
        Path binaryPath = Paths.get(AllureConstants.EXTRACTION_DIR.toString(),"allure-"+version,"bin" ,"allure");
        return  OSUtils.getCurrentOS() == OSUtils.OS.WINDOWS
                ? binaryPath.resolveSibling(binaryPath.getFileName() + ".bat")
                : binaryPath;
    }



    // Download ZIP file for Allure
    private static Path downloadZip(String version){
        try {
            // link download for allure
            // https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.44.0/allure-commandline-2.44.0.zip

            String url = AllureConstants.ALLURE_ZIP_BASE_URL+version+"/allure-commandline-"+version +".zip";

            // C:\Users\mohamed hassan\.m2\repository\allure
            
            Path zipFile =Paths.get( AllureConstants.EXTRACTION_DIR.toString(),"allure-"+version+".zip");

            if(!Files.exists(zipFile)){
                Files.createDirectories(AllureConstants.EXTRACTION_DIR);
                LogsManager.info("Downloading Allure from:", url);
                try (BufferedInputStream in = new BufferedInputStream(new URI(url).toURL().openStream());
                     OutputStream out = Files.newOutputStream(zipFile)) {
                    in.transferTo(out);
                }

                LogsManager.info("Allure zip already exists:", zipFile.toString());

            }
            return zipFile;
        }
        catch (Exception e){
            LogsManager.error("Error downloading Allure zip file",e.getMessage());
            return null;
        }
    }




    // Extract ZIP file for Allure

    private static void extractZip(Path zipPath){

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath)))
        {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry())!=null){
                Path filePath = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), File.separator,entry.getName());
                if (entry.isDirectory()){
                    Files.createDirectories(filePath);

                }
                else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zipInputStream,filePath);

                }
            }

        }
       catch (Exception e){
            LogsManager.error("Error extracting Allure zip file ",e.getMessage());
       }
    }
}
