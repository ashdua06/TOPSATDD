package com.optum.topsuat.pages;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.optum.topsuat.utils.AutomationCore;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.DeleteRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.request.UpdateRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.DeleteResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.response.UpdateResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;
import com.rallydev.rest.util.Ref;

import java.io.RandomAccessFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Rally_TCResults_RestAPI {

	public static HashMap<String , String> hm = new HashMap<String , String >();
	
	public static void main(String[] args) throws URISyntaxException, IOException { 
	Rally_fetchExcel();
	
	int totalTCs = Integer.parseInt(hm.get("TotalTCs"));
	if(totalTCs==0){
		System.out.println("No Results to be updated for PRJ and US");
		return;
	}
	System.out.println("Total test case results to be updated is "+totalTCs);
	
	//Test case results data from sheet is "
	for(Map.Entry<String, String> st : hm.entrySet()){
		System.out.println(st.getKey()+" : "+st.getValue());
	}
	
	
	
	String host = "https://rally1.rallydev.com";
	AutomationCore core = new AutomationCore();
//	String username = "himanshu_dua@optum.com";
//    String password = "MANash@100";
    String wsapiVersion = "v2.0";
    String workspaceRef = "UHG";
    String applicationName = "RestExample_UpdateTestCaseResultsinCARally";
   // String apiKey = core.Read("API_CArally");
    
    //Himanshu Api key used in TC results
   // String apiKey = "_zO1PJgXTp2tuiqvdThB9wOc3joRHErrtjcGrIayJtQ";

    
    //Amit Sahani API key 
    String apiKey = "_JtMKOyQwyVWZudFwxF7yxcKwh8kqTsIwjbc4C2GI";
   
    RallyRestApi restApi = new RallyRestApi(new URI(host), apiKey);
    restApi.setWsapiVersion(wsapiVersion);
    restApi.setApplicationName(applicationName);
    
    for(int k = 1;k<=totalTCs;k++){
    	
 String RallyTestCaseId = hm.get("TC"+k);
 String verdict = hm.get("Status"+k);
 String build = hm.get("Build"+k);
 String attchmntpath = hm.get("Attachment"+k);

 //---------------If loop to update defect status-----------------------------------------------------------
 if(hm.get("TC"+k).substring(0, 2).equalsIgnoreCase("DE")){
	 String defectstatus = hm.get("DefectStatus"+k);
	 // Query to get the Defect id from Rally
	 System.out.println("Defect id is "+RallyTestCaseId);
	 
	 QueryRequest defectRequest = new QueryRequest("defect");
	 defectRequest.setFetch(new Fetch("FormattedID","Name"));
	 defectRequest.setWorkspace(workspaceRef);
	 defectRequest.setQueryFilter(new QueryFilter("FormattedID", "=", RallyTestCaseId));
	 QueryResponse defectResults = restApi.query(defectRequest);
	 if (!defectResults.wasSuccessful()){
		 System.out.println(hm.get("TC"+k)+"Defect Not Found");
	 }
	 JsonObject defectSuiteJsonObject = defectResults.getResults().get(0).getAsJsonObject();
     System.out.println("defectSuiteJsonObject" + defectSuiteJsonObject);
     String defectSuiteRef = defectSuiteJsonObject.get("_ref").getAsString(); 
	// String defectRef = defectResults.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
	 System.out.println("Defect ref is "+defectSuiteRef);
	 //Update Defect status
	 JsonObject newDefectUpdate = new JsonObject();
	// newDefectUpdate.addProperty("defect", defectSuiteRef);
	 newDefectUpdate.addProperty("State", defectstatus);
	
	 // Can update more details if reqd
	 /* newDefectUpdate.addProperty("Environment", "Test");
	 newDefectUpdate.addProperty("Severity", "Critical");
	 newDefectUpdate.addProperty("Priority", "None");
	 newDefectUpdate.addProperty("Name", "Defect from Rest API");*/
	 
	 UpdateRequest DefectCreateRequest = new UpdateRequest(defectSuiteRef, newDefectUpdate);
	 UpdateResponse DefectCreateResponse = restApi.update(DefectCreateRequest);
	 if(DefectCreateResponse.wasSuccessful()){
		 System.out.println("JsonObj defect update "+newDefectUpdate);
		 String Defectresultref;
			Defectresultref = DefectCreateResponse.getObject().get("_ref").getAsString();
			 System.out.println("Defect result ref is "+Defectresultref); 
	 }
	 else{
		 String[] updateErrors;
     	updateErrors = DefectCreateResponse.getErrors();
 		System.out.println("Error");
     	for (int i=0; i<updateErrors.length;i++) {
     		System.out.println(updateErrors[i]);
     	}
	 }
	
	//Attach screenshot
	 // Read In Image Content
	    String fullImageFile = attchmntpath;
	    String imageBase64String;
	    long attachmentSize;

	    // Open file
	    RandomAccessFile myImageFileHandle = new RandomAccessFile(fullImageFile, "r");

	    try {
	        // Get and check length
	        long longlength = myImageFileHandle.length();
	        System.out.println("Size of file is "+longlength);
	        // Max upload size for Rally attachments is 5MB
	        long maxAttachmentLength = 5120000;
	        if (longlength > maxAttachmentLength) throw new IOException("File size too big for Rally attachment, > 5 MB");

	        // Read file and return data
	        byte[] fileBytes = new byte[(int) longlength];
	        myImageFileHandle.readFully(fileBytes);
	        imageBase64String = Base64.encodeBase64String(fileBytes);
	        attachmentSize = longlength;

	        // First create AttachmentContent from image string
	        JsonObject myAttachmentContent = new JsonObject();
	        myAttachmentContent.addProperty("Content", imageBase64String);
	        CreateRequest attachmentContentCreateRequest = new CreateRequest("AttachmentContent", myAttachmentContent);
	        CreateResponse attachmentContentResponse = restApi.create(attachmentContentCreateRequest);
	        String myAttachmentContentRef = attachmentContentResponse.getObject().get("_ref").getAsString();
	        System.out.println("Attachment Content created: " + myAttachmentContentRef);            

	        // Now create the Attachment itself
	        JsonObject myAttachment = new JsonObject();
	        myAttachment.addProperty("ARTIFACT", defectSuiteRef);
	        myAttachment.addProperty("Content", myAttachmentContentRef);
	        myAttachment.addProperty("Name", RallyTestCaseId+" Screenshots");
	        myAttachment.addProperty("Description", "Attachment From REST");
	        myAttachment.addProperty("ContentType","docx");
	        myAttachment.addProperty("Size", attachmentSize);
	       // myAttachment.addProperty("User", userRef);          

	        CreateRequest attachmentCreateRequest = new CreateRequest("Attachment", myAttachment);
	        CreateResponse attachmentResponse = restApi.create(attachmentCreateRequest);
	        String myAttachmentRef = attachmentResponse.getObject().get("_ref").getAsString();
	        System.out.println("Attachment  created: " + myAttachmentRef);  

	        if (attachmentResponse.wasSuccessful()) {
	            System.out.println("Successfully created Attachment");
	        } else {
	            String[] attachmentContentErrors;
	            attachmentContentErrors = attachmentResponse.getErrors();
	                    System.out.println("Error occurred creating Attachment: ");
	            for (int i=0; i<attachmentContentErrors.length;i++) {
	                    System.out.println(attachmentContentErrors[i]);
	            }                   
	        }
	    } catch (Exception e) {
	            System.out.println("Exception occurred while attempting to create Content and/or Attachment: ");
	            e.printStackTrace();            
	    }
	 
	 continue;
	}
  
 
 
 
 // Query to get the id from Rally for the test case
 QueryRequest testCaseRequest = new QueryRequest("TestCase");
 testCaseRequest.setFetch(new Fetch("FormattedID","Name"));
 testCaseRequest.setWorkspace(workspaceRef);
 testCaseRequest.setQueryFilter(new QueryFilter("FormattedID", "=", RallyTestCaseId));
 QueryResponse testCaseQueryResponse = restApi.query(testCaseRequest);
 String testCaseRef = testCaseQueryResponse.getResults().get(0).getAsJsonObject().get("_ref").getAsString();

//---------------If loop to delete test case-----------------------------------------------------------
if(build.equalsIgnoreCase("delete")){
	JsonObject newTestCaseResult = testCaseQueryResponse.getResults().get(0).getAsJsonObject();
	System.out.println("deleting " + newTestCaseResult.get("FormattedID")) ;
    DeleteRequest deleteRequest = new DeleteRequest(newTestCaseResult.get("_ref").getAsString());
    DeleteResponse deleteResponse = restApi.delete(deleteRequest);
    continue;
}
 
 //Add a Test Case Result               
    JsonObject newTestCaseResult = new JsonObject();
    newTestCaseResult.addProperty("Verdict", verdict);
    java.util.Date date= new java.util.Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    String timestamp = sdf.format(date);

    newTestCaseResult.addProperty("Date", timestamp);
    newTestCaseResult.addProperty("Build", build);
    newTestCaseResult.addProperty("Notes", "Rest API Automated Test Run");
    newTestCaseResult.addProperty("TestCase", testCaseRef);
                   
    CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
    CreateResponse createResponse = restApi.create(createRequest);
    String testcaseresultref = createResponse.getObject().get("_ref").getAsString();
    
    
    //Attach screenshot
 // Read In Image Content
    String fullImageFile = attchmntpath;
    String imageBase64String;
    long attachmentSize;

    // Open file
    RandomAccessFile myImageFileHandle = new RandomAccessFile(fullImageFile, "r");

    try {
        // Get and check length
        long longlength = myImageFileHandle.length();
        System.out.println("Size of file is "+longlength);
        // Max upload size for Rally attachments is 5MB
        long maxAttachmentLength = 5120000;
        if (longlength > maxAttachmentLength) throw new IOException("File size too big for Rally attachment, > 5 MB");

        // Read file and return data
        byte[] fileBytes = new byte[(int) longlength];
        myImageFileHandle.readFully(fileBytes);
        imageBase64String = Base64.encodeBase64String(fileBytes);
        attachmentSize = longlength;

        // First create AttachmentContent from image string
        JsonObject myAttachmentContent = new JsonObject();
        myAttachmentContent.addProperty("Content", imageBase64String);
        CreateRequest attachmentContentCreateRequest = new CreateRequest("AttachmentContent", myAttachmentContent);
        CreateResponse attachmentContentResponse = restApi.create(attachmentContentCreateRequest);
        String myAttachmentContentRef = attachmentContentResponse.getObject().get("_ref").getAsString();
        System.out.println("Attachment Content created: " + myAttachmentContentRef);            

        // Now create the Attachment itself
        JsonObject myAttachment = new JsonObject();
        myAttachment.addProperty("TestCaseResult", testcaseresultref);
        myAttachment.addProperty("Content", myAttachmentContentRef);
        myAttachment.addProperty("Name", RallyTestCaseId+" Screenshots");
        myAttachment.addProperty("Description", "Attachment From REST");
        myAttachment.addProperty("ContentType","docx");
        myAttachment.addProperty("Size", attachmentSize);
       // myAttachment.addProperty("User", userRef);          

        CreateRequest attachmentCreateRequest = new CreateRequest("Attachment", myAttachment);
        CreateResponse attachmentResponse = restApi.create(attachmentCreateRequest);
        String myAttachmentRef = attachmentResponse.getObject().get("_ref").getAsString();
        System.out.println("Attachment  created: " + myAttachmentRef);  

        if (attachmentResponse.wasSuccessful()) {
            System.out.println("Successfully created Attachment");
        } else {
            String[] attachmentContentErrors;
            attachmentContentErrors = attachmentResponse.getErrors();
                    System.out.println("Error occurred creating Attachment: ");
            for (int i=0; i<attachmentContentErrors.length;i++) {
                    System.out.println(attachmentContentErrors[i]);
            }                   
        }
    } catch (Exception e) {
            System.out.println("Exception occurred while attempting to create Content and/or Attachment: ");
            e.printStackTrace();            
    }
    }
    
    restApi.close();
    
    }
	
	
	public static void Rally_fetchExcel(){
		int TCs=0;
		int j;
		String testname="";
		String ustory="";
		try {
			XSSFSheet ws;
			XSSFWorkbook wb;
			XSSFCell cell;
			XSSFRow row;
			String celldata = null;
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\TestData\\Rally_TestResults_RestAPI.xlsx");			
			System.out.println("Test Results sheet opened");
			wb = new XSSFWorkbook(fis);
			ws = wb.getSheet("TestResults");
			int totalrows = ws.getLastRowNum();
			System.out.println("total rows are "+totalrows);
			System.out.println("file loaded");
			
			for(int i=2;i<=totalrows;i++){
				testname= ws.getRow(i).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue();
				TCs=TCs+1;
				System.out.println("TC is "+ws.getRow(i).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("TC"+TCs, ws.getRow(i).getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("Build"+TCs, ws.getRow(i).getCell(2,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("Status"+TCs, ws.getRow(i).getCell(3,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("Attachment"+TCs, ws.getRow(i).getCell(4,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
				hm.put("DefectStatus"+TCs, ws.getRow(i).getCell(5,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			}
			if(TCs!=0)
				hm.put("TotalTCs", Integer.toString(TCs));
			else{
				System.out.println("No test cases found for PRJ  and US ");
			hm.put("TotalTCs", "0");
			}
			} 
		catch (Exception e) {
			System.out.println("Program stopped due to exception "+e);
		}
		
	}
	
	
}