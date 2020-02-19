package com.optum.topsuat.pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.optum.topsuat.reporting.ExtentUtilities;

public class Rally_UpdateTestCases {
	static WebDriver driver;
	static HashMap<String , String> hm = new HashMap<String , String >();

	public static void Rally_login() throws Exception{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
	    options.setExperimentalOption("useAutomationExtension", false);
	    driver = new ChromeDriver(options);
	  
	    driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//login to rally
		driver.get("https://rally1.rallydev.com");
		driver.findElement(By.cssSelector("input[id='j_username']")).sendKeys("himanshu_dua@optum.com");
		driver.findElement(By.cssSelector("input[id='j_password']")).sendKeys("MANash@100");
		driver.findElement(By.cssSelector("input[id='login-button']")).click();
		//Thread.sleep(2000);
	}
	
	public static void Rally_Open_US_PRJ(String PRJ, String US ) throws Exception{
		//Rally_login();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By prj_nav =By.xpath("//*[@class='project-nav-button icon-chevron-down visible']");
		By prj_name= By.xpath("//a[contains(text(),'"+PRJ+"')]");
		By prj_title = By.xpath("//div[contains(@title,'"+PRJ+"')]");
		By prj_plan = By.xpath("//a[@title='Plan']");
		By prj_userstory = By.xpath("//a[text()='User Stories']");
		By prj_USFilter=By.xpath("//input[@name='idFilter']");
		By prj_USFilterButton = By.xpath("//button[text()='Filter']");
		By prj_US = By.xpath("//table[@id='userstory_tps']//following::tbody[5]/tr/td[3]/a");
		//scroll projects
		wait.until(ExpectedConditions.elementToBeClickable(prj_nav)).click();

		
		//Give project name
		wait.until(ExpectedConditions.visibilityOfElementLocated(prj_name)).click();
		
		//click on plan and  user story
		wait.until(ExpectedConditions.visibilityOfElementLocated(prj_title));
		driver.findElement(prj_plan).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(prj_userstory)).click();
		
		//Filter user story
		wait.until(ExpectedConditions.visibilityOfElementLocated(prj_USFilter)).clear();
		driver.findElement(prj_USFilter).sendKeys(US);
		wait.until(ExpectedConditions.elementToBeClickable(prj_USFilterButton)).click();
				
