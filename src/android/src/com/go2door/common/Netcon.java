package com.go2door.common;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;


public class Netcon {
	
//	String url="";
	 
	
	public Netcon(String url) {
		super();
//		this.url = url;
	}

	public static String getValuefromUrl(String url) throws TimeoutException
	{
		try
		{
			
			
		  final int TIMEOUT_MILLISEC = 500000;
          HttpParams httpParams = new BasicHttpParams();
          HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
          HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
          HttpConnectionParams.setTcpNoDelay(httpParams, true);
          HttpClient httpClient = new DefaultHttpClient(httpParams);
			//DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
//			HttpPost httpPost= new  HttpPost(url);
		    ResponseHandler<String> resHandler = new BasicResponseHandler();
			String page = httpClient.execute(httpGet, resHandler);
//		    String page = httpClient.execute(httpPost, resHandler);
//			httpPost.addHeader("client", "json");
			Log.v("PAGE",page);
			return page;
		}
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String zero="zero";
			return zero;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String zero="zero";
			return zero;
		}
		
	}
	
		
	
}
