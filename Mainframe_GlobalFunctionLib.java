package com.optum.topsuat.functionlib;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.hp.lft.report.ReportException;
//import com.hp.lft.report.Reporter;
import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.RegExpProperty;
import com.hp.lft.sdk.te.EmulatorStatus;
import com.hp.lft.sdk.te.Field;
import com.hp.lft.sdk.te.FieldDescription;
import com.hp.lft.sdk.te.Screen;
import com.hp.lft.sdk.te.ScreenDescription;
import com.optum.topsuat.pages.EDS9;
import com.optum.topsuat.utils.MainframeOR;
import com.optum.topsuat.utils.MainframeOR.TeWindow;
import com.optum.topsuat.utils.TestNGHelper;

import cucumber.api.Scenario;


public class Mainframe_GlobalFunctionLib{
	public static String actReplaceValue;
	public static String expReplaceValue;
	public static String icn;
	public static String ssn;
	public static String policy;
	public static String patientfname;
	public static String patientlname;
	public static String patientrelation;
	public static String temp1;
	public static String temp2;
	public static String temp3;
	public static String temp4;
	public static String temp5;
	public static String temp6;
	public static int temp11;
	public static int temp12;
	public static int temp13;
	public static int temp14;
	public static int temp15;
	public static int temp16;
	

	
	public static Connection conn;
	public static Scenario scenario_val;
	public static String Global_ICN;
	/**
	 * Method used to launch TE 
	 */	

	public static void launchTE(Scenario scenario) throws Exception
	{
		File file = new File("C:\\ProgramData\\IBM\\Personal Communications\\TN3270.WS");
    	Desktop.getDesktop().open(file);
    	scenario_val=scenario;
		//this.scenario = scenario;
		String scenario_name=scenario_val.getName();
		System.out.println("Scenario name is" +scenario_name);
	}


	/**
	 * Method used to set text on TE
	 */
	public static void inputText(String strTable, String strField, String strValue) throws GeneralLeanFtException, ClassNotFoundException, SQLException, ReportException
	{	
		MainframeOR test=new MainframeOR();
    	TeWindow TeWin  = test.TeWindow();
    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
    	String varMDBCoord = Mainframe_GlobalFunctionLib.ReadTestdataMDB(System.getProperty("user.dir")+"\\src\\main\\resources\\TOPSObjectRepository.accdb", strTable, strField);
    	String varCoordinates = varMDBCoord.toString();
		String[] strCoord= varCoordinates.split(":");
		int varRow = Integer.parseInt(strCoord[0]);
		int varCol = Integer.parseInt(strCoord[1]);
		System.out.println(varRow + ":" + varCol);
    	screen.describe(Field.class, new FieldDescription.Builder().startPosition(varRow, varCol).build()).setText(strValue);
    	String newValInput = screen.describe(Field.class, new FieldDescription.Builder().startPosition(varRow, varCol).build()).getText();
    	if(strValue.equals(newValInput.trim()))
		{
			System.out.println("Value successfully set to :" + newValInput);
		}
		else
		{
			System.out.println("Value input unsuccessful");
		}
	}
	
	/**
	 * Method used to Comapre the Expected and Actual Values
	 * StepName : StepName is what you are trying to Execute / TestStep Name
	 */
	public static void validateText(String strTable, String strField, String strValue, String StepName) throws Exception
	{   
		//Object Repository
		MainframeOR test=new MainframeOR();
    	TeWindow TeWin  = test.TeWindow();
    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
    	String varMDBCoord = Mainframe_GlobalFunctionLib.ReadTestdataMDB(System.getProperty("user.dir")+"\\src\\main\\resources\\TOPSObjectRepository.accdb", strTable, strField);
    	String varCoordinates = varMDBCoord.toString();
		String[] strCoord= varCoordinates.split(":");
		int varRow = Integer.parseInt(strCoord[0]);
		System.out.println("X_RowNo:" +varRow);
		int varCol = Integer.parseInt(strCoord[1]);
		System.out.println("Y_ColNo:" +varCol);
		int varLength = Integer.parseInt(strCoord[2]);
		//screen.describe(Field.class, new FieldDescription.Builder().startPosition(varRow, varCol).build()).highlight();
    	String screenVal = screen.describe(Field.class, new FieldDescription.Builder().startPosition(varRow, varCol).build()).getText().trim();
    	System.out.println("Expected Value:" +strValue);
    	System.out.println("Actual Value:" +screenVal);
    	TestNGHelper.assertEqual(screenVal, strValue, "yes", StepName+" Expected Value "+strValue+" AND Actual value "+screenVal);
    	
	}
	
