package com.optum.topsuat.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ICUE_Appeals {
	static WebDriver driver;
	
	public static void ICUE_Screenshot(String Username,String password,String CaseID){
		
		try {
			/*DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
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


			WebDriver driver = new InternetExplorerDriver(capabilities);*/
			
			//Chrome Driver
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
		    options.setExperimentalOption("useAutomationExtension", false);
		    driver = new ChromeDriver(options);
			

			driver.manage().deleteAllCookies();
   
			driver.navigate().to("https://icue.uhc.com/icue/index.jsp");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
			ICUE_login(Username,password);
			ICUE_HealthServiceCaseSearch(CaseID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ICUE Screenshots not captured due to exception "+e);
		}
	}
	
	public static void ICUE_login(String Username,String password){
		By userid = By.id("userID");
		By pswd = By.id("password");
		By signon = By.xpath("//input[@value='Sign On']");
		
		driver.findElement(userid).sendKeys(Username);
		driver.findElement(pswd).sendKeys(password);
		driver.findElement(signon).click();
		
	}
	
	public static void ICUE_HealthServiceCaseSearch(String CaseID) throws Exception{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By HealthServiceCaseLink = By.xpath("//a[text()='Health Service Case']");
		By VendorCaseID = By.id("searchID");
		By SearchBtn = By.xpath("//button[text()='Search']");
		By HSCHistory= By.xpath("//span[text()='HSC History']");
		By innerwindow = By.id("ext-gen11");
		
		wait.until(ExpectedConditions.elementToBeClickable(HealthServiceCaseLink)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(VendorCaseID)).sendKeys(CaseID);
		driver.findElement(SearchBtn).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(HSCHistory));
		wait.until(ExpectedConditions.visibilityOfElementLocated(innerwindow));
		Thread.sleep(1000);
		ICUE_screenshotsCapture();
      //  js.executeScript("window.scrollBy(0,200)");  // this is to sroll main window
    //  js.executeScript("window.scrollBy(0,document.body.scrollHeight)");  // this is to sroll main window to bottom
     //  js.executeScript("arguments[0].scrollIntoView();",Element );// this is to sroll main window till the visibility of element
        
		Thread.sleep(2000);
		driver.quit();
	}
	
	public static void ICUE_screenshotsCapture() throws Exception{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement wl = driver.findElement(By.id("ext-gen13"));
		WebElement btn = driver.findElement(By.id("performAction"));
		Point btnpoint;
		int sc;
		
		
		Screenshot.driverScreenshot(driver,"ICUE1");
		//Scroll main window to bottom
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)"); 
		Thread.sleep(700);
		Screenshot.driverScreenshot(driver,"ICUE2");
		
		for(sc=1;sc<=40;sc++){
		js.executeScript("arguments[0].scrollTop = arguments[1];",wl, 380*sc);
		System.out.println("Location of Button is : "+btn.getLocation());
		Thread.sleep(700);
		Screenshot.driverScreenshot(driver,"ICUE"+(sc+2));
		System.out.println("Screenshot taken ICUE"+(sc+2));
		btnpoint = btn.getLocation();
		//Break when reaches at the bottom of window
		if(btnpoint.getY()>900 && btnpoint.getY()<1000)
			break;
		}
		
		
	}
	
	
	
}
