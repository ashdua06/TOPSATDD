package com.optum.topsuat.reporting;

import java.io.File;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.optum.topsuat.utils.TestNGHelper;



public class Report extends TestListenerAdapter{
	
	WebDriver driver;
	com.optum.topsuat.utils.AutomationCore AutoCore = new com.optum.topsuat.utils.AutomationCore();
	String buildNumber = "";
	
	@Override
	public void onTestFailure(ITestResult tr){
		//driver = SeleniumHelper.getDriver();
		System.out.println("On Test Failure method");
		System.out.println(tr.getThrowable());
			buildNumber = System.getenv("BUILD_NUMBER");
			Reporter.log("current build number is " + buildNumber);
			String errorMessage = tr.getThrowable().toString();
			Reporter.log("<br> Step Result: Failed. <br>"
					+ errorMessage.substring(errorMessage.indexOf(" ") + 1,
							errorMessage.length()));
			Reporter.log("<br> <b>TestCase: </b>" + tr.getName()
					+ " &nbsp <b> Result :</b> Fail &nbsp <b> Start Time: </b>"
					+ new Date(tr.getStartMillis())
					+ " &nbsp <b> End Time: </b>" + new Date(tr.getEndMillis())
					+ " &nbsp <b> Response Time in Seconds: </b>"
					+ (tr.getEndMillis() - tr.getStartMillis()) / 1000
					+ " &nbsp <b> Read: </b> " 
					+ " &nbsp <b> Exe Machine Name: </b>"
					+ AutoCore.getHostName() + " &nbsp <b> App Url: </b>"
					+  "&nbsp");
			String seMinVal = AutoCore.formatDateAndTime(
					AutoCore.getCurrentDateAndTime(), "mmss");
			String imageName = tr.getName() + "Fail" + seMinVal + ".png";
			
			String path = System.getProperty("user.dir") + File.separator  + imageName;
			System.out.println("On Failed Path "+path);
			try {
				TestNGHelper.takeScreenshot(path);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

				//ExtentUtilities.logScreenshot( System.getProperty("user.dir") + File.separator +path);
				try {
					TestNGHelper.logFailedScreenshot(AutoCore.currentStepName, "Yes", errorMessage
							.substring(errorMessage.indexOf(" ") + 1,
									errorMessage.length()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	@Override
	public void onTestSuccess(ITestResult tr){
		//driver = SeleniumHelper.getDriver();
		System.out.println("On Test Success method");
			Reporter.log("<br> <b>TestCase: </b>" + tr.getName()
					+ " &nbsp <b> Result :</b> Pass &nbsp <b> Start Time: </b>"
					+ new Date(tr.getStartMillis())
					+ " &nbsp <b> End Time: </b>" + new Date(tr.getEndMillis())
					+ " &nbsp <b> Response Time in Seconds: </b>"
					+ (tr.getEndMillis() - tr.getStartMillis()) / 1000
					+ " &nbsp <b> Read: </b> " 
					+ AutoCore.getHostName() + " &nbsp <b> App Url: </b>"
					);
			String seMinVal = AutoCore.formatDateAndTime(
					AutoCore.getCurrentDateAndTime(), "mmss");
			String imageName = tr.getName() + "Pass" + seMinVal + ".png";

			String path = System.getProperty("user.dir") + File.separator  + imageName;
			System.out.println("On test Success Path "+path);
			try {
				TestNGHelper.takeScreenshot(path);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//ExtentUtilities.setParentTestStatus("Pass", SeleniumHelper.extractTestName(tr.getName()) + " Passed");
		}

	@Override
	public void onTestSkipped(ITestResult tr){
		//driver = SeleniumHelper.getDriver();
		System.out.println("On Test Skipped method");
		System.out.println("driver " + driver);
		
//		ExtentUtilities.setParentTestStatus("Skip", SeleniumHelper.extractTestName(tr.getName())
//				+ " Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentUtilities.extentFlush();
	}
}