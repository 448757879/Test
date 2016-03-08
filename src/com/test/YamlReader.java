package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ho.yaml.Yaml;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class YamlReader 
{
	private String yamlFile;
	private WebDriver driver;
	private HashMap<String, HashMap<String, String>> ml;
	private HashMap<String, HashMap<String, String>> extendLocator;
	private int retryTimes = 0;
	
	public YamlReader( WebDriver driver, String fileName )
	{
		this.yamlFile = fileName;
		this.driver = driver;
		this.getYamlFile();
	}
	
	@SuppressWarnings("unchecked")
	public void getYamlFile()
	{
		File file = new File("locator/"+yamlFile+".yaml");
		
		try 
		{
			ml = Yaml.loadType(new FileInputStream(file.getAbsolutePath()), HashMap.class);
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//增加拓展yaml文件
	@SuppressWarnings("unchecked")
	public void loadExtendYamlFile( String filename )
	{
		File file = new File("locator/"+filename+".yaml");
		
		try 
		{
			extendLocator = Yaml.loadType(new FileInputStream(file.getAbsolutePath()), HashMap.class);
			ml.putAll(extendLocator);
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private By getBy( String type, String value )
	{
		By by = null;
		if( type.equals("id") )
		{
			by = By.id(value);
		}
		else if( type.equals("name") )
		{
			by = By.name(value);
		}
		else if( type.equals("classname") )
		{
			by = By.className(value);
		}
		else if( type.equals("xpath") )
		{
			by = By.xpath(value);
		}
		else if( type.equals("cssSelector") )
		{
			by = By.cssSelector(value);
		}
		else if( type.equals("linkText") )
		{
			by = By.linkText(value);
		}
		else
		{
			Log.logError("Get Wrong Type, please check Yaml File", driver);
		}
		return by;
	}
	
	//根据key获取yaml里的页面对象
	public WebElement getElement( String key )
	{
		return this.getLocator(key, true);
	}
	
	public WebElement getElement( String key, String replace )
	{
		return this.getLocatorWithYamlVar(key, replace, true);
	}
	
	//根据key获取yaml里的页面对象，但是对象取得失败的时候不等待
	public WebElement getElementNoWait( String key )
	{
		return this.getLocator(key, false);
	}
	
	public WebElement getElementNoWait( String key, String replace )
	{
		return this.getLocatorWithYamlVar(key, replace, false);
	}
	
	//等待取得元素
	public WebElement waitForElement( final By by )
	{
		WebElement element = null;
		int waitTime = Config.waitTime;
		
		//try
		//{
			element = new WebDriverWait( driver, waitTime ).until
			(
				new ExpectedCondition<WebElement>()
				{
					public WebElement apply( WebDriver driver )
					{
						return driver.findElement(by);
					}
				}
			);
		//}
		//catch( Exception e )
		if( null == element )
		{
			Log.logWarn(by.toString()+" is not exist until "+ waitTime+" second");
		}
		return element;
	}
	
	//等待取得元素
	public List<WebElement> waitForElements( final By by )
	{
		/*Log.logInfo(driver.toString());*/
		
		List<WebElement> eleList = null;
		int waitTime = Config.waitTime;
			
		//try
		//{
			eleList = new WebDriverWait( driver, waitTime ).until
			(
				new ExpectedCondition<List<WebElement>>()
				{
					public List<WebElement> apply( WebDriver driver )
					{
						return driver.findElements(by);
					}
				}
			);
		//}
		//catch( Exception e )
		if( null == eleList )
		{
			Log.logWarn(by.toString()+" is not exist until "+ waitTime+" second");
		}
		return eleList;
	}
	
	//等待元素显示
	public boolean waitForElementDisplayed( final WebElement element )
	{
		/*Log.logInfo(driver.toString());*/
		
		boolean bDisplay = false;
		if( null == element )
		{
			return bDisplay;
		}
		
		int waitTime = Config.waitTime;
		
		try
		{
			bDisplay = new WebDriverWait( driver, waitTime ).until
			(
				new ExpectedCondition<Boolean>()
				{
					public Boolean apply( WebDriver driver )
					{
						return element.isDisplayed();
					}
				}
			);
		}
		catch( Exception e )
		{
			Log.logError(element.toString()+" is not displayed "+ waitTime, driver);
		}
		return bDisplay;
	}
	
	//等待元素显示
	public boolean waitForElementDisplayed( final WebElement element, int setWaitTime )
	{
		boolean bDisplay = false;
		if( null == element )
		{
			return bDisplay;
		}
			
		int waitTime = setWaitTime;
			
		try
		{
			bDisplay = new WebDriverWait( driver, waitTime ).until
			(
				new ExpectedCondition<Boolean>()
				{
					public Boolean apply( WebDriver driver )
					{
						return element.isDisplayed();
					}
				}
			);
		}
		catch( Exception e )
		{
			Log.logError(element.toString()+" is not displayed "+ waitTime);
		}
		return bDisplay;
	}
	
	//等待元素消失
	public boolean waitForElementUndisplayed( final WebElement element )
	{
		boolean bDisplay = false;
		if( null == element )
		{
			return bDisplay;
		}
			
		int waitTime = Config.waitTime;
			
		try
		{
			bDisplay = new WebDriverWait( driver, waitTime ).until
			(
				new ExpectedCondition<Boolean>()
				{
					public Boolean apply( WebDriver driver )
					{
						return !element.isDisplayed();
					}
				}
			);
		}
		catch( Exception e )
		{
			Log.logError(element.toString()+" is displayed "+ waitTime, driver);
		}
		return bDisplay;
	}
	
	public WebElement getLocator( String key, boolean bWait )
	{
		/*Log.logInfo(driver.toString());*/
		
		WebElement element = null;
		
		element = this.getPageElement(key, bWait);

		if( null == element )
		{
			while( retryTimes < 3 )
			{
				/*try 
				{
					Thread.sleep(3000);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				retryTimes++;
				Log.logWarn("catch error,start retry times:"+retryTimes);
				//driver.navigate().refresh();
				element = this.getPageElement(key, bWait);
			}
			
			Log.logError("retry over,Can not found the element:"+key, driver);
			retryTimes = 0;
		}
		
		return element;
	}
	
	public WebElement getPageElement( String key, boolean bWait )
	{
		/*Log.logInfo(driver.toString());*/
		
		WebElement element;
		
		if( ml.containsKey(key) )
		{
			Map<String, String> map = ml.get(key);
			String type = map.get("type");
			String value = map.get("value");
			
			By by = this.getBy(type, value);
			
			if( bWait )
			{
				element = this.waitForElement(by);
				if( null != element )
				{
					if( false == this.waitForElementDisplayed(element) )
					{
						element = null;
					}
				}
			}
			else
			{
				try
				{
					element = driver.findElement(by);
				}
				catch( Exception e )
				{
					Log.logWarn( "Can not found the element" );
					element = null;
				}
			}
		}
		else
		{
			Log.logError( "Can not found the element, Please check the Yaml File: locator/"+yamlFile+".yaml" );
			element = null;	
		}
		
		return element;
	}
	
	private String getLocatorString( String locatorString, String ss )
	{
		
		locatorString = locatorString.replaceFirst("%s", ss);
		
		return locatorString;
	}
	
	//替换yaml里的变量
	public void setLocatorVariableValue(String variable, String value)
	{
	    Set<String> keys = ml.keySet();
	    for(String key:keys)
	    {
	         String v = ml.get(key).get("value").replaceAll("%"+variable+"%", value);
	         ml.get(key).put("value",v);
	    }
	}
	
	public WebElement getLocatorWithYamlVar( String key, String replace, boolean bWait )
	{
		WebElement element;
		
		if( ml.containsKey(key) )
		{
			Map<String, String> map = ml.get(key);
			String type = map.get("type");
			String value = map.get("value");
			
			if( replace != null )
			{
				value = this.getLocatorString(value, replace);
			}
			
			By by = this.getBy(type, value);
			
			if( bWait )
			{
				element = this.waitForElement(by);
				if( false == this.waitForElementDisplayed(element) )
				{
					element = null;
				}
			}
			else
			{
				try
				{
					element = driver.findElement(by);
				}
				catch( Exception e )
				{
					Log.logError( "Can not found the element", driver );
					element = null;
				}
			}
		}
		else
		{
			Log.logError( "Can not found the element, Please check the Yaml File: locator/"+yamlFile+".yaml" );
			element = null;	
		}
		
		return element;
	}
	
	public List<WebElement> getElementsList(String key, boolean bWait )
	{
		/*Log.logInfo(driver.toString());*/
		
		List<WebElement> eleList = null;
		
		eleList = this.getPageElementsList(key, bWait);

		if( null == eleList )
		{
			while( retryTimes < 3 )
			{
				retryTimes++;
				Log.logWarn("catch error,start retry times:"+retryTimes);
				//driver.navigate().refresh();
				eleList = this.getPageElementsList(key, bWait);
			}
			
			Log.logError("retry over,Can not found the elementList:"+key, driver);
			retryTimes = 0;
		}
		
		return eleList;
	}
	
	public List<WebElement> getPageElementsList(String key, boolean bWait )
	{
		/*Log.logInfo(driver.toString());*/
		
		List<WebElement> eleList = null;
		
		if( ml.containsKey(key) )
		{
			Map<String, String> map = ml.get(key);
			String type = map.get("type");
			String value = map.get("value");
			
			By by = this.getBy(type, value);
			
			if( bWait )
			{
				eleList = this.waitForElements(by);
			}
			else
			{
				try
				{
					eleList = driver.findElements(by);
				}
				catch( Exception e )
				{
					Log.logWarn( "Can not found the elementList" );		
				}
			}
		}
		else
		{
			Log.logError( "Can not found the element, Please check the Yaml File: locator/"+yamlFile+".yaml" );
		}
		
		return eleList;
	}
	
	public void waitForLoad(WebDriver driver) 
	{
	    ExpectedCondition<Boolean> pageLoad= new ExpectedCondition<Boolean>() 
	    	{
	            public Boolean apply(WebDriver driver) 
	            {
	                //return (boolean)((JavascriptExecutor)driver.ExecuteScript("return document.readyState").Equals("complete"));
	                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	            }
	        };
	    WebDriverWait wait = new WebDriverWait(driver, 30);
	    wait.until(pageLoad);
	}
	
	public Map<String, String> getYamlInfoByKey( String key )
	{
		Map<String, String> map = null;
		if( ml.containsKey(key) )
		{
			map = ml.get(key);
		}
		
		return map;
	}
	
}
