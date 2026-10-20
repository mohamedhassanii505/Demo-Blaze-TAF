package com.automationexercise.listeners;

import com.automationexercise.FileUtils;
import com.automationexercise.drivers.UITest;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.media.ScreenRecordManager;
import com.automationexercise.media.ScreenshotsManager;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.utils.report.AllureAttachmentManager;
import com.automationexercise.utils.report.AllureConstants;
import com.automationexercise.utils.report.AllureEnvironmentManager;
import com.automationexercise.utils.report.AllureReportGenerator;
import com.automationexercise.validations.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements ISuiteListener ,IExecutionListener , IInvokedMethodListener, ITestListener,IClassListener {


    public TestNGListeners() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TestNGListeners CREATED <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    public void onStart(ISuite suite) {
        suite.getXmlSuite().setName("Automation Exercise Test Suite");
    }

    public void onExecutionStart() {
        cleanTestOutputDirectories();
        LogsManager.info("Test Execution Started");

        LogsManager.info("Test Output Directories Cleaned");
        createTestOutputDirectories();
        LogsManager.info("Test Output Directories Created");
        PropertyReader.loadProperties();
        LogsManager.info("Properties Loaded");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Allure Environment Variables Set");
    }

    public void onExecutionFinish() {

        LogsManager.info("Test Execution Finished");

        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(true);

        AllureReportGenerator.openReportInBrowser(
                AllureReportGenerator.renameReport()
        );

        LogsManager.shutdown();
        LogsManager.archiveLogs();
    }
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

       /* if (method.isTestMethod()){
            if(testResult.getInstance() instanceof UITest )
            {
                ScreenRecordManager.startRecording();
            }

            LogsManager.info("Test case "+ testResult.getName()+"started");
        }*/

        if (method.isTestMethod()) {

            boolean isUITest =
                    testResult.getTestClass()
                            .getRealClass()
                            .isAnnotationPresent(UITest.class)
                            || testResult.getMethod()
                            .getConstructorOrMethod()
                            .getMethod()
                            .isAnnotationPresent(UITest.class);

            if (isUITest) {
                ScreenRecordManager.startRecording();
            }

            LogsManager.info("Test case " + testResult.getName() + " started");
        }

    }




    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        /*WebDriver driver = null;

        if (method.isTestMethod()){
            if (testResult.getInstance() instanceof UITest)
            {
                ScreenRecordManager.stopRecording(testResult.getName());
                if(testResult.getInstance() instanceof WebDriverProvider provider)
                    driver = provider.getWebDriver(); // initialize driver from WebDriverProvider


                switch (testResult.getStatus()){
                    case ITestResult.SUCCESS -> ScreenshotsManager.takeFullPageScreenshot(driver,"passed " + testResult.getName());
                    case ITestResult.FAILURE -> ScreenshotsManager.takeFullPageScreenshot(driver,"failed"+testResult.getName());
                    case ITestResult.SKIP -> ScreenshotsManager.takeFullPageScreenshot(driver,"skipped"+testResult.getName());
                }
                AllureAttachmentManager.attachRecords(testResult.getName());
            }

            Validation.assertAll(testResult);
            AllureAttachmentManager.attachLogs();


        }*/


        if (method.isTestMethod()) {

            boolean isUITest =
                    testResult.getTestClass()
                            .getRealClass()
                            .isAnnotationPresent(UITest.class)
                            || testResult.getMethod()
                            .getConstructorOrMethod()
                            .getMethod()
                            .isAnnotationPresent(UITest.class);

            if (isUITest) {

                WebDriver driver = null;

                if (testResult.getInstance() instanceof WebDriverProvider provider) {
                    driver = provider.getWebDriver();
                }

                ScreenRecordManager.stopRecording(testResult.getName());

                if (driver != null) {
                    switch (testResult.getStatus()) {
                        case ITestResult.SUCCESS ->
                                ScreenshotsManager.takeFullPageScreenshot(
                                        driver, "passed " + testResult.getName());

                        case ITestResult.FAILURE ->
                                ScreenshotsManager.takeFullPageScreenshot(
                                        driver, "failed " + testResult.getName());

                        case ITestResult.SKIP ->
                                ScreenshotsManager.takeFullPageScreenshot(
                                        driver, "skipped " + testResult.getName());
                    }
                } else {
                    LogsManager.error("Screenshot skipped", "WebDriver is null");
                }

                AllureAttachmentManager.attachRecords(testResult.getName());
            }

            Validation.assertAll(testResult);
            AllureAttachmentManager.attachLogs();
        }









    }



    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test case " + result.getName()+"passed");
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test case "+result.getName()+"failed");
    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test case "+result.getName()+"skipped");
    }



    // cleaning and creating directories (logs , screenshots , recordings , allure-results
    private void cleanTestOutputDirectories(){
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotsManager.SCREENSHOTS_PATH));
        FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDINGS_PATH));
        FileUtils.cleanDirectory(new File("src/main/resources/downloads/"));
        /*FileUtils.forceDelete(new File(LogsManager.LOGS_PATH + File.separator + "logs.log"));*/

    }


    private void createTestOutputDirectories(){
        FileUtils.createDirectory(ScreenshotsManager.SCREENSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManager.RECORDINGS_PATH);
        FileUtils.createDirectory("src/main/resources/downloads/");



    }
}
