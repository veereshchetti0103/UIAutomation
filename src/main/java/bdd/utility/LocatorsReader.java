package bdd.utility;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class LocatorsReader extends GeneralUtils{
	private Config locatorsConfig;
	
	public LocatorsReader() {
		try {
			locatorsConfig=ConfigFactory.load("locators");
		}catch(Exception e) {
			printlog("Locators file not found in resources folder :: "+e,false,false,true);
		}
	}

	
	public String getLocatorValue(String key) {
		try {
			if(locatorsConfig.getString(key)!=null) return locatorsConfig.getString(key);
			else throw new RuntimeException("Locator file not found in resources folder or no configuration setting found for key :: "+key);
			
		}catch(Exception e) {
			printlog("Exception ::"+e,false,false,true);
		}
		return null;
	}
	
	
}
