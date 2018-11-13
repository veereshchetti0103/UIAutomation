package bdd.stepdefinations;

import bdd.pages.CalendarPage;
import bdd.utility.FileReaderManager;
import bdd.utility.GeneralUtils;
import bdd.utility.WebDriverManager;
import cucumber.api.java.en.Given;

public class CalenderPageSteps extends GeneralUtils{

@Given("^I am on caender Page$")
	public void i_am_on_calender_page(String arg)throws Throwable{
		try {
			WebDriverManager.getInstance().getDriver().get(FileReaderManager.getInstance().getConfigReader().getporturl()+arg);
			CalendarPage.calenderPagehandler();
			printlog(this.getClass().getMethod("i_am_on_calender_page", String.class).getAnnotation(Given.class).value().replace("^"," ").replace("$", " "),true,true);
		}catch(Exception e) {
			printlog(this.getClass().getMethod("i_am_on_calender_page", String.class).getAnnotation(Given.class).value().replace("^"," ").replace("$", " ")+"Exception ::"+e,false,true,true);
		}
	}
	
	
}
