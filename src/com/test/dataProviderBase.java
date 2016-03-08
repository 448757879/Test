package com.test;


import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Element;
import org.testng.annotations.DataProvider;

public class dataProviderBase
{
	@DataProvider
	public Object[][] providerMethod( Method method )
	{
		String filePath = "testData/"+this.getClass().getSimpleName()+".xml";
		ParseXml px = new ParseXml( filePath );
		String methodName = method.getName();
		List<Element> elements = px.getElementObjects("/*/"+methodName);
		Object[][] object = new Object[elements.size()][];
		
		for( int i = 0; i < elements.size(); i++ )
		{
			Object[] temp = new Object[]{px.getchildElementInfo(elements.get(i))};
			object[i] = temp;
		}
		return object;
	}
}
