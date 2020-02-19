package com.optum.topsuat.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class ExtentUtilities {
	
	public static ExtentReports extent;
	public static ExtentHtmlReporter htmlReporter;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    public static ExtentTest parent;
    public static ExtentTest child;
    public static String testFileAttachmentPath = "";
    public static com.optum.topsuat.utils.AutomationCore autoCore = null;
    public static boolean flag=false;
    
	
	public static void initilaizeExtentReport() throws Exception
	{
		if(flag==false)
		{
			autoCore = new com.optum.topsuat.utils.AutomationCore();
			System.out.println("extent report path is" +System.getProperty("user.dir")+File.separator+autoCore.Read("extentReportPath"));
			extent = ExtentManager.createInstance(System.getProperty("user.dir")+File.separator+autoCore.Read("extentReportPath"));
			
			flag = true;
		}
		
		System.out.println("extent report created in ext utilities");
				
	}
	
	public static void createParentTest(String testName)
	{
		parent = extent.createTest(testName);
        parentTest.set(parent);
	}
	
	public static void createChildTest(String childTestName)
	{
		child = parentTest.get().createNode(childTestName);
        test.set(child);
	}
	
	public static void extentFlush() {
		System.out.println("flush");
		extent.flush();
	}
	
//	public static void logScreenshot(String imagePath)
//	{
//		try
//		{
//			child.addScreenCaptureFromPath(imagePath);
//		}
//		catch(Exception e)
//		{
//			System.out.println("Unable to log screenshot");
//		}		
//	}
	
	public static void logScreenshot(String imagePath)
	{
		try
		{
			parent.addScreenCaptureFromPath(imagePath);
		}
		catch(Exception e)
		{
			System.out.println("Unable to log screenshot");
		}		
	}
	
	public static void logScreenshotWithTitle(String imagePath, String stepName)
	{
		try
		{
			parent.log(Status.PASS, stepName, MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
		}
		catch(Exception e)
		{
			System.out.println("Unable to log screenshot");
		}		
	}
	
	public static void logFailedScreenshotWithTitle(String imagePath, String stepName)
	{
		try
		{
			
			System.out.println("path in extent utilities" +imagePath);
			parent.log(Status.FAIL, stepName, MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
		}
		catch(Exception e)
		{
			System.out.println("Unable to log screenshot");
		}		
	}
	
	public static void logReportWithTitlePass(String stepName)
	{
		try
		{
			parent.log(Status.PASS, stepName);
		}
		catch(Exception e)
		{
			System.out.println("Reported Pass with StepName");
		}		
	}
	
	public static void logReportWithTitleFail(String stepName)
	{
		try
		{
			parent.log(Status.FAIL, stepName);
		}
		catch(Exception e)
		{
			System.out.println("Reported Fail with StepName");
		}		
	}
	
	public static void logChildTestStatus(Status status, String details)
	{
		try
		{
			child.log(status, details);
		}
		catch(Exception e)
		{
			System.out.println("Unable to log status");
		}		
	}
	
	public static void logParentTestStatus(Status status, String details)
	{
		try
		{
			parent.log(status, details);
		}
		catch(Exception e)
		{
			System.out.println("Unable to log status");
		}		
	}
	
	public static void setChildTestStatus(String status, String details)
	{
		switch (status.toUpperCase()) {
		case "PASS":
			test.get().pass(details);
			break;
		case "FAIL":
			test.get().fail(details);
			break;
		case "SKIP":
			test.get().skip(details);
			break;
		default:
			break;
		}
	}
	
	public static void setParentTestStatus(String status, String details)
	{
		switch (status.toUpperCase()) {
		case "PASS":
			parentTest.get().pass(details);
			break;
		case "FAIL":
			parentTest.get().fail(details);
			break;
		case "SKIP":
			parentTest.get().skip(details);
			break;
		default:
			break;
		}
	}
	
	public static void logInfo(String details)
	{
		parent.info(details);
	}
	
//	public static void setTestStatus(ITestResult result)
//	{
//		if (result.getStatus() == ITestResult.FAILURE)
//            test.get().fail(result.getThrowable());
//        else if (result.getStatus() == ITestResult.SKIP)
//            test.get().skip(result.getThrowable());
//        else
//            test.get().pass("Test passed");
//
////        extent.flush();
//	}

}
