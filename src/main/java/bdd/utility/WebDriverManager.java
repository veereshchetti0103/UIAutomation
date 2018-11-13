package bdd.utility;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import bdd.enums.DriverType;

public class WebDriverManager extends GeneralUtils{
	
	private static WebDriverManager webDriverManger;
	private static WebDriver driver;
	private static DriverType driverType;
	private static final String CHROME_DRIVER_PROPERTY="webdriver.chrome.driver";
	private static Logger logger = Logger.getLogger(WebDriverManager.class);

public WebDriverManager() {
	driverType=FileReaderManager.getInstance().getConfigReader().getBrowser();
}

public static WebDriverManager getInstance() {
	return(webDriverManger == null)? new WebDriverManager() : webDriverManger;
}

public WebDriver getDriver() {
	if(driver == null) driver = createDriver();
	return driver;
}

private WebDriver createDriver() {
	switch(driverType) {
	
	case FIREFOX:
	try{
		logger.info("Open FF browser");
		driver = new FirefoxDriver();
	}catch(Exception e) {
		printlog("Failed to open FF browser :: "+e,false,false,true);
	}
	break;
	case CHROME:
		try{
			logger.info("Open Chrome browser");
		System.setProperty(CHROME_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getDriverPath());
		driver= new ChromeDriver();	
		}catch(Exception e) {
			printlog("Failed to open chrome browser :: "+e,false,false,true);
		}	
		break;
	case IE:
		try{
			logger.info("Open IE browser");
driver = new InternetExplorerDriver();
		}catch(Exception e) {
			printlog("Failed to open IErowser :: "+e,false,false,true);
		}	
		break;
	case ANDRIOD:
		break;
	case IOS:
		break;
	}
	try {
	driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImpicitlyWait(), TimeUnit.SECONDS);
	driver.manage().window().setSize(new Dimension(400, 800));
	return driver;
	}catch(Exception e) {
		printlog("Failed to find the driver instance :: "+e,false,false,true);
	return null;
	}
	
}

public void closeDriver() {
	try {
		logger.info("Closing browser window");
		driver.quit();
		driver=null;
		
	}catch(Exception e) {
		printlog("Failed to close the driver instance/no driver instance found to close :: "+e,false,false,true);
	}
}


}
