package com.optum.topsuat.pages;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.util.EntityUtils;

public class SharepointCreateFolder {

	public static void main(String[] args) {
		System.out.println("main...");
		try {
			String baseurl="http://app40-07.uhg.com/sites/BP/CA/ORS/Appeal%20RPA%20%20Detail/Test/";
			String folderName="dua1";
			makeFolder(baseurl,folderName);
			System.out.println("folder created");
		} catch (Exception e) {
			System.out.println("Error caught in main: "+e);
		}
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public static HttpResponse makeFolder(String baseurl,String folderName) throws Exception{
		String url=baseurl+folderName;
		System.out.println("Folder path is "+url);
	DefaultHttpClient httpClient = new DefaultHttpClient();
	httpClient.getCredentialsProvider().setCredentials(
	        AuthScope.ANY,
	        new NTCredentials("hdua", "password",
	                        "", "MS"));

	BasicHttpRequest httpPost = new BasicHttpRequest("MKCOL", url);
	HttpUriRequest httpUriRequest = new RequestWrapper(httpPost);

    HttpResponse status = httpClient.execute(httpUriRequest);
    EntityUtils.consume(status.getEntity());
    return status;
}
}
