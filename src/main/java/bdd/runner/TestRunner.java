package bdd.runner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;

import bdd.utility.GeneralUtils;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;


@CucumberOptions(
		features= {"src/main/resources/features"},
		glue= {"bdd.stepdefinations","bdd.hooks"},
		format= {"pretty",
				"html:target/cucumber-reports/cucumber-pretty",
				"json:target/cucumber-reports/cucumberTestReport.json",
				"rerun:target/cucumber-reports/rerun.txt"
				
		}
		)


public class TestRunner extends GeneralUtils{
	private TestNGCucumberRunner testngcucumberrunner;
	
	static{
		SimpleDateFormat dateFormat =  new SimpleDateFormat("dd-mm-yyyy-hh-mm-ss");
System.setProperty("cuurent.date.time", dateFormat.format(new Date()));
System.setProperty("ws.directory",System.getProperty("user.dir"));
	}
	
	
	@BeforeSuite
	public void beforSuite() {
		DOMConfigurator.configure("log4j.xml");
	extent=new ExtentReports(System.getProperty("user.dir")+"//ExtentReport//Report.html",true);
	extent.loadConfig(new File(System.getProperty("user.dir")+"//extent-config.xml"));
	}
	
	@BeforeClass(alwaysRun=true)
	public void setUpClass() {
		testngcucumberrunner= new TestNGCucumberRunner(this.getClass());
	}

	
	@Test(groups="Cucumber",description="Runs cucumber feature",dataProvider="features")
	public void feature(CucumberFeatureWrapper cucumberfeature) {
		testngcucumberrunner.runCucumber(cucumberfeature.getCucumberFeature());
	}
	
	@DataProvider
	public Object[][] features(){
		return testngcucumberrunner.provideFeatures();
	}
	
	@AfterClass(alwaysRun=true)
	public void tearDownClass() {
		testngcucumberrunner.finish();
	}
	
	public void aftersuite() {
		extent.flush();
		extent.close();
	}
}
