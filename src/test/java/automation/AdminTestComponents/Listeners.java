package automation.AdminTestComponents;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import automation.Resources.ExtentReporter;

public class Listeners extends BaseTest implements ITestListener, ISuiteListener {
	
	ExtentTest test;
	ExtentReports extent = ExtentReporter.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	ThreadLocal<String> testName = new ThreadLocal<>();
	Date suiteStartTime;

	@Override
	public void onStart(ISuite suite) {
		suiteStartTime = new Date(); // Record suite start time
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		testName.set(result.getMethod().getMethodName());
		test = extent.createTest(testName.get());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
	    extentTest.get().fail(result.getThrowable());

	    try {
	        driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }

	    String filePath = null;
	    try {
	        filePath = takeScreenshot(result.getMethod().getMethodName(), driver);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());

	    LogEntries entry = driver.manage().logs().get(LogType.BROWSER);
	    List<LogEntry> logs = entry.getAll();

	    // Define the directory path for saving the logs
	    String logDirectoryPath = "logs";

	    // Create the directory if it doesn't exist
	    File logDirectory = new File(logDirectoryPath);
	    if (!logDirectory.exists()) {
	        logDirectory.mkdirs(); // create directories including parent directories if not existing
	    }

	    // Define the file path for saving the logs
	    String logFilePath = logDirectoryPath + "/" + result.getMethod().getMethodName() + "_logs.txt";

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath))) {
	        for (LogEntry e : logs) {
	            writer.write(e.getMessage());
	            writer.newLine();
	        }
	        System.out.println("Browser logs written to file: " + logFilePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// extentTest.get().log(Status.SKIP, "Test Skipped");
		extentTest.get().skip(result.getThrowable());
		
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String filePath = null;
		try {
			filePath = takeScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test Execution Ended");
	}

	@Override
	public void onFinish(ISuite suite) {
		// Calculate total suite elapsed time
		Date suiteEndTime = new Date();
		long suiteElapsedTimeInMillis = suiteEndTime.getTime() - suiteStartTime.getTime();
		long suiteElapsedTimeInSeconds = suiteElapsedTimeInMillis / 1000;

		// Add total suite elapsed time to the extent report
		ExtentReporter.addSuiteElapsedTimeInfo(extent, suiteElapsedTimeInSeconds);

		System.out.println("Total Elapsed Time: " + suiteElapsedTimeInSeconds + " seconds");
		
		extent.flush();
	}
}
