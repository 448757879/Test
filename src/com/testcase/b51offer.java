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
		//��ȡҳ��Ԫ����Ϣ
		yr = new YamlReader(driver,"pageElement");
	}
	
	public void login( String username, String pwd )
	{
		//ҳ����ת
		Log.logInfo("��¼51offerB�˺�̨");
		driver.navigate().to(Config.deleteExistUserUrl);
		
		//ȡ���û��������
		WebElement usernameInput = yr.getLocator("b51offerLoginPageUsernameInput", true);
		if( usernameInput != null )
		{	
			//�����û���
			Log.logInfo("�����û�����"+username);
			usernameInput.sendKeys(username);
		}
		
		//ȡ�����������
		WebElement passwordInput = yr.getLocator("b51offerLoginPagePasswordInput", true);
		if( passwordInput != null )
		{	
			//�����û���
			Log.logInfo("��������");
			passwordInput.sendKeys(pwd);
		}
		
		//ȡ�õ�¼��ť
		WebElement loginBtn = yr.getLocator("b51offerLoginPageLoginBtn", true);
		if( loginBtn != null )
		{	
			//�����û���
			Log.logInfo("�������¼��");
			loginBtn.click();
		}
	}
	
	public void deleteUser( String username, String mobile )
	{
		//ȡ�á�������������
		WebElement whitelistLink = yr.getLocator("b51offerHomePageWhitelistLink", true);
		if( whitelistLink != null )
		{	
			//�����û���
			Log.logInfo("�������������");
			whitelistLink.click();
			
			//ȡ���û��������
			WebElement usernameInput = yr.getLocator("b51offerWhitelistPageUsernameInput", true);
			if( usernameInput != null )
			{
				//�����û���
				Log.logInfo("�����û�����"+username);
				usernameInput.sendKeys(username);
			}
			
			//ȡ���ֻ������
			WebElement mobileInput = yr.getLocator("b51offerWhitelistPageMobileInput", true);
			if( mobileInput != null )
			{
				//�����û���
				Log.logInfo("�����ֻ��ţ�"+mobile);
				mobileInput.sendKeys(mobile);
			}
			
			//ȡ�á�ɾ������ť
			WebElement deleteBtn = yr.getLocator("b51offerWhitelistPageDeleteBtn", true);
			if( deleteBtn != null )
			{
				//�����ɾ����
				Log.logInfo("�����ɾ����");
				deleteBtn.click();
			}
		}
	}
	
	public void end()
	{
		sd.endDriver();
	}
}
