package com.optum.topsuat.pages;

import java.util.Random;

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
import com.optum.topsuat.utils.MainframeOR.TeWindow;

public class Adjustment_69_70{

	
	// StaticMethod | Screen:AHI | Field:Navigation to MHI from AHI | Author : Himanshu Dua
		public static void AHI_to_MHI_Navigation(String ICN) throws Exception{
		System.out.println("ICN is "+ICN);
				for(int i=1;i<=9;i++){
				if(ICN.equals(Mainframe_GlobalFunctionLib.GetText("AHI", "L"+i+" Document Number"))){
					Mainframe_GlobalFunctionLib.inputText("AHI", "L"+i+" Action", "s");
					Mainframe_GlobalFunctionLib.Transmit();
					Thread.sleep(1000);
					break;
				}
				
				if(i==9){
					Navigation.PressF8();
					i=0;
					continue;
				}
				
				if(Mainframe_GlobalFunctionLib.GetText("AHI", "L"+i+" Document Number").equals("----------")){
					System.out.println("Given ICN not found in AHI screen");
					break;
				}
				
			}
		}
		
		
		public static void MHI_ControlLine_69_70(String ICN) throws Exception {
			String[] mhiData = Mainframe_GlobalFunctionLib.GetText("MPC", "Control Line").split(",");
			Mainframe_GlobalFunctionLib.inputText("MPC", "Control Line", "MPI,"+mhiData[1]+","+mhiData[2]+","+mhiData[3]+","+mhiData[4]+","+mhiData[5]+",i"+ICN+",,,gzh");
			Mainframe_GlobalFunctionLib.Transmit();
		}
		
