package com.testcase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlMethodSelector;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.test.Config;
import com.test.emailReport;

//import com.test.testngListener;

public class startTest 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		XmlSuite suite = new XmlSuite();
		suite.setName("51offer");
		XmlTest test = new XmlTest(suite);
		test.setName("test");
		test.setPreserveOrder("true");
		List<XmlClass> classes = new ArrayList<XmlClass>();
		
		XmlClass registerClass = new XmlClass("com.testcase.register");
		
		/*List<XmlInclude> registerIncludes = new ArrayList<XmlInclude>();
		
		XmlInclude registerMethodOne= new XmlInclude("selectRegister");
		
		XmlInclude registerMethodTwo= new XmlInclude("register"); 
		XmlInclude registerMethodThree= new XmlInclude("finishRegister"); 
		//XmlInclude registerMethodFour= new XmlInclude("logout");
		registerIncludes.add(registerMethodOne);
		registerIncludes.add(registerMethodTwo);
		registerIncludes.add(registerMethodThree);
		//registerIncludes.add(registerMethodFour);
		
		registerClass.setIncludedMethods(registerIncludes); */
		
		XmlClass loginClass = new XmlClass("com.testcase.login");
		
		/*List<XmlInclude> loginIncludes = new ArrayList<XmlInclude>();
		XmlInclude loginMethodOne= new XmlInclude("selectLogin"); 
		XmlInclude loginMethodTwo= new XmlInclude("login"); 
		XmlInclude loginMethodThree= new XmlInclude("startThreeStep"); 
		XmlInclude loginMethodFour= new XmlInclude("oneStep");
		XmlInclude loginMethodFive= new XmlInclude("twoStep"); 
		XmlInclude loginMethodSix= new XmlInclude("threeStep");
		XmlInclude loginMethodSeven= new XmlInclude("logout");
		registerIncludes.add(loginMethodOne);
		registerIncludes.add(loginMethodTwo); 
		registerIncludes.add(loginMethodThree); 
		registerIncludes.add(loginMethodFour);
		registerIncludes.add(loginMethodFive); 
		registerIncludes.add(loginMethodSix); 
		registerIncludes.add(loginMethodSeven);
		
		loginClass.setIncludedMethods(loginIncludes); */
		
		classes.add(registerClass);
		classes.add(loginClass);
		
		test.setXmlClasses(classes) ;
		
		//testngListener tla = new testngListener();
		TestNG testng = new TestNG();
		//testng.addListener(tla);
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suite.addListener("com.test.testngListener");
		suites.add(suite);
		testng.setXmlSuites(suites);
		testng.setPreserveOrder(true);
		testng.run();
		
		emailReport emailReportIns = emailReport.getInstance();
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
		Config.setTestSkip(false);
	}
}
