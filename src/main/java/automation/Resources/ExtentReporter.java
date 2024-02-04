package automation.Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.Date;


//public class ExtentReporter {
//
//	public static ExtentReports getReportObject() {
//		String path = System.getProperty("user.dir")+"\\reports\\index.html";
//		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
//		reporter.config().setReportName("Web Application Reports");
//		reporter.config().setDocumentTitle("Test Result");
//
//
//		ExtentReports extent = new ExtentReports();
//		
//		extent.attachReporter(reporter);
//		extent.setSystemInfo("Operating System", "Windows10 Pro");
//		extent.setSystemInfo("Tested By", "Ren Castillano");
//		
//		return extent;
//	}
//}

public class ExtentReporter {

    private static Date startTime;

    public static ExtentReports getReportObject() {
        String path = System.getProperty("user.dir") + "\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Application Reports");
        reporter.config().setDocumentTitle("Test Result");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);

        // Record start time
        startTime = new Date();
        
        extent.setSystemInfo("Operating System", "Windows10 Pro");
        extent.setSystemInfo("Tested By", "Ren Castillano");

        return extent;
    }

    public static void addElapsedTimeInfo(ExtentReports extent) {
        // Record end time
        Date endTime = new Date();
        // Calculate elapsed time
        long elapsedTimeInMillis = endTime.getTime() - startTime.getTime();
        // Convert elapsed time to seconds
        long elapsedTimeInSeconds = elapsedTimeInMillis / 1000;
        
        // Add elapsed time info to the report
        extent.setSystemInfo("Elapsed Time: ", elapsedTimeInSeconds + " seconds");
    }
}

