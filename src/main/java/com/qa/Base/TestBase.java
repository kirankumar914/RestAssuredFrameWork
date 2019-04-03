package com.qa.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	
	public Properties prop;
	
	public int RESPONSE_STATUS_CODE_200=200;
	public int RESPONSE_STATUS_CODE_201=201;
	
	
	public TestBase()
	{
	 prop=new Properties();
		try {
			FileInputStream fip=new FileInputStream("C:\\Users\\NAVYA\\eclipse-workspace\\apiTesting\\src\\main\\java\\com\\qa\\Properties\\config.properties");
		    prop.load(fip);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}

}
