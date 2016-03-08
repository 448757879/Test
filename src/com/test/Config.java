package com.test;

import org.openqa.selenium.WebDriver;

public class Config
{
	public static String brower;
	public static String firefoxInstallPath;
	public static String chromeInstallPath;
	public static String ocrInstallPath;
	public static int waitTime;
	public static int pageLoadWaitTime;
	public static String configFilePath;
	public static String testPageUrl;
	public static String mobileValidatedCodeUrl;
	public static String registerMobile;
	public static String regsiterUsername;
	public static String regsiterPassword;
	public static String deleteExistUserUrl;
	public static String adminUrl;
	public static boolean bTestFailure;
	public static boolean bTestSkip;
	public static WebDriver currentDriver;
	public static String remoteBrower;
	public static String remoteUrl;
	public static String remoteBrowerInstallPath;
	
	static
	{
		configFilePath = "config/CommonConfig.xml";
		ParseXml pXml = new ParseXml(configFilePath);
		brower = pXml.getElementText("/*/brower/type");
		firefoxInstallPath = pXml.getElementText("/*/brower/firefoxInstallPath");
		chromeInstallPath = pXml.getElementText("/*/brower/chromeInstallPath");
		ocrInstallPath = pXml.getElementText("/*/ocrInstallPath");
		waitTime = Integer.parseInt(pXml.getElementText("/*/waitTime"));
		pageLoadWaitTime = Integer.parseInt(pXml.getElementText("/*/PageLoadWaitTime"));
		testPageUrl = pXml.getElementText("/*/url");
		mobileValidatedCodeUrl = pXml.getElementText("/*/MobileValidatedCodeUrl");
		registerMobile = pXml.getElementText("/*/registerMobile");
		deleteExistUserUrl = pXml.getElementText("/*/DeleteExistUserUrl");
		adminUrl = pXml.getElementText("/*/adminUrl");
		remoteBrower = pXml.getElementText("/*/brower/remote/brower");
		remoteUrl = pXml.getElementText("/*/brower/remote/remoteUrl");
		remoteBrowerInstallPath = pXml.getElementText("/*/brower/remote/remoteBrowerInstallPath");
	}
	
	public static void setRegsiterUsername( String username )
	{
		regsiterUsername = username;
	}
	
	public static void setRegsiterPassword( String Password )
	{
		regsiterPassword = Password;
	}
	
	public static void setTestFailure( boolean bFailure )
	{
		bTestFailure = bFailure;
	}
	
	public static void setTestSkip( boolean bskip )
	{
		bTestSkip = bskip;
	}
	
	public static void setCurrentDriver( WebDriver driver )
	{
		currentDriver = driver;
	}
}
