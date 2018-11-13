package bdd.stepdefinations;

import bdd.utility.FileReaderManager;
import bdd.utility.GeneralUtils;
import cucumber.api.java.en.Given;

public class CommonSteps extends GeneralUtils {

	@Given("^scenario (.+)$")
	public void scenario(String data) throws Throwable{
		
		testdata=FileReaderManager.getInstance().getExcelReader().getExcelData(airline, testcaseid, data);
		
	}
	
}
