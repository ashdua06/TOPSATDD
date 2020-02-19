package com.optum.topsuat.pages;

import java.io.File;
import java.net.URI;

import com.hp.lft.sdk.Desktop;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.SDK;
import com.hp.lft.sdk.te.Field;
import com.hp.lft.sdk.te.FieldDescription;
import com.hp.lft.sdk.te.Keys;
import com.hp.lft.sdk.te.Protocol;
import com.hp.lft.sdk.te.Screen;
import com.hp.lft.sdk.te.ScreenDescription;
import com.hp.lft.sdk.te.Window;
import com.hp.lft.sdk.te.WindowDescription;
import com.optum.topsuat.functionlib.Mainframe_GlobalFunctionLib;
import com.optum.topsuat.reporting.ExtentUtilities;
import com.optum.topsuat.utils.AutomationCore;
import com.optum.topsuat.utils.MainframeOR;
import com.optum.topsuat.utils.TestNGHelper;



public class Tops_UserLogin   {
	
	public static void UserLogin(String RegionName, String FilmOfficeNumber, String AdjustingOfficeNumber, String SystemValue) throws Exception {
		AutomationCore core = new AutomationCore();
		ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
		config.setServerAddress(new URI("ws://localhost:5095"));
		SDK.init(config);
	    Thread.sleep(2000);
	    //System.out.println("Initialization Complete");
	    //File file = new File("C:\\ProgramData\\IBM\\Personal Communications\\TN3270.WS");
	    //java.awt.Desktop.getDesktop().open(file);
    	//Thread.sleep(10000);
	    MainframeOR test=new MainframeOR();
		Window teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
		Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
		Field field = teScreen.describe(Field.class, new FieldDescription.Builder().build());
		boolean regionnameexist = teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).exists();
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).setText(RegionName);
		TestNGHelper.assertEqual(true,regionnameexist , "yes","TE Login Window displayed successfully");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(1000);
    	teScreen.setCursorPosition(1, 1);
    	teScreen.setText("proi");
    	teScreen.sendTEKeys(Keys.ENTER);
    	Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(1,2).build()).setText(core.Read("Username"));
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(6,19).build()).setText(core.Read("TOPSpassword"));
		Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(6,46).build()).setText(FilmOfficeNumber);
		Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(8,24).build()).setText(AdjustingOfficeNumber);
		Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(8,41).build()).setText(SystemValue);
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(2000);
		Mainframe_GlobalFunctionLib.validateText("UserLogin", "SIGN ON COMPLETE", "W018ADJ SIGN ON COMPLETE,", "User Login is successful");
		//Mainframe_GlobalFunctionLib.validateContainsText("UserLogin", "SIGN ON COMPLETE", "W018ADJ SIGN ON COMPLETE,", "User Login is successful");
	}
	
	
	
	public static void UserLogin_new(String RegionName, String FilmOfficeNumber, String AdjustingOfficeNumber, String SystemValue) throws Exception {
		AutomationCore core = new AutomationCore();
		ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
		config.setServerAddress(new URI("ws://localhost:5095"));
		SDK.init(config);
	    Thread.sleep(2000);
	    //System.out.println("Initialization Complete");
	    //File file = new File("C:\\ProgramData\\IBM\\Personal Communications\\TN3270.WS");
	    //java.awt.Desktop.getDesktop().open(file);
    	//Thread.sleep(10000);
	    MainframeOR test=new MainframeOR();
		Window teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
		Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
		Field field = teScreen.describe(Field.class, new FieldDescription.Builder().build());
		
		System.out.println("Values in Config file in login function before updation"+core.Read("regionName")+" "+core.Read("filmOffc")+" "+core.Read("adjOffc"));
		
		if(RegionName.equals(core.Read("regionName"))){
			
			
			if(FilmOfficeNumber.equals(core.Read("filmOffc")) && AdjustingOfficeNumber.equals(core.Read("adjOffc"))){
				Navigation.PressClear();
				Navigation.PressClear();
			}
			else{
				Navigation.PressClear();
				Navigation.PressClear();
				teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(1,2).build()).setText(core.Read("Username"));
				teScreen.sendTEKeys(Keys.ENTER);
				Thread.sleep(1000);
				teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(6,19).build()).setText(core.Read("TopsPassword"));
				Thread.sleep(1000);
				teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(6,46).build()).setText(FilmOfficeNumber);
				Thread.sleep(1000);
				teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(8,24).build()).setText(AdjustingOfficeNumber);
				Thread.sleep(1000);
				teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(8,41).build()).setText(SystemValue);
				teScreen.sendTEKeys(Keys.ENTER);
				Thread.sleep(2000);
				Mainframe_GlobalFunctionLib.validateText("UserLogin", "SIGN ON COMPLETE", "W018ADJ SIGN ON COMPLETE,", "User Login is successful");
				//Mainframe_GlobalFunctionLib.validateContainsText("UserLogin", "SIGN ON COMPLETE", "W018ADJ SIGN ON COMPLETE,", "User Login is successful");
			}
		}
		
		else{
		//logoff screen first
			System.out.println("Logging off screen due to different region");
				try {
					System.out.println("value is "+Mainframe_GlobalFunctionLib.GetText("Logoff_Screen", "LOGOFF"));
					if(Mainframe_GlobalFunctionLib.GetText("Logoff_Screen", "LOGOFF").contains("UHC0010")){
						System.out.println("System logged out successfully without exception");}
					else{
					Tops_UserLogOff.screenclear();
					ExtentUtilities.extentFlush();
					}
				} catch (Exception e) {
					System.out.println("System logged out successfully with some exception in catch block and exception is "+e);
					Tops_UserLogOff.screenclear();
					ExtentUtilities.extentFlush();
				}	
			
		//login again
		boolean regionnameexist = teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).exists();
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).setText(RegionName);
		TestNGHelper.assertEqual(true,regionnameexist , "yes","TE Login Window displayed successfully");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(1000);
    	teScreen.setCursorPosition(1, 1);
    	teScreen.setText("proi");
    	teScreen.sendTEKeys(Keys.ENTER);
    	Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(1,2).build()).setText(core.Read("Username"));
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(6,19).build()).setText(core.Read("TopsPassword"));
		Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(6,46).build()).setText(FilmOfficeNumber);
		Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(8,24).build()).setText(AdjustingOfficeNumber);
		Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(8,41).build()).setText(SystemValue);
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(2000);
		Mainframe_GlobalFunctionLib.validateText("UserLogin", "SIGN ON COMPLETE", "W018ADJ SIGN ON COMPLETE,", "User Login is successful");
		//Mainframe_GlobalFunctionLib.validateContainsText("UserLogin", "SIGN ON COMPLETE", "W018ADJ SIGN ON COMPLETE,", "User Login is successful");
		}
		
	System.out.println("Updating Region , Offc and adj offc in config file to "+RegionName+" "+FilmOfficeNumber+" "+AdjustingOfficeNumber);
	core.Update("regionName",RegionName);
	core.Update("filmOffc",FilmOfficeNumber);
	core.Update("adjOffc",AdjustingOfficeNumber);
	System.out.println("Values in Config file in login function after updation "+core.Read("regionName")+" "+core.Read("filmOffc")+" "+core.Read("adjOffc"));
	
	}
	
	public static void DB2_UserLogin(String Environment, String UserID, String ClaimID) throws Exception {
		AutomationCore core = new AutomationCore();
		ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
		config.setServerAddress(new URI("ws://localhost:5095"));
		SDK.init(config);
	    Thread.sleep(2000);
	    System.out.println("Initialization Complete");
	  // File file = new File("C:\\ProgramData\\IBM\\Personal Communications\\TN3270.WS");
	   //java.awt.Desktop.getDesktop().open(file);
    	Thread.sleep(10000);
	    MainframeOR test=new MainframeOR();
		Window teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
		Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
		Field field = teScreen.describe(Field.class, new FieldDescription.Builder().build());
		boolean regionnameexist = teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).exists();
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).setText(Environment);
		TestNGHelper.assertEqual(true,regionnameexist , "yes","TE Login Window displayed successfully");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(2000);
		teScreen.setCursorPosition(2,1);
		teScreen.setText(UserID);
		//teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(2,1).build()).setText(UserID);
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(2000);
		teScreen.setCursorPosition(4,1);
		teScreen.setText("Opt@3opt");
		//teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(4,1).build()).setText("Opt@3opt");
		Thread.sleep(1000);
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(1000);
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(1000);
		teScreen.setCursorPosition(3,1);
		teScreen.setText("DB2");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(1000);
		//teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).setText("DB2");
		Thread.sleep(1000);
		teScreen.setCursorPosition(24,14);
		Thread.sleep(1000);
		teScreen.setText("TSO DB2");
		teScreen.sendTEKeys(Keys.ENTER);
		//teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(24,14).build()).setText("TSO DB2");
		Thread.sleep(1000);
		teScreen.sendTEKeys(Keys.ENTER);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(4,22).build()).setText("5");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(2000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(4,22).build()).setText("1");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(2000);
		teScreen.setCursorPosition(2,15);
		Thread.sleep(1000);
		//teScreen.setText("COL");
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(2,15).build()).setText("COL");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(2000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(12,55).build()).setText("='"+ClaimID+"'");
		teScreen.sendTEKeys(Keys.PF6);
		Thread.sleep(1000);
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(4,15).build()).setText("L "+"UB92_NOT_COV_AMT"+"");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(2000);
		Mainframe_GlobalFunctionLib.validateText("UserLogin", "SIGN ON COMPLETE", "W018ADJ SIGN ON COMPLETE,", "User Login is successful");
	}

}
