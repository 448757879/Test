package com.testcase;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.test.Config;
import com.test.Log;
import com.test.YamlReader;

public class bakRegisterUserListPage extends adminBase
{
	//跳转至注册列表页
	public void openPage( String userType )
	{
		//打开后台并登录
		this.login("superTester");			
					
		//取得页面左侧的iframe
		WebElement LeftMenuIframe = yr.getLocator("adminLeftMenuIframe", true);
		if( LeftMenuIframe != null )
		{	
			//切换至页面左侧的iframe
			driver.switchTo().frame(LeftMenuIframe);
						
			//取得注册用户入口
			WebElement regUserBtn = yr.getLocator("adminRegisterUsersMenuBtn", true);
			if( regUserBtn != null )
			{	
				//点击【注册用户】
				Log.logInfo("点击【注册用户】");
				regUserBtn.click();
							
				//切换至原先的页面
				driver.switchTo().defaultContent();
			}
		}
	}
	
	//搜索
	public boolean searchUser( String searchOption, String searchValue )
	{
		boolean ret = false;
		
		//取得注册用户页面右侧的iframe
		WebElement regUserListRightMenuIframe = yr.getLocator("adminRegisterUserListRightIframe", true);
		if( regUserListRightMenuIframe != null )
		{
			//切换至注册用户页面右侧的iframe
			driver.switchTo().frame(regUserListRightMenuIframe);
				
			//取得模糊搜索的下拉框
			WebElement searchSelect = yr.getLocator("adminRegisterUserListSearchSelect", true);
			if( searchSelect != null )
			{
				Select select = new Select(searchSelect);
				if( select != null )
				{
					select.selectByVisibleText(searchOption);
					Log.logInfo("搜索筛选选择为："+select.getFirstSelectedOption().getText());
					
					//取得模糊搜索的输入框
					WebElement searchInput = yr.getLocator("adminRegisterUserListSearchInput", true);
					if( searchInput != null )
					{
						searchInput.sendKeys(searchValue);
						Log.logInfo("搜索："+searchValue);
						
						//取得【搜索】按钮
						WebElement searchBtn = yr.getLocator("adminRegisterUserListSearchBtn", true);
						if( searchBtn != null )
						{
							searchBtn.click();
							ret = true;
						}
					}
				}
			}
		}
		return ret;
	}
	
	//设置用户类型
	public void setUserType( String type )
	{
		//取得注册用户页面右侧的iframe
		WebElement regUserListRightMenuIframe = yr.getLocator("adminRegisterUserListRightIframe", true);
		if( regUserListRightMenuIframe != null )
		{
			//切换至注册用户页面右侧的iframe
			driver.switchTo().frame(regUserListRightMenuIframe);
		}
		
//		//取得搜索结果的第一行显示的用户名
//		String mobileExistUsername = null;
//		WebElement searchRetUsername = yr.getLocator("adminRegisterUserList1stUsername", true);
//		if( searchRetUsername != null )
//		{
//			mobileExistUsername = searchRetUsername.getText();
//			mobileExistUsername = mobileExistUsername.substring(0, (mobileExistUsername.length()-1));
//			Log.logInfo("用户名:"+mobileExistUsername);
//			Config.setRegsiterUsername(mobileExistUsername);
//		}
		
		//取得搜索结果的第一行显示的uid
		String mobileExistUid = null;
		WebElement searchRetUid = yr.getLocator("adminRegisterUserList1stUid", true);
		if( searchRetUid != null )
		{
			mobileExistUid = searchRetUid.getText();
			Log.logInfo("用户uid:"+mobileExistUid);
			Log.logInfo("用户名:offer"+mobileExistUid);
			Config.setRegsiterUsername("offer"+mobileExistUid);
			
			WebElement typeSetBtn = yr.getLocatorWithYamlVar("adminRegisterUserList1stTypeSetBtn", mobileExistUid, true);
			if( typeSetBtn != null )
			{
				typeSetBtn.click();
				
/*			    //得到当前窗口的句柄  
		        String currentWindow = driver.getWindowHandle();  
		        //得到所有窗口的句柄  
		        Set<String> handles = driver.getWindowHandles();  
		        Iterator<String> it = handles.iterator();  
		        while(it.hasNext())
		        {  
		            String handle = it.next();  
		            if(currentWindow.equals(handle))
		            {
		            	continue;  
		            }
		            
		            //切换至类型设置的弹窗
		            WebDriver windowDriver = driver.switchTo().window(handle);  
		             
		            YamlReader yrWindow = new YamlReader(windowDriver,"pageElement");*/
								
				//WebElement typeSetIframe = yr.getLocator("adminRegisterUserListTypeSetIframe", true);
				WebElement typeSetIframe = driver.findElement(By.xpath(".//*[@id='fancybox-content']/iframe"));
				if( typeSetIframe != null )
				{	
					//切换至页面左侧的iframe
					driver.switchTo().frame(typeSetIframe);
				
		            String selectType = null;
		            
		            if( type.equals("有效") )
		            {
		            	selectType = "adminRegisterUserListTypeSetValid";
		            }
		            else if( type.equals("跟进中") )
		            {
		            	selectType = "adminRegisterUserListTypeSetFollowing";
		            }
		            else if( type.equals("暂缓") )
		            {
		            	selectType = "adminRegisterUserListTypeSetDefer";
		            }
		            else if( type.equals("不合适") )
		            {
		            	selectType = "adminRegisterUserListTypeSetUnapt";
		            }
		            else if( type.equals("无效") )
		            {
		            	selectType = "adminRegisterUserListTypeSetInvalid";
		            }
		            else if( type.equals("测试数据") )
		            {
		            	selectType = "adminRegisterUserListTypeSetTest";
		            }
		            else if( type.equals("错误数据") )
		            {
		            	selectType = "adminRegisterUserListTypeSetWrongData";
		            }
		            else if( type.equals("联系不上") )
		            {
		            	selectType = "adminRegisterUserListTypeSetContactFail";
		            }
		            else
		            {
		            	Log.logError("错误的用户类型："+type);
		            	return;
		            }
		            
		            //根据参数取得类型设置按钮
			        WebElement typeSelect = yr.getLocator(selectType, true);
			        if( typeSelect != null )
			        {
			        	Log.logInfo("用户：offer"+mobileExistUid+"被设置为:"+type);
			        	typeSelect.click();
		            }
			        
			        //取得【确认】按钮
			        WebElement commitBtn = yr.getLocator("adminRegisterUserListTypeSetCCommitBtn", true);
			        if( commitBtn != null )
			        {
			        	Log.logInfo("确认保存");
			        	commitBtn.click();
		            }
		        }
			}
		}
	}
}
