package base;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import libraries.HTMLReport;
import libraries.SeleniumWrapper;
import utilities.ExcelReader;
import utilities.PropertiesReader;

public class BeforeAndAfter extends SeleniumWrapper{
	
	public int iBrowserType = 1; // 1-Chrome,2-FF,3-Edge
	public WebDriver driver;
	public String sURL = "";
	public String sProName = "Config";
	public String sSheetName = "";
	public String testCaseName,testDescription,module;
	
	@BeforeSuite
	public void initReport() {
		startReport();
	}
	
	@AfterSuite
	public void flushReport() {
		endReport();
	}
	
  @BeforeClass
  public void invokeBrowser() {

		switch (iBrowserType) {
		case 1:
			System.out.println("User option is " + iBrowserType + ",So invoking Chrome browser!!!");
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case 2:
			System.out.println("User option is " + iBrowserType + ",So invoking Firefox browser!!!");
			System.setProperty("webdriver.gecko.driver", "./driver/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case 3:
			System.out.println("User option is " + iBrowserType + ",So invoking Edge browser!!!");
			System.setProperty("webdriver.edge.driver", "./driver/msedgedriver.exe");
			driver = new EdgeDriver();
			break;

		default:
			System.out.println("User option is wrong " + iBrowserType + ",So invoking default Chrome browser!!!");
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		tlDriver.set(driver);
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		sURL = PropertiesReader.getPropertyValue(sProName, "url");
		getDriver().get(sURL);
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		startTestCase(testCaseName, testDescription);
		startTestcase(module);
	}
  
  @AfterClass
  public void closeBrowser() {
	  getDriver().quit();
	}
  
  @DataProvider(name = "ExcelData")
  public Object[][] getExcelValue(){
		Object[][] data = ExcelReader.getSheet(sSheetName);
		return data;
	}

 

}
