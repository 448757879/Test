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
		//��ȡҳ��Ԫ����Ϣ
		yr = new YamlReader(driver,"pageElement");
		//��ȡ��̨��¼��ɫ�˺���Ϣ
		yrUsers = new YamlReader(driver,"admin51offerUsers");
	}
	
	public void login( String userType )
	{
		//ҳ����ת
		Log.logInfo("��¼51offer��̨");
		driver.navigate().to(Config.adminUrl);
		
		Map<String, String> mapLoginInfo = yrUsers.getYamlInfoByKey(userType);
		
		//ȡ���û��������
		WebElement usernameInput = yr.getLocator("adminHomeUsernameInput", true);
		if( usernameInput != null )
		{	
			//�����û���
			Log.logInfo("�����û�����"+mapLoginInfo.get("username"));
			usernameInput.sendKeys(mapLoginInfo.get("username"));
		}
		
		//ȡ�����������
		WebElement passwordInput = yr.getLocator("adminHomePasswordInput", true);
		if( passwordInput != null )
		{	
			//�����û���
			Log.logInfo("��������");
			passwordInput.sendKeys(mapLoginInfo.get("password"));
		}
		
		//ȡ�õ�¼��ť
		WebElement loginBtn = yr.getLocator("adminHomeLoginBtn", true);
		if( loginBtn != null )
		{	
			//�����û���
			Log.logInfo("�������¼��");
			loginBtn.click();
		}
	}
	
	public void end()
	{
		sd.endDriver();
	}
}
