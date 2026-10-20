package com.automationexercise.utils.logs;

import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.report.AllureConstants;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class LogsManager {

    public static final String  LOGS_PATH = AllureConstants.USER_DIR + "/test-output/Logs/";


    private static final String LOG_FILE_NAME = "logs.log";


    private static Logger logger (){
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
    }




    public static String getLogFilePath() {
        return LOGS_PATH + LOG_FILE_NAME;
    }







   //info("errorMessage"+ " " +e.getMessage()); > //info("errorMessage",e.getMessage());
    public static void info(String... message)
    {
        logger().info(String.join(" ", message));}


    public static void error(String... message){logger().error(String.join(" ", message));}
    public static void warn(String... message){logger().warn(String.join(" ", message));}




    public static void fatal(String... message) {logger().fatal(String.join(" ", message));}

    public static void debug(String... message){
        logger().debug(String.join(" ", message));
    }

    
    public static void shutdown() {
        // Shutdown LogManager to flush and close log files.
        LogManager.shutdown();


    }



   /* public static void archiveLogs() {

        try {
            Path logFile = Path.of(getLogFilePath());

            if (!Files.exists(logFile) || Files.size(logFile) == 0) {
                return;
            }

            Path archiveDirectory = Paths.get(LOGS_PATH, "archive");
            Files.createDirectories(archiveDirectory);

            String archiveFileName =
                    "logs-" + TimeManager.getTimeStamp() + ".log";

            Path archiveFile = archiveDirectory.resolve(archiveFileName);

            Files.copy(
                    logFile,
                    archiveFile,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // بعد ما أخذنا نسخة Archive، نفرغ الملف
            Files.writeString(
                    logFile,
                    "",
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (Exception e) {
            System.out.println("Failed to archive logs: " + e.getMessage());
        }
    }

*/



    public static void archiveLogs() {

        try {
            Path logFile = Path.of(getLogFilePath());

            if (!Files.exists(logFile) || Files.size(logFile) == 0) {
                return;
            }

            Path archiveDirectory =
                    Paths.get(LOGS_PATH, "archive");

            Files.createDirectories(archiveDirectory);

            String archiveFileName =
                    "logs-" + TimeManager.getTimeStamp() + ".log";

            Path archiveFile =
                    archiveDirectory.resolve(archiveFileName);

            Files.copy(
                    logFile,
                    archiveFile,
                    StandardCopyOption.REPLACE_EXISTING
            );

            deleteOldArchives(archiveDirectory);

        } catch (Exception e) {
            System.out.println("Failed to archive logs: " + e.getMessage());
        }
    }




    private static void deleteOldArchives(Path archiveDirectory) {

        try (Stream<Path> files = Files.list(archiveDirectory)) {

            List<Path> archives = files
                    .filter(path -> path.getFileName()
                            .toString()
                            .endsWith(".log"))
                    .sorted(
                            Comparator.comparing(
                                    path -> path.toFile().lastModified(),
                                    Comparator.reverseOrder()
                            )
                    )
                    .toList();

            for (int i = 10; i < archives.size(); i++) {
                Files.deleteIfExists(archives.get(i));
            }

        } catch (Exception e) {
            System.out.println(
                    "Failed to clean old log archives: " + e.getMessage()
            );
        }
    }






}
