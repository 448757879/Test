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
	//��ת��ע���б�ҳ
	public void openPage( String userType )
	{
		//�򿪺�̨����¼
		this.login("superTester");			
					
		//ȡ��ҳ������iframe
		WebElement LeftMenuIframe = yr.getLocator("adminLeftMenuIframe", true);
		if( LeftMenuIframe != null )
		{	
			//�л���ҳ������iframe
			driver.switchTo().frame(LeftMenuIframe);
						
			//ȡ��ע���û����
			WebElement regUserBtn = yr.getLocator("adminRegisterUsersMenuBtn", true);
			if( regUserBtn != null )
			{	
				//�����ע���û���
				Log.logInfo("�����ע���û���");
				regUserBtn.click();
							
				//�л���ԭ�ȵ�ҳ��
				driver.switchTo().defaultContent();
			}
		}
	}
	
	//����
	public boolean searchUser( String searchOption, String searchValue )
	{
		boolean ret = false;
		
		//ȡ��ע���û�ҳ���Ҳ��iframe
		WebElement regUserListRightMenuIframe = yr.getLocator("adminRegisterUserListRightIframe", true);
		if( regUserListRightMenuIframe != null )
		{
			//�л���ע���û�ҳ���Ҳ��iframe
			driver.switchTo().frame(regUserListRightMenuIframe);
				
			//ȡ��ģ��������������
			WebElement searchSelect = yr.getLocator("adminRegisterUserListSearchSelect", true);
			if( searchSelect != null )
			{
				Select select = new Select(searchSelect);
				if( select != null )
				{
					select.selectByVisibleText(searchOption);
					Log.logInfo("����ɸѡѡ��Ϊ��"+select.getFirstSelectedOption().getText());
					
					//ȡ��ģ�������������
					WebElement searchInput = yr.getLocator("adminRegisterUserListSearchInput", true);
					if( searchInput != null )
					{
						searchInput.sendKeys(searchValue);
						Log.logInfo("������"+searchValue);
						
						//ȡ�á���������ť
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
	
	//�����û�����
	public void setUserType( String type )
	{
		//ȡ��ע���û�ҳ���Ҳ��iframe
		WebElement regUserListRightMenuIframe = yr.getLocator("adminRegisterUserListRightIframe", true);
		if( regUserListRightMenuIframe != null )
		{
			//�л���ע���û�ҳ���Ҳ��iframe
			driver.switchTo().frame(regUserListRightMenuIframe);
		}
		
//		//ȡ����������ĵ�һ����ʾ���û���
//		String mobileExistUsername = null;
//		WebElement searchRetUsername = yr.getLocator("adminRegisterUserList1stUsername", true);
//		if( searchRetUsername != null )
//		{
//			mobileExistUsername = searchRetUsername.getText();
//			mobileExistUsername = mobileExistUsername.substring(0, (mobileExistUsername.length()-1));
//			Log.logInfo("�û���:"+mobileExistUsername);
//			Config.setRegsiterUsername(mobileExistUsername);
//		}
		
		//ȡ����������ĵ�һ����ʾ��uid
		String mobileExistUid = null;
		WebElement searchRetUid = yr.getLocator("adminRegisterUserList1stUid", true);
		if( searchRetUid != null )
		{
			mobileExistUid = searchRetUid.getText();
			Log.logInfo("�û�uid:"+mobileExistUid);
			Log.logInfo("�û���:offer"+mobileExistUid);
			Config.setRegsiterUsername("offer"+mobileExistUid);
			
			WebElement typeSetBtn = yr.getLocatorWithYamlVar("adminRegisterUserList1stTypeSetBtn", mobileExistUid, true);
			if( typeSetBtn != null )
			{
				typeSetBtn.click();
				
/*			    //�õ���ǰ���ڵľ��  
		        String currentWindow = driver.getWindowHandle();  
		        //�õ����д��ڵľ��  
		        Set<String> handles = driver.getWindowHandles();  
		        Iterator<String> it = handles.iterator();  
		        while(it.hasNext())
		        {  
		            String handle = it.next();  
		            if(currentWindow.equals(handle))
		            {
		            	continue;  
		            }
		            
		            //�л����������õĵ���
		            WebDriver windowDriver = driver.switchTo().window(handle);  
		             
		            YamlReader yrWindow = new YamlReader(windowDriver,"pageElement");*/
								
				//WebElement typeSetIframe = yr.getLocator("adminRegisterUserListTypeSetIframe", true);
				WebElement typeSetIframe = driver.findElement(By.xpath(".//*[@id='fancybox-content']/iframe"));
				if( typeSetIframe != null )
				{	
					//�л���ҳ������iframe
					driver.switchTo().frame(typeSetIframe);
				
		            String selectType = null;
		            
		            if( type.equals("��Ч") )
		            {
		            	selectType = "adminRegisterUserListTypeSetValid";
		            }
		            else if( type.equals("������") )
		            {
		            	selectType = "adminRegisterUserListTypeSetFollowing";
		            }
		            else if( type.equals("�ݻ�") )
		            {
		            	selectType = "adminRegisterUserListTypeSetDefer";
		            }
		            else if( type.equals("������") )
		            {
		            	selectType = "adminRegisterUserListTypeSetUnapt";
		            }
		            else if( type.equals("��Ч") )
		            {
		            	selectType = "adminRegisterUserListTypeSetInvalid";
		            }
		            else if( type.equals("��������") )
		            {
		            	selectType = "adminRegisterUserListTypeSetTest";
		            }
		            else if( type.equals("��������") )
		            {
		            	selectType = "adminRegisterUserListTypeSetWrongData";
		            }
		            else if( type.equals("��ϵ����") )
		            {
		            	selectType = "adminRegisterUserListTypeSetContactFail";
		            }
		            else
		            {
		            	Log.logError("������û����ͣ�"+type);
		            	return;
		            }
		            
		            //���ݲ���ȡ���������ð�ť
			        WebElement typeSelect = yr.getLocator(selectType, true);
			        if( typeSelect != null )
			        {
			        	Log.logInfo("�û���offer"+mobileExistUid+"������Ϊ:"+type);
			        	typeSelect.click();
		            }
			        
			        //ȡ�á�ȷ�ϡ���ť
			        WebElement commitBtn = yr.getLocator("adminRegisterUserListTypeSetCCommitBtn", true);
			        if( commitBtn != null )
			        {
			        	Log.logInfo("ȷ�ϱ���");
			        	commitBtn.click();
		            }
		        }
			}
		}
	}
}
