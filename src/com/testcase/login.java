package com.testcase;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import com.test.Config;
import com.test.Log;
import com.test.SeleniumDriver;
import com.test.dataProviderBase;
import com.test.YamlReader;
import com.test.emailReport;

public class login extends dataProviderBase
{
	public SeleniumDriver sd;
	public WebDriver driver;
	public YamlReader yr;
	
	@Test(priority = 1, dependsOnGroups = { "register.*"})
	public void selectLogin()
	{
		//ȡ�õ�¼��ť
		WebElement loginBtn = yr.getLocator("loginBtn", true);
		if( loginBtn != null )
		{	
			//�����¼��ť
			Log.logInfo("��ҳ�������¼����ť");
			loginBtn.click();
		}
	}
	
	@Test(priority = 2, dependsOnGroups = { "register.*"})
	public void login() 
	{
		//ȡ���˺ŵ�¼��ť
		WebElement selLoginBtn = yr.getLocator("userAccount", true);
		if( selLoginBtn != null )
		{
			//ѡ���˺ŵ�¼
			Log.logInfo("ѡ���˺ŵ�¼��");
			selLoginBtn.click();
		}
		
		//ȡ���û��������
		WebElement usernameInput = yr.getLocator("username", true);
		if( usernameInput != null )
		{
			//�����û���
			Log.logInfo("�����û�����"+Config.regsiterUsername);
			usernameInput.sendKeys(Config.regsiterUsername);
		}
		
		//ȡ�����������
		WebElement passwordInput = yr.getLocator("password", true);
		if( passwordInput != null )
		{
			//��������
			Log.logInfo("�������룺"+Config.regsiterPassword);
			passwordInput.sendKeys(Config.regsiterPassword);
		}
		
		//ȡ��ȷ�ϵ�¼��ť
		WebElement submitLoginBtn = yr.getLocator("submitLogin", true);
		if( submitLoginBtn != null )
		{
			Log.logInfo("�����ȷ�ϵ�¼��");
			submitLoginBtn.submit();
		}
	}
	
	@Test(priority = 3, dependsOnGroups = { "register.*"})
	public void startThreeStep()
	{
		//ȡ�á���ѻ�ȡ��ѧ��������ť
		WebElement startThreeStepBtn = yr.getLocator("homePageStartThreeStep", true);
		if( startThreeStepBtn != null )
		{
			//��������
			Log.logInfo("�������ѻ�ȡ��ѧ��������ť");
			startThreeStepBtn.click();
		}
	}
	
	@Test(dataProvider = "providerMethod",priority = 4, dependsOnGroups = { "register.*"})
	public void oneStep( Map<String, String> map ) 
	{
		Log.logInfo("��ʼ��һ��");
		//ȡ����ȥ�Ĺ��ҵĵ�ѡ��
		List<WebElement> listHopeCountryRadio = yr.getElementsList("oneStepPageHopeCountryRadio", true);
		if( listHopeCountryRadio != null )
		{
			for( int index = 0; index < listHopeCountryRadio.size(); index++ )
			{
				WebElement element = (WebElement)(listHopeCountryRadio.get(index));
				if(( element != null )&&(yr.waitForElementDisplayed(element)))
				{
					if( map.get("hopeCountry").equals(element.getText()) )
					{
						element.click();
						Log.logInfo("��ȥ�Ĺ���ѡ��Ϊ��"+map.get("hopeCountry"));
						break;
					}
				}
			}
		}
		
		//ȡ�üƻ�����ʱ��ĵ�ѡ��
		List<WebElement> listInlineYearRadio = yr.getElementsList("oneStepPageInlineYearRadio", true);
		if( listInlineYearRadio != null )
		{
			for( int index = 0; index < listInlineYearRadio.size(); index++ )
			{
				WebElement element = (WebElement)(listInlineYearRadio.get(index));
				if(( element != null )&&(yr.waitForElementDisplayed(element)))
				{
					if( map.get("inlineYear").equals(element.getText()) )
					{
						element.click();
						Log.logInfo("�ƻ�����ʱ��ѡ��Ϊ��"+map.get("inlineYear"));
						break;
					}
				}
			}
		}
		
		//ȡ��Ŀǰ�Ͷ���͵�������
		WebElement currentGradeSpa = yr.getLocator("oneStepPageCurrentGradeDropdown", true);
		if( currentGradeSpa != null )
		{
			currentGradeSpa.click();
			//ȡ��Ŀǰ�Ͷ���͵�����ѡ��
			List<WebElement> listCurrentGradeSelect = yr.getElementsList("oneStepPageCurrentGradeDropdownList", true);
			if( listCurrentGradeSelect != null )
			{
				for( int index = 0; index < listCurrentGradeSelect.size(); index++ )
				{
					WebElement element = (WebElement)(listCurrentGradeSelect.get(index));
					if(( element != null )&&(yr.waitForElementDisplayed(element)))
					{
						if( map.get("currentGrade").equals(element.getText()) )
						{
							element.click();
							Log.logInfo("Ŀǰ�Ͷ����ѡ��Ϊ��"+map.get("currentGrade"));
							break;
						}
					}
				}
			}
		}
		
		//ȡ�á��鿴�ҵ���ѧ��������ť
		WebElement submitBtn = yr.getLocator("oneStepPageSubmitBtn", true);
		if( submitBtn != null )
		{
			submitBtn.click();
			Log.logInfo("������鿴�ҵ���ѧ������");
			Log.logInfo("�û�:"+Config.regsiterUsername+"��ɵ�һ��");
		}
	}
	