		/** Screen:MPC | Field:69/70 reversal on MPC | Updated Comments - ATDD Guidelines |
	     * Updated Author Name: Himanshu Dua   
		 * @throws Exception */
		public static void Reversal_69_MPC() throws Exception{
			Window teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
			Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
			for(int i=7;i>=1;i--){
				if(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Remark CD").equals("--")){
					System.out.println("Remark code updated to 69 in "+(7-i)+" service lines on MPC screen");
					break;
				}
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Remark CD", "69");
			}
			Mainframe_GlobalFunctionLib.inputText("MPC", "Transaction Code", "69");
			
		}
		
		
		/** Screen:MPC | Field:Closed claim validation on MPC screen | Updated Comments - ATDD Guidelines |
	     * Updated Author Name: Himanshu Dua   */
		public static void ADJ_claim() throws Exception{
			Window teWindow = Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
			Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
			Field teField = teScreen.describe(Field.class, new FieldDescription.Builder().id(1842).build());
			teScreen.setCursorPosition(2,2);
	    	teScreen.setText("ADJ");
	    	Thread.sleep(1000);
	    	teScreen.sendTEKeys(Keys.ENTER);
	    	Thread.sleep(2000);
		}
		
		
		/** Screen:MPC | Field:Closed claim validation on MPC screen | Updated Comments - ATDD Guidelines |
	     * Updated Author Name: Himanshu Dua   */
		public static void dashout_OI_OIM() throws Exception{
			for(int k=7;k>0;k--){
				if(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+k+" Service Code").equals("OI") || Mainframe_GlobalFunctionLib.GetText("MPC", "L"+k+" Service Code").equals("OIM")){
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Place of Service", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Service Code", "------");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" First Date of Service", "------");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Last Date of Service", "------");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Number", "---");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Override CD", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Payee CD", "-");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Provider Pos Nbr", "-");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Remark CD", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Sanction Ind", "-");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Charge", "-----");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Charge Cent", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Base Covered", "-----");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Base Covered Cent", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Base Payable", "-----");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Base Payable Cent", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Supplemental Amt", "----");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" Supplemental Amt Cent", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" MM Covered", "-----");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" MM Covered Cent", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" MM Payable", "-----");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+k+" MM Payable Cent", "--");
					}
			}
		}
		
		/** Screen:MPC | Field:Closed claim validation on MPC screen | Updated Comments - ATDD Guidelines |
	     * Updated Author Name: Himanshu Dua   */
		public static void Reversal_70_MPC(String Adjustment) throws Exception{
			int newdollaramnt =0;
			int newcentamnt=0;
			dashout_OI_OIM();
			if(Adjustment.equals("Overpayment")){
				
				int totallines = total_svclines_adjustment();
				System.out.println("Total lines before 70 adjustment is "+totallines);
				
				for(int i =0 ;i<totallines;i++){
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Override CD", "13");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Covered",Addzero_charge(generate_randomNo_overpayment(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+(7-i)+" MM Covered"),Mainframe_GlobalFunctionLib.GetText("MPC", "L"+(7-i)+" MM Ded")),5));
				}
				Mainframe_GlobalFunctionLib.Transmit();
				
				String[] amnt = get_overpaidamount();
				
				for(int i =0 ;i<totallines;i++){
					
					if(!(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+(7-i)+" MM Payable").equals(""))){
						newdollaramnt=newdollaramnt+Integer.parseInt(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+(7-i)+" MM Payable"));
					}
					
					newcentamnt=newcentamnt+Integer.parseInt(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+(7-i)+" MM Payable Cent"));
				}
				
				System.out.println("new paid dollar paid amnt before if loop is "+newdollaramnt+" and new paid cent amount is "+newcentamnt);
				
				if(Integer.toString(newcentamnt).length()>2){
					newdollaramnt=newdollaramnt+(newcentamnt/100);
					newcentamnt =newcentamnt%100;
					System.out.println("new paid dollar paid amnt in if loop is "+newdollaramnt+" and new paid cent amount in if loopis "+newcentamnt);
				}
				
				System.out.println("new paid dollar paid amnt is "+newdollaramnt+" and new paid cent amount is "+newcentamnt);
				
				dashout_lines(totallines);
				
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 Charge",Addzero_charge(newdollaramnt,5));
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 Charge Cent",Integer.toString(newcentamnt));
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 Not Covered", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 Not Covered Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 Base Covered", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 Base Covered Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 MM Covered",Addzero_charge(newdollaramnt,5));
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 MM Covered Cent",Integer.toString(newcentamnt));
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 MM Coins %","100");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 MM Payable",Addzero_charge(newdollaramnt,5));
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 MM Payable Cent",Integer.toString(newcentamnt));
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 MM Ded", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 MM Ded Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L7 MM Ded Ind", "-");
				
				Mainframe_GlobalFunctionLib.Transmit();
				
				add_20Line(totallines, amnt);
				Mainframe_GlobalFunctionLib.Transmit();
				Thread.sleep(500);
				ClosedRetrieval.Edit_message("1805INVALID OTS REASON CD");
				Thread.sleep(500);
				Mainframe_GlobalFunctionLib.inputText("MPC","L1 Number","061");
				Mainframe_GlobalFunctionLib.Transmit();
				ClosedRetrieval.Edit_message("W118 OK TO PAY");
				
			}
			
			
			
			
			
			if(Adjustment.equals("Underpayment")){
				int totallines = total_svclines_adjustment();
				System.out.println("Total lines before 70 adjustment is "+totallines);
				
				for(int i =0 ;i<totallines;i++){
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Not Covered", "-----");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Not Covered Cent", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Override CD", "13");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Coins %", "100");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Covered",Addzero_charge(underpaid_amount(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+(7-i)+" MM Payable")),5));
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Covered Cent",Mainframe_GlobalFunctionLib.GetText("MPC", "L"+(7-i)+" MM Payable Cent"));
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Ded", "-----");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Ded Cent", "--");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Ded Ind", "-");
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Charge",Addzero_charge(underpaid_amount(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+(7-i)+" MM Payable")),5));
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Charge Cent",Mainframe_GlobalFunctionLib.GetText("MPC", "L"+(7-i)+" MM Payable Cent"));
				}
				Mainframe_GlobalFunctionLib.Transmit();
				ClosedRetrieval.Edit_message("W118 OK TO PAY");
			}
			
			
			
			
		}
		
		
		
		/** Screen:MPC | Field:Closed claim validation on MPC screen | Updated Comments - ATDD Guidelines |
	     * Updated Author Name: Himanshu Dua   */
		public static int underpaid_amount(String underpaidAmount){
			if(underpaidAmount.equals(""))
				return 0;
			else
			return Integer.parseInt(underpaidAmount);
		}
		
		/** Screen:MPC | Field:Closed claim validation on MPC screen | Updated Comments - ATDD Guidelines |
	     * Updated Author Name: Himanshu Dua   */
		public static int total_svclines_adjustment() throws Exception{
			int totallines;
			for(totallines=7;totallines>0;totallines--){
				if(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+totallines+" Place of Service").equals("--")){
					System.out.println("line with dash is "+totallines);
					break;
				}
			}
			System.out.println("total lines before subtracting is "+totallines);
			totallines=7-totallines;
			return totallines;
		}
		
		
		/** Screen:MPC | Field:Closed claim validation on MPC screen | Updated Comments - ATDD Guidelines |
	     * Updated Author Name: Himanshu Dua   */
		public static String[] get_overpaidamount() throws Exception{
			
			String controlLine = Mainframe_GlobalFunctionLib.GetText("MPC", "OK_TO_PAY");
			String overpaidamnt = controlLine.substring(18, 27).trim();
			System.out.println("overpaid amount is "+overpaidamnt);
			String[] amnt = overpaidamnt.split("\\."); 
			System.out.println("Dollar Amnt is  "+amnt[0]+" and cent amount is "+amnt[1]);
			return amnt;
		}
		
		public static void dashout_lines(int totallines) throws Exception{
			for(int i =1 ;i<totallines;i++){
				
				
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Not Covered", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Not Covered Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Base Ded", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Base Ded Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Base Ded Ind", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Base Coins %", "---");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Ded", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Ded Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Ded Ind", "-");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Coins %", "---");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Reserve Bank Amt", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Reserve Bank Amt Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Place of Service", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Service Code", "------");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" First Date of Service", "------");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Last Date of Service", "------");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Number", "---");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Override CD", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Payee CD", "-");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Provider Pos Nbr", "-");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Remark CD", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Sanction Ind", "-");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Charge", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Charge Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Base Covered", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Base Covered Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Base Payable", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Base Payable Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Supplemental Amt", "----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" Supplemental Amt Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Covered", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Covered Cent", "--");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Payable", "-----");
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+(7-i)+" MM Payable Cent", "--");
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
		
		public static void add_20Line(int totalLines, String[] amnt) throws Exception{
			
			
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Place of Service", Mainframe_GlobalFunctionLib.GetText("MPC", "L7 Place of Service"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Service Code", Mainframe_GlobalFunctionLib.GetText("MPC", "L7 Service Code"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 First Date of Service", Mainframe_GlobalFunctionLib.GetText("MPC", "L7 First Date of Service"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Last Date of Service", Mainframe_GlobalFunctionLib.GetText("MPC", "L7 Last Date of Service"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Number", Addzero_charge(Integer.parseInt(Mainframe_GlobalFunctionLib.GetText("MPC", "L7 Number")),3));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Override CD", "20");
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Payee CD", Mainframe_GlobalFunctionLib.GetText("MPC", "L7 Payee CD"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Provider Pos Nbr", Mainframe_GlobalFunctionLib.GetText("MPC", "L7 Provider Pos Nbr"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Remark CD", "70");
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Sanction Ind", Mainframe_GlobalFunctionLib.GetText("MPC", "L7 Sanction Ind"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Charge", Addzero_charge(Integer.parseInt(amnt[0]),5));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 Charge Cent", amnt[1]);
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 MM Covered", Addzero_charge(Integer.parseInt(amnt[0]),5));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 MM Covered Cent", amnt[1]);
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 MM Coins %", "100");
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 MM Payable", Addzero_charge(Integer.parseInt(amnt[0]),5));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L6 MM Payable Cent", amnt[1]);
		}
		
		public static int generate_randomNo_overpayment(String overpaymntamnt, String ded){
			System.out.println("overpayment amount in random fn is "+overpaymntamnt+" and ded is "+ded);
			if(overpaymntamnt.equals(""))
				return 0;
						
						
			else if(ded.equals("")){	
			int no = Integer.parseInt(overpaymntamnt);
			Random rand = new Random();
			  int ran = rand.nextInt(no);
			  if (ran==0){
			      ran= ran+1;
			      System.out.println("random : "+ran); 
			  }
			  else{
			      System.out.println("random : "+ran);  
			  }
			  System.out.println("random no is when ded is NOT present "+ran);
			return ran;
			}
			
			
			else{
				int no = Integer.parseInt(overpaymntamnt);
				int min = Integer.parseInt(ded);
				int ran;
			  if (no==min){
			      ran= min;
			      System.out.println("random : "+ran); 
			  }
			  else{
				  Random rand = new Random();
				  ran = rand.nextInt(no-min)+min;
			      System.out.println("random : "+ran);  
			  }
			  System.out.println("random no is when ded is present "+ran);
			return ran;}
			
			
		}
		
		
		public static void mainframe_wait(String strTable,String strField,String strValue) throws Exception{
			MainframeOR test=new MainframeOR();
	    	TeWindow TeWin  = test.TeWindow();
	    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
	    	String mainframeText="";
	    	for(int i=1;i<100;i++){
	    		mainframeText = Mainframe_GlobalFunctionLib.GetText(strTable, strField);
	    		if(mainframeText.contains(strValue))
	    			break;	
	    		else
	    			Thread.sleep(1000);
	    	}
		}
		
		public static void Add_30Line(String Adjustment) throws Exception{
			
			int dollaramount_30line=0;
			int centamount_30line=0;
			int p=1;
			int i;
			for(i=1;i<=7;i++){
				if(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Service Code").equals("------"))
					break;
				
				if(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Service Code").equals("OI") || Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Service Code").equals("OIM") || Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Service Code").equals("CXINT"))
					continue;
				
				if(!(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" MM Payable").equals(""))){
					dollaramount_30line=dollaramount_30line+Integer.parseInt(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" MM Payable"));
				}
				centamount_30line=centamount_30line+Integer.parseInt(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" MM Payable Cent"));
				Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Override CD", "13");
			}
			System.out.println("new paid dollar paid amnt in ADJ before if is "+dollaramount_30line+" and new paid cent amount in ADJ is "+centamount_30line);
			if(Integer.toString(centamount_30line).length()>2){
				dollaramount_30line=dollaramount_30line+(centamount_30line/100);
				centamount_30line =centamount_30line%100;
				System.out.println("new paid dollar paid amnt in ADJ is "+dollaramount_30line+" and new paid cent amount in ADJ is "+centamount_30line);
			}
			
			System.out.println("new paid dollar paid amnt in ADJ  after if is "+dollaramount_30line+" and new paid cent amount in ADJ is "+centamount_30line);
			
			System.out.println("value of blank line is" +i);
			
			if(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Service Code").equals("OI") || Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Service Code").equals("OIM"))
				p=2;
			
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Place of Service", Mainframe_GlobalFunctionLib.GetText("MPC", "L"+p+" Place of Service"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Service Code", Mainframe_GlobalFunctionLib.GetText("MPC", "L"+p+" Service Code"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" First Date of Service", Mainframe_GlobalFunctionLib.GetText("MPC", "L"+p+" First Date of Service"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Last Date of Service", Mainframe_GlobalFunctionLib.GetText("MPC", "L"+p+" Last Date of Service"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Number", "001");
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Override CD", "30");
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Payee CD", Mainframe_GlobalFunctionLib.GetText("MPC", "L"+p+" Payee CD"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Provider Pos Nbr", Mainframe_GlobalFunctionLib.GetText("MPC", "L"+p+" Provider Pos Nbr"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Sanction Ind", Mainframe_GlobalFunctionLib.GetText("MPC", "L"+p+" Sanction Ind"));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Charge", Addzero_charge(dollaramount_30line,5));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" Charge Cent",Integer.toString(centamount_30line));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" MM Covered", Addzero_charge(dollaramount_30line,5));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" MM Covered Cent", Integer.toString(centamount_30line));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" MM Coins %", "100");
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" MM Payable", Addzero_charge(dollaramount_30line,5));
			Mainframe_GlobalFunctionLib.inputText("MPC", "L"+i+" MM Payable Cent", Integer.toString(centamount_30line));
			
			
			
			
			if(Adjustment.equals("Underpayment")){
				for(int j=1;j<i;j++){
					if(!(Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Service Code").equals("OI") || Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Service Code").equals("OIM") || Mainframe_GlobalFunctionLib.GetText("MPC", "L"+i+" Service Code").equals("CXINT")))
					Mainframe_GlobalFunctionLib.inputText("MPC", "L"+j+" MM Coins %", "100");
				}
				}
				Mainframe_GlobalFunctionLib.Transmit();
				ClosedRetrieval.Edit_message("W118 OK TO PAY");
		}
}
