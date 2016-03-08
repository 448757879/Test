package com.testcase;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ocr.ocrGetCode;
import com.test.Config;
import com.test.Log;
import com.test.SeleniumDriver;
import com.test.YamlReader;
import com.test.dataProviderBase;
import com.test.emailReport;

public class register extends dataProviderBase
{
	public SeleniumDriver sd;
	public WebDriver driver;
	public YamlReader yr;
	
	//�ں�̨�����û�����Ϊ�����û�
	public void setTestUserType()
	{
		//�Ƚ����û�ͨ��51offer��̨����Ϊ��������
		registerUserListPage st = new registerUserListPage();
		//��¼51offer��̨
		st.openPage("superTester");
		//�����绰
		if(st.searchUser("mobile", Config.registerMobile))
		{
			//�����û�����
			st.setUserType("��������");
		}
		
		//�رպ�̨
		st.end();			
	}
	
	//ɾ����ע���û���ָ���ֻ��ţ�
	public void deleteExistMobileTestUser()
	{
		b51offer badmin = new b51offer();
		//��¼51offerB�˺�̨
		badmin.login("test", "123123");
		//ɾ���û�
		badmin.deleteUser(Config.regsiterUsername, Config.registerMobile);
		//�ر�B�˺�̨
		badmin.end();
	}
	
	//����ͼƬ������ͼƬ��֤��
	public void inputValidatedCode()
	{
		String codeUrl = null;
		//ȡ����֤��ͼƬ
		WebElement codePic = yr.getLocator("validatedPic", true);
		if( codePic != null )
		{
			codeUrl = codePic.getAttribute("src");
		}		

		ocrGetCode orc = new ocrGetCode();
		
		String code = orc.getCode(codeUrl);
		
		//ȡ��ͼƬ��֤�������
		WebElement validatedCodeInput = yr.getLocator("validatedCode", true);
		if( validatedCodeInput != null )
		{
			//����ͼƬ��֤��
			Log.logInfo("����ͼƬ��֤�룺"+code);
			validatedCodeInput.clear();
			validatedCodeInput.sendKeys(code);
		}
	}
	
	//ȡ�ö�����֤�벢����
	public void inputMobileValidatedCode( String mobile )
	{
		//ȡ�ö�����֤�������
		WebElement mobileCodeInput = yr.getLocator("mobileValidatedCode", true);
		if( mobileCodeInput != null )
		{
			mobileValidatedCode mvc = new mobileValidatedCode();
			//ȡ�ö�����֤��
			String mobileCode = mvc.getLastMobileValidatedCode(mobile);
			//�رն��Ų�ѯƽ̨
			mvc.end();
			
			//���������֤��֤��
			Log.logInfo("���������֤�룺"+mobileCode);
			mobileCodeInput.clear();
			mobileCodeInput.sendKeys(mobileCode);
		}
	}
	
