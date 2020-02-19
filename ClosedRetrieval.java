package com.optum.topsuat.pages;

import com.hp.lft.sdk.Desktop;
import com.hp.lft.sdk.RegExpProperty;
import com.hp.lft.sdk.te.Field;
import com.hp.lft.sdk.te.FieldDescription;
import com.hp.lft.sdk.te.Keys;
import com.hp.lft.sdk.te.Protocol;
import com.hp.lft.sdk.te.Screen;
import com.hp.lft.sdk.te.ScreenDescription;
import com.hp.lft.sdk.te.Window;
import com.hp.lft.sdk.te.WindowDescription;
import com.optum.topsuat.functionlib.Mainframe_GlobalFunctionLib;
import com.optum.topsuat.utils.MainframeOR;
import com.optum.topsuat.utils.TestNGHelper;
import com.optum.topsuat.utils.MainframeOR.TeWindow;

public class ClosedRetrieval {

	//Screen: EDS9 | Field: NEXT SCR | Author: Himanshu Dua
		public static void EDS9_RCOM() throws Exception{
			Window teWindow = Desktop.describe(Window.class,new WindowDescription.Builder().protocol(Protocol.PR3270).build());
			Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24, 80).build());
			Mainframe_GlobalFunctionLib.inputText("EDS_1_Facility", "NXT SCR", "9");
			Mainframe_GlobalFunctionLib.Transmit();
			claim_RCOM();		
		}

		
		// Release claim from COMET | Author: Himanshu Dua
		public static void claim_RCOM() throws Exception{
			MainframeOR test=new MainframeOR();
	    	TeWindow TeWin  = test.TeWindow();
	    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
			String claim_status = RepricingTool.EDS9_Last_Status("COMT");
			if(claim_status.equals("COMT")){
				Mainframe_GlobalFunctionLib.inputText("EDS_9","WORK QUEUE ACTION","RCOM");
				Mainframe_GlobalFunctionLib.Transmit();
				Thread.sleep(1000);
			}
		}
		
		/** Screen:EDS5 | Field:Enter closure code | Updated Comments: Added author name |Updated Author Name: Himanshu Dua */
		public static void Navigation_MPC_to_EDS5_EDS6() throws Exception{
			Mainframe_GlobalFunctionLib.inputText("MPC", "Control Line",Mainframe_GlobalFunctionLib.GetText("MPC", "Control Line").replaceAll("MPC", "EDS") );
			Mainframe_GlobalFunctionLib.Transmit();
			Thread.sleep(1000);
		}
		
		
		/** Screen:EDS5 | Field:Enter closure code | Updated Comments: Added author name |Updated Author Name: Himanshu Dua */
		public static void EnterClosureCode(String ClaimType , String ClosureCode) throws Exception{
			
			if(ClaimType.equals("P")){
			Mainframe_GlobalFunctionLib.validateText("EDS_5", "EDS 5", "EDS 5", "Navigated to EDS5 screen");
			for(int i=1;i<=5;i++){
				if(Mainframe_GlobalFunctionLib.GetText("EDS_5", "L"+i+" Service Code").equals("")){
					System.out.println("Closure code entered in "+(i-1)+" service lines on EDS5 screen");
					break;
				}
				Mainframe_GlobalFunctionLib.inputText("EDS_5", "L"+i+" DOL Closure CD1", ClosureCode);
			}
			Mainframe_GlobalFunctionLib.Transmit();
			}
			if(ClaimType.equals("I")){
				Thread.sleep(1000);
				for(int i=1;i<=5;i++){
					if(Mainframe_GlobalFunctionLib.GetText("EDS_6", "L"+i+" Service Code").equals("")){
						System.out.println("Closure code entered in "+(i-1)+" service lines on EDS6 screen");
						break;
					}
					Mainframe_GlobalFunctionLib.inputText("EDS_6", "L"+i+" DOL Closure CD1", ClosureCode);
				}
				Mainframe_GlobalFunctionLib.Transmit();
				}
			TestNGHelper.logScreenshot("Closure code entered", "yes");
			Mainframe_GlobalFunctionLib.inputText("EDS_6", "NXT SCR", "A");
			Mainframe_GlobalFunctionLib.Transmit();
			Thread.sleep(1500);
		}
		
		
		/** Screen:EDS5 | Field:Enter Resubmit date clear closure code | Updated Comments: Added author name |Updated Author Name: Himanshu Dua */
		public static void resubmtdate(String ClaimType, String ResubmitDate) throws Exception{
			
			if(ClaimType.equals("P")){
				Mainframe_GlobalFunctionLib.validateText("EDS_5", "EDS 5", "EDS 5", "Navigated to EDS5 screen");
				for(int i=1;i<=5;i++){
					if(Mainframe_GlobalFunctionLib.GetText("EDS_5", "L"+i+" Service Code").equals("")){
						System.out.println("Closure code removed in "+(i-1)+" service lines on EDS5 screen");
						break;
					}
					Mainframe_GlobalFunctionLib.inputText("EDS_5", "L"+i+" DOL Closure CD1", "--");
				}
				
			Mainframe_GlobalFunctionLib.inputText("EDS_5", "RS DT", ResubmitDate);
			Mainframe_GlobalFunctionLib.inputText("EDS_5", "RS", "R");
			Mainframe_GlobalFunctionLib.Transmit();
			}
			
			if(ClaimType.equals("I")){
				Thread.sleep(1000);
				for(int i=1;i<=5;i++){
					if(Mainframe_GlobalFunctionLib.GetText("EDS_6", "L"+i+" Service Code").equals("")){
						System.out.println("Closure code removed in "+(i-1)+" service lines on EDS6 screen");
						break;
					}
					Mainframe_GlobalFunctionLib.inputText("EDS_6", "L"+i+" DOL Closure CD1", "--");
				}
				Mainframe_GlobalFunctionLib.inputText("EDS_6", "RS DT", ResubmitDate);
				Mainframe_GlobalFunctionLib.inputText("EDS_6", "RS", "R");
				Mainframe_GlobalFunctionLib.Transmit();
				}
			
			TestNGHelper.logScreenshot("Closure code entered", "yes");
			Mainframe_GlobalFunctionLib.inputText("EDS_6", "NXT SCR", "A");
			Mainframe_GlobalFunctionLib.Transmit();
			Thread.sleep(1500);
		}
	
		
		/** Screen:MPC | Field:Closed claim validation on MPC screen | Updated Comments - ATDD Guidelines |
	     * Updated Author Name: Himanshu Dua   */
		public static void closedClaim(String arg1) throws Exception{
			Window teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
			Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
			for(int i=1;i<=7;i++){
				if(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Place of Service").equals("--")){
					System.out.println("Closure code validated in "+(i-1)+" service lines on MPC screen");
					break;
				}
				Mainframe_GlobalFunctionLib.validateText("MPC", "L"+i+" Override CD", "C", "Override code validation for closed claim");
				Mainframe_GlobalFunctionLib.validateText("MPC", "L"+i+" Remark CD", "7Y", "Remark code validation for closed claim");
			}
			//Validate W1510CLAIM CLOSED edit message on MPC screen
			Edit_message(arg1);
		}
		
		//Screen:DCI | Field:Create Online FLN | Updated Comments:       |Updated Author: Himanshu Dua
		public static void Create_onlineFLN(String Offno, String NewFLN) throws Exception{
			Mainframe_GlobalFunctionLib.inputText("DCI", "Control Line", "DCI,"+Offno+","+NewFLN.substring(0, 5)+",");
			Mainframe_GlobalFunctionLib.Transmit();
			Mainframe_GlobalFunctionLib.inputText("DCI", "Action", "A");
			Mainframe_GlobalFunctionLib.inputText("DCI","Reason Code","KY");
			
			for(int i=1 ; i<=17;i++){
				String FLNupdated="";
				for(int j=1; j<=4;j++){
					if(Mainframe_GlobalFunctionLib.GetText("DCI", "L"+i+" From"+j).equals("-----")){
						Mainframe_GlobalFunctionLib.inputText("DCI", "L"+i+" From"+j, NewFLN.substring(5, 10));
						Mainframe_GlobalFunctionLib.inputText("DCI", "L"+i+" Thru"+j, NewFLN.substring(5, 10));
						Mainframe_GlobalFunctionLib.Transmit();
						FLNupdated = Mainframe_GlobalFunctionLib.GetText("MPC", "OK_TO_PAY").trim();
						break;
					}
					
				}
				if(FLNupdated.contains("W070FILES CAN BE UPDATED")){
					
					Mainframe_GlobalFunctionLib.inputText("DCI", "Control Line", Mainframe_GlobalFunctionLib.GetText("DCI", "Control Line").replaceAll("DCC", "DCU"));
					Mainframe_GlobalFunctionLib.Transmit();
					Mainframe_GlobalFunctionLib.validateContainsText("MPC", "OK_TO_PAY", "UPDATED", "Online FLN can be created on DCI screen");
					break;
				}
				
				if(FLNupdated.contains("E124INVALID CODE")){
					System.out.println("Invalid Retrieval FLN "+NewFLN);
					break;
				}
				
			}
		}
		
		/** Screen:MPC | Field:Validate WE2578REPRICING APPLIES edit on MPC screen | Updated Comments - ATDD Guidelines |
	     * Updated Author Name: Himanshu Dua   */
		public static void Edit_message(String arg1) throws Exception{
			Window teWindow = Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
			Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
			Field teField = teScreen.describe(Field.class, new FieldDescription.Builder().id(1842).build());
			String ValEdit = arg1;
			for (int i=1; i<=15; i++)
			{
			 String ValEditMPC = teField.getText();
			 boolean ValStatus = ValEditMPC.contains(ValEdit);
			 System.out.println("Expected Value of substring: "+ValStatus);
			 if (ValStatus == true){
				 System.out.println("MPC contains "+ValEdit);
				 Mainframe_GlobalFunctionLib.validateContainsText("MPC", "OK_TO_PAY", ValEdit, ValEdit+" Edit generated on MPC screen");
				 break;
			 }
			 teScreen.sendTEKeys(Keys.ENTER);
			 Thread.sleep(1500);
			}
			
		}
		
		
		// StaticMethod | Screen:AHI | Field:AHI Navigation | Author : Himanshu Dua
		public static void AHInaviation()throws Exception{
			Mainframe_GlobalFunctionLib.inputText("EDS_1_Facility", "NXT SCR", "h");
			Mainframe_GlobalFunctionLib.Transmit();
		}
		
		
		// StaticMethod | Screen:AHI | Field:AHI Navigation | Author : Himanshu Dua
		public static void AHIcontrolLine_retrieval(String FLN, String ICN) throws Exception{
			String[] ahiData = Mainframe_GlobalFunctionLib.GetText("MPC", "Control Line").split(",");
			Mainframe_GlobalFunctionLib.inputText("MPC", "Control Line", "AHI,"+ahiData[1]+","+ahiData[2]+","+ahiData[3]+","+ahiData[4]+",,f"+FLN);
			Mainframe_GlobalFunctionLib.Transmit();
			
			for(int i=1;i<=9;i++){
				
				if(ICN.equals(Mainframe_GlobalFunctionLib.GetText("AHI", "L"+i+" Document Number"))){
					Mainframe_GlobalFunctionLib.inputText("AHI", "L"+i+" Action", "c");
					Mainframe_GlobalFunctionLib.Transmit();
					Thread.sleep(1000);
					Mainframe_GlobalFunctionLib.Transmit();
					break;
				}
				
				if(i==9){
					Navigation.PressF8();
					i=1;
					continue;
				}
				
				if(Mainframe_GlobalFunctionLib.GetText("AHI", "L"+i+" Document Number").equals("----------")){
					System.out.println("Given Closed ICN not found in AHI screen");
					break;
				}
				
			}
		}
		
}
