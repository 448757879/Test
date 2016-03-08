package com.test;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class testngListener extends TestListenerAdapter
{
	@Override
	public void onTestFailure(ITestResult tr)
	{
		super.onTestFailure(tr);
		Log.logError("【"+tr.getName()+"】TestCase failure", Config.currentDriver);
		tr.setStatus(ITestResult.FAILURE);
		Config.setTestFailure(true);
		
	}

	@Override
	public void onTestSkipped(ITestResult tr)
	{
		super.onTestSkipped(tr);
		Log.logError("【"+tr.getName()+"】TestCase Skipped");
		tr.setStatus(ITestResult.SKIP);
		Config.setTestSkip(true);
	}

	@Override
	public void onTestSuccess(ITestResult tr)
	{
		super.onTestSuccess(tr);
		Log.logInfo("【"+tr.getName()+"】TestCase Success");
		tr.setStatus(ITestResult.SUCCESS);
	}
	
	@Override
	public void onTestStart(ITestResult tr)
	{
		super.onTestStart(tr);
		Log.logInfo("【"+tr.getName()+"】 Start");
	}

	@Override
	public void onFinish(ITestContext testContext)
	{
		super.onFinish(testContext);
		Log.logInfo("test finish");
		
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