	public static void claim_RCOM() throws Exception{
		MainframeOR test=new MainframeOR();
    	TeWindow TeWin  = test.TeWindow();
    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
		String claim_status = EDS9.EDS9_Last_Status("COMT");
		if(claim_status.equals("COMT")){
			Mainframe_GlobalFunctionLib.inputText("EDS_9","WORK QUEUE ACTION","RCOM");
			Mainframe_GlobalFunctionLib.Transmit();
			Thread.sleep(1000);
		}
	}
	
	//*****get text by brahmani*****//
		public static String GetText(String strTable, String strField) throws Exception
		{   
			//Object Repository
			MainframeOR test=new MainframeOR();
	    	TeWindow TeWin  = test.TeWindow();
	    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
	    	String varMDBCoord = Mainframe_GlobalFunctionLib.ReadTestdataMDB(System.getProperty("user.dir")+"\\src\\main\\resources\\TOPSObjectRepository.accdb", strTable, strField);
	    	String varCoordinates = varMDBCoord.toString();
			String[] strCoord= varCoordinates.split(":");
			int varRow = Integer.parseInt(strCoord[0]);
			//System.out.println("RowNo:" +varRow);
			int varCol = Integer.parseInt(strCoord[1]);
	    	String screenVal = screen.describe(Field.class, new FieldDescription.Builder().startPosition(varRow, varCol).build()).getText().trim();
	    	//System.out.println("Expected Value:" +strValue);
	    	//System.out.println("Actual Value:" +screenVal);
	    	//TestNGHelper.assertEqualTrue(true,true, "NO", strField + "Value from Tops Screen "+screenVal);
	    	  	return screenVal;
	    	
		}
		

	
	/** Method used to get ScreenID to check whether you are in correct screen */ 
	public static void validateScreenID(String strTable, String strField, String strValue, String StepName) throws Exception
	{   
		MainframeOR test=new MainframeOR();
		
    	TeWindow TeWin  = test.TeWindow();
    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
    	String varMDBCoord = Mainframe_GlobalFunctionLib.ReadTestdataScreenID(System.getProperty("user.dir")+"\\src\\main\\resources\\TOPSObjectRepository.accdb", strTable, strField);
    	String varCoordinates = varMDBCoord.toString();
		String[] strCoord= varCoordinates.split(":");
		int varRow = Integer.parseInt(strCoord[0]);
		System.out.println("RowNo:" +varRow);
		int varCol = Integer.parseInt(strCoord[1]);
    	String screenVal = screen.describe(Field.class, new FieldDescription.Builder().startPosition(varRow, varCol).build()).getText().trim();
    	System.out.println("Expected Value:" +strValue);
    	System.out.println("Actual Value:" +screenVal);
    	TestNGHelper.assertEqual(screenVal, strValue, "yes", StepName+" Excpected Value "+strValue+" AND Actual value "+screenVal);
    	
	}
	
	public static void getScreenID() throws GeneralLeanFtException, InterruptedException, ClassNotFoundException, SQLException, ReportException
	{
		MainframeOR test=new MainframeOR();
    	TeWindow TeWin  = test.TeWindow();
    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
		int screenID = screen.getId();
		System.out.println("Screen ID is :" + screenID);
	}
	
	/**
	 * Method used to get message from TE
	 * @throws GeneralLeanFtException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ReportException 
	 */	
	public static void getMessage(Integer varRow, Integer varCol) throws GeneralLeanFtException, InterruptedException, ClassNotFoundException, SQLException, ReportException
	{
		MainframeOR test=new MainframeOR();
    	TeWindow TeWin  = test.TeWindow();
    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
		String screenError = screen.describe(Field.class, new FieldDescription.Builder().startPosition(varRow, varCol).build()).getText();
		System.out.println("Value of Error text is :" + screenError);
	}
	
	/**
	 * Method used to Click Enter from KeyBoard
	 * @throws GeneralLeanFtException
	 */	
	public static void Transmit() throws GeneralLeanFtException, InterruptedException
	{
		MainframeOR test=new MainframeOR();
    	TeWindow TeWin  = test.TeWindow();
    	Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
    	screen.sendTEKeys(com.hp.lft.sdk.te.Keys.ENTER);
    	Thread.sleep(5000);
	}
	
