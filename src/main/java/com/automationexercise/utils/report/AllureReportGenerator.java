package com.automationexercise.utils.report;

import com.automationexercise.FileUtils;
import com.automationexercise.utils.OSUtils;
import com.automationexercise.utils.TerminalUtils;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.automationexercise.utils.report.AllureConstants.HISTORY_FOLDER;
import static com.automationexercise.utils.report.AllureConstants.RESULTS_HISTORY_FOLDER;

public class AllureReportGenerator {


    // generate Allure report

    public static void generateReports(Boolean isSingleFile){

        //  --single-file    generate single file report
        Path outputFolder = isSingleFile ? AllureConstants.REPORT_PATH : AllureConstants.FULL_REPORT_PATH;

        // allure generate -o reports --single-file --clean
        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                AllureConstants.RESULTS_FOLDER.toString(),
                "-o",outputFolder.toString(),
                "--clean"
        ));
        if (isSingleFile) command.add("--single-file");
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));

    }




    // open Allure report in browser


   /* public static void openReportInBrowser(String reportFileName){
if (!PropertyReader.getProperty("executionType").toLowerCase().contains("local"))  return;

Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);
switch (OSUtils.getCurrentOS()){
    case WINDOWS -> TerminalUtils.executeTerminalCommand("cmd.exe","/c","start", reportPath.toString());
    case MAC ,LINUX -> TerminalUtils.executeTerminalCommand("open",reportPath.toString());
        default -> LogsManager.warn("Opening Allure Report is not supported on this OS ");
}


    }*/


    public static void openReportInBrowser(String reportFileName) {

        if (!PropertyReader.getProperty("executionType")
                .toLowerCase()
                .contains("local")) {
            return;
        }

        try {

            Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);

            if (!Files.exists(reportPath)) {
                LogsManager.error(
                        "Allure report not found",
                        reportPath.toString()
                );
                return;
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(reportPath.toUri());
            } else {
                LogsManager.warn("Desktop is not supported on this OS");
            }

        } catch (Exception e) {

            LogsManager.error(
                    "Error opening Allure Report",
                    e.getMessage()
            );
        }
    }











    // copy history folder to results folder


    public static void copyHistory(){
        try {
            FileUtils.copyDirectory(HISTORY_FOLDER.toFile(),RESULTS_HISTORY_FOLDER.toFile());



        }
        catch (Exception e){
            LogsManager.error("Error copying history files ",e.getMessage());
        }
    }


    // rename report file to AllureReport_timestamp.html

    public static String renameReport() {
        String newFileName = AllureConstants.REPORT_PREFIX + TimeManager.getTimeStamp() + AllureConstants.REPORT_EXTENSION;  // AllureReport_20260712_211530h-m-s.html
        FileUtils.renameFile(AllureConstants.REPORT_PATH.resolve(AllureConstants.INDEX_HTML).toString() ,newFileName);
        return newFileName;


    }










}
