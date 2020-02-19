package com.optum.topsuat.stepdefinitions;

import java.io.File;
import java.net.URI;

import org.testng.annotations.AfterSuite;

import com.hp.lft.sdk.Desktop;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.SDK;
import com.hp.lft.sdk.java.WindowDescription;
import com.hp.lft.sdk.te.Field;
import com.hp.lft.sdk.te.FieldDescription;
import com.hp.lft.sdk.te.Keys;
import com.hp.lft.sdk.te.Screen;
import com.hp.lft.sdk.te.ScreenDescription;
import com.optum.topsuat.functionlib.Mainframe_GlobalFunctionLib;
import com.optum.topsuat.pages.*;
import com.optum.topsuat.reporting.ExtentManager;
import com.optum.topsuat.reporting.ExtentUtilities;
import com.optum.topsuat.utils.AutomationCore;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Base_StepDefinitions{

	public static Scenario scenario_val;
	public static String region_name="";
	public static String film_offc="";
	public static String adj_offc="";

	@Before
	public void LaunchApplication(Scenario scenario) throws Throwable {

		ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
		config.setServerAddress(new URI("ws://localhost:5095"));
		SDK.init(config);
		scenario_val = scenario;
		// this.scenario = scenario;
		String scenario_name = scenario_val.getName();

		ExtentUtilities.initilaizeExtentReport();
		ExtentManager.setExtentReportName();
		ExtentUtilities.createParentTest(scenario.getName());
		// System.out.println("Scenario name is" +scenario_name);
		// File file = new File("C:\\ProgramData\\IBM\\Personal
		// Communications\\TN3270.WS");
		// java.awt.Desktop.getDesktop().open(file);
		// Thread.sleep(10000);
		// Raghu modifyin the code and developing login sceenario
	}

	@Given("^User login with valid inputs \"(.*)\" and \"(.*)\" and \"(.*)\" and \"(.*)\"$")
	public void user_login_with_valid_inputs_and_and_and(String arg1, String arg2, String arg3, String arg4)
			throws Throwable {
		
		region_name=arg1;
		film_offc=arg2;
		adj_offc=arg3;
		System.out.println("region name in user login :"+region_name+" and off and adj is "+film_offc+" "+adj_offc);
		// User Login Program with different input Data
		//Tops_UserLogin.UserLogin(arg1, arg2, arg3, arg4);
		Tops_UserLogin.UserLogin_new(arg1, arg2, arg3, arg4);
		// extra line added by ayarrai;
		// one more comment by ayarrai;
	}
	
	

	
	/*@Given("^User logoff TSO$")
		public void user_TSO_logoff() throws Throwable{
			TSO_UserLogin.TSO_logoff();
		}*/
	
	
	@When("^User Enters in MEI screen$")
	public void user_Enters_in_MEI_screen() throws Throwable {
		EMC.MEI_Validation();

	}

	@When("^User Enters StartFLN value \"(.*)\"$")
	public void user_Enters_StartFLN_value(String arg1) throws Throwable {
		EMC.EMC_Screen_Validation();
		// Mainframe_GlobalFunctionLib.getScreenID();
		EMC.EMC_Validation(arg1);
	}

	@When("^Enter the value and press Enter to access the EDS1 screen \"(.*)\"$")
	public void enter_the_value_and_press_Enter_to_access_the_EDS_screen(String arg1) throws Throwable {
		EDS1_Facility.TotalCharge_Validation(arg1);
		EDS1_Facility.getICN();
	}

	@Then("^Enter the value and press Enter to access the EDS1 screen1 \"(.*)\"$")
	public void enter_the_value_and_press_Enter_to_access_the_EDS_screen1(String arg1) throws Throwable {
		EDS1_Professional.EDS1Screen_Validation();
	}

	@When("^Enter the screen value and press Enter to access the EDS3 screen \"(.*)\"$")
	public void enter_the_screen_value_and_press_Enter_to_access_the_EDS_screen(String arg1) throws Throwable {
		// EDS3.EDS3Screen_Validation();
		Mainframe_GlobalFunctionLib.getScreenID();
		// EDS3.ProvCntlLine_Validation(arg1);

	}

//	@Then("^Goto EDS 6 screen and select the First Service line to open EDS half screen$")
//	public void Goto_EDS_6_screen_and_select_the_First_Service_line_to_open_EDS_half_screen() throws Throwable {
//		EDS6_5.EDS6_5Navigation_for_First_Service_line();
//	}

	@Then("^Validate Diagnosis Code vaue \"(.*)\"$")
	public void validate_Diagnosis_Code_value(String arg1) throws Throwable {
		MPC.DiagnoisCode_Validation(arg1);
	}

	@Then("^Validate RemarkCode value \"([^\"]*)\"$")
	public void validate_RemarkCode_value(String arg1) throws Throwable {
		MPC.RemarkCode_Validation(arg1);
	}

	@Then("^Validate Service Codes on multiple lines \"([^\"]*)\" and \"([^\"]*)\"$")
	public void validate_Line_and_Line_Service_Codes_and(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		MPC.ServiceCodes_Validation(arg1, arg2);
	}

	@Then("^Validate the Claim Payment validation is successfull$")
	public void validate_the_Claim_Payment_validation_is_successfull() throws Throwable {
		MPC.PaymentValidation1();
	}

	@Then("^Validate the value in RPE field value \"([^\"]*)\"$")
	public void validate_the_value_in_RPE_field_value(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}

	@Then("^User Logoff$")
	public void user_Logoff() throws Throwable {
		System.out.println("region name in logoff :"+region_name+" and off and adj is "+film_offc+" "+adj_offc);
		//Tops_UserLogOff.screenclear();
		ExtentUtilities.extentFlush();
	}

	/*@After
	public void Cleanup() throws Throwable {
		try {
			System.out.println("value in after suite "+Mainframe_GlobalFunctionLib.GetText("Logoff_Screen", "LOGOFF"));
if(Mainframe_GlobalFunctionLib.GetText("Logoff_Screen", "LOGOFF").contains("UHC0010")){
			System.out.println("System logged out successfully without exception");}
else{
			System.out.println("System logged out successfully with some exception in test");
Tops_UserLogOff.screenclear();
ExtentUtilities.extentFlush();
}
		} catch (Exception e) {
			System.out.println("System logged out successfully with some exception in catch block");
			Tops_UserLogOff.screenclear();
			ExtentUtilities.extentFlush();
		}	
		
	}*/
	
	
	/*@AfterSuite
	public void Cleanup() throws Throwable {
		AutomationCore core = new AutomationCore();
		
		try {
			//System.out.println("value in after suite is "+Mainframe_GlobalFunctionLib.GetText("Logoff_Screen", "LOGOFF"));
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
		
		core.Update("regionName","xyz");
		core.Update("filmOffc","000");
		core.Update("adjOffc","000");
		System.out.println("Values in Config file in After suite after updation is"+core.Read("regionName")+" "+core.Read("filmOffc")+" "+core.Read("adjOffc"));
		}*/
	
}
