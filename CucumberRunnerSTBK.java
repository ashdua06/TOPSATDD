package com.optum.topsuat.utils;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.optum.topsuat.pages.STBK;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(plugin = {"pretty", "html:target/cucumber-html-report"}, 	
features    = { "src/main/resources/Feature_TOPS"},
	 glue 		= { "com.optum.topsuat.stepdefinitions" },
	 monochrome = true,                                                   
	 strict = true,
			tags = {"@STBK_Keying_Prod"})
public class CucumberRunnerSTBK extends AbstractTestNGCucumberTests{
	@BeforeSuite
	public static void STBK_login() throws Exception{
		STBK.STBKprod_login();
	}
	
	@AfterSuite
	public static void STBK_logoff() throws Exception{
		STBK.STBK_id_logoff();
	}
	
	public static void main(String[] args) throws Throwable {
       // cucumber.api.cli.Main.main(args);
        
        try {
        	//CucumberRunnerSTBK.class.getClassLoader().getResource("/resources/Feature_TOPS/STBK.feature").getPath()
        	System.out.println("path is "+CucumberRunnerSTBK.class.getClassLoader().getResource("resources/Feature_TOPS/STBK.feature").getPath());
        	cucumber.api.cli.Main.main(args);
        	//cucumber.api.cli.Main.main(new String[]{"-g", "com.optum.topsuat.stepdefinitions", "-t", "@tcappealsrpa2","/"+CucumberRunnerSTBK.class.getClassLoader().getResource("resources/Feature_TOPS/STBK.feature").getPath()});
		} catch (Exception e) {
			System.out.println("Exception is "+e);
		}
    }
}
