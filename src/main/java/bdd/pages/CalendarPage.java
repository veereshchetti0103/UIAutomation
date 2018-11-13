package bdd.pages;

import org.apache.log4j.Logger;

import bdd.utility.FileReaderManager;
import bdd.utility.GeneralUtils;

public class CalendarPage extends GeneralUtils{
private static Logger logger=Logger.getLogger(CalendarPage.class);


private static void clickContinueBtn(String locator) {
	click(locator);
	fluentWaitForCondition().until(textNotPresent(" Select Dates"));
}

private static void clickCalendarMatrixCell(String locator) {
	click(locator);
	fluentWaitForCondition().until(textNotPresent(" Select Dates"));
}
	

//***********************************************************
public static void calenderPagehandler() {
	
	if(GeneralUtils.quickVerifyElement(FileReaderManager.getInstance().getLocatorsReader().getLocatorValue("CALENDERPAGE.CONTINUE"))) {
		clickContinueBtn(FileReaderManager.getInstance().getLocatorsReader().getLocatorValue("CALENDERPAGE.CONTINUE"));
	}else {
		clickCalendarMatrixCell(FileReaderManager.getInstance().getLocatorsReader().getLocatorValue("CALENDERPAGE.LOWESTPRICEMATRIXCELL"));
	}
	
}



}
