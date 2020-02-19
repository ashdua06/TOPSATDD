package com.optum.topsuat.utils;

import org.testng.annotations.AfterSuite;

import com.optum.topsuat.functionlib.Mainframe_GlobalFunctionLib;
import com.optum.topsuat.pages.Tops_UserLogOff;
import com.optum.topsuat.reporting.ExtentUtilities;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(plugin = {"pretty", "html:target/cucumber-html-report"}, 	
			 features    = { "src/main/resources/Regression_TOPS"},
      		 glue 		= { "com.optum.topsuat.stepdefinitions" },
      		 monochrome = true,                                                   
      		 strict = true,
      				tags = {"@UTR_AddNewClaim"})
public class CucumberRunner extends AbstractTestNGCucumberTests {
	
	@AfterSuite
	public void Cleanup() throws Throwable {
		AutomationCore core = new AutomationCore();
		Tops_UserLogOff.screenclear();
		ExtentUtilities.extentFlush();
		core.Update("regionName","xyz");
		core.Update("filmOffc","000");
		core.Update("adjOffc","000");
		System.out.println("Values in Config file in After suite after updation is"+core.Read("regionName")+" "+core.Read("filmOffc")+" "+core.Read("adjOffc"));
		}
}
