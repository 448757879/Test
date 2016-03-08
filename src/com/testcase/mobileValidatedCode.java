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
		//��ȡҳ��Ԫ����Ϣ
		yr = new YamlReader(driver,"pageElement");
	}
	
	public String getLastMobileValidatedCode( String mobile )
	{
		String lastMobileValidatedCode = null;
		
		//ҳ����ת
		Log.logInfo("��¼���϶��Ų�ѯҳ��");
		driver.navigate().to(Config.mobileValidatedCodeUrl);
		
		//ȡ���ֻ��������
		WebElement mobileInput = yr.getLocator("msgSearchmobileInput", true);
		if( mobileInput != null )
		{	
			//�����û���
			Log.logInfo("�����ֻ��ţ�"+mobile);
			mobileInput.sendKeys(mobile);
		}
		
		//ȡ�á���ѯ����ť
		WebElement msgQuery = yr.getLocator("msgSearchBtn", true);
		if( msgQuery != null )
		{	
			//�������ѯ��
			Log.logInfo("�������ѯ��");
			msgQuery.click();
		}
		
		//ȡ�����һ����������
		WebElement lastMessageText = yr.getLocator("msgSearchLastMessageText", true);
		if( lastMessageText != null )
		{	
			lastMobileValidatedCode = lastMessageText.getText();
			//�Ӷ�����ȡ����֤��
			lastMobileValidatedCode = lastMobileValidatedCode.replace("��֤�룺", "");
			lastMobileValidatedCode = lastMobileValidatedCode.substring(0, 4);
		}
		
		return lastMobileValidatedCode;
	}
	
	public void end()
	{
		sd.endDriver();
	}
}
