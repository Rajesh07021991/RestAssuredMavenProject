package api.utilities;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
	
	ExtentSparkReporter extentSparkReporter;
	String reportName;
	ExtentReports extent;
	ExtentTest test;
	
	public void OnStart(ITestContext testContext)
	{
		String timestamp =  new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportName = "Test-Report-"+timestamp+".html";
		
		//ExtendSparkReporter - for look and feel
		extentSparkReporter = new ExtentSparkReporter(".\\Reports\\"+reportName);
		extentSparkReporter.config().setDocumentTitle("RestAssuredMavenproject");
		extentSparkReporter.config().setReportName("Rajesh Singaraj");
		extentSparkReporter.config().setTheme(Theme.DARK);
		
		//Extent Reports - common information will be given here
		extent = new ExtentReports();
		extent.attachReporter(extentSparkReporter);
		extent.setSystemInfo("Application", "Pet Store");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Username", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Rajesh Singaraj");
	}
	
	public void OnTestSuccess(ITestResult result)
	{
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.PASS, "Tests Passed");
	}
	
	public void OnTestFailure(ITestResult result)
	{
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.FAIL, "Tests Failed");
		test.log(Status.FAIL, result.getThrowable().getMessage());
	}
	
	public void OnTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.SKIP, "Tests Skipped");
		test.log(Status.SKIP, result.getThrowable().getMessage());
	}
	
	public void OnFinish(ITestContext testContext)
	{
		extent.flush();
	}
}
