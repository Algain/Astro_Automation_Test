package com.astro.webautomation.com.astro.webautomation;

import java.awt.AWTException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ecommerceTest extends DriverConfig{

	public static String requiredDate;
	public static String ecommercepageoneURL = "https://www.lazada.com.my/";
	public static String ecommercepagetwoURL = "https://www.amazon.com/";
	public static String productdescription = "Finding for ";
	public static String typeproduct = "iphone";
	public static String ecommercepagename = "Lazada";
	public static String ecommercepagenamesecond = "Amazon";
	public static String newlinw = "\n";


	@Test
	public void ecommerceproductcomparison() throws InterruptedException, IOException, AWTException{

		// Date Format
		DateFormat df = new SimpleDateFormat("ddMMyyHHmm");
		requiredDate = 	df.format(new Date()).toString();
		System.out.println(requiredDate);

		//log File
		Logger logthis = Logger.getLogger("MyLog");  
		FileHandler fhandler; 

		try {  
			// Crate new log File  
			fhandler = new FileHandler(log + requiredDate + "_webUI.log");  
			logthis.addHandler(fhandler);
			SimpleFormatter sformater = new SimpleFormatter();  
			fhandler.setFormatter(sformater);  

			//LAZADA
			// 1 - Open Browser
			System.out.println(ecommercepagename);
			System.out.println(ecommercepageoneURL);
			driver.get(ecommercepageoneURL);
			logthis.info(ecommercepagename);
			logthis.info(ecommercepageoneURL); 
			Thread.sleep(1000);

			// 2 - Screen Capture
			String screencapturefilename = ecommercepagename +"_"+ requiredDate+".png";
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				com.google.common.io.Files.copy(srcFile, new File(screencapture + screencapturefilename));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Thread.sleep(1000);

			// 3 - Locate Search Element and Input Value
			WebElement searchbox = driver.findElement(By.id("q"));
			searchbox.sendKeys(typeproduct);
			logthis.info(productdescription + typeproduct); 
			Thread.sleep(2000);

			// 4 - Click Search
			WebElement search_click = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/div[2]/div/div[2]/form/div/div[2]/button"));
			search_click.click();
			Thread.sleep(5000);

			// 5 - Change Product Order Listing
			((JavascriptExecutor) driver).executeScript("scroll(0,300)");
			Thread.sleep(2000);
			WebElement productdecendinglist = driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[1]/div/div[1]/div[1]/div/div[2]/div/div[2]/div/div"));
			productdecendinglist.click();
			Thread.sleep(1000);
			productdecendinglist.sendKeys(Keys.DOWN, Keys.RETURN);
			Thread.sleep(1000);

			// 6 - Get Total Number
			WebElement searchresult = driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[1]/div/div[1]/div[1]/div/div[1]/div/div"));
			String totalproducts = searchresult.getText();
			System.out.println(totalproducts);
			logthis.info(totalproducts); 
			Thread.sleep(2000);

			// 7 - Get Product List
			WebElement productlist = driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[1]/div/div[1]/div[3]"));	
			String productlistdetails = productlist.getText();
			System.out.println(productlistdetails);
			String restructuredproductlisting = productlistdetails.replaceAll("([()]|Selangor|Perak|Federal Territory of Kuala Lumpur|Installment available)", "");
			System.out.println(restructuredproductlisting);
			logthis.info(driver.getCurrentUrl());
			logthis.info("Product Listing Saved to " + "productlisting" + requiredDate);
			Thread.sleep(1000);

			// Crate new file for copying product listing
			FileWriter fileWriter = new FileWriter(log + "productlisting" + requiredDate);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			// Write Product Listing Details
			printWriter.println(ecommercepagename);
			printWriter.println(driver.getCurrentUrl() + newlinw);
			printWriter.println(totalproducts + newlinw);
			printWriter.println(restructuredproductlisting);



			//AMAZON
			// 1 - Open Browser
			System.out.println(ecommercepagenamesecond);
			System.out.println(ecommercepagetwoURL);
			driver.get(ecommercepagetwoURL);
			logthis.info(ecommercepagenamesecond);
			logthis.info(ecommercepagetwoURL); 
			Thread.sleep(1000);

			//2 - Screen Capture
			String screencapturefilename_pagetwo = ecommercepagenamesecond +"_"+ requiredDate+".png";
			File srcFile_pagetwo = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				com.google.common.io.Files.copy(srcFile_pagetwo, new File(screencapture + screencapturefilename_pagetwo));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Thread.sleep(1000);

			// 3 - Locate Search Element and Input Value
			WebElement searchbox_pagetwo = driver.findElement(By.id("twotabsearchtextbox"));
			searchbox_pagetwo.sendKeys(typeproduct, Keys.RETURN);
			logthis.info(productdescription + typeproduct); 
			Thread.sleep(5000);

			// 4 - Change Product Order Listing			
			WebElement productdecendinglist_pagetwo = driver.findElement(By.className("a-dropdown-label"));
			productdecendinglist_pagetwo.click();
			Thread.sleep(2000);
			WebElement productdecendinglist_selection_pagetwo = driver.findElement(By.id("s-result-sort-select_2"));
			productdecendinglist_selection_pagetwo.click();
			Thread.sleep(2000);

			// 5 - Get Total Number
			WebElement searchresultpagetwo = driver.findElement(By.xpath("/html/body/div[1]/div[1]/span/h1/div/div[1]/div/div/span[1]"));
			String totalproductspagetwo = searchresultpagetwo.getText();
			System.out.println(totalproductspagetwo);
			logthis.info(totalproductspagetwo); 
			Thread.sleep(2000);

			// 6- Get Product Listing
			WebElement productlist_pagetwo = driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[1]"));	
			String productlistdetails_pagetwo = productlist_pagetwo.getText();
			String restructuredproductlisting_pagetwo = productlistdetails_pagetwo.replaceAll("(Eligible for Shipping to Malaysia|More Buying Choices)", "");
			System.out.println(restructuredproductlisting_pagetwo);
			logthis.info(driver.getCurrentUrl());
			logthis.info("Product Listing Saved to " + "productlisting" + requiredDate);
			Thread.sleep(1000);

			printWriter.println(ecommercepagenamesecond);
			printWriter.println(driver.getCurrentUrl() + newlinw);
			printWriter.println(restructuredproductlisting_pagetwo);
			printWriter.close();


		} catch (SecurityException e) {  
			e.printStackTrace(); 
		}
		email.main(null);
	}

}
