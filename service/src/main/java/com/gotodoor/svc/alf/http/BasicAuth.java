package com.gotodoor.svc.alf.http;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class BasicAuth {
	
	public static void downloadFileWithAuth(String urlStr, String user, String pass, String outFilePath) {
	    try {
	        // URL url = new URL ("http://ip:port/download_url");
	        URL url = new URL(urlStr);
	        String authStr = user + ":" + pass;
	  
	        String authEncoded = Base64.encodeBase64String(authStr.getBytes());
	        System.out.println(authEncoded);

	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setDoOutput(true);
	        connection.setRequestProperty("Authorization", "Basic " + authEncoded);

	        File file = new File(outFilePath);
	        InputStream in = (InputStream) connection.getInputStream();
	        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
	        for (int b; (b = in.read()) != -1;) {
	            out.write(b);
	        }
	        out.close();
	        in.close();
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public static void main(String args[]) throws ClientProtocolException, IOException{
		
		@SuppressWarnings({ "deprecation", "resource" })
		HttpClient httpclient = new DefaultHttpClient();
		 String encoding = Base64.encodeBase64String ("alfcmadm:alfcmadm@oct14".getBytes());
		 
		 HttpGet httpget = new HttpGet("https://ciscoshare-services.cisco.com");
		 httpget.setHeader("Authorization", "Basic " + encoding);
		 
		 HttpResponse response;
         response = httpclient.execute(httpget);
         HttpEntity entity = response.getEntity();

         System.out.println("----------------------------------------");
         System.out.println(response.getStatusLine());
         if (entity != null) {
             System.out.println("Response content length: " + entity.getContentLength());
             //System.out.println(entity.getContent());
             System.out.println(EntityUtils.toString(entity));
         }
         if (entity != null) {
             entity.consumeContent();
         }

         httpclient.getConnectionManager().shutdown();  
		
	
	}
}
