package com.testcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.test.Config;
import com.test.Log;
import com.test.SeleniumDriver;
import com.test.YamlReader;

public class b51offer 
{
	public SeleniumDriver sd;
	public WebDriver driver;
	public YamlReader yr;
	
	public b51offer()
	{
		sd = new SeleniumDriver();
		driver = sd.getWebDriver();
		//读取页面元素信息
		yr = new YamlReader(driver,"pageElement");
	}
	
	public void login( String username, String pwd )
	{
		//页面跳转
		Log.logInfo("登录51offerB端后台");
		driver.navigate().to(Config.deleteExistUserUrl);
		
		//取得用户名输入框
		WebElement usernameInput = yr.getLocator("b51offerLoginPageUsernameInput", true);
		if( usernameInput != null )
		{	
			//输入用户名
			Log.logInfo("输入用户名："+username);
			usernameInput.sendKeys(username);
		}
		
		//取得密码输入框
		WebElement passwordInput = yr.getLocator("b51offerLoginPagePasswordInput", true);
		if( passwordInput != null )
		{	
			//输入用户名
			Log.logInfo("输入密码");
			passwordInput.sendKeys(pwd);
		}
		
		//取得登录按钮
		WebElement loginBtn = yr.getLocator("b51offerLoginPageLoginBtn", true);
		if( loginBtn != null )
		{	
			//输入用户名
			Log.logInfo("点击【登录】");
			loginBtn.click();
		}
	}
	
	public void deleteUser( String username, String mobile )
	{
		//取得【白名单】链接
		WebElement whitelistLink = yr.getLocator("b51offerHomePageWhitelistLink", true);
		if( whitelistLink != null )
		{	
			//输入用户名
			Log.logInfo("点击【白名单】");
			whitelistLink.click();
			
			//取得用户名输入框
			WebElement usernameInput = yr.getLocator("b51offerWhitelistPageUsernameInput", true);
			if( usernameInput != null )
			{
				//输入用户名
				Log.logInfo("输入用户名："+username);
				usernameInput.sendKeys(username);
			}
			
			//取得手机输入框
			WebElement mobileInput = yr.getLocator("b51offerWhitelistPageMobileInput", true);
			if( mobileInput != null )
			{
				//输入用户名
				Log.logInfo("输入手机号："+mobile);
				mobileInput.sendKeys(mobile);
			}
			
			//取得【删除】按钮
			WebElement deleteBtn = yr.getLocator("b51offerWhitelistPageDeleteBtn", true);
			if( deleteBtn != null )
			{
				//点击【删除】
				Log.logInfo("点击【删除】");
				deleteBtn.click();
			}
		}
	}
	
	public void end()
	{
		sd.endDriver();
	}
}
