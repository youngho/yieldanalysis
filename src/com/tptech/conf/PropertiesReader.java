package com.tptech.conf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.tptech.util.Utils;
public class PropertiesReader {
 

	 public static String getProperties(String key) {
		 
		 String filename = "";
		 String host = Utils.getMachineName();
		 Properties ppt = new Properties();
		 String currentDir=System.getProperty("user.dir");
		 if(host.startsWith("BRZ-D20164")){
			 filename = "local_smtoscar.properties";
		 }else{
			 filename = "smtoscar.properties";
		 }

		 String pptFile = currentDir + "/conf/" + filename;
	  
		 try {
			 FileInputStream in = new FileInputStream(pptFile);
			 ppt.load(in);
			 in.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 String result = ppt.getProperty(key);
		 return result;
	 }
 

	 public void setProperties(String propertyFile, String keyname, String value) {
	  try {
	   Properties props = new Properties();
	   FileInputStream fis = new FileInputStream(propertyFile);
	   props.load(fis);
	   props.setProperty(keyname, value);
	   props.store(new FileOutputStream(propertyFile), "");
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }
	 
}
