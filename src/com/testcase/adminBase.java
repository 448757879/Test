package com.testcase;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.test.Config;
import com.test.Log;
import com.test.SeleniumDriver;
import com.test.YamlReader;

public class adminBase 
{
	public SeleniumDriver sd;
	public WebDriver driver;
	public YamlReader yr;
	public YamlReader yrUsers;
	
	public adminBase()
	{
		sd = new SeleniumDriver();
		driver = sd.getWebDriver();
		//读取页面元素信息
		yr = new YamlReader(driver,"pageElement");
		//读取后台登录角色账号信息
		yrUsers = new YamlReader(driver,"admin51offerUsers");
	}
	
	public void login( String userType )
	{
		//页面跳转
		Log.logInfo("登录51offer后台");
		driver.navigate().to(Config.adminUrl);
		
		Map<String, String> mapLoginInfo = yrUsers.getYamlInfoByKey(userType);
		
		//取得用户名输入框
		WebElement usernameInput = yr.getLocator("adminHomeUsernameInput", true);
		if( usernameInput != null )
		{	
			//输入用户名
			Log.logInfo("输入用户名："+mapLoginInfo.get("username"));
			usernameInput.sendKeys(mapLoginInfo.get("username"));
		}
		
		//取得密码输入框
		WebElement passwordInput = yr.getLocator("adminHomePasswordInput", true);
		if( passwordInput != null )
		{	
			//输入用户名
			Log.logInfo("输入密码");
			passwordInput.sendKeys(mapLoginInfo.get("password"));
		}
		
		//取得登录按钮
		WebElement loginBtn = yr.getLocator("adminHomeLoginBtn", true);
		if( loginBtn != null )
		{	
			//输入用户名
			Log.logInfo("点击【登录】");
			loginBtn.click();
		}
	}
	
	public void end()
	{
		sd.endDriver();
	}
}
