package automation.AdminTestComponents;

import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.WebDriver;
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
		// ExtentReporter.addElapsedTimeInfo(); // Record start time for individual test
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
