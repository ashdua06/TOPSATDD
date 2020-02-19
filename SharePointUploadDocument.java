package com.optum.topsuat.pages;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import com.microsoft.sharepoint.webservices.*;

public class SharePointUploadDocument extends SharePointBaseLib {

	
	public static Properties properties = new Properties();

	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {/*
		 
		 // Set root logger level to DEBUG and its only appender to A1.
		System.out.println("main...");
	
		try {		
			SharePointUploadDocument example = new SharePointUploadDocument();
			example.initialize();
			CopySoap p = example.getCopySoap();
			example.uploadDocument(p, properties.getProperty("copy.sourceFile"));
		} catch (Exception ex) {
			System.out.println("Error caught in main: "+ex);

		}
	*/
		
	}

	public Properties getProperties() {
		return properties;
	}

	


	protected void initialize() throws Exception {
		System.out.println("initialize()...");
		InputStream in = SharePointUploadDocument.class.getResourceAsStream("/SharePointUploadDocument.properties");
		properties.load(in);
		super.initialize();		
	}
}
