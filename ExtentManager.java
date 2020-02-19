package com.optum.topsuat.reporting;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.optum.topsuat.utils.AutomationCore;

public class ExtentManager {
	
	static com.optum.topsuat.utils.AutomationCore AutoCore;
    
    private static ExtentReports extent;
    public static ExtentHtmlReporter htmlReporter;
    
    public static ExtentReports getInstance() throws IOException {
    	AutoCore = new AutomationCore();
    	
    	if (extent == null)
    		createInstance(AutoCore.Read("extentReportPath"));
    	
        return extent;
   }
    
    public static ExtentReports createInstance(String fileName) {
    	System.out.println("extent report path in manager is" +fileName);
        htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("utf-8");
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        htmlReporter.setAppendExisting(true);
        
        //System.out.println("extent report created in ext manager");
        
        return extent;
    }
    
    public static void setExtentReportName()
    {
    	htmlReporter.config().setReportName("Tops Results");
    	htmlReporter.config().setDocumentTitle("Tops Results");
    }
    
}