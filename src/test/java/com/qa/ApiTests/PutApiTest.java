package com.qa.ApiTests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Base.TestBase;
import com.qa.Client.RestClient;
import com.qa.data.Users;

import junit.framework.Assert;

public class PutApiTest extends TestBase {
	
	TestBase testbase;
	String applicationurl;
	String serviceurl;
	String postMethodurl;
	RestClient client;
	CloseableHttpResponse closablehhtpResponse;
	
	@BeforeClass
	public void setup()
	{
		testbase=new TestBase();
		applicationurl=prop.getProperty("url");
		serviceurl=prop.getProperty("putserviceURL");
		postMethodurl= applicationurl + serviceurl;
	}
	
	@Test
	public void putAPiTest() throws JsonGenerationException, JsonMappingException, IOException, JSONException
	{
		client=new RestClient();
		HashMap<String,String> headerMap=new HashMap<String, String>();
		headerMap.put("Content-Type","application/json");
		
		//JackSONAPI= this is used for marshalling (i.e- Converting Java object to JSON Object)
	 
	 ObjectMapper mapper=new ObjectMapper();
	 
	 Users users=new Users("kiran","automationtester"); //expected users details
	 
	 //Java object to JSON Object convertion
	 mapper.writeValue(new File("C:\\Users\\NAVYA\\eclipse-workspace\\apiTesting\\src\\main\\java\\com\\qa\\data\\users.json"), users);
	
	// Object to JSON in String
	 String userJsonString =mapper.writeValueAsString(users);
	 System.out.println(userJsonString);
	 
	 //Post method 
	 
	 closablehhtpResponse = client.getPut(postMethodurl, userJsonString, headerMap);
	
	 //1.status code
	 
	 int statusCode=closablehhtpResponse.getStatusLine().getStatusCode();	 
	 Assert.assertEquals(RESPONSE_STATUS_CODE_200, statusCode);
	 
	 //2.JSON String
	 
	 String responseString=EntityUtils.toString(closablehhtpResponse.getEntity(),"UTF-8");
	 
	 JSONObject responseJson=new JSONObject(responseString);
	 System.out.println("Response of json is "+ responseJson);
	 
	 //UnMarshalling-JSON string to Java Object
	 
	Users usersObject= mapper.readValue(responseString,Users.class);// Response Users details
	System.out.println("Response of json to Java Object is "+ usersObject);
	
	Assert.assertEquals(users.getName(),usersObject.getName());
	Assert.assertEquals(users.getJob(),usersObject.getJob()); 
	System.out.println(usersObject.getUpdatedAt());
	 
	}
	

}
