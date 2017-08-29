package com.citi.util;

import java.util.List;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class Utility {

	private static final Logger logger = Logger.getLogger("utilli");
	
	public static void updatePropertyFile(String propertyName, String propertyValue){
		Properties prop = new Properties();
		String propFileName = "stock.properties";
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(propFileName);
			prop.load(inputStream);
			prop.setProperty(propertyName, propertyValue);
			OutputStream out = new FileOutputStream(propFileName);
			prop.store(out, propFileName);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static List<String> getStockSymbol(String propertyName){
		Properties prop = new Properties();
		String propFileName = "stock.properties";
		InputStream inputStream = null;
		List<String> list = new ArrayList<String>();
		try {
			inputStream = new FileInputStream(propFileName);
			prop.load(inputStream);
			String value = (String) prop.get(propertyName);
			String[] fields = value.split(",");
			for(String field: fields){
				list.add(field);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Error loading stock.properties file");
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error loading stock.properties file");
			e.printStackTrace();
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
