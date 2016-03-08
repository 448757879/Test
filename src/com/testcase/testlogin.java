package com.testcase;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import com.test.Config;
import com.test.Log;
import com.test.SeleniumDriver;
import com.test.dataProviderBase;
import com.test.YamlReader;

public class testlogin extends dataProviderBase
{
	public SeleniumDriver sd;
	public WebDriver driver;
	public YamlReader yr;
	
	@Test
	public void test()
	{
		
	}
	
	@BeforeClass
	public void beforeClass() 
	{
		// TODO Auto-generated method stub
		sd = new SeleniumDriver();
		driver = sd.getWebDriver();
		if( null != driver )
		{
			//读取页面元素信息
			yr = new YamlReader(driver,"pageElement");
			//页面跳转
			driver.navigate().to(Config.testPageUrl);
		}
	}

	@AfterClass
	public void afterClass() 
	{
		try 
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sd.endDriver();
	}

	@BeforeSuite
	public void beforeSuite() 
	{

	}

	@AfterSuite
	public void afterSuite() 
	{

	}
}
