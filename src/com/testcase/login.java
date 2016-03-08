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
		//取得登录按钮
		WebElement loginBtn = yr.getLocator("loginBtn", true);
		if( loginBtn != null )
		{	
			//点击登录按钮
			Log.logInfo("首页点击【登录】按钮");
			loginBtn.click();
		}
	}
	
	@Test(priority = 2, dependsOnGroups = { "register.*"})
	public void login() 
	{
		//取得账号登录按钮
		WebElement selLoginBtn = yr.getLocator("userAccount", true);
		if( selLoginBtn != null )
		{
			//选择账号登录
			Log.logInfo("选择【账号登录】");
			selLoginBtn.click();
		}
		
		//取得用户名输入框
		WebElement usernameInput = yr.getLocator("username", true);
		if( usernameInput != null )
		{
			//输入用户名
			Log.logInfo("输入用户名："+Config.regsiterUsername);
			usernameInput.sendKeys(Config.regsiterUsername);
		}
		
		//取得密码输入框
		WebElement passwordInput = yr.getLocator("password", true);
		if( passwordInput != null )
		{
			//输入密码
			Log.logInfo("输入密码："+Config.regsiterPassword);
			passwordInput.sendKeys(Config.regsiterPassword);
		}
		
		//取得确认登录按钮
		WebElement submitLoginBtn = yr.getLocator("submitLogin", true);
		if( submitLoginBtn != null )
		{
			Log.logInfo("点击【确认登录】");
			submitLoginBtn.submit();
		}
	}
	
	@Test(priority = 3, dependsOnGroups = { "register.*"})
	public void startThreeStep()
	{
		//取得【免费获取留学方案】按钮
		WebElement startThreeStepBtn = yr.getLocator("homePageStartThreeStep", true);
		if( startThreeStepBtn != null )
		{
			//输入密码
			Log.logInfo("点击【免费获取留学方案】按钮");
			startThreeStepBtn.click();
		}
	}
	
	@Test(dataProvider = "providerMethod",priority = 4, dependsOnGroups = { "register.*"})
	public void oneStep( Map<String, String> map ) 
	{
		Log.logInfo("开始第一滑");
		//取得想去的国家的单选框
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
						Log.logInfo("想去的国家选择为："+map.get("hopeCountry"));
						break;
					}
				}
			}
		}
		
		//取得计划出国时间的单选框
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
						Log.logInfo("计划出国时间选择为："+map.get("inlineYear"));
						break;
					}
				}
			}
		}
		
		//取得目前就读年纪的下拉框
		WebElement currentGradeSpa = yr.getLocator("oneStepPageCurrentGradeDropdown", true);
		if( currentGradeSpa != null )
		{
			currentGradeSpa.click();
			//取得目前就读年纪的下拉选项
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
							Log.logInfo("目前就读年纪选择为："+map.get("currentGrade"));
							break;
						}
					}
				}
			}
		}
		
		//取得【查看我的留学方案】按钮
		WebElement submitBtn = yr.getLocator("oneStepPageSubmitBtn", true);
		if( submitBtn != null )
		{
			submitBtn.click();
			Log.logInfo("点击【查看我的留学方案】");
			Log.logInfo("用户:"+Config.regsiterUsername+"完成第一滑");
		}
	}
	
	@Test(priority = 5, dependsOnGroups = { "register.*"})
	public void twoStep() 
	{
		Log.logInfo("开始第二滑");
		//取得留学方案多选框
		List<WebElement> listAppleCheckbox = yr.getElementsList("twoStepPageApplyCheckbox", true);
		//取得留学方案多选框文案
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
						Log.logInfo("选择留学方案:"+elementCheckboxText.getText());
					}
				}
			}
		}
		
		//取得【继续制定我的选校方案】按钮
		WebElement submitBtn = yr.getLocator("twoStepPageSubmitBtn", true);
		if( submitBtn != null )
		{
			submitBtn.click();
			Log.logInfo("点击【继续制定我的选校方案】");
			Log.logInfo("用户:"+Config.regsiterUsername+"完成第二滑");
		}
	}
	
	@Test(dataProvider = "providerMethod",priority = 6, dependsOnGroups = { "register.*"})
	public void threeStep( Map<String, String> map )
	{
		Log.logInfo("开始第三滑");
		//取得姓名输入框
		WebElement nameInput = yr.getLocator("threeStepPageNameInput", true);
		if( nameInput != null )
		{
			nameInput.sendKeys(map.get("realname"));
			Log.logInfo("输入姓名:"+map.get("realname"));
		}
		
		//取得最近就读学校输入框
		WebElement schoolInput = yr.getLocator("threeStepPageSchoolInput", true);
		if( schoolInput != null )
		{
			schoolInput.sendKeys(map.get("school"));
			Log.logInfo("输入最近就读学校:"+map.get("school"));
		}
		
		//取得就读专业下拉框
		WebElement majorDropdown = yr.getLocator("threeStepPageMajorDropdown", true);
		if( majorDropdown != null )
		{
			majorDropdown.click();
			//取得就读专业下拉选项
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
							Log.logInfo("就读专业选择为："+map.get("major"));
							break;
						}
					}
				}
			}
		}
		
		//取得期中期末考试平均分输入框
		WebElement scoreInput = yr.getLocator("threeStepPageScoreInput", true);
		if( scoreInput != null )
		{
			scoreInput.sendKeys(map.get("score"));
			Log.logInfo("输入期中期末考试平均分:"+map.get("score"));
		}
		
		//取得希望就读专业（大类）下拉框
		WebElement hopeMajorDropdown = yr.getLocator("threeStepPageHopeMajorDropdown", true);
		if( hopeMajorDropdown != null )
		{
			hopeMajorDropdown.click();
			//取得希望就读专业（大类）下拉选项
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
							Log.logInfo("希望就读专业选择为："+map.get("hopeMajor"));
							break;
						}
					}
				}
			}
		}
		
		//取得希望就读专业（小类）下拉框
		WebElement hopeSubMajorDropdown = yr.getLocator("threeStepPageHopeSubMajorDropdown", true);
		if( hopeSubMajorDropdown != null )
		{
			hopeSubMajorDropdown.click();
			//取得希望就读专业（小类）下拉框第一项
			WebElement hopeSubMajorDropdownList1st = yr.getLocator("threeStepPageHopeSubMajorDropdown1st", true);
			if( hopeSubMajorDropdownList1st != null )
			{
				//专业小类的下拉选项只有当大类选择后才会出现，所有等待小类下拉项出现后再进行后续操作
				//取得希望就读专业（小类）下拉选项
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
								Log.logInfo("希望就读专业小类选择为："+map.get("hopeSubMajor"));
								break;
							}
						}
					}
				}
			}
		}
		
		//取得【立即查看我的选校方案】按钮
		WebElement submitBtn = yr.getLocator("threeStepPageSubmitBtn", true);
		if( submitBtn != null )
		{
			submitBtn.click();
			Log.logInfo("点击【立即查看我的选校方案】");
			Log.logInfo("用户:"+Config.regsiterUsername+"完成第三滑");
		}
		
		//取得【立即查看我的选校方案】按钮
		WebElement finishThreeStep = yr.getLocator("threeStepFinishSubmitBtn", true);
		if( finishThreeStep != null )
		{
			finishThreeStep.click();
			Log.logInfo("点击【查看选校结果】");
		}
	}
	
	@Test(priority = 7, dependsOnGroups = { "register.*"})
	public void logout()
	{	
		//取得用户下拉框
		WebElement userWrap = yr.getLocator("userWrap", true);
		if( userWrap != null )
		{
			Actions act = new Actions(driver);
			if( act != null )
			{
				//Point point = userWrap.getLocation();
				//act.moveByOffset(point.x, point.y);
				//act.moveToElement(userWrap);
				//移动光标没有用，选择直接点击
				userWrap.click();
				
				//取得退出按钮
				WebElement logoutBtn = yr.getLocator("logout", true);
				if( logoutBtn != null )
				{
					Log.logInfo("点击【退出】按钮");
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
			//读取页面元素信息
			yr = new YamlReader(driver,"pageElement");
			//页面跳转
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
				emailReportIns.sendEmail("自动化测试遇到了错误，具体请查看附件的测试报告，",
										"test-output/emailable-report.html");
			}
			else
			{
				emailReportIns.sendEmail("自动化测试顺利完成，附件为测试报告，",
										"test-output/emailable-report.html");
			}
		}
		
		Config.setTestFailure(false);
		Config.setTestSkip(false);*/
	}
}
