package com.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumDriver 
{
	private WebDriver driver = null;

	public SeleniumDriver()
	{
		initDriver();
	}
	
	public WebDriver getWebDriver()
	{
		return this.driver;
	}
	
	public void initDriver()
	{
		//根据xml配置文件启动对应的浏览器
		if( "firefox".equals(Config.brower) )
		{
			System.setProperty("webdriver.firefox.bin", 
					   Config.firefoxInstallPath);
			
			driver = new FirefoxDriver();
		}
		else if("ie".equals(Config.brower))
		{
			System.setProperty("webdriver.ie.driver",
					"files/IEDriverServerX64.exe");

			driver = new InternetExplorerDriver();
		}
		else if( "chrome".equals(Config.brower) )
		{
			System.setProperty("webdriver.chrome.bin", 
					   Config.chromeInstallPath);
			System.setProperty("webdriver.chrome.driver",
					"files/chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if( "unit".equals(Config.brower) )
		{
			driver = new HtmlUnitDriver();
		}
		else if( "remote".equals(Config.brower) )
		{
			DesiredCapabilities capabilities = null;
			if( "firefox".equals(Config.remoteBrower) )
			{
				capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("firefox_binary",  
				        Config.remoteBrowerInstallPath);
			}
			else if("ie".equals(Config.remoteBrower))
			{
				//暂时不支持
				//capabilities = DesiredCapabilities.internetExplorer();
			}
			else if( "chrome".equals(Config.remoteBrower) )
			{
				//暂时不支持
				//capabilities = DesiredCapabilities.chrome();
			}
			else if( "unit".equals(Config.remoteBrower) )
			{
				//暂时不支持
				//capabilities = DesiredCapabilities.htmlUnit();
				//capabilities = DesiredCapabilities.htmlUnitWithJs();
			}
			else
			{
				Log.logError("没有找到指定的浏览器，请查看配置文件:"+Config.configFilePath);
			}

			try 
			{
				driver = new RemoteWebDriver(new URL(Config.remoteUrl), capabilities);
			}
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Log.logError("没有找到指定的浏览器，请查看配置文件:"+Config.configFilePath);
		}
		
		if( null != driver )
		{
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Config.pageLoadWaitTime, TimeUnit.SECONDS);
			Config.setCurrentDriver(driver);
		}
	}
	
	public void endDriver()
	{
		if( null != driver )
		{
			driver.close();
			//driver.quit();
		}
	}
}