		//Open user story 
		wait.until(ExpectedConditions.textToBePresentInElementLocated(prj_US, US));
		driver.findElement(prj_US).click();
	}
	
	public static void Rally_FetchTestResultsDataFromExcel(String PRJ,String US){

		int TCs=0;
		int j;
		String prjname="";
		String ustory="";
		try {
			XSSFSheet ws;
			XSSFWorkbook wb;
			XSSFCell cell;
			XSSFRow row;
			String celldata = null;
			
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\TestData\\Rally_TestResults.xlsx");
			System.out.println("Test Results sheet opened");
			wb = new XSSFWorkbook(fis);
			ws = wb.getSheet("TestResults");
			int totalrows = ws.getLastRowNum();
			System.out.println("total rows are "+totalrows);
			System.out.println("file loaded");
			
			for(int i=2;i<=totalrows;i++){
				prjname= ws.getRow(i).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue();
				ustory= ws.getRow(i).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue();
				if(prjname.equals(PRJ)&&ustory.equals(US)){
					TCs=TCs+1;
				System.out.println("Project is "+prjname);
				System.out.println("UserStory is "+ustory);
				System.out.println("TC is "+ws.getRow(i).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("Project"+TCs, ws.getRow(i).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("UserStory"+TCs, ws.getRow(i).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("TC"+TCs, ws.getRow(i).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("Build"+TCs, ws.getRow(i).getCell(4,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("Status"+TCs, ws.getRow(i).getCell(5,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("Attachment"+TCs, ws.getRow(i).getCell(6,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				}
			}
			if(TCs!=0)
				hm.put("TotalTCs", Integer.toString(TCs));
			else{
				System.out.println("No test cases found for PRJ "+PRJ+" and US "+US);
			hm.put("TotalTCs", "0");
			}
			} 
		catch (Exception e) {
			System.out.println("Program stopped due to exception "+e);
		}
	
	}

	public static void Rally_UpdateTestResults() throws Exception{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By prj_testrun=By.xpath("//a[contains(@class,'testRun')]");
		By prj_testrun_text = By.xpath("//span[text()='Test Run']");
		By prj_testresult_window= By.xpath("//span[@aria-label='testCaseResults']");
		By prj_attachment_uploaded=By.xpath("//span[text()='Uploaded']");
		By prj_attachment =By.xpath("//span[text()='Drag or click to add attachments']");
		By prj_testresult_create =By.xpath("//span[text()='Create']");
		By prj_testresult_created=By.xpath("//h3[text()='Test Case Result']");
		
		
		int totalTCs = Integer.parseInt(hm.get("TotalTCs"));
		if(totalTCs==0){
			System.out.println("No Results to be updated for PRJ and US");
			return;
		}
		System.out.println("Total test case results to be updated is "+totalTCs);
		//Click on test run
		wait.until(ExpectedConditions.elementToBeClickable(prj_testrun)).click();
		
		
		
		for(int k = 1;k<=totalTCs;k++){
			//Wait for test run window to open
			wait.until(ExpectedConditions.visibilityOfElementLocated(prj_testrun_text));
			//Find test case
			int count=0;
			String tc="";
			count = driver.findElements(By.xpath("//tr[@class='testrun-testcase-header']")).size();
			System.out.println("No of test cases on first page is "+count);
			for(int i=1;i<=count;i++){
				for(int f=1 ;f<15;f++){
					try {
						tc=driver.findElement(By.xpath("//tr[@class='testrun-testcase-header']["+i+"]/td[1]/a")).getText();
						break;	
					} catch (Exception e) {
						Thread.sleep(1000);
						continue;
					}
				}
				
				System.out.println("Test case name on row "+i+" is "+tc);
				if(tc.contains(hm.get("TC"+k))){
					driver.findElement(By.xpath("//tr[@class='testrun-testcase-header']["+i+"]//img[@class='newTestCaseResult']")).click();
					break;
				}
				if(i==20){
					//go to next page and initialize variables
					try {
						driver.findElement(By.xpath("//a[contains(@class,'sprite-tps-next')]")).click();
						Thread.sleep(1000);
						wait.until(ExpectedConditions.visibilityOfElementLocated(prj_testrun_text));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Test case not found and exception is "+e);
						break;
					}
					count = driver.findElements(By.xpath("//tr[@class='testrun-testcase-header']")).size();
					System.out.println("No of test cases in next page is "+count);
					i=0;
					continue;
				}
			}
		wait.until(ExpectedConditions.visibilityOfElementLocated(prj_testresult_window));
		//For loop used to avoid stale element exception
		for(int d=1;d<=15;d++){
		try {
			WebElement el = driver.findElement(By.xpath("//div[@class='smb-TextInput-renderedText']"));
			Actions actions = new Actions(driver);
			actions.moveToElement(el);
			actions.click();
			actions.sendKeys(hm.get("Build"+k));
			actions.build().perform();
			break;
		} catch (Exception e1) {
			Thread.sleep(1000);
			System.out.println("Exception is "+e1);
			continue;
		}
		}
		Robot robot = new Robot();
		
		//Robot class code to enter value in build
		/*robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		String build = hm.get("Build"+k);
		for (char c1 : build.toCharArray()) {
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c1);
	        robot.keyPress(keyCode);
	        robot.keyRelease(keyCode);
		}*/
		
		
		//For loop used to avoid stale element exception
		for(int i=1 ;i<15;i++){
			try {
				By prj_testresult_status=By.xpath("//li[contains(@aria-label,'"+hm.get("Status"+k)+"')]");
				driver.findElement(By.xpath("//span[@class='smb-Select-text']")).click();
				Thread.sleep(700);
				wait.until(ExpectedConditions.elementToBeClickable(prj_testresult_status)).click();
				Thread.sleep(700);
				break;	
			} catch (Exception e) {
				Thread.sleep(1000);
				continue;
			}
		}
		
		
		String str = hm.get("Attachment"+k);
		System.out.println("attachment Path is "+str);
		//Check attachment path is present in sheet or not
		if(!str.equals("")){
			wait.until(ExpectedConditions.elementToBeClickable(prj_attachment)).click();
		Thread.sleep(1000);
				for (char c : str.toCharArray()) {
				if(c==':'){
					robot.keyPress(KeyEvent.VK_SHIFT);  
		            robot.keyPress(KeyEvent.VK_SEMICOLON);  
		            robot.keyRelease(KeyEvent.VK_SEMICOLON);  
		            robot.keyRelease(KeyEvent.VK_SHIFT);
					 continue;
				}
				if(c=='\\'){
					 robot.keyPress(KeyEvent.VK_BACK_SLASH);
					 robot.keyRelease(KeyEvent.VK_BACK_SLASH);
					 continue;
				}
				if(c==' '){
					 robot.keyPress(KeyEvent.VK_SPACE);
					 robot.keyRelease(KeyEvent.VK_SPACE);
					 continue;
				}
					
		        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
		        robot.keyPress(keyCode);
		        robot.keyRelease(keyCode);
		    }
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			wait.until(ExpectedConditions.visibilityOfElementLocated(prj_attachment_uploaded));
		}
		
			driver.findElement(prj_testresult_create).click();
			//wait.until(ExpectedConditions.invisibilityOfElementLocated(prj_testresult_create));
			//Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(prj_testresult_created));
			Thread.sleep(500);
			driver.findElement(By.xpath("//span[@aria-label='Close detail editor']")).click();
			System.out.println("Test case "+hm.get("TC"+k)+" result updated successfully");
			wait.until(ExpectedConditions.visibilityOfElementLocated(prj_testrun_text));
		}
		
	}

	public static void Rally_ResultsUpdated_Successfully(){
		hm.clear();
		ExtentUtilities.extentFlush();
	}
	
	
	public static void Rally_logout() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By signout_dropdown=By.xpath("//span[@class='icon-chevron-down']");
		By signout = By.xpath("//span[text()='Sign Out']");
		driver.findElement(signout_dropdown).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(signout)).click();
		Thread.sleep(2000);
		driver.close();
	}
}
