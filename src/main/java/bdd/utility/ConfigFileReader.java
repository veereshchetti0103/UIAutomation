package bdd.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import bdd.enums.DriverType;

public class ConfigFileReader extends GeneralUtils{
	
	private Properties properties;
	private final String propertyFilePath=System.getProperty("user.dir")+"/BDD/src/main/resources/Configuration.properties";

	
	public ConfigFileReader() {
		BufferedReader reader;
		try{
			reader=new BufferedReader(new FileReader(propertyFilePath));
			properties= new Properties();
			try {
				properties.load(reader);
				reader.close();
			}catch(IOException e) {
				printlog("Failed to load propertie file"+e,false,false,true);
				
			}
			
			
		}catch(FileNotFoundException e) {
			printlog("Configuration.properties not found in path "+propertyFilePath,false,false,true);
		}
	}
	
	
	public String getDriverPath() {
		try {
			String driverPath=properties.getProperty("driverPath");
			if(driverPath!=null) return driverPath;
			else throw new RuntimeException("DriverPath not specified in the configuration file.");
			
		}catch(Exception e) {
			printlog("Exception :: "+e,false,false,true);
		}
		return null;
	}
	

	public long getImpicitlyWait() {
		try {
			String implicitlyWait= properties.getProperty("impicityWait");
		if (implicitlyWait !=null && !implicitlyWait.isEmpty()) return Long.parseLong(implicitlyWait);
		else throw new RuntimeException("impicitlyWait not specified in the configuration.properties file");
		}catch(Exception e) {
			printlog("Exception :: "+e,false,false,true);
		}
		return 0;
	}


	public DriverType getBrowser() {
		try {
			String browserName=properties.getProperty("browser");
			if(browserName == null || browserName.equals("chrome"))return DriverType.CHROME;
			else if( browserName.equals("firefox"))return DriverType.FIREFOX;
			else if( browserName.equals("iexplorer"))return DriverType.IE;
			else if( browserName.equals("andriod"))return DriverType.ANDRIOD;
			else if( browserName.equals("chrome"))return DriverType.IOS;
			else throw new RuntimeException("Browser name key value in configuration.properties is not matched : "+browserName);
		}catch(Exception e) {
			printlog("Exception :: "+e,false,false,true);
		}
		return null;
	}

	public String getTestDataResourcePath() {
		try {
			String testDataResourcePath=properties.getProperty("testDataResourcesPath");
			if(testDataResourcePath != null || !testDataResourcePath.isEmpty())return testDataResourcePath;
			else throw new RuntimeException("test data resources path not specified in configuration.properties file for the key : "+testDataResourcePath);
			
		}catch(Exception e) {
			printlog("Exception :: "+e,false,false,true);
		}
		return null;
	}

	public String getporturl() {
		try {
			String porturl=properties.getProperty("porturl");
			if(porturl != null || !porturl.isEmpty())return porturl;
			else throw new RuntimeException("url not specified in configuration.properties file for the key : "+porturl);
			
		}catch(Exception e) {
			printlog("Exception :: "+e,false,false,true);
		}
		return null;
	}

	public String getReportConfigPath() {
		try {
			String reportConfigPath=properties.getProperty("reportConfigPath");
			if(reportConfigPath != null || !reportConfigPath.isEmpty())return reportConfigPath;
			else throw new RuntimeException("reportConfigPath not specified in configuration.properties file for the key : "+reportConfigPath);
			
		}catch(Exception e) {
			printlog("Exception :: "+e,false,false,true);
		}
		return null;
	}
	
	
	
	
}
