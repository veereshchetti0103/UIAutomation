package bdd.utility;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.codoid.products.fillo.Recordset;
import com.google.common.base.Predicate;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class GeneralUtils {
	
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String airline;
	public static String testcaseid;
	public static Recordset testdata;
	public static Logger logger = Logger.getLogger(GeneralUtils.class);
	
	
	public static By getLocator(String locator) {
		By by=null;
		String[] identifier=locator.split(":");
		switch(identifier[0]) {
		
		case "XPATH":
			by=By.xpath(identifier[1]);
			break;
		case "ID":
			by=By.id(identifier[1]);
			break;
		case "NAME":
			by=By.name(identifier[1]);
			break;
			default:
				System.out.println("");
				break;
		}
		return by;
		
	}
	
	public static void waitForPresenceOfElement(String xpath) {
		try{
			logger.info("Waiting for the presence of element");
			fluentWaitForCondition().until(ExpectedConditions.presenceOfElementLocated(getLocator(xpath)));
		}catch(Exception e) {
			throw new RuntimeException("Exception in waiting for presence of element : "+xpath+"Exception message :"+e);
		}
	}
	

	public static FluentWait<WebDriver> fluentWaitForCondition(){
		logger.info("Waiting for condition");
		return new FluentWait<WebDriver> (WebDriverManager.getInstance().getDriver()).withTimeout(60,TimeUnit.SECONDS).pollingEvery(10,TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class,ElementNotVisibleException.class);
	}
	
	
	
	public static boolean quickVerifyElement(String xpath) {
		try{
			
			fluentWaitForCondition().until(angularHasFinishedProcessing());
			return WebDriverManager.getInstance().getDriver().findElement(getLocator(xpath)).isDisplayed();
		}catch(Exception e) {
			return false;
		}
	}
	
	public static ExpectedCondition<Boolean> angularHasFinishedProcessing() {
		return new ExpectedCondition<Boolean> () {
			public Boolean apply(WebDriver driver) {
				return Boolean.valueOf(((JavascriptExecutor)driver).executeScript("return(window.angular !== undefined) && (angular.element(document).injector() !==undefined) && (angular.element(document).injector().get('$http').pendingRequests.length ===0)").toString());
			}
		};
	}

	public static ExpectedCondition<Boolean> elementDisplayed(final String locator) {
		return new ExpectedCondition<Boolean> () {
			public Boolean apply(WebDriver driver) {
				return (elementIsCurrentlyVisible(locator));
			}
			
			public String toString() {
				return "Expecting element is displayed : "+locator.toString();
			}
		};
	}

	protected static Boolean elementIsCurrentlyVisible(String locator) {
		try{
			logger.info("Checking for element visibe");
		List<WebElement> matchingelement=WebDriverManager.getInstance().getDriver().findElements(getLocator(locator));
		return (!matchingelement.isEmpty() && matchingelement.get(0).isDisplayed());
			
		}catch(NoSuchElementException nosucheement) {
			return false;
		}catch(StaleElementReferenceException staleelement) {
			return false;
		}	
		
	}

	public static ExpectedCondition<Boolean> elementNotDisplayed(final String locator) {
		return new ExpectedCondition<Boolean> () {
			public Boolean apply(WebDriver driver) {
				return (elementIsNotDisplayed(locator));
			}
			
			public String toString() {
				return "Expecting element not displayed : "+locator.toString();
			}
		};
	}
	
	protected static Boolean elementIsNotDisplayed(String locator) {
		List<WebElement> matchingElements = findAllElement(locator);
		for(WebElement matchingElement :matchingElements ) {
			if(elementIsCurrentlyVisible(locator)) {
				return false;
			}
		}
		return true;
	}
	
	
	

	public static List<WebElement> findAllElement(String locator) {
		fluentWaitForCondition().until(angularHasFinishedProcessing());
		
		return WebDriverManager.getInstance().getDriver().findElements(getLocator(locator));
	}

	public static ExpectedCondition<Boolean> textPresent(final String expectedText) {
		return new ExpectedCondition<Boolean> () {
			public Boolean apply(WebDriver driver) {
				return (containsText(expectedText));
			}
			
			public String toString() {
				return "Expecting text present : "+expectedText +"'";
			}
		};
	}
	
	protected static Boolean containsText(String expectedText) {
		fluentWaitForCondition().until(angularHasFinishedProcessing());
		return  WebDriverManager.getInstance().getDriver().findElement(By.tagName("body")).getText().contains(expectedText);
	}

	public static ExpectedCondition<Boolean> textNotPresent(final String expectedText) {
		return new ExpectedCondition<Boolean> () {
			public Boolean apply(WebDriver driver) {
				return !containsText(expectedText);
			}
			
			public String toString() {
				return "Expecting text not present : "+expectedText+"'";
			}
		};
	}
	
	public static ExpectedCondition<Boolean> titlePresent(final String expectedTitle) {
		return new ExpectedCondition<Boolean> () {
			public Boolean apply(WebDriver driver) {
				return titleIs(expectedTitle);
			}
			
			public String toString() {
				return "Expecting title present :' "+expectedTitle+"', but found '"+ WebDriverManager.getInstance().getDriver().getTitle() +"'instead.'";
			}
		};
	}
	
	protected static Boolean titleIs(String expectedTitle) {
		
		return (( WebDriverManager.getInstance().getDriver().getTitle()!=null) && ( WebDriverManager.getInstance().getDriver().getTitle().equals(expectedTitle)));
	}

	public static ExpectedCondition<Boolean> titleNotPresent(final String expectedTitle) {
		return new ExpectedCondition<Boolean> () {
			public Boolean apply(WebDriver driver) {
				return !titleIs(expectedTitle);
			}
			
			public String toString() {
				return "Expecting title present :' "+expectedTitle+"', but found '"+ WebDriverManager.getInstance().getDriver().getTitle() +"'instead.'";
			}
		};
	}
	
	
	
	public static WebElement findElement(String locator) {
		WebElement element=null;
		try{
			fluentWaitForCondition().until(angularHasFinishedProcessing());
			element=WebDriverManager.getInstance().getDriver().findElement(getLocator(locator));
			
		}catch(Exception e) {
			printlog("Exception in finding the element :"+locator+" Exception message : "+e,false,true,true);
		}
	return element;
	}
	
	public static void jsClick(String locator) {
		try{
			((JavascriptExecutor)WebDriverManager.getInstance().getDriver()).executeScript("argument[0].click();", findElement(locator));
			
		}catch(Exception e) {
			printlog("Exception on click element :"+locator+" Exception message : "+e,false,true,true);
		}
			
	}
	
	public static void jsTypeInto(String locator,String value) {
		try{
			((JavascriptExecutor)WebDriverManager.getInstance().getDriver()).executeScript("argument[0].value='"+value+"';", findElement(locator));
			
		}catch(Exception e) {
			printlog("Exception on click element :"+locator+" Exception message : "+e,false,true,true);
		}
			
	}
	
	
	
	public static boolean elementIsPresent(final By byElementCriteria) {
		boolean isDisplayed=true;
		try{
			
			List<WebElement> matchingElements =WebDriverManager.getInstance().getDriver().findElements(byElementCriteria);
			if(matchingElements.isEmpty()) {
				isDisplayed=false;
			}
			
		}catch(NoSuchElementException nosuchElement) {
			isDisplayed=false;
		}
		return isDisplayed;
	}
	
	
	public static void wait(int timeToWaitInSeconds) {
		try {
			long timeToWaitInMs= (long)(timeToWaitInSeconds*1000);
			Thread.sleep(timeToWaitInMs);
			
			
		}catch(Exception e) {
			printlog("Exception on wait "+e,false,true,true);
		}
		
	}
	
	
	
	public void typeInto(String xpath,String testdata ) {
		try {
			fluentWaitForCondition().until(angularHasFinishedProcessing());
			WebDriverManager.getInstance().getDriver().findElement(getLocator(xpath)).sendKeys(testdata);
			
		}catch(Exception e) {
			try {
				getinViewPort(xpath);
				jsTypeInto(xpath, testdata);
			}catch(Exception e1) {
				printlog("Exception in entering vaue for element "+xpath +"Exception : "+e1,false,true,true);
			}
			
		}
				
	}
	
	public void tab(String xpath) {
		try {
			fluentWaitForCondition().until(angularHasFinishedProcessing());
			WebDriverManager.getInstance().getDriver().findElement(getLocator(xpath)).sendKeys(Keys.TAB);
			
		}catch(Exception e) {
			printlog("Exception in tab "+xpath +"Exception : "+e,false,true);
		}
	}
	
	public static void click(String xpath) {
		try{
			fluentWaitForCondition().until(angularHasFinishedProcessing());
			WebDriverManager.getInstance().getDriver().findElement(getLocator(xpath)).click();
			
		}catch(Exception e) {
			
			try {
				getinViewPort(xpath);
			jsClick(xpath);	
			}catch(Exception e1) {
				printlog("Exception in click  for element "+xpath +"Exception : "+e1,false,true,true);
			}
			
			
		}
	}
	
	public static void selectByValue(String xpath,String Value) {
		try{
			
			fluentWaitForCondition().until(angularHasFinishedProcessing());
			Select select =new Select(WebDriverManager.getInstance().getDriver().findElement(getLocator(xpath)));
			select.selectByValue(Value);
		}catch(Exception e) {
			try {
			getinViewPort(xpath);
			Select select =new Select(WebDriverManager.getInstance().getDriver().findElement(getLocator(xpath)));
			select.selectByValue(Value);
			}catch(Exception e1) {
				printlog("Exception in selecting  for element "+xpath +"Exception : "+e1,false,true,true);
			}
			}
	}

	private static void getinViewPort(String xpath) {
	try {
		Coordinates cor = ((Locatable)WebDriverManager.getInstance().getDriver().findElement(getLocator(xpath))).getCoordinates();
		cor.inViewPort();
	}catch(Exception e) {
		printlog("Exception on getinViewPort for element "+xpath +"Exception : "+e,false,true,true);
	}
	}
	
	public static boolean quickVerifyElement1(String xpath) {
		try{
		fluentWaitForCondition().until(angularHasFinishedProcessing());
	return WebDriverManager.getInstance().getDriver().findElement(getLocator(xpath)).isDisplayed();
		}catch(Exception e) {
			return false;
		}
	}
	


	public static void printlog(String stepDescription,boolean stepStatus,boolean snapForPass,boolean exitTest) {
		if(stepStatus) {
			if(snapForPass) {
				test.log(LogStatus.PASS, stepDescription + " - PASS",test.addScreenCapture(getSnap()));
				logger.info(stepDescription + " - PASS");
			}else {
				test.log(LogStatus.PASS, stepDescription + " - PASS","");
				logger.info(stepDescription + " - PASS");
				
			}
			
		}else {
			if(exitTest) {
				if(snapForPass) {
					test.log(LogStatus.FAIL, stepDescription + " - FAILED.Stopping the execution of current scenario",test.addScreenCapture(getSnap()));
				Assert.assertTrue(false,stepDescription + "Check failed and stopping the execution of current scenario");
				logger.error(stepDescription+ " - FAIL");
				}else {
					test.log(LogStatus.FAIL, stepDescription + " - FAILED.Stopping the execution of current scenario","");
					Assert.assertTrue(false,stepDescription + "Check failed and stopping the execution of current scenario");
					logger.error(stepDescription+ " - FAIL");
				}
				
			}else {
				test.log(LogStatus.FAIL, stepDescription + " - FAIL",test.addScreenCapture(getSnap()));
				logger.error(stepDescription+ " - FAIL");
			}
			
		}
	}
	
	public static void printlog(String stepDescription,boolean stepStatus,boolean snapForPass) {
		if(stepStatus) {
			if(snapForPass) {
				test.log(LogStatus.PASS, stepDescription + " - PASS",test.addScreenCapture(getSnap()));
				logger.info(stepDescription + " - PASS");
			}else {
				test.log(LogStatus.PASS, stepDescription + " - PASS","");
				logger.info(stepDescription + " - PASS");
			}
		}else {
			test.log(LogStatus.FAIL, stepDescription + " - FAIL",test.addScreenCapture(getSnap()));
			logger.error(stepDescription+ " - FAIL");
		}
	}

	public static String getSnap() {
		String snapPath="";
		logger.info("Taking screenshot");
		File srcFile = ((TakesScreenshot)WebDriverManager.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
		String imagesPath=System.getProperty("user.dir")+"/images/imag_"+getDate();
		new File(imagesPath).mkdir();
		snapPath=imagesPath+"/"+"img_"+getDateTime()+".png";
		
		try{
			FileUtils.copyFile(srcFile,new File(snapPath));
		}catch(IOException e) {
			printlog("Exception in taking the screenshot"+e,false,true,false);
		}
		
		return null;
	}

	public static String getDateTime() {
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String sTimeStamp=df.format(new java.util.Date());
		return sTimeStamp.toString();
		}

	public static String getDate() {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String sTimeStamp=df.format(new java.util.Date());
		return sTimeStamp.toString();
	}
	
	
	public static int getYear(int yeartoSub) {
	   Calendar now = Calendar.getInstance();
	   now.add(Calendar.YEAR, (int)- yeartoSub);
	   return now.get(Calendar.YEAR);
	}
	
}