	/** ReadTestdataScreenID from Database to get ScreenID */
	public static String ReadTestdataScreenID(String mdbPath, String TableName,String Rowflag) throws SQLException, ClassNotFoundException {
		String varRowCol = null;	
		System.out.println("CallingReadMDB");
		System.out.println(mdbPath);	

        try {
 
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {
 
            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
    	try
    	{
    	Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + mdbPath);
    	System.out.println(conn);
    	Statement s = conn.createStatement();
      System.out.println("SELECT screenID FROM TOPS_ATDD where ScreenID = \""  + Rowflag + "\" and ScreenName = \""  + TableName + "\"");
      String selTable = "SELECT screenID FROM TOPS_ATDD where ScreenID = \""  + Rowflag + "\" and ScreenName = \""  + TableName + "\"";
      ResultSet rs = s.executeQuery(selTable);
      while (rs.next()) {
    	  System.out.println("String1 is : " + rs.getInt(1));
    	  System.out.println("String2 is : " + rs.getInt(2));
      varRowCol = rs.getInt(1) + ":" + rs.getInt(2);
      System.out.println(varRowCol);
      }
      s.close();
      conn.close();
    }
    	 catch(Exception err)
        {
            System.out.println(err);
        }
	return varRowCol;
  }
	
	/**Read data from Database */
	public static String ReadTestdataMDB(String mdbPath, String TableName,String Rowflag) throws SQLException, ClassNotFoundException {
		String varRowCol = null;	
		System.out.println("CallingReadMDB");
		System.out.println(mdbPath);	

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {
 
            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
        
    	try
    	{
    Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + mdbPath);
    	System.out.println(conn);
    	Statement s = conn.createStatement();
      System.out.println("SELECT StartRow, StartColumn FROM TOPS_ATDD where FieldName = \""  + Rowflag + "\" and ScreenName = \""  + TableName + "\"");
     String selTable = "SELECT StartRow, StartColumn,FieldLength FROM TOPS_ATDD where FieldName = \""  + Rowflag + "\" and ScreenName = \""  + TableName + "\"";
      //System.out.println(selTable);
      ResultSet rs = s.executeQuery(selTable);
      while (rs.next()) {
    	  System.out.println("X-co_ordinate is : " + rs.getInt(1));
    	  System.out.println("Y-co_ordinate is : " + rs.getInt(2));
    	  System.out.println("FieldLength is : " + rs.getInt(3));
          varRowCol = rs.getInt(1) + ":" + rs.getInt(2)+":"+rs.getInt(3);
      System.out.println(varRowCol);
      }
      // close and cleanup
      s.close();
      conn.close();
    }
    	 catch(Exception err)
        {
            System.out.println(err);
        }
	return varRowCol;
  }
	
	
	/** Wait till Screen Ready */
	public static void waitTillScreenReady() throws GeneralLeanFtException, InterruptedException
	{
		MainframeOR test=new MainframeOR();
    	TeWindow TeWin  = test.TeWindow();
    	EmulatorStatus TEStatus = TeWin.getEmulatorStatus();
    	String stat = TEStatus.toString();
    	System.out.println(stat);
    	while (stat != "READY"){
    		System.out.println("Inside while");
    		Thread.sleep(10);
    		TEStatus = TeWin.getEmulatorStatus();
    		stat = TEStatus.toString();
    	}
    	 
	}
	/** Screen:General | Field:Get and Return Value passing Screen and field | Updated Comments - ATDD Guidelines |
     * Updated Author Name: Namita Kalra   */
       public static String validateText1(String strTable, String strField) throws Exception
       {   
              //Object Repository
              MainframeOR test=new MainframeOR();
       TeWindow TeWin  = test.TeWindow();
       Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
       String varMDBCoord = Mainframe_GlobalFunctionLib.ReadTestdataMDB(System.getProperty("user.dir")+"\\src\\main\\resources\\TOPSObjectRepository.accdb", strTable, strField);
       String varCoordinates = varMDBCoord.toString();
              String[] strCoord= varCoordinates.split(":");
              int varRow = Integer.parseInt(strCoord[0]);
              System.out.println("RowNo:" +varRow);
              int varCol = Integer.parseInt(strCoord[1]);
       String screenVal = screen.describe(Field.class, new FieldDescription.Builder().startPosition(varRow, varCol).build()).getText().trim();
       return screenVal;
       }


/** Screen:General | Field:Compare expected and actual with direct value passed | Updated Comments - ATDD Guidelines |
     * Updated Author Name: Namita Kalra   */
       public static void GetTextAssertion(String screenVal, String strValue, String StepName) throws Exception
       {
       TestNGHelper.assertEqual(screenVal, strValue, "yes", StepName+" Expected Value "+strValue+" AND Actual value "+screenVal);
       }

       /**
   	 * Method used to Comapre the Expected and Actual Values
   	 * StepName : StepName is what you are trying to Execute / TestStep Name
   	 */
     //Updated for replacing special characters in EDIT messages of MPC screen for Global Days project by Rajat
       
       public static void validateContainsText(String strTable, String strField, String strExpValue, String StepName) throws Exception
       {   
              //Object Repository
              MainframeOR test=new MainframeOR();
              TeWindow TeWin  = test.TeWindow();
              Screen screen = TeWin.describe(Screen.class, new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
              String varMDBCoord = Mainframe_GlobalFunctionLib.ReadTestdataMDB(System.getProperty("user.dir")+"\\src\\main\\resources\\TOPSObjectRepository.accdb", strTable, strField);
              String varCoordinates = varMDBCoord.toString();
              String[] strCoord= varCoordinates.split(":");
              int varRow = Integer.parseInt(strCoord[0]);
              System.out.println("RowNo:" +varRow);
              int varCol = Integer.parseInt(strCoord[1]);
              String actScreenVal = screen.describe(Field.class, new FieldDescription.Builder().startPosition(varRow, varCol).build()).getText().trim();
              System.out.println("Expected Value:" +strExpValue);
              System.out.println("Actual Value:" +actScreenVal);
              String actReplaceValue = actScreenVal.replaceAll("[^a-zA-Z0-9]", "_"); 
              String expReplaceValue = strExpValue.replaceAll("[^a-zA-Z0-9]", "_");
              boolean textStatus = actReplaceValue.contains(expReplaceValue);
              System.out.println("Actual message contains expected message status  is: "+textStatus);
              TestNGHelper.assertEqualTrue(textStatus,true, "yes", StepName+" Expected Value "+expReplaceValue+" AND Actual value "+actReplaceValue);
              //TestNGHelper.assertEqualTrue(textStatus,true, "yes", StepName+" Expected Value "+strExpValue+" AND Actual value "+actScreenVal);
       //     TestNGHelper.assertEqual(screenVal, strValue, "yes", StepName+" Expected Value "+strValue+" AND Actual value "+screenVal);           
       }

	
    /**
	 * Method used to Comapre the Expected and Actual Values
	 * StepName : StepName is what you are trying to Execute / TestStep Name
	 */
	public static void assertContains(String strActValue,String strExpValue, String StepName) throws Exception
	{   
		boolean textStatus = strActValue.contains(strExpValue);
    	TestNGHelper.assertEqualTrue(textStatus,true, "yes", StepName+" Expected Value "+strExpValue+" in Actual value "+strActValue);
	}
	
	
	//*****Created For Global Days project for reformatting MPC Control Line and converting into ISI Control Line by Rajat*****//
    public static String ControlLineReset(String strTable, String strField, String Input) throws Exception
    {   
           //Object Repository
           String varMPCControlLine = Mainframe_GlobalFunctionLib.GetText(strTable, strField);
           String varMPCCntlne = varMPCControlLine.toString();
     String[] strArray= varMPCCntlne.split(",");
     strArray[0] = Input;
     StringBuffer sb = new StringBuffer(strArray[0]);
     for (int i=1; i < strArray.length-1; i++)
           {
sb.append(","+strArray[i]);
           }
     String output = sb.toString();
           return output;
                                       
    }
    //*****Created For EOP project for reformatting MPC Control Line and converting into ISI Control Line by Rajat*****//
    public static String ControlLineReset1(String strTable, String strField, String Input) throws Exception
    {   
           //Object Repository
           String varMPCControlLine = Mainframe_GlobalFunctionLib.GetText(strTable, strField);
           String varMPCCntlne = varMPCControlLine.toString();
     String[] strArray= varMPCCntlne.split(",");
     strArray[0] = Input;
     StringBuffer sb = new StringBuffer(strArray[0]);
     for (int i=1; i < strArray.length-3; i++)
           {
sb.append(","+strArray[i]);
           }
     String output = sb.toString();
           return output;
                                       
    }


	
}
