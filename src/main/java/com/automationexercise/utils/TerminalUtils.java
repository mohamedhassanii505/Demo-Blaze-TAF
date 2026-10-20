package com.automationexercise.utils;

import com.automationexercise.utils.logs.LogsManager;

import java.io.IOException;

public class TerminalUtils {
    public static void executeTerminalCommand(String... commandParts){
        try {
            LogsManager.info(
                    "Executing command:",
                    String.join(" ", commandParts)
            );

            Process process = Runtime.getRuntime().exec(commandParts);   // example : allure generate -o reports --single-file --clean
            int exitCode = process.waitFor();
            if (exitCode !=0 )  {
                LogsManager.error("Command failed with exit code: " , String.valueOf(exitCode));

            }

        }
        catch (IOException | InterruptedException e){
            LogsManager.error("failed to execute terminal command: " + String.join(" ",commandParts),e.getMessage());
        }
    }
}
