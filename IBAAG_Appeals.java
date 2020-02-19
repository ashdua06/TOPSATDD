package com.optum.topsuat.pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hp.lft.sdk.Desktop;
import com.hp.lft.sdk.te.Keys;
import com.hp.lft.sdk.te.Protocol;
import com.hp.lft.sdk.te.Screen;
import com.hp.lft.sdk.te.ScreenDescription;
import com.hp.lft.sdk.te.Window;
import com.hp.lft.sdk.te.WindowDescription;
import com.optum.topsuat.functionlib.Mainframe_GlobalFunctionLib;

public class IBAAG_Appeals {
	static HashMap<String,String> ibaag = new HashMap<String,String>();
	static Window teWindow;
	static Screen teScreen;
	static String[] MHI_CLine=null;
	static WebDriver driver;
	public static void IBAAG_Screenshot(String RegionName, String FilmOfficeNumber, String AdjustingOfficeNumber, String SystemValue,String ICN) throws Exception{
		try {
			teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
			teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
			Tops_UserLogin.UserLogin(RegionName, FilmOfficeNumber, AdjustingOfficeNumber, SystemValue);
			Mainframe_GlobalFunctionLib.inputText("MPC", "Control Line", "RET,I"+ICN+",M");
			Mainframe_GlobalFunctionLib.Transmit();
			fetch_MHIdetails();
			Mainframe_GlobalFunctionLib.inputText("MPC", "Control Line", "CEI,"+MHI_CLine[1]+","+MHI_CLine[2]);
			Mainframe_GlobalFunctionLib.Transmit();
			fetch_CEIdetails();
			Tops_UserLogOff.screenclear();
			Open_IBAAGApp();
		
			
		} catch (Exception e) {
			System.out.println("NDB Screenshot not taken due to exception "+e);
		}
	}
	
	public static void fetch_MHIdetails() throws Exception{
		ibaag.clear();
		String effdate="";
		String[] claim_Data;
		MHI_CLine=Mainframe_GlobalFunctionLib.GetText("MPC", "Control Line").split(",");
		ibaag.put("Policy", MHI_CLine[1]);
		ibaag.put("Patient", MHI_CLine[3].trim());
		ibaag.put("Rel", MHI_CLine[4]);
		claim_Data=Mainframe_GlobalFunctionLib.GetText("ERIS_MHI", "SvcLine1_data").split("\\s+");
		effdate=claim_Data[2].substring(0,2)+"/"+claim_Data[2].substring(2,4)+"/20"+claim_Data[2].substring(4,6);
		ibaag.put("EffDate", effdate);
		
		for(Map.Entry<String,String> m:ibaag.entrySet()){
			System.out.println(m.getKey()+" : "+m.getValue());
		}

	}
	
	public static void fetch_CEIdetails() throws Exception{
		Date MHI_Date;
		Date CEI_StartDate;
		Date CEI_EndDate;
		String CEI_effDate;
		String CEI_canDate;

		
		MHI_Date=new SimpleDateFormat("MM/dd/yyyy").parse(ibaag.get("EffDate"));
		System.out.println("MHI start date is "+MHI_Date);
		
		for(int i=1;i<=4;i++){
			if(Mainframe_GlobalFunctionLib.GetText("CEI","L"+i+" First Name").equals(ibaag.get("Patient")) && 
					Mainframe_GlobalFunctionLib.GetText("CEI","L"+i+" Relation Code").equals(ibaag.get("Rel"))){
			CEI_effDate= Mainframe_GlobalFunctionLib.GetText("CEI","L"+i+" Current Effective Date");
			CEI_effDate = CEI_effDate.substring(0,2)+"/"+CEI_effDate.substring(2,4)+"/20"+CEI_effDate.substring(4,6);
			CEI_canDate= Mainframe_GlobalFunctionLib.GetText("CEI","L"+i+" Current Cancel Date");
			CEI_canDate = CEI_canDate.substring(0,2)+"/"+CEI_canDate.substring(2,4)+"/20"+CEI_canDate.substring(4,6);
			
			CEI_StartDate=new SimpleDateFormat("MM/dd/yyyy").parse(CEI_effDate);
			CEI_EndDate=new SimpleDateFormat("MM/dd/yyyy").parse(CEI_canDate);
			System.out.println("CEI String start date is "+CEI_effDate+" and end date is "+CEI_canDate);
			System.out.println("CEI start date is "+CEI_StartDate+" and end date is "+CEI_EndDate);

			if(MHI_Date.after(CEI_StartDate)&&MHI_Date.before(CEI_EndDate)){
				ibaag.put("setno", Mainframe_GlobalFunctionLib.GetText("CEI", "L"+i+" Current Set2"));
				ibaag.put("ProductNo", Mainframe_GlobalFunctionLib.GetText("CEI", "L"+i+" Current Product"));
				ibaag.put("setEffDate", CEI_effDate);
				ibaag.put("currentplan", Mainframe_GlobalFunctionLib.GetText("CEI", "L"+i+" Current Plan"));
				break;
			}
			
			else {
			CEI_effDate= Mainframe_GlobalFunctionLib.GetText("CEI","L"+i+" Provider Effective Date");
			CEI_effDate = CEI_effDate.substring(0,2)+"/"+CEI_effDate.substring(2,4)+"/20"+CEI_effDate.substring(4,6);
			CEI_canDate= Mainframe_GlobalFunctionLib.GetText("CEI","L"+i+" Provider Cancel Date");
			CEI_canDate = CEI_canDate.substring(0,2)+"/"+CEI_canDate.substring(2,4)+"/20"+CEI_canDate.substring(4,6);
			
			CEI_StartDate=new SimpleDateFormat("MM/dd/yyyy").parse(CEI_effDate);
			CEI_EndDate=new SimpleDateFormat("MM/dd/yyyy").parse(CEI_canDate);
			System.out.println("CEI String start date is "+CEI_effDate+" and end date is "+CEI_canDate);
			System.out.println("CEI start date is "+CEI_StartDate+" and end date is "+CEI_EndDate);			
			if(MHI_Date.after(CEI_StartDate)&&MHI_Date.before(CEI_EndDate)){
				ibaag.put("setno", Mainframe_GlobalFunctionLib.GetText("CEI", "L"+i+" Provider Set2"));
				ibaag.put("ProductNo", Mainframe_GlobalFunctionLib.GetText("CEI", "L"+i+" Provider Product"));
				ibaag.put("setEffDate", CEI_effDate);
				ibaag.put("currentplan", Mainframe_GlobalFunctionLib.GetText("CEI", "L"+i+" Provider Plan"));
				break;
			}
				
			}
				
			break;
			}
			
			if(i==4){
				if(Mainframe_GlobalFunctionLib.GetText("MPC", "Edit/warning").contains("NO MORE DEPENDENTS"))
					break;
				teScreen.sendTEKeys(Keys.PF8);
				i=0;
			}
			
			
		}
		
		for(Map.Entry<String, String> sm:ibaag.entrySet()){
			System.out.println(sm.getKey()+" : "+sm.getValue());
		}
	}
	
