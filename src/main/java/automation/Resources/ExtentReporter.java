package automation.Resources;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {

	public static ExtentReports getReportObject() {
		String path = System.getProperty("user.dir") + "\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Application Reports");
		reporter.config().setDocumentTitle("Test Result");
		reporter.config().setTheme(Theme.DARK);

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);

		extent.setSystemInfo("Operating System", "Windows10 Pro");
		extent.setSystemInfo("Tested By", "Ren Castillano");

		return extent;
	}

	public static void addSuiteElapsedTimeInfo(ExtentReports extent, long suiteElapsedTimeInSeconds) {
		long minutes = suiteElapsedTimeInSeconds / 60;
		long seconds = suiteElapsedTimeInSeconds % 60;

		String totalTimeFormatted = String.format("%02d:%02d", minutes, seconds);

		extent.setSystemInfo("Total Elapsed Time: ", totalTimeFormatted);
	}

}
