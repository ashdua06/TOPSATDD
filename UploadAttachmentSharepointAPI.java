package com.optum.topsuat.pages;

public class UploadAttachmentSharepointAPI {
	
public static void main(String[] args) throws Exception{

	//Provide these details to function to upload attachment
	String url ="http://cqa-sp.optum.com//CQA%20Yellow%20Team%201";
	String filepath="C:\\Users\\hdua\\Desktop\\UTR Application Automation.docx";
	String folderpath="/TOPS QA/Regression Automation/Automated Weekend Checkout Reports/";
	String attachmntTitle="AutomationExample";
	
	UploadAttachmentSharepointSelenium.uploadScreenshotSharepoint(url, filepath, folderpath,attachmntTitle);
}

}