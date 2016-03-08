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
	
	//在后台设置用户类型为测试用户
	public void setTestUserType()
	{
		//先将该用户通过51offer后台设置为测试数据
		registerUserListPage st = new registerUserListPage();
		//登录51offer后台
		st.openPage("superTester");
		//搜索电话
		if(st.searchUser("mobile", Config.registerMobile))
		{
			//设置用户类型
			st.setUserType("测试数据");
		}
		
		//关闭后台
		st.end();			
	}
	
	//删除已注册用户（指定手机号）
	public void deleteExistMobileTestUser()
	{
		b51offer badmin = new b51offer();
		//登录51offerB端后台
		badmin.login("test", "123123");
		//删除用户
		badmin.deleteUser(Config.regsiterUsername, Config.registerMobile);
		//关闭B端后台
		badmin.end();
	}
	
	//解析图片并输入图片验证码
	public void inputValidatedCode()
	{
		String codeUrl = null;
		//取得验证码图片
		WebElement codePic = yr.getLocator("validatedPic", true);
		if( codePic != null )
		{
			codeUrl = codePic.getAttribute("src");
		}		

		ocrGetCode orc = new ocrGetCode();
		
		String code = orc.getCode(codeUrl);
		
		//取得图片验证码输入框
		WebElement validatedCodeInput = yr.getLocator("validatedCode", true);
		if( validatedCodeInput != null )
		{
			//输入图片验证码
			Log.logInfo("输入图片验证码："+code);
			validatedCodeInput.clear();
			validatedCodeInput.sendKeys(code);
		}
	}
	
	//取得短信验证码并输入
	public void inputMobileValidatedCode( String mobile )
	{
		//取得短信验证码输入框
		WebElement mobileCodeInput = yr.getLocator("mobileValidatedCode", true);
		if( mobileCodeInput != null )
		{
			mobileValidatedCode mvc = new mobileValidatedCode();
			//取得短信验证码
			String mobileCode = mvc.getLastMobileValidatedCode(mobile);
			//关闭短信查询平台
			mvc.end();
			
			//输入短信验证码证码
			Log.logInfo("输入短信验证码："+mobileCode);
			mobileCodeInput.clear();
			mobileCodeInput.sendKeys(mobileCode);
		}
	}
	
	public boolean registerSubmit()
	{
		String currentUrl = driver.getCurrentUrl();
		
		//取得【确认注册】按钮
		WebElement registerBtn = yr.getLocator("submitRegister", true);
		if( registerBtn != null )
		{
			//点击【确认注册】
			Log.logInfo("点击【确认注册】");
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
		//取得注册按钮
		WebElement registerBtn = yr.getLocator("registerBtn", true);
		if( registerBtn != null )
		{	
			//点击登录按钮
			Log.logInfo("首页点击【注册】按钮");
			registerBtn.click();
		}
	}
	
	@Test(dataProvider = "providerMethod",priority = 2, groups = { "register" }, dependsOnMethods = "selectRegister")
	public void register( Map<String, String> map ) 
	{
		String currentUrl = driver.getCurrentUrl();
		
		//Log.logInfo(currentUrl);
		
		//因为需要收取手机验证码，暂时不考虑其他国家的手机，所以国家不操作，使用默认的中国
		//取得手机号输入框
		WebElement mobileInput = yr.getLocator("mobile", true);
		if( mobileInput != null )
		{
			//输入手机号
			Log.logInfo("输入手机号："+map.get("mobile"));
			mobileInput.sendKeys(map.get("mobile"));
		}
		
		//取得密码输入框
		WebElement passwordInput = yr.getLocator("registerPassword", true);
		if( passwordInput != null )
		{
			//输入密码
			Log.logInfo("输入密码："+map.get("password"));
			passwordInput.sendKeys(map.get("password"));
			Config.setRegsiterPassword(map.get("password"));
		}
		
		//输入图片验证码
		inputValidatedCode();
		
		//取得【获取短信验证码】按钮
		WebElement getMobileCodeBtn = yr.getLocator("getMobileValidatedCode", true);
		if( getMobileCodeBtn != null )
		{
			//点击【获取短信验证码】按钮
			Log.logInfo("点击【获取短信验证码】");
			getMobileCodeBtn.click();
			
			//取得当前页面的错误提醒
			//手机重复提醒
			WebElement errMobileExist = yr.getLocator("errMobileExist", false);

			Log.logInfo("验证注册信息是否正确");
			
			//手机重复的场合
			if( errMobileExist.isDisplayed() )
			//if( yr.waitForElementDisplayed(errMobileExist, 1) )
			{
				Log.logInfo("手机号已注册");
				//删除前先将该用户设置为测试用户
				this.setTestUserType();
				//删除该手机号的注册用户
				this.deleteExistMobileTestUser();
				//再次点击【获取短信验证码】按钮
				Log.logInfo("再次点击【获取短信验证码】");
				getMobileCodeBtn.click();
			}
			
			//输入短信验证码
			this.inputMobileValidatedCode(map.get("mobile"));
		}
		
		//取得【确认注册】按钮
		WebElement registerBtn = yr.getLocator("submitRegister", true);
		if( registerBtn != null )
		{
			//点击【确认注册】
			Log.logInfo("点击【确认注册】");
			if( registerSubmit() )
			{
				return;
			}
		}
		
		//图片验证码错误，ocr的识别率不是很高，经常识别错误
		WebElement errValidatedCode = yr.getLocator("errValidatedCode", false);
		
		//图片验证码解析错误的场合
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
				Log.logInfo("图片验证码识别错误");
				//点击【换一张】按钮
				//Log.logInfo("点击【换一张】");
				//changeValidatedPicBtn.click();
				//再次输入图片验证码
				Log.logInfo("更换验证再次输入");
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
			
			//验证码失效
			WebElement invalidMobileValidatedCode = yr.getLocator("invalidMobileValidatedCode", false);
			
			//短信验证码失效的场合
			if( invalidMobileValidatedCode.isDisplayed() )
			//if( yr.waitForElementDisplayed(invalidMobileValidatedCode, 1) )
			{
				Log.logInfo("短信验证码失效");
				Log.logInfo("再次点击【获取短信验证码】");
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
		/*//取得注册后的用户
		WebElement registerUsername = yr.getLocator("registerUsername", true);
		
		if( registerUsername != null )
		{
			Log.logInfo("用户名为："+registerUsername.getText());
			Config.setRegsiterUsername(registerUsername.getText());
		}*/
		
		//注册完设置为测试用户
		this.setTestUserType();
		
		//取得开始DIY页面的【进入我的留学申请】按钮
		WebElement startDiyPageEnterBtn = yr.getLocator("startDiyPageEnterBtn", true);
		
		if( startDiyPageEnterBtn != null )
		{
			Log.logInfo("点击【进入我的留学申请】");
			startDiyPageEnterBtn.click();
		}
	}
	
/*	@Test(priority = 4)
	public void logout()
	{
		//取得用户下拉框
		WebElement userWrap = yr.getLocator("userWrap", true);
		if( userWrap != null )
		{
			Actions act = new Actions(driver);
			if( act != null )
			{
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
	}*/
	
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
