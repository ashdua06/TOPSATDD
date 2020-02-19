package com.optum.topsuat.utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.optum.topsuat.reporting.ExtentUtilities;

import cucumber.api.Scenario;
import cucumber.runtime.CucumberException;

public class TestNGHelper {

	static Logger log = Logger.getLogger(TestNGHelper.class);
	static AutomationCore AutoCore = new AutomationCore();
	static WebDriver driver;
	private static Assertion hardAssert = new Assertion();
	private static SoftAssert softAssert = new SoftAssert();
	public static Scenario scenario_val;

	public static void assertEqual(Object actual, Object expected, String screenshotStatus, String stepName)
			throws Exception {

		try {
			Assert.assertEquals(actual, expected);
			logScreenshot(stepName, screenshotStatus);
		} catch (AssertionError error) {
			logFailedScreenshot(stepName, screenshotStatus, stepName);
		}

	}

	public static void assertEqual(Object actual, Object expected, String message, String screenshotStatus,
			String stepName) throws Exception {
		try {
			Assert.assertEquals(actual, expected, message);
			logScreenshot(stepName, screenshotStatus);
		} catch (AssertionError error) {
			logFailedScreenshot(stepName, screenshotStatus,
					message + "<br> Expected " + expected + " but found " + actual);
			// logScreenshot(stepName, screenshotStatus);
			// call/write Close TE Window Method
			// SeleniumHelper.quitDriver();
			throw new CucumberException(message);
		}

	}

	public static void fail(String message) {

		try {
			logFailedScreenshot(AutomationCore.currentStepName, "Yes", message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// SeleniumHelper.quitDriver();
		System.out.println("testg called");
		Assert.fail("Failed in Testng helper");
		new SoftAssert().assertAll();

	}

	public static void assertNotEqual(Object actual, Object expected, String screenshotStatus, String stepName) {

		Assert.assertNotEquals(actual, expected);
		try {
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void assertNotEqual(Object actual, Object expected, String message, String screenshotStatus,
			String stepName) {

		Assert.assertNotEquals(actual, expected, message);
		try {
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void assertTrue(boolean condition, String screenshotStatus, String stepName) {
		try {
			Assert.assertTrue(condition);
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {

			try {
				logFailedScreenshot(stepName, screenshotStatus, stepName);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			e.printStackTrace();
		}
	}

	public static void assertTrue(boolean condition, String message, String screenshotStatus, String stepName) {
		hardAssert.assertTrue(condition, message);
		try {
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void isTrue(boolean condition, String message, String screenshotStatus, String stepName) {

		try {
			softAssert.assertTrue(condition);
			logFailedScreenshot(stepName, screenshotStatus, "Assert is true failed");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void assertFalse(boolean condition, String screenshotStatus, String stepName) {
		Assert.assertFalse(condition);
		try {
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void assertFalse(boolean condition, String message, String screenshotStatus, String stepName) {
		Assert.assertFalse(condition, message);
		try {
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void assertNotNull(Object object, String screenshotStatus, String stepName) {
		hardAssert.assertNotNull(object);
		try {
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void assertNotNull_NoFailure(Object object, String screenshotStatus, String stepName) {
		softAssert.assertNotNull(object);
		try {
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void assertNotNull(Object object, String message, String screenshotStatus, String stepName) {
		hardAssert.assertNotNull(object, message);
		try {
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void isNotNull(Object object, String message, String screenshotStatus, String stepName) {
		softAssert.assertNotNull(object, message);
		try {
			logScreenshot(stepName, screenshotStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void logScreenshot(String stepName, String screenshotStatus) throws Exception {
		Reporter.log("<br> Step Result: " + stepName + " :Passed");
		log.info("Step: " + stepName + " passed");
		stepName = stepName.trim();
		try {
			if (screenshotStatus.equalsIgnoreCase("yes")) {
				log.info("Screenshot Status is yes and hence taking screenshot");

				String seMinVal = AutoCore.formatDateAndTime(AutoCore.getCurrentDateAndTime(), "mmss");
				String path = AutoCore.Read("screenShotsPath") + "\\" + stepName + "Pass" + seMinVal + ".png";
				System.out.println("Path to store screen " + path);
				Reporter.log("<br> <a href=file:" + path + " target='blank'> <img src=" + path
						+ " target='blank' height='300' width='500' /> </a></br>");

				takeScreenshot(System.getProperty("user.dir") + File.separator + path);
				ExtentUtilities.logScreenshotWithTitle(System.getProperty("user.dir") + File.separator + path,
						stepName);
			}
			else if (screenshotStatus.equalsIgnoreCase("no"))
			{
				ExtentReportWithTitle(true,stepName);
			}
			
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}

	}

	public static void takeScreenshot(String path) throws Exception {

		try {
			Robot robot = new Robot();
			String format = "png";

			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			// Rectangle screenRect = new Rectangle(x,y,width,height);
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			ImageIO.write(screenFullImage, format, new File(path));
			Reporter.log("A full screenshot saved!");
			System.out.println("Screen Captured Done");

		} catch (AWTException | IOException ex) {
			Reporter.log("A full screenshot Not saved!");
			System.out.println("Unable to take screenshot");
		}

	}

	public static void logFailedScreenshot(String stepName, String screenshotStatus, String errorDetails)
			throws Exception {
		Reporter.log("<br> Step Result: " + stepName + " :Failed");
		log.info("Step: " + stepName + " Failed");
		stepName = stepName.trim();
		try {
			 if (screenshotStatus.equalsIgnoreCase("no"))
				{
					ExtentReportWithTitle(false,stepName);
				}
			 else {
			String seMinVal = AutoCore.formatDateAndTime(AutoCore.getCurrentDateAndTime(), "mmss");
			String imageName = stepName + "Pass" + seMinVal + ".png";
			String path = AutoCore.Read("screenShotsPath") + File.separator + imageName;

			takeScreenshot(System.getProperty("user.dir") + File.separator + path);
			Reporter.log("<br> <a href=file:" + System.getProperty("user.dir") + File.separator + path
					+ " target='blank'> <img src=" + System.getProperty("user.dir") + File.separator + path
					+ " target='blank' height='300' width='500' /> </a></br>");
			ExtentUtilities.logFailedScreenshotWithTitle(System.getProperty("user.dir") + File.separator + path,
					" Step Failed <br> Failure Reason: "
							+ errorDetails.substring(errorDetails.indexOf(":") + 1, errorDetails.length()));
			 }
		} catch (AWTException | IOException ex) {
			
			System.out.println("Unable to take screenshot");
		}
	}

	public static void assertEqualTrue(boolean actual, boolean expected, String screenshotStatus, String stepName)
			throws Exception {
		try {
			Assert.assertEquals(actual, expected);
			logScreenshot(stepName, screenshotStatus);
		} catch (AssertionError error) {
			logFailedScreenshot(stepName, screenshotStatus, stepName);
		}
	}

	
public static void ExtentReportWithTitle(boolean status,String stepName){
	try {
		Assert.assertEquals(true, status);
		ExtentUtilities.logReportWithTitlePass(stepName);
	} catch (AssertionError error) {
		ExtentUtilities.logReportWithTitleFail(stepName);
	}
}
}
