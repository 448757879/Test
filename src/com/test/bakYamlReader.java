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

public class bakYamlReader 
{
	private String yamlFile;
	private WebDriver driver;
	private HashMap<String, HashMap<String, String>> ml;
	private HashMap<String, HashMap<String, String>> extendLocator;
	
	public bakYamlReader( WebDriver driver, String fileName )
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
	
	//������չyaml�ļ�
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
	
	//����key��ȡyaml���ҳ�����
	public WebElement getElement( String key )
	{
		return this.getLocator(key, true);
	}
	
	public WebElement getElement( String key, String replace )
	{
		return this.getLocatorWithYamlVar(key, replace, true);
	}
	
	//����key��ȡyaml���ҳ����󣬵��Ƕ���ȡ��ʧ�ܵ�ʱ�򲻵ȴ�
	public WebElement getElementNoWait( String key )
	{
		return this.getLocator(key, false);
	}
	
	public WebElement getElementNoWait( String key, String replace )
	{
		return this.getLocatorWithYamlVar(key, replace, false);
	}
	
	//�ȴ�ȡ��Ԫ��
	public WebElement waitForElement( final By by )
	{
		WebElement element = null;
		int waitTime = Config.waitTime;
		
		try
		{
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
		}
		catch( Exception e )
		{
			Log.logError(by.toString()+" is not exist until "+ waitTime+" second", driver);
		}
		return element;
	}
	
	//�ȴ�Ԫ����ʾ
	public boolean waitForElementDisplayed( final WebElement element )
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
	
	//�ȴ�Ԫ����ʾ
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
			//Log.logError(element.toString()+" is not displayed "+ waitTime);
		}
		return bDisplay;
	}
	
	//�ȴ�Ԫ����ʧ
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
	
	private String getLocatorString( String locatorString, String ss )
	{
		
		locatorString = locatorString.replaceFirst("%s", ss);
		
		return locatorString;
	}
	
	//�滻yaml��ı���
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
	
	public List<WebElement> getElementsList(String key)
	{
		List<WebElement> eleList = null;
		
		if( ml.containsKey(key) )
		{
			Map<String, String> map = ml.get(key);
			String type = map.get("type");
			String value = map.get("value");
			
			By by = this.getBy(type, value);
			
			try
			{
				eleList = driver.findElements(by);
			}
			catch( Exception e )
			{
				Log.logError( "Can not found the element", driver );
			}			
		}
		else
		{
			Log.logError( "Can not found the element, Please check the Yaml File: locator/"+yamlFile+".yaml" );
		}
		
		return eleList;
	}
	
}
