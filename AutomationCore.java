package com.optum.topsuat.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Reporter;

import com.optum.topsuat.reporting.ExtentManager;
import com.optum.topsuat.reporting.ExtentUtilities;


public class AutomationCore 
{
	public static String currentStepName="";
	public static String platform=null;
	public static int stepNum = 0;
	public static String testName = "";
	public static Properties properties = new Properties();
	static Logger log = Logger.getLogger(AutomationCore.class);
	public static Properties props = new java.util.Properties();
	
	/**
	 * This method Loads the config.properties file
	 */
	public Properties loadProperties()
	{
		InputStream instream = getClass().getClassLoader().getResourceAsStream("config.properties");
		if(instream!=null)
		{
			Properties prop = new Properties();
			try 
			{
				prop.load(instream);
				return prop;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
			return null;	
	}


/**
	 * This method is used to get the property value from config.properties file.
	 * 
	 * @param propertyName : The name of the property for which user need to retrieve the value
	 * @return the property value for specified property name
	 * 
	 * Ex: Read("Username"):- it returns the value of Username property from config.properties file
 * @throws IOException 
	 */

	public  String Read(String propertyName) throws IOException
	{
		
		/*InputStream in = ExtentUtilities.class.getClassLoader().getResourceAsStream("resources/config.properties");
		System.out.println("Stream is "+in);
		if (in == null) {
			System.out.println("File not found!");
		}
		props.load(in);
		System.out.println("Flag is false");
		
		//autoCore = new com.optum.topsuat.utils.AutomationCore();
		
		return props.getProperty(propertyName);*/
		
		Properties propValue = loadProperties();		
		return propValue.getProperty(propertyName);
	}

	public  void Update(String propertyName, String propValue) throws Exception
	{
		PropertiesConfiguration conf = new PropertiesConfiguration("config.properties");
		conf.setProperty(propertyName, propValue);
		conf.save();  
	}
	
	public static int getStepNumber() {
		stepNum = stepNum + 1;
		return stepNum;
	}
	
	public static void logStepDetails(String stepName) {
		AutomationCore.currentStepName = trimStepName(stepName);
		log.info(stepName);
		Reporter.log(AutomationCore.getCurrentDateAndTime()+ " Step"+ AutomationCore.getStepNumber()+": " +stepName);
		//log.info(stepName);
	}
  
	public static String trimStepName(String stepName) {
		String tempStepName = stepName.substring(stepName.indexOf(": ") + 2, stepName.length());
		// tempStepName = tempStepName.replace(" ", "");
		return tempStepName;

	}
	
	public static void logTestSetNameInReport(String Name) {
		log.info("Logging Test Set Name: " + Name);
		String name = Name.trim().replace(" ", "");
		Reporter.log("<br> <h2 id=" + name + "> Test Set Name: </b>" + Name);
	}

	public static void logTestCaseNameInReport(String TestCaseName, String TestSetName) {
		log.info("Logging Test Case Name: " + TestCaseName);
		String tag = TestSetName.trim().replace(" ", "") + TestCaseName;
		Reporter.log("<br> <h2 id=" + tag + "> Test Case Name: </b>" + TestCaseName);

	}
	/**
	 * This method is used to get the current time stamp
	 * @return Current Date 
	 */
	public static Date getCurrentDateAndTime()
	{
		Date dNow = new Date( );
		return dNow;
	}
	
	/**
	 * This method is used to format the date type value into required date format in string 
	 * @param dateValue The date value that is in other format
	 * @param format Required format type to be formatted
	 * @return Returns the string type date value
	 * <p>
	 * Example:-
	 * <p>
	 * formatDateAndTime(13 dec 2015, "MM_dd_yyyy");
	 */
	public String formatDateAndTime(Date  dateValue, String format)
	{		
		SimpleDateFormat ft =  new SimpleDateFormat(format);	
		String formatedDateValue= ft.format(dateValue);
		return formatedDateValue;
	}
	
	public String formatDateAndTime(String  dateValue, String inputformat, String outputFormat)
	{		
		SimpleDateFormat inputDateFormat =  new SimpleDateFormat(inputformat);	
		SimpleDateFormat outputDateFormat =  new SimpleDateFormat(outputFormat);	
		String formatedDateValue = null;
		Date inputDate=null;
		try 
		{
			inputDate=inputDateFormat.parse(dateValue);			
			formatedDateValue = outputDateFormat.format(inputDate);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatedDateValue;
	}
	
	/**
	 * This method is used to get the machine name
	 * @return Machine name of string type
	 */
	public String getHostName()
	{
		String hostName="Not found";
		try
		{
			hostName= InetAddress.getLocalHost().getHostName().toString();
			return hostName;
		}
		catch(Exception e)
		{
			
		}
		return hostName;
	}
	
}
