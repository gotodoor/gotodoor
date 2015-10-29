package com.test;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Stack;

import org.apache.http.client.utils.URLEncodedUtils;

public class QueryStringParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(URLEncodedUtils.parse("http://doogle:90/Download.aspx?ObjectID=%21V3%21UKDMPRODUCTION1%21C%21D$121881%21D", Charset.defaultCharset()));
		
		System.out.println(URLDecoder.decode("http://doogle:90/Download.aspx?ObjectID=!V3!UKDMPRODUCTION1!C!D$121881!D"));
		Stack st = new Stack<Integer>();
		st.push(1);
		st.push(2);
		st.push(3);
		System.out.println(st.toString());
		st.pop();
		System.out.println(st.toString());
	}

}