	public static void Open_IBAAGApp() throws Exception{
		String href="";
		 DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
	        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	        capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
	        capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
	        capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
	        capabilities.setCapability("ignoreProtectedModeSettings",true);
	        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
	        capabilities.setCapability("requireWindowFocus", true);
	        capabilities.setCapability("enablePersistentHover", false);
	        capabilities.setCapability("disable-popup-blocking", true);
	        String IEpath = System.getProperty("user.dir") + "\\Drivers\\IEDriverServer.exe";


	        System.setProperty("webdriver.ie.driver",IEpath);


	        WebDriver driver = new InternetExplorerDriver(capabilities);

	        driver.manage().deleteAllCookies();
	    
	        driver.navigate().to("http://ibaag.uhc.com/sum_srch.asp");
	        driver.manage().window().maximize();
	        
	        driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
	        WebDriverWait wait = new WebDriverWait(driver, 10);
			By policy = By.xpath("//input[@name='policy_number']");
			By set = By.xpath("//input[@name='set_number']");
			By effDate = By.xpath("//input[@name='eff_date']");
			By TOPS_Benefits = By.id("submit2");
			System.out.println("Searching IBAAG APP");
			wait.until(ExpectedConditions.visibilityOfElementLocated(policy)).sendKeys(ibaag.get("Policy"));
			driver.findElement(set).sendKeys(ibaag.get("setno"));
			driver.findElement(effDate).sendKeys(ibaag.get("setEffDate"));
			driver.findElement(TOPS_Benefits).click();
			
			//Click on specific row now
			By totalrows = By.xpath("//*[contains(text(),'Product Type')]//ancestor::tbody[1]/tr");
			List<WebElement> wb = driver.findElements(totalrows);
			System.out.println("Total rows in list are "+wb.size());
			
			
			for(int r=3;r<=wb.size();r++){
				JavascriptExecutor js = (JavascriptExecutor)driver;
				By prodCheck = By.xpath("//*[contains(text(),'Product Type')]//ancestor::tbody[1]/tr["+r+"]/td[2]//b");
				By setlink = By.xpath("//*[contains(text(),'Product Type')]//ancestor::tbody[1]/tr["+r+"]/td[1]//a");
				String prod= driver.findElement(prodCheck).getText().trim();
				
				System.out.println("Toolbar link is "+href);
				
				if(prod.equals("PS1")){
				href = driver.findElement(setlink).getAttribute("href");
				href="http://ibaag.uhc.com/"+href.substring(href.indexOf('\'')+1, href.indexOf(',')-1);
				System.out.println("Product no matched is "+prod);
				
				js.executeScript("arguments[0].click();", driver.findElement(setlink));
				break;
				}
				r=r+1;
			}
			
			Thread.sleep(3000);
			//driver.navigate().to(href);
			System.out.println("Current url is "+driver.getCurrentUrl());
			
			saveIBAAGwebpage(href);
			driver.quit();
	}
	
	public static void saveIBAAGwebpage(String webpage) throws Exception{
		String str="C:\\Users\\hdua\\Desktop\\Copy_Screenshot_Here\\IBAAG";
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_S);
		Thread.sleep(6000);
		
		//Give Location 
		
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
			
			if(c=='_'){
				 robot.keyPress(KeyEvent.VK_SHIFT);
				 robot.keyPress(KeyEvent.VK_MINUS);
				 robot.keyRelease(KeyEvent.VK_SHIFT);
				 robot.keyRelease(KeyEvent.VK_MINUS);
				 continue;
			}
				
	        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
	        robot.keyPress(keyCode);
	        robot.keyRelease(keyCode);
	    }
		
		
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(6000);
	}
	
	
}
