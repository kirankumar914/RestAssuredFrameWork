package com.qa.ApiTests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.Base.TestBase;
import com.qa.Client.RestClient;
import com.qa.util.TestUtil;
public class GetTest extends TestBase{
	
	TestBase testbase;
	String applicationurl;
	String serviceurl;
	String getMethodurl;
	RestClient client;
	CloseableHttpResponse closablehhtpResponse;
	
	@BeforeClass
	public void setup()
	{
		testbase=new TestBase();
		applicationurl=prop.getProperty("url");
		serviceurl=prop.getProperty("serviceURL");
		getMethodurl= applicationurl + serviceurl;
	}
	
	
	@Test
	public void getTest() throws ClientProtocolException, IOException, JSONException
	{
		client=new RestClient();
		closablehhtpResponse=client.getMethod(getMethodurl);
		
		//1. get the status of the get method
				int statucode=closablehhtpResponse.getStatusLine().getStatusCode();
				System.out.println("status code is "+ statucode);
				Assert.assertEquals(statucode, RESPONSE_STATUS_CODE_200);
				
				//2. get the entire response and convert into JSON.
				String responseString=EntityUtils.toString(closablehhtpResponse.getEntity(), "UTF-8");
				JSONObject getJsonResponse=new JSONObject(responseString);
				System.out.println("get method response is "+ getJsonResponse);
				
				//3.get the value of Per_page
				String perPageValue=TestUtil.getValueByJpath(getJsonResponse, "/per_page");
				System.out.println("per page value is--->"+ perPageValue);
				Assert.assertEquals(Integer.parseInt(perPageValue),3);
				
				//4.get the value of total.
				String totalValue=TestUtil.getValueByJpath(getJsonResponse, "/total");
				System.out.println("Total value is--->"+ totalValue);
				Assert.assertEquals(Integer.parseInt(totalValue),12);
				 
				//5.get the first value from the data array
				
				String lastName=TestUtil.getValueByJpath(getJsonResponse, "/data[0]/last_name");
				System.out.println("last name of first set of data array is--->"+ lastName);
				Assert.assertEquals(lastName, "Bluth");
				//3. get all the headers of the get method
				Header[] headersArray=closablehhtpResponse.getAllHeaders();
				HashMap<String, String> allheaders=new HashMap<String, String>();
				
				for (Header header:headersArray)
				{
					allheaders.put(header.getName(), header.getValue());
				}
				
				System.out.println("all headers are"+ allheaders);
	}
	
	
	
	@Test
	public void getTestWithHeaders() throws ClientProtocolException, IOException, JSONException
	{
		client=new RestClient();
		HashMap<String,String> headerMap=new HashMap<String, String>();
		headerMap.put("Content-Type","application/json");
//		headerMap.put("usernsme","test");
//		headerMap.put("password","123");
//		headerMap.put("Auth-Token","12345S");
		
		
		closablehhtpResponse=client.getMethod(getMethodurl,headerMap);
		
		//1. get the status of the get method
				int statucode=closablehhtpResponse.getStatusLine().getStatusCode();
				System.out.println("status code is "+ statucode);
				Assert.assertEquals(statucode, RESPONSE_STATUS_CODE_200);
				
				//2. get the entire response.
				String responseString=EntityUtils.toString(closablehhtpResponse.getEntity(), "UTF-8");
				JSONObject getJsonResponse=new JSONObject(responseString);
				System.out.println("get method response is "+ getJsonResponse);
				
				//3.get the value of Per_page
				String perPageValue=TestUtil.getValueByJpath(getJsonResponse, "/per_page");
				System.out.println("per page value is--->"+ perPageValue);
				Assert.assertEquals(Integer.parseInt(perPageValue),3);
				
				//4.get the value of total.
				String totalValue=TestUtil.getValueByJpath(getJsonResponse, "/total");
				System.out.println("Total value is--->"+ totalValue);
				Assert.assertEquals(Integer.parseInt(totalValue),12);
				
				//5.get the first value from the data array
				
				String lastName=TestUtil.getValueByJpath(getJsonResponse, "/data[0]/last_name");
				System.out.println("last name of first set of data array is--->"+ lastName);
				Assert.assertEquals(lastName, "Bluth");
				//3. get all the headers of the get method
				Header[] headersArray=closablehhtpResponse.getAllHeaders();
				HashMap<String, String> allheaders=new HashMap<String, String>();
				
				for (Header header:headersArray)
				{
					allheaders.put(header.getName(), header.getValue());
				}
				
				System.out.println("all headers are"+ allheaders);
	}

}