	public boolean registerSubmit()
	{
		String currentUrl = driver.getCurrentUrl();
		
		//ȡ�á�ȷ��ע�᡿��ť
		WebElement registerBtn = yr.getLocator("submitRegister", true);
		if( registerBtn != null )
		{
			//�����ȷ��ע�᡿
			Log.logInfo("�����ȷ��ע�᡿");
			registerBtn.click();
		}
		
		try 
		{
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( !currentUrl.equals(driver.getCurrentUrl()) )
		{
			return true;
		}
		
		return false;
	}
	
	@Test(priority = 1, groups = { "register" })
	public void selectRegister()
	{
		//ȡ��ע�ᰴť
		WebElement registerBtn = yr.getLocator("registerBtn", true);
		if( registerBtn != null )
		{	
			//�����¼��ť
			Log.logInfo("��ҳ�����ע�᡿��ť");
			registerBtn.click();
		}
	}
	
	@Test(dataProvider = "providerMethod",priority = 2, groups = { "register" }, dependsOnMethods = "selectRegister")
	public void register( Map<String, String> map ) 
	{
		String currentUrl = driver.getCurrentUrl();
		
		//Log.logInfo(currentUrl);
		
		//��Ϊ��Ҫ��ȡ�ֻ���֤�룬��ʱ�������������ҵ��ֻ������Թ��Ҳ�������ʹ��Ĭ�ϵ��й�
		//ȡ���ֻ��������
		WebElement mobileInput = yr.getLocator("mobile", true);
		if( mobileInput != null )
		{
			//�����ֻ���
			Log.logInfo("�����ֻ��ţ�"+map.get("mobile"));
			mobileInput.sendKeys(map.get("mobile"));
		}
		
		//ȡ�����������
		WebElement passwordInput = yr.getLocator("registerPassword", true);
		if( passwordInput != null )
		{
			//��������
			Log.logInfo("�������룺"+map.get("password"));
			passwordInput.sendKeys(map.get("password"));
			Config.setRegsiterPassword(map.get("password"));
		}
		
		//����ͼƬ��֤��
		inputValidatedCode();
		
		//ȡ�á���ȡ������֤�롿��ť
		WebElement getMobileCodeBtn = yr.getLocator("getMobileValidatedCode", true);
		if( getMobileCodeBtn != null )
		{
			//�������ȡ������֤�롿��ť
			Log.logInfo("�������ȡ������֤�롿");
			getMobileCodeBtn.click();
			
			//ȡ�õ�ǰҳ��Ĵ�������
			//�ֻ��ظ�����
			WebElement errMobileExist = yr.getLocator("errMobileExist", false);

			Log.logInfo("��֤ע����Ϣ�Ƿ���ȷ");
			
			//�ֻ��ظ��ĳ���
			if( errMobileExist.isDisplayed() )
			//if( yr.waitForElementDisplayed(errMobileExist, 1) )
			{
				Log.logInfo("�ֻ�����ע��");
				//ɾ��ǰ�Ƚ����û�����Ϊ�����û�
				this.setTestUserType();
				//ɾ�����ֻ��ŵ�ע���û�
				this.deleteExistMobileTestUser();
				//�ٴε������ȡ������֤�롿��ť
				Log.logInfo("�ٴε������ȡ������֤�롿");
				getMobileCodeBtn.click();
			}
			
			//���������֤��
			this.inputMobileValidatedCode(map.get("mobile"));
		}
		
		//ȡ�á�ȷ��ע�᡿��ť
		WebElement registerBtn = yr.getLocator("submitRegister", true);
		if( registerBtn != null )
		{
			//�����ȷ��ע�᡿
			Log.logInfo("�����ȷ��ע�᡿");
			if( registerSubmit() )
			{
				return;
			}
		}
		
		//ͼƬ��֤�����ocr��ʶ���ʲ��Ǻܸߣ�����ʶ�����
		WebElement errValidatedCode = yr.getLocator("errValidatedCode", false);
		
		//ͼƬ��֤���������ĳ���
		//while( (currentUrl.equals(driver.getCurrentUrl()))&&(errValidatedCode.isDisplayed()))
		//while( yr.waitForElementDisplayed(errValidatedCode, 1) )
		
		while(currentUrl.equals(driver.getCurrentUrl()))
		{
/*			if( !currentUrl.equals(driver.getCurrentUrl()) )
			{
				return;
			}*/
			if( errValidatedCode.isDisplayed())
			{
				Log.logInfo("ͼƬ��֤��ʶ�����");
				//�������һ�š���ť
				//Log.logInfo("�������һ�š�");
				//changeValidatedPicBtn.click();
				//�ٴ�����ͼƬ��֤��
				Log.logInfo("������֤�ٴ�����");
				this.inputValidatedCode();
				if( registerSubmit() )
				{
					return;
				}
			}

			/*Log.logInfo(currentUrl);
			Log.logInfo(driver.getCurrentUrl());*/
			
		/*	if( !currentUrl.equals(driver.getCurrentUrl()) )
			{
				return;
			}*/
			
			//��֤��ʧЧ
			WebElement invalidMobileValidatedCode = yr.getLocator("invalidMobileValidatedCode", false);
			
			//������֤��ʧЧ�ĳ���
			if( invalidMobileValidatedCode.isDisplayed() )
			//if( yr.waitForElementDisplayed(invalidMobileValidatedCode, 1) )
			{
				Log.logInfo("������֤��ʧЧ");
				Log.logInfo("�ٴε������ȡ������֤�롿");
				getMobileCodeBtn.click();
				this.inputMobileValidatedCode(map.get("mobile"));
				if( registerSubmit() )
				{
					return;
				}
			}
		}
	} 
	
	@Test(priority = 3, groups = { "register" }, dependsOnMethods = "register")
	public void finishRegister()
	{
		/*//ȡ��ע�����û�
		WebElement registerUsername = yr.getLocator("registerUsername", true);
		
		if( registerUsername != null )
		{
			Log.logInfo("�û���Ϊ��"+registerUsername.getText());
			Config.setRegsiterUsername(registerUsername.getText());
		}*/
		
		//ע��������Ϊ�����û�
		this.setTestUserType();
		
		//ȡ�ÿ�ʼDIYҳ��ġ������ҵ���ѧ���롿��ť
		WebElement startDiyPageEnterBtn = yr.getLocator("startDiyPageEnterBtn", true);
		
		if( startDiyPageEnterBtn != null )
		{
			Log.logInfo("����������ҵ���ѧ���롿");
			startDiyPageEnterBtn.click();
		}
	}
	
/*	@Test(priority = 4)
	public void logout()
	{
		//ȡ���û�������
		WebElement userWrap = yr.getLocator("userWrap", true);
		if( userWrap != null )
		{
			Actions act = new Actions(driver);
			if( act != null )
			{
				userWrap.click();
				
				//ȡ���˳���ť
				WebElement logoutBtn = yr.getLocator("logout", true);
				if( logoutBtn != null )
				{
					Log.logInfo("������˳�����ť");
					logoutBtn.click();
				}
			}
		}
	}*/
	
	@BeforeClass
	public void beforeClass() 
	{
		// TODO Auto-generated method stub
		sd = new SeleniumDriver();
		driver = sd.getWebDriver();
		if( null != driver )
		{
			//��ȡҳ��Ԫ����Ϣ
			yr = new YamlReader(driver,"pageElement");
			//ҳ����ת
			driver.navigate().to(Config.testPageUrl);
		} 
	}

	@AfterClass
	public void afterClass() 
	{
		try 
		{
			Thread.sleep(1000);
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
		Log log = new Log();
		log.deleteLogFile();
		Config.setTestFailure(false);
		Config.setTestSkip(false);
	}

	@AfterSuite
	public void afterSuite() 
	{
		/*emailReport emailReportIns = emailReport.getInstance();
		if( emailReportIns != null )
		{
			if(( Config.bTestFailure )||( Config.bTestSkip ))
			{
				emailReportIns.sendEmail("�Զ������������˴��󣬾�����鿴�����Ĳ��Ա��棬",
										"test-output/emailable-report.html");
			}
			else
			{
				emailReportIns.sendEmail("�Զ�������˳����ɣ�����Ϊ���Ա��棬",
										"test-output/emailable-report.html");
			}
		}
		
		Config.setTestFailure(false);
		Config.setTestSkip(false);*/
	}
}
