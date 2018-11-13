package bdd.pages;

import org.testng.log4testng.Logger;

import bdd.utility.FileReaderManager;
import bdd.utility.GeneralUtils;

public class CommonBoundPage extends GeneralUtils{
	private static Logger logger=  Logger.getLogger(CommonBoundPage.class);
	
	private static void expandRecommendation(String locator,int n) {
		
		logger.info("CommonBoundPage : Expand flight recommendation");
		findAllElement(locator).get(n).click();
		wait(2);
	}
	
private static void clickNthFarefamily(String locator,int n) {
		
		logger.info("CommonBoundPage : click"+n+"th family");
		findAllElement(locator).get(n).click();
		fluentWaitForCondition().until(textNotPresent("Sekect flight"));
	}
	

//******************************************************************************************************

public static void selectOutBoundFlight() {
	fluentWaitForCondition().until(textPresent("Sekect flight"));
	wait(2);
	expandRecommendation(FileReaderManager.getInstance().getLocatorsReader().getLocatorValue("COMMONBOUNDPAGE.FTYPE"),0);
	clickNthFarefamily(FileReaderManager.getInstance().getLocatorsReader().getLocatorValue("COMMONBOUNDPAGE.FFamily"),0);
}


}