	@Test(priority = 5, dependsOnGroups = { "register.*"})
	public void twoStep() 
	{
		Log.logInfo("��ʼ�ڶ���");
		//ȡ����ѧ������ѡ��
		List<WebElement> listAppleCheckbox = yr.getElementsList("twoStepPageApplyCheckbox", true);
		//ȡ����ѧ������ѡ���İ�
		List<WebElement> listAppleCheckboxText = yr.getElementsList("twoStepPageApplyCheckboxText", true);
		if(( listAppleCheckbox != null )&&( listAppleCheckboxText != null ))
		{
			for( int index = 0; index < listAppleCheckbox.size(); index++ )
			{
				WebElement elementCheckbox = (WebElement)(listAppleCheckbox.get(index));
				WebElement elementCheckboxText = (WebElement)(listAppleCheckboxText.get(index));
				if(( elementCheckbox != null )&&( elementCheckboxText != null ))
				{
					if( yr.waitForElementDisplayed(elementCheckbox) )
					{
						elementCheckbox.click();
					}
					if( yr.waitForElementDisplayed(elementCheckboxText) )
					{
						Log.logInfo("ѡ����ѧ����:"+elementCheckboxText.getText());
					}
				}
			}
		}
		
		//ȡ�á������ƶ��ҵ�ѡУ��������ť
		WebElement submitBtn = yr.getLocator("twoStepPageSubmitBtn", true);
		if( submitBtn != null )
		{
			submitBtn.click();
			Log.logInfo("����������ƶ��ҵ�ѡУ������");
			Log.logInfo("�û�:"+Config.regsiterUsername+"��ɵڶ���");
		}
	}
	
