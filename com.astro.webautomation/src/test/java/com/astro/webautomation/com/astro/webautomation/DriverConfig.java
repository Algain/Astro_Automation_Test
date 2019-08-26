package com.astro.webautomation.com.astro.webautomation;

import java.awt.Toolkit;
import org.testng.annotations.*;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverConfig {
	protected static WebDriver driver;
	//Directory
	static String workingcampaign;
	static String workingdir = System.getProperty("user.dir");
	static String filepathchrome = workingdir + "/driver/chromedriver"; 
	static String filepathfirefox= workingdir + "/driver/geckodriver"; 
	static String log = workingdir + "/test-log/";
	static String productproperties = workingdir + "/productproperties/";
	static String screencapture = workingdir + "/screencapture/";


	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void setup() {

		//FIREFOX
		//		System.setProperty("webdriver.gecko.driver", filepathfirefox);
		//		WebDriver driver = new FirefoxDriver();			

		// Chrome Driver
		System.setProperty("webdriver.chrome.driver", filepathchrome);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-popup-blocking");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);

		// ScreenSize
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;

		System.out.println("Running On " + screenWidth +" X "+ screenHeight + " Resolution");
		Dimension screen = new Dimension(screenWidth,screenHeight);
		driver.manage().window().setSize(screen);
		driver.manage().window().setPosition(new Point(0,0));	

	}
	@AfterClass
	public static void tearDown() {
		driver.quit();
	}
}
