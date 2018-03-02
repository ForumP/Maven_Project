import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class CoinsquareTest {

	public WebDriver driver;
	public ExtentReports extent;
	public ExtentTest extenttest;
	
	
	
	@BeforeTest
	public void SetExtent() {
		
		extent = new ExtentReports(System.getProperty("user.dir")+ "/test-output/ExtentReport.html",true);
		extent.addSystemInfo("Host Name", "Forum Patel");
		extent.addSystemInfo("User Name", "fpatel");
		extent.addSystemInfo("Environment", "QA");
				
	}
	
	@BeforeMethod
	
	public void SetUp() {
		System.setProperty("webdriver.gecko.driver","/home/fpatel/Automation/Browsers/geckodriver");
		driver = new FirefoxDriver();
		
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get("https://dev.coinsquare.io:33007/");
				
		
	}
	@Test
	public void Testcase1() {
		
		System.out.println("Test case 1 passed");
		
	}
	
	@Test
	public void CoinsquareTitleTest() {
		
		extenttest = extent.startTest("CoinsquareTitleTest");
		String Title = driver.getTitle();
		System.out.println(Title);
		AssertJUnit.assertEquals(Title, "Coinsquare - Buy Bitcoin, Ethereum and Litecoin in");
		
		
	}
	@Test
	public void TestCase2() {
		System.out.println("Test case 3 passed");
	}
	
	public String GetScreenShot(WebDriver driver,String ScreenshotName ) throws IOException {
		  
		String dateName = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir")+"/FailedTestScreenshots/" + ScreenshotName + dateName + ".png";
		File finaldestination= new File(destination);
		FileUtils.copyFile(source, finaldestination);
		return destination;
		
				
	}
	
	@AfterMethod
	
	public void teardown(ITestResult result) throws IOException {
		
		if(result.getStatus()== ITestResult.FAILURE) {
			extenttest.log(LogStatus.FAIL,"Test case Failed is:" + result.getName());
			extenttest.log(LogStatus.FAIL,"Test case Failed is:" + result.getThrowable());
			
			String screenshotPath = CoinsquareTest.this.GetScreenShot(driver,result.getName());
			extenttest.log(LogStatus.FAIL,extenttest.addScreenCapture(screenshotPath));
			//extenttest.log(LogStatus.FAIL,extenttest.addScreencast(screenshotPath));
			
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			extenttest.log(LogStatus.SKIP, "Test Case Scipped is:" + result.getName());
			
		}
	
		extent.endTest(extenttest);
		
		driver.quit();
		
	}
	
	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
		
	}
		
}
