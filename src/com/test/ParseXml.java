package com.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseXml
{
	private Document doc;
	private String filePath;
	
	public ParseXml( String filePath )
	{
		this.filePath = filePath;
		this.loadFile(this.filePath);
	}
	
	public void loadFile( String filePath )
	{
		File file = new File(filePath);
		if( file.exists() )
		{
			SAXReader saxReader = new SAXReader();
			if( null != saxReader )
			{
				try 
				{
					doc = saxReader.read(file);
				}
				catch (DocumentException e) 
				{
					Log.logError("文件读取失败,读取路劲为："+filePath);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			Log.logError("文件读取失败,读取路劲为："+filePath);
		}
	}
	
	//指定路劲取得单个元素
	public Element getElementObject( String elementPath )
	{
		return (Element)doc.selectSingleNode(elementPath);
	}
	
	//返回xml里Path参数匹配的所有节点,按照显示的顺序取得 
	@SuppressWarnings("unchecked")
	public List<Element> getElementObjects( String elementPath )
	{
		return doc.selectNodes(elementPath);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getchildElementInfo( Element element )
	{
		Map<String, String> map = new HashMap<String, String>();
		List<Element> childElementList = element.elements();
		for( Element e : childElementList )
		{
			map.put(e.getName(), e.getText());
		}
		return map;
	}
	
	public String getElementText( String elementPath )
	{
		Element element = null;
		String text = null;
		//获得xml的节点元素
		element = (Element) doc.selectSingleNode(elementPath);
		
		if( bElementExist(elementPath) )
		{
			//取得节点元素的文字
			text = element.getText().trim();
		}
		return text;
	}
	
	public boolean bElementExist( String elementPath )
	{
		boolean exist = false;
		Element element = null;
		
		element = (Element)doc.selectSingleNode(elementPath);
		if( null != element )
		{
			exist = true;
		}
		
		return exist;
	}
}
