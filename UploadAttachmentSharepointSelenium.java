package com.optum.topsuat.pages;


import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UploadAttachmentSharepointSelenium {
	static WebDriver driver;

public static void uploadScreenshotSharepoint(String url,String filePath, String folderpath,String Title) throws Exception{

System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
ChromeOptions options = new ChromeOptions();
options.setExperimentalOption("useAutomationExtension", false);
driver = new ChromeDriver(options);
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

//------identify objects--------------------------------------
WebDriverWait wait =new WebDriverWait(driver, 20);
//New document upload on sharepoint
By newdocument = By.xpath("//span[text()='new document']");
//IFrame on upload screen
By uploadIframe = By.xpath("//iframe[@class='ms-dlgFrame']");
//upload file button
By uploadfile = By.xpath("//input[@title='Choose a file']");
//Destination folder text box
By destFolder = By.xpath("//input[@fieldname='RoutingTargetPath']");
//Button "OK" to upload attachment
By okupload = By.xpath("//input[@value='OK']");
//Identify title of the attachment in sharepoint
By attchmntTitle = By.xpath("//input[@title='Title']");
//Identify name of the attachment in sharepoint
By attchmntName = By.xpath("//input[@title='Name Required Field']");
//Save attachment button
By attchmntSave = By.xpath("//td/table[@class='ms-formtoolbar']//input[@value='Save']");

//Open URL
driver.get(url+folderpath);

//Click on new document upload on sharepoint
wait.until(ExpectedConditions.visibilityOfElementLocated(newdocument)).click();

//Switch to frame
driver.switchTo().frame(driver.findElement(uploadIframe));

//Click on upload file
wait.until(ExpectedConditions.visibilityOfElementLocated(uploadfile)).click();

//Upload file using robot class in below function
upload_attachment(filePath);
Thread.sleep(1000);

//Give Destination folder
wait.until(ExpectedConditions.visibilityOfElementLocated(destFolder)).clear();
driver.findElement(destFolder).sendKeys(folderpath);

//Click on "OK" to upload attachment
wait.until(ExpectedConditions.visibilityOfElementLocated(okupload)).click();

//Give title to the attachment
wait.until(ExpectedConditions.visibilityOfElementLocated(attchmntTitle)).sendKeys(Title);

//Saving name of the attachment in variable and click on OK
String NameAttachment = driver.findElement(attchmntName).getAttribute("value");
driver.findElement(attchmntSave).click();

//Switch to default content
driver.switchTo().defaultContent();
System.out.println("Name of the attachment is "+NameAttachment+".");

//Verify if the attachment has been uploaded successfully or not
By verifyAttachmentUploaded = By.xpath("//a[text()='"+NameAttachment+"']");
if(wait.until(ExpectedConditions.visibilityOfElementLocated(verifyAttachmentUploaded)) != null){
	System.out.println(NameAttachment+" Attachment uploaded successfully");
}
else
	System.out.println("Attachment not uploaded");

driver.close();
driver.quit();
	}
	
	public static void upload_attachment(String path) throws Exception{
		if(!path.equals("")){
			try {
				Robot robot = new Robot();
Thread.sleep(1000);
					for (char c : path.toCharArray()) {
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
			} catch (Exception e) {
				System.out.println("Attachment not uploaded");
			}
		}
	}
}
