package Utils;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;

public class ExtentReporterNG {
	
    static ExtentReports extent;
	
    public static String fileName = new SimpleDateFormat("yyyy-MM-dd::HH:mm").format(new Date());  
    
	public static ExtentReports extentReportGenerator() {
		
	String path = System.getProperty("user.dir")+"\\reports\\index.html";
	
	ExtentSparkReporter reporter = new ExtentSparkReporter(path);
	
	reporter.config().setReportName("Mediclaim_Automation");
	
	reporter.config().setDocumentTitle("RegressionTestCases");
	
	extent = new ExtentReports();
	
	extent.attachReporter(reporter);
	
	extent.setSystemInfo("Tester", "Sharik");
	
	return extent;
	
	
	
	} 
}