	@Test(dataProvider = "providerMethod",priority = 6, dependsOnGroups = { "register.*"})
	public void threeStep( Map<String, String> map )
	{
		Log.logInfo("��ʼ������");
		//ȡ�����������
		WebElement nameInput = yr.getLocator("threeStepPageNameInput", true);
		if( nameInput != null )
		{
			nameInput.sendKeys(map.get("realname"));
			Log.logInfo("��������:"+map.get("realname"));
		}
		
		//ȡ������Ͷ�ѧУ�����
		WebElement schoolInput = yr.getLocator("threeStepPageSchoolInput", true);
		if( schoolInput != null )
		{
			schoolInput.sendKeys(map.get("school"));
			Log.logInfo("��������Ͷ�ѧУ:"+map.get("school"));
		}
		
		//ȡ�þͶ�רҵ������
		WebElement majorDropdown = yr.getLocator("threeStepPageMajorDropdown", true);
		if( majorDropdown != null )
		{
			majorDropdown.click();
			//ȡ�þͶ�רҵ����ѡ��
			List<WebElement> listMajorDropdown = yr.getElementsList("threeStepPageMajorDropdownList", true);
			if( listMajorDropdown != null )
			{
				for( int index = 0; index < listMajorDropdown.size(); index++ )
				{
					WebElement element = (WebElement)(listMajorDropdown.get(index));
					if(( element != null )&&(yr.waitForElementDisplayed(element)))
					{
						if( map.get("major").equals(element.getText()) )
						{
							element.click();
							Log.logInfo("�Ͷ�רҵѡ��Ϊ��"+map.get("major"));
							break;
						}
					}
				}
			}
		}
		
		//ȡ��������ĩ����ƽ���������
		WebElement scoreInput = yr.getLocator("threeStepPageScoreInput", true);
		if( scoreInput != null )
		{
			scoreInput.sendKeys(map.get("score"));
			Log.logInfo("����������ĩ����ƽ����:"+map.get("score"));
		}
		
		//ȡ��ϣ���Ͷ�רҵ�����ࣩ������
		WebElement hopeMajorDropdown = yr.getLocator("threeStepPageHopeMajorDropdown", true);
		if( hopeMajorDropdown != null )
		{
			hopeMajorDropdown.click();
			//ȡ��ϣ���Ͷ�רҵ�����ࣩ����ѡ��
			List<WebElement> listHopeMajorDropdown = yr.getElementsList("threeStepPageHopeMajorDropdownList", true);
			if( listHopeMajorDropdown != null )
			{
				for( int index = 0; index < listHopeMajorDropdown.size(); index++ )
				{
					WebElement element = (WebElement)(listHopeMajorDropdown.get(index));
					if(( element != null )&&(yr.waitForElementDisplayed(element)))
					{
						if( map.get("hopeMajor").equals(element.getText()) )
						{
							element.click();
							Log.logInfo("ϣ���Ͷ�רҵѡ��Ϊ��"+map.get("hopeMajor"));
							break;
						}
					}
				}
			}
		}
		
		//ȡ��ϣ���Ͷ�רҵ��С�ࣩ������
		WebElement hopeSubMajorDropdown = yr.getLocator("threeStepPageHopeSubMajorDropdown", true);
		if( hopeSubMajorDropdown != null )
		{
			hopeSubMajorDropdown.click();
			//ȡ��ϣ���Ͷ�רҵ��С�ࣩ�������һ��
			WebElement hopeSubMajorDropdownList1st = yr.getLocator("threeStepPageHopeSubMajorDropdown1st", true);
			if( hopeSubMajorDropdownList1st != null )
			{
				//רҵС�������ѡ��ֻ�е�����ѡ���Ż���֣����еȴ�С����������ֺ��ٽ��к�������
				//ȡ��ϣ���Ͷ�רҵ��С�ࣩ����ѡ��
				List<WebElement> listHopeSubMajorDropdown = yr.getElementsList("threeStepPageHopeSubMajorDropdownList", true);
				if( listHopeSubMajorDropdown != null )
				{
					for( int index = 0; index < listHopeSubMajorDropdown.size(); index++ )
					{
						WebElement element = (WebElement)(listHopeSubMajorDropdown.get(index));
						if(( element != null )&&(yr.waitForElementDisplayed(element)))
						{
							if( map.get("hopeSubMajor").equals(element.getText()) )
							{
								element.click();
								Log.logInfo("ϣ���Ͷ�רҵС��ѡ��Ϊ��"+map.get("hopeSubMajor"));
								break;
							}
						}
					}
				}
			}
		}
		
		//ȡ�á������鿴�ҵ�ѡУ��������ť
		WebElement submitBtn = yr.getLocator("threeStepPageSubmitBtn", true);
		if( submitBtn != null )
		{
			submitBtn.click();
			Log.logInfo("����������鿴�ҵ�ѡУ������");
			Log.logInfo("�û�:"+Config.regsiterUsername+"��ɵ�����");
		}
		
		//ȡ�á������鿴�ҵ�ѡУ��������ť
		WebElement finishThreeStep = yr.getLocator("threeStepFinishSubmitBtn", true);
		if( finishThreeStep != null )
		{
			finishThreeStep.click();
			Log.logInfo("������鿴ѡУ�����");
		}
	}
	
	@Test(priority = 7, dependsOnGroups = { "register.*"})
	public void logout()
	{	
		//ȡ���û�������
		WebElement userWrap = yr.getLocator("userWrap", true);
		if( userWrap != null )
		{
			Actions act = new Actions(driver);
			if( act != null )
			{
				//Point point = userWrap.getLocation();
				//act.moveByOffset(point.x, point.y);
				//act.moveToElement(userWrap);
				//�ƶ����û���ã�ѡ��ֱ�ӵ��
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
	}
	
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

	}

	@AfterSuite
	public void afterSuite() 
	{
		Log log = new Log();
		log.createLogFile();
		
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
