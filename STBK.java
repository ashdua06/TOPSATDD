package com.optum.topsuat.pages;

import java.io.FileInputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
import com.optum.topsuat.utils.AutomationCore;
import com.optum.topsuat.utils.MainframeOR;
import com.optum.topsuat.utils.TestNGHelper;

public class STBK {

	public static void STBKtraining_login() throws Exception{
		
		AutomationCore core = new AutomationCore();
		ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
		config.setServerAddress(new URI("ws://localhost:5095"));
		SDK.init(config);
	    Thread.sleep(2000);
	    MainframeOR test=new MainframeOR();
		Window teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
		Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
		Field field = teScreen.describe(Field.class, new FieldDescription.Builder().build());
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).setText("fetrain");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(1000);
    	teScreen.setCursorPosition(1, 1);
    	teScreen.setText("tofu");
    	teScreen.sendTEKeys(Keys.ENTER);
    	Thread.sleep(1000);
    	Mainframe_GlobalFunctionLib.inputText("STBK_Login", "ID", core.Read("STBK_Training_ID"));
    	Mainframe_GlobalFunctionLib.inputText("STBK_Login", "PASSWORD", core.Read("STBK_Training_Password"));
    	teScreen.sendTEKeys(Keys.ENTER);
    	Thread.sleep(1000);
    	//Created SIGN ON COMPLETE Object in STBK
		Mainframe_GlobalFunctionLib.validateContainsText("STBK_TRAINING", "SIGN ON COMPLETE", "SIGNED ON TO TRAINING", "User Login is successful");	
		
	}
	
	public static void STBKprod_login() throws Exception{
		AutomationCore core = new AutomationCore();
		ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
		config.setServerAddress(new URI("ws://localhost:5095"));
		SDK.init(config);
	    Thread.sleep(2000);
	    MainframeOR test=new MainframeOR();
		Window teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
		Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
		Field field = teScreen.describe(Field.class, new FieldDescription.Builder().build());
		teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).setText("topsfe");
		teScreen.sendTEKeys(Keys.ENTER);
		Thread.sleep(1000);
		Navigation.PressClear();
		Thread.sleep(1000);
    	teScreen.setCursorPosition(1, 1);
    	teScreen.setText("tofu");
    	teScreen.sendTEKeys(Keys.ENTER);
    	Thread.sleep(1000);
    	Mainframe_GlobalFunctionLib.inputText("STBK_Login", "ID", core.Read("STBK_Prod_id"));
    	Mainframe_GlobalFunctionLib.inputText("STBK_Login", "PASSWORD", core.Read("STBK_prod_pwd"));
    	teScreen.sendTEKeys(Keys.ENTER);
    	Thread.sleep(1000);
    	//Created SIGN ON COMPLETE Object in STBK
		Mainframe_GlobalFunctionLib.validateContainsText("STBK_TRAINING", "SIGN ON COMPLETE", "SIGNED ON TO PRODUCTION", "User Login is successful");	
	}
	
	public static void STBK_mainMenu(String ClaimType,String FLN, String ALTID, String Provider, String patient, String relation, String date, String Region) throws Exception{
		//CHECK IF USER IS ON THE MAIN MENU
		for(int i=1;i<=7;i++){
			Navigation.PressClear();
			Navigation.PressClear();
			if(Mainframe_GlobalFunctionLib.GetText("STBK_HOME", "MAIN MENU").equals("MAIN MENU"))
				break;
		}
		
		switch (ClaimType)
		{
		case "Professional":
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "ENTER SELECTION", "H");
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "START FLN", FLN);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "END FLN", FLN);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DT", date);
			
			if(Region.equals("Prod"))
				Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DCC", "869");
			else if(Region.equals("Test"))
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DCC", "828");
			
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "FLN", FLN.substring(5));
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "EE", ALTID);
			//Changed Ptin object in object repository
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "Ptin", Provider.substring(1, 10));
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "PAT", patient);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "RL", relation);
			Mainframe_GlobalFunctionLib.Transmit();
			break;
			
		case "HCFA":
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "ENTER SELECTION", "H");
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "START FLN", FLN);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "END FLN", FLN);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DT", date);
			
			if(Region.equals("Prod"))
				Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DCC", "869");
			else if(Region.equals("Test"))
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DCC", "828");
			
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "FLN", FLN.substring(5));
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "EE", ALTID);
			//Changed Ptin object in object repository
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "Ptin", Provider.substring(1, 10));
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "PAT", patient);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "RL", relation);
			Mainframe_GlobalFunctionLib.Transmit();
			break;
			
		case "UB92":
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "ENTER SELECTION", "U");
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "START FLN", FLN);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "END FLN", FLN);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DT", date);
			
			if(Region.equals("Prod"))
				Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DCC", "869");
			else if(Region.equals("Test"))
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DCC", "828");
			
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "FLN", FLN.substring(5));
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "EE", ALTID);
			//Changed Ptin object in object repository
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "Ptin", Provider.substring(1, 10));
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "PAT", patient);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "RL", relation);
			Mainframe_GlobalFunctionLib.Transmit();
			break;
			
		case "RX":
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "ENTER SELECTION", "X");
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "START FLN", FLN);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "END FLN", FLN);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DT", date);
			
			if(Region.equals("Prod"))
				Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DCC", "869");
			else if(Region.equals("Test"))
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "DCC", "828");
			
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "FLN", FLN.substring(5));
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "EE", ALTID);
			//Changed Ptin object in object repository
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "Ptin", Provider.substring(1, 10));
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "PAT", patient);
			Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "RL", relation);
			Mainframe_GlobalFunctionLib.Transmit();
			break;
			
		}
		
		
	}
	
	
	public static void employee_policy_pick(String MRI) throws Exception{
		try {
			String emp_plcy_screen = Mainframe_GlobalFunctionLib.GetText("STBK_Policy_Select", "EMPLOYEE(protected)");
			String Policy = MRI.substring(4, 10);
			String SSN = MRI.substring(11);
			
			
			System.out.println("SSN and Policy from fearure file is "+SSN+" and "+Policy);
			if(emp_plcy_screen.equals("EMPLOYEE POLICY PICK")){
				for(int i=1;i<=6;i++){
					String ssn = Mainframe_GlobalFunctionLib.GetText("STBK_Policy_Select", "frstRow"+i).trim();
					String plcy = Mainframe_GlobalFunctionLib.GetText("STBK_Policy_Select", "policy"+i);
					System.out.println("First row on i value "+i+" is"+ssn);
					System.out.println("policy on i value "+i+" is"+plcy);
					
					if(ssn.contains(SSN)&&plcy.equals(Policy))
					{
						System.out.println("inside equal");
						Mainframe_GlobalFunctionLib.inputText("STBK_Policy_Select", "SELECT"+i, "s");
						Mainframe_GlobalFunctionLib.Transmit();
						break;
					}
					
					if(i==6){
						i=0;
						Navigation.PressF8();
						Thread.sleep(1000);
					}
						
				}
				
			}
		} catch (Exception e) {
			System.out.println("Employee Policy pick screen not displayed");
		}
	}
	
	public static void provider_pick(String Provider) throws Exception{
		try {
			String provider_screen = Mainframe_GlobalFunctionLib.GetText("STBK_ProviderScreen", "TIN(protected)");
			String suffix = Provider.substring(10);
			if(provider_screen.equals("TIN")){
				Mainframe_GlobalFunctionLib.inputText("STBK_ProviderScreen", "SUF", suffix);
				Mainframe_GlobalFunctionLib.inputText("STBK_ProviderScreen", "E", "e");
				Mainframe_GlobalFunctionLib.Transmit();
			}
		} catch (Exception e) {
			System.out.println("Provider screen not populated");
		}
	}
	
	
	public static void patient_info(String FLN,String ClaimType) throws Exception{
		
		switch (ClaimType){
		
		case "Professional":
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "FLN",FLN.substring(5));
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "BILLING NME","abc");
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUBMITTEDMEMBERID",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "SSN"));
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB PAT FN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_FST"));
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB PAT LN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_LST"));
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUBINSFN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_FST"));
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUBINSLN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_LST"));
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "PAT MN","na");
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB PAT SFX","na");
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB INS SFX","na");
		Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "INS MN","na");
		Mainframe_GlobalFunctionLib.Transmit();
		Thread.sleep(1000);
		break;
		
		case "HCFA":
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "FLN",FLN.substring(5));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUBMITTEDMEMBERID",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "SSN"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB PAT FN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_FST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB PAT LN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_LST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUBINSFN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_FST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUBINSLN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_LST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "PAT MN","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB PAT SFX","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB INS SFX","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "INS MN","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SIG FST", "na");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SIG LST", "na");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "DEGREE", "AS");
			Mainframe_GlobalFunctionLib.Transmit();
			Thread.sleep(1000);
			break;
			
		case "UB92":
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "FLN",FLN.substring(5));
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "SUBM MEMBER ID",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "SSN"));
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "SUB PAT FN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_FST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "SUB PAT LN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_LST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "SUB INS FN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_FST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "SUB INS LN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_LST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "PAT MN","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "SUB PAT SFX","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "SUB INS SFX","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_UB92_1SCREEN", "INS MN","na");
			Mainframe_GlobalFunctionLib.Transmit();
			Thread.sleep(1000);
			break;
		
		case "RX":
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "FLN",FLN.substring(5));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUBMITTEDMEMBERID",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "SSN"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB PAT FN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_FST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB PAT LN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_LST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUBINSFN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_FST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUBINSLN",Mainframe_GlobalFunctionLib.GetText("STBK_HCFA_1SCREEN", "INS_LST"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "PAT MN","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB PAT SFX","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "SUB INS SFX","na");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_1SCREEN", "INS MN","na");
			Mainframe_GlobalFunctionLib.Transmit();
			Thread.sleep(1000);
			break;
			
		}
	}
	
	
	public static String Addzero_charge(int newdollaramnt,int maxlen){
		String newchargeamnt=Integer.toString(newdollaramnt);
		int len = newchargeamnt.length();
		for(int i =1;i<=maxlen-len;i++){
			newchargeamnt=0+newchargeamnt;
		}
		return newchargeamnt;
	}
	
	
	public static void enter_claimDetails(String ClaimType,String FLN,String MRI,String Provider,String patient,String relation) throws Exception{
		try {
			HashMap<String,String> hm = new HashMap<String,String>();
			hm = claimData_from_excel(FLN,ClaimType);
			int totallines = Integer.parseInt(hm.get("TotalLines"));
			int totalcharge=0;
			
			switch(ClaimType){
			
			case "UB92":
				String[] diagcode = hm.get("Diag").split(",");
				for(int d=0;d<diagcode.length;d++){
					Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "DX"+(d+1), diagcode[d]);
				}
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "BILL TYPE", hm.get("Billtype"));
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "FLN", FLN.substring(5));
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "EE", MRI.substring(12));
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "P", Provider.substring(1, 10));
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "PAT", patient);
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "RL", relation);
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "ICD", "0");
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "FROM", hm.get("From"));
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "THROUGH", hm.get("To"));
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "ASSIGNMENT", "Y");
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "MRN", "na");
				
				for(int i=1;i<=totallines;i++){
					Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "LN"+i, hm.get("LN"+i));
					Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "REV"+i, hm.get("Rev"+i));
					Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "HCPC"+i, hm.get("HCPC"+i));
					Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "DATE"+i, hm.get("Date"+i));
					Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "UNIT"+i, Addzero_charge(Integer.parseInt(hm.get("Unit"+i)),4));
					Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "CHARGE"+i, Addzero_charge(Integer.parseInt(hm.get("CHG"+i)),9));
					totalcharge=totalcharge+Integer.parseInt(hm.get("CHG"+i));
				}
				
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "TOT CHG", Addzero_charge(totalcharge,9));
				Mainframe_GlobalFunctionLib.Transmit();
				break;
				
				
			case "RX":
				
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "FLN", FLN.substring(5));
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "EE", MRI.substring(12));
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "P", Provider.substring(1, 10));
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "PAT", patient);
				Mainframe_GlobalFunctionLib.inputText("STBK_UB92_2SCREEN", "RL", relation);
				Mainframe_GlobalFunctionLib.inputText("STBK_RX2_SCREEN", "YR", hm.get("Year1"));
				
				
				for(int i=1;i<=totallines;i++){
					Mainframe_GlobalFunctionLib.inputText("STBK_RX2_SCREEN", "DATE"+i, hm.get("Date"+i));
					Mainframe_GlobalFunctionLib.inputText("STBK_RX2_SCREEN", "CHG"+i, Addzero_charge(Integer.parseInt(hm.get("CHG"+i)),7));
					Mainframe_GlobalFunctionLib.inputText("STBK_RX2_SCREEN", "RX"+i, hm.get("RX"+i));
					Mainframe_GlobalFunctionLib.inputText("STBK_RX2_SCREEN", "DESC"+i, hm.get("DESC"+i));
					totalcharge=totalcharge+Integer.parseInt(hm.get("CHG"+i));
				}
				
				Mainframe_GlobalFunctionLib.inputText("STBK_RX2_SCREEN", "TOT_CHG", Addzero_charge(totalcharge,8));
				Mainframe_GlobalFunctionLib.inputText("STBK_RX2_SCREEN", "ASSIGNMENT", "1");
				Mainframe_GlobalFunctionLib.inputText("STBK_RX2_SCREEN", "ATTACHMENTS", "RP");
				Mainframe_GlobalFunctionLib.Transmit();
				break;
				
			default:
			String[] diagcode1 = hm.get("Diag").split(",");
			for(int d=0;d<diagcode1.length;d++){
				Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "DiagCode"+(d+1), diagcode1[d]);
			}
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "YR", hm.get("YEAR1"));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "FLN", FLN.substring(5));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "EE", MRI.substring(12));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "P", Provider.substring(1, 10));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "PAT", patient);
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "RL", relation);
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "NXT", "M");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "ASGMT", "Y");
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "ICD", "0");
			
			
			for(int i=1;i<=totallines;i++){
				Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "LN"+i, hm.get("LN"+i));
				Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "FROM"+i, hm.get("FROM"+i));
				Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "TO"+i, hm.get("TO"+i));
				Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "PS"+i, hm.get("PS"+i));
				Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "SVC SM"+i, hm.get("SVC"+i));
				Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "CHG"+i, Addzero_charge(Integer.parseInt(hm.get("CHG"+i)),7));
				Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "NBR"+i, hm.get("NBR"+i));
				Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "T"+i, "X");
				totalcharge=totalcharge+Integer.parseInt(hm.get("CHG"+i));
			}
			
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "TOT CHG", Addzero_charge(totalcharge,8));
			Mainframe_GlobalFunctionLib.inputText("STBK_HCFA_2SCREEN", "PT", patient);
			Mainframe_GlobalFunctionLib.Transmit();
			break;
			
			}
		} catch (Exception e) {
			System.out.println("Line details are not found in excel sheet and exception is "+e);
		}
	}
	
	
	@SuppressWarnings("resource")
	public static HashMap<String,String> claimData_from_excel(String FLN,String ClaimType) throws Exception{
		HashMap<String,String> hm = new HashMap<String,String>();
		try {
			XSSFSheet ws;
			XSSFWorkbook wb;
			XSSFCell cell;
			XSSFRow row;
			String celldata = null;
			
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\TestData\\STBK_claim_details.xlsx");
			
			switch(ClaimType){
			
			case "UB92":
			System.out.println("UB92 sheet opened");
			wb = new XSSFWorkbook(fis);
			ws = wb.getSheet("FacClaimDetails");
			int totalrows = ws.getLastRowNum();
			System.out.println("total rows are "+totalrows);
			System.out.println("file loaded");
			int i=1;
			while(i<=totalrows){
				System.out.println("value of line at i "+i+" is "+ws.getRow(i).getCell(6,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				System.out.println("Value of FLN is "+ws.getRow(i).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
				if(ws.getRow(i).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals(FLN)){
					int j;
					hm.put("Diag", ws.getRow(i).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					System.out.println("Diag added "+ws.getRow(i).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					hm.put("From", ws.getRow(i).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					System.out.println("From added "+ws.getRow(i).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					hm.put("To", ws.getRow(i).getCell(4,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					System.out.println("To added "+ws.getRow(i).getCell(4,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					hm.put("Billtype", ws.getRow(i).getCell(5,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					System.out.println("billtype added "+ws.getRow(i).getCell(5,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					for(j=1;j<=10;j++){
						
						System.out.println("Value of FLN"+j+" is "+ws.getRow(i+j-1).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
						System.out.println("Diag added"+j+" "+ws.getRow(i+j-1).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						hm.put("LN"+j, ws.getRow(i+j-1).getCell(6,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
						System.out.println("LN"+j+" "+ws.getRow(i+j-1).getCell(6,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
						
						hm.put("Rev"+j, ws.getRow(i+j-1).getCell(7,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						System.out.println("Rev"+j+" "+ws.getRow(i+j-1).getCell(7,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						
						hm.put("HCPC"+j, ws.getRow(i+j-1).getCell(8,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						System.out.println("HCPC"+j+" "+ws.getRow(i+j-1).getCell(8,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						
						hm.put("Date"+j, ws.getRow(i+j-1).getCell(9,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						System.out.println("Date"+j+" "+ws.getRow(i+j-1).getCell(9,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						
						hm.put("Unit"+j, ws.getRow(i+j-1).getCell(10,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						System.out.println("Unit"+j+" "+ws.getRow(i+j-1).getCell(10,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						
						hm.put("CHG"+j, ws.getRow(i+j-1).getCell(11,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						System.out.println("CHG"+j+" "+ws.getRow(i+j-1).getCell(11,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
			
						System.out.println("new row is "+(i+j));
						
						if((i+j)>totalrows)
							break;
						if(ws.getRow(i+j).getCell(6,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().equals("001"))
							break;
						
					}
					hm.put("TotalLines", Integer.toString(j));
					break;
				}
				i++;
				
			}
			break;
			
			
			case "RX":
				System.out.println("RX sheet opened");
				wb = new XSSFWorkbook(fis);
				ws = wb.getSheet("RXclaimDetails");
				int totalrows1 = ws.getLastRowNum();
				System.out.println("total rows are "+totalrows1);
				System.out.println("file loaded");
				int i1=1;
				while(i1<=totalrows1){
					System.out.println("value of Line at i "+i1+" is "+ws.getRow(i1).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
					System.out.println("Value of FLN is "+ws.getRow(i1).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					if(ws.getRow(i1).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals(FLN)){
						int j;
						for(j=1;j<=15;j++){
							
							System.out.println("Value of FLN"+j+" is "+ws.getRow(i1+j-1).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
							hm.put("RX"+j, ws.getRow(i1+j-1).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
							System.out.println("RX"+j+" "+ws.getRow(i1+j-1).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
							
							hm.put("Date"+j, ws.getRow(i1+j-1).getCell(4,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("Date"+j+" "+ws.getRow(i1+j-1).getCell(4,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							
							hm.put("Year"+j, ws.getRow(i1+j-1).getCell(5,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("Year"+j+" "+ws.getRow(i1+j-1).getCell(5,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
								
							hm.put("CHG"+j, ws.getRow(i1+j-1).getCell(6,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("CHG"+j+" "+ws.getRow(i1+j-1).getCell(6,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							
							hm.put("DESC"+j, ws.getRow(i1+j-1).getCell(7,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("DESC"+j+" "+ws.getRow(i1+j-1).getCell(7,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
				
							System.out.println("new row is "+(i1+j));
							
							if((i1+j)>totalrows1)
								break;
							if(ws.getRow(i1+j).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().equals("1"))
								break;
							
						}
						hm.put("TotalLines", Integer.toString(j));
						break;
					}
					i1++;
					
				}
				break;
			
			default:
				wb = new XSSFWorkbook(fis);
				if(ClaimType.equals("Professional"))
				ws = wb.getSheet("ProfClaimDetails");
				else
					ws = wb.getSheet("HCFAClaimDetails");
				
				int totalrowsP = ws.getLastRowNum();
				System.out.println("total rows are "+totalrowsP);
				System.out.println("file loaded");
				int i2=1;
				while(i2<=totalrowsP){
					System.out.println("value of line at i "+i2+" is "+ws.getRow(i2).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
					System.out.println("Value of FLN is "+ws.getRow(i2).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
					if(ws.getRow(i2).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals(FLN)){
						int j;
						hm.put("Diag", ws.getRow(i2).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						System.out.println("Diag added "+ws.getRow(i2).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
						for(j=1;j<=6;j++){
							
							System.out.println("Value of FLN"+j+" is "+ws.getRow(i2+j-1).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
							System.out.println("Diag added"+j+" "+ws.getRow(i2+j-1).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							hm.put("LN"+j, ws.getRow(i2+j-1).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
							System.out.println("LN"+j+" "+ws.getRow(i2+j-1).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
							
							hm.put("FROM"+j, ws.getRow(i2+j-1).getCell(4,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("FROM"+j+" "+ws.getRow(i2+j-1).getCell(4,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							
							hm.put("TO"+j, ws.getRow(i2+j-1).getCell(5,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("TO"+j+" "+ws.getRow(i2+j-1).getCell(5,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							
							hm.put("YEAR"+j, ws.getRow(i2+j-1).getCell(6,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("YEAR"+j+" "+ws.getRow(i2+j-1).getCell(6,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							
							hm.put("PS"+j, ws.getRow(i2+j-1).getCell(7,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("PS"+j+" "+ws.getRow(i2+j-1).getCell(7,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							
							hm.put("SVC"+j, ws.getRow(i2+j-1).getCell(8,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("SVC"+j+" "+ws.getRow(i2+j-1).getCell(8,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							
							hm.put("CHG"+j, ws.getRow(i2+j-1).getCell(9,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("CHG"+j+" "+ws.getRow(i2+j-1).getCell(9,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							
							hm.put("NBR"+j, ws.getRow(i2+j-1).getCell(10,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("NBR"+j+" "+ws.getRow(i2+j-1).getCell(10,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().trim());
							System.out.println("new row is "+(i2+j));
							
							if((i2+j)>totalrowsP)
								break;
							if(ws.getRow(i2+j).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue().equals("001"))
								break;
							
						}
						hm.put("TotalLines", Integer.toString(j));
						break;
					}
					i2++;
					
				}
				
				break;
			
			}
			for(Map.Entry m:hm.entrySet()){
				System.out.println(m.getKey()+" "+m.getValue());
			}
				return hm;
		} catch (Exception e) {
			System.out.println("FLN not found in the sheetand exception is "+e);
			return hm;
		}
	}
	
	public static void STBK_claimAdded_successfull() throws Exception{
		Mainframe_GlobalFunctionLib.validateContainsText("STBK_TRAINING", "SIGN ON COMPLETE", "I024", "STBK claim addedd succesfully");
		System.out.println("Claim addedd successfully "+Mainframe_GlobalFunctionLib.GetText("STBK_TRAINING", "SIGN ON COMPLETE"));
	}
	
	
	public static void STBK_id_logoff() throws Exception{
		for(int i=1;i<=7;i++){
			Navigation.PressClear();
			if(Mainframe_GlobalFunctionLib.GetText("STBK_HOME", "MAIN MENU").equals("MAIN MENU"))
				break;
		}
		Mainframe_GlobalFunctionLib.inputText("STBK_HOME", "ENTER SELECTION", "L");
		Mainframe_GlobalFunctionLib.Transmit();
		Thread.sleep(600);
		Navigation.PressClear();
		
		MainframeOR test=new MainframeOR();
		Window teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
		Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
		Field field = teScreen.describe(Field.class, new FieldDescription.Builder().build());
    	teScreen.setCursorPosition(1, 1);
    	teScreen.setText("off");
    	teScreen.sendTEKeys(Keys.ENTER);
    	Thread.sleep(1000);
	}
	
}
