package com.test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

//截图类
public class ScreenShot
{
	public WebDriver driver;
	
	public ScreenShot( WebDriver driver )
	{
		this.driver = driver;
	}
	
	public void takeScreenShot( String path )
	{
		try
		{
			File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(path));
		}
		catch( IOException e )
		{
			System.out.println("Screen shot error : "+path);
		}
		
		emailReport emailReportIns = emailReport.getInstance();
		if( emailReportIns != null )
		{
			emailReportIns.sendEmail("自动化测试遇到了错误，附件是错误时的页面截图，", path);
		}
	}
	
	  public void takeScreenShot()
	  {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

	      String screenName = sdf.format(new Date().getTime()) + ".jpg";

	      File file = new File("test-output/snapshot");
	      if( !file.exists() )
	      {
	    	  file.mkdirs();
	      }

	      String path = file.getAbsolutePath() + "/" + screenName;
	      this.takeScreenShot(path);
	   }
}
