package com.optum.topsuat.pages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.URL;
import java.util.Properties;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.util.EntityUtils;

import com.microsoft.sharepoint.webservices.Copy;
import com.microsoft.sharepoint.webservices.CopyErrorCode;
import com.microsoft.sharepoint.webservices.CopyResult;
import com.microsoft.sharepoint.webservices.CopyResultCollection;
import com.microsoft.sharepoint.webservices.CopySoap;
import com.microsoft.sharepoint.webservices.DestinationUrlCollection;
import com.microsoft.sharepoint.webservices.FieldInformation;
import com.microsoft.sharepoint.webservices.FieldInformationCollection;
import com.microsoft.sharepoint.webservices.FieldType;

public abstract class SharePointBaseLib {



	protected abstract Properties getProperties();

	
	protected CopySoap getCopySoap() throws Exception {
		System.out.println("Creating a CopySoap instance...");

		Copy service = new Copy(new URL(getProperties().getProperty("copy.wsdl")),
				new QName("http://schemas.microsoft.com/sharepoint/soap/", "Copy"));
		CopySoap copySoap = service.getCopySoap();
		BindingProvider bp = (BindingProvider) copySoap;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, getProperties().getProperty("username"));
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, getProperties().getProperty("password"));
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getProperties().getProperty("copy.endpoint"));
		return copySoap;
	}

	protected void initialize() throws Exception {
		System.out.println("initialize()...");
		java.net.CookieManager cm = new java.net.CookieManager();
		java.net.CookieHandler.setDefault(cm);
		
		Authenticator.setDefault(new SharepointAuthenticator(getProperties()));
	}

	
	protected static byte[] readAll(File file) throws IOException {
		System.out.println("readAll()..." + file.getAbsolutePath());
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1)
				ous.write(buffer, 0, read);
		} finally {
			try {
				if (ous != null)
					ous.close();
			} finally {
				if (ios != null)
					ios.close();
			}
		}
		return ous.toByteArray();
	}
	

	protected  void uploadDocument(CopySoap port, String sourceUrl)	throws Exception {

		File f = new File(sourceUrl);
		System.out.println("Uploading: " + f.getName());

		String url = getProperties().getProperty("copy.location") + f.getName();
		DestinationUrlCollection destinationUrlCollection = new DestinationUrlCollection();
		destinationUrlCollection.getString().add(url);
		if(getProperties().getProperty("copy.location2") != null){
			 url = getProperties().getProperty("copy.location2") + f.getName();
			 destinationUrlCollection.getString().add(url);
		}
		

		FieldInformation titleFieldInformation = new FieldInformation();
		titleFieldInformation.setDisplayName("Title");
		titleFieldInformation.setType(FieldType.TEXT);
		titleFieldInformation.setValue(f.getName());

		FieldInformationCollection fields = new FieldInformationCollection();
		fields.getFieldInformation().add(titleFieldInformation);

		CopyResultCollection results = new CopyResultCollection();
		Holder<CopyResultCollection> resultHolder = new Holder<CopyResultCollection>(results);
		Holder<Long> longHolder = new Holder<Long>(new Long(-1));
		
		//make the call to upload
		port.copyIntoItems(sourceUrl, destinationUrlCollection, fields, readAll(f), longHolder,resultHolder);
		
		//does not seem to change based on different CopyResults
		System.out.println("Long holder: " + longHolder.value);
		
		//do something meaningful here
		for (CopyResult copyResult : resultHolder.value.getCopyResult()) {	
			System.out.println("Destination: " + copyResult.getDestinationUrl());
			System.out.println("Error Message: " + copyResult.getErrorMessage());
			System.out.println("Error Code: " + copyResult.getErrorCode());
			if(copyResult.getErrorCode() != CopyErrorCode.SUCCESS)
				throw new Exception("Upload failed for: " + copyResult.getDestinationUrl() + " Message: " 
						+ copyResult.getErrorMessage() + " Code: " +   copyResult.getErrorCode() );
		}

	}
}
