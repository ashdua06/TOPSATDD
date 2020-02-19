package com.optum.topsuat.utils;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.optum.topsuat.pages.Rally_UpdateTestCases;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(plugin = {"pretty", "html:target/cucumber-html-report"}, 	
features    = { "src/main/resources/Feature_TOPS"},
	 glue 		= { "com.optum.topsuat.stepdefinitions" },
	 monochrome = true,                                                   
	 strict = true,
			tags = {" @Rally_results_TC"})
public class CucumberRunnerRally extends AbstractTestNGCucumberTests{
	@BeforeSuite
	public static void Rally_Login() throws Exception{
		Rally_UpdateTestCases.Rally_login();
	}
	
	@AfterSuite
	public static void Rally_logout() throws InterruptedException{
		Rally_UpdateTestCases.Rally_logout();
	}
}
