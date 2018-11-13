package bdd.hooks;

import org.apache.log4j.Logger;

import bdd.utility.GeneralUtils;
import bdd.utility.WebDriverManager;
import cucumber.api.Scenario;
import cucumber.api.java.Before;

public class ServiceHooks extends GeneralUtils{
private static Logger logger = Logger.getLogger(ServiceHooks.class);


@Before
public void initializeTest(Scenario scenario) {
	logger.info("******************** Start Test : "+scenario.getName()+"************************");
	
	test=extent.startTest(scenario.getName(), scenario.getName());
	String str=scenario.getName();
	String[] data=str.split("_");
	airline=data[0];
	testcaseid=data[1];
	WebDriverManager.getInstance().getDriver();
	
}
	
	
	public void afterScenario(Scenario scenario) {
		WebDriverManager.getInstance().closeDriver();
		extent.endTest(test);
		logger.info("******************** End Test : "+scenario.getName()+"************************");
		
	}
	
	

}
