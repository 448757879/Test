package com.testcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.test.Config;
import com.test.Log;
import com.test.SeleniumDriver;
import com.test.YamlReader;

public class mobileValidatedCode 
{
	public SeleniumDriver sd;
	public WebDriver driver;
	public YamlReader yr;
	
	public mobileValidatedCode()
	{
		sd = new SeleniumDriver();
		driver = sd.getWebDriver();
		//读取页面元素信息
		yr = new YamlReader(driver,"pageElement");
	}
	
	public String getLastMobileValidatedCode( String mobile )
	{
		String lastMobileValidatedCode = null;
		
		//页面跳转
		Log.logInfo("登录线上短信查询页面");
		driver.navigate().to(Config.mobileValidatedCodeUrl);
		
		//取得手机号输入框
		WebElement mobileInput = yr.getLocator("msgSearchmobileInput", true);
		if( mobileInput != null )
		{	
			//输入用户名
			Log.logInfo("输入手机号："+mobile);
			mobileInput.sendKeys(mobile);
		}
		
		//取得【查询】按钮
		WebElement msgQuery = yr.getLocator("msgSearchBtn", true);
		if( msgQuery != null )
		{	
			//点击【查询】
			Log.logInfo("点击【查询】");
			msgQuery.click();
		}
		
		//取得最近一条短信内容
		WebElement lastMessageText = yr.getLocator("msgSearchLastMessageText", true);
		if( lastMessageText != null )
		{	
			lastMobileValidatedCode = lastMessageText.getText();
			//从短信里取到验证码
			lastMobileValidatedCode = lastMobileValidatedCode.replace("验证码：", "");
			lastMobileValidatedCode = lastMobileValidatedCode.substring(0, 4);
		}
		
		return lastMobileValidatedCode;
	}
	
	public void end()
	{
		sd.endDriver();
	}
}
