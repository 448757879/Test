package com.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class emailReport
{
	private static emailReport instance = null;  
	  
    private emailReport()
    {  
  
    } 
    
    public static emailReport getInstance()
    {  
        if (instance == null)
        {  
            instance = new emailReport();  
        }  
        return instance;  
    }
    
	public void sendEmail( String mailMessage, String filepath )
	{
		String host = "smtp.exmail.qq.com";  	//发件人使用发邮件的电子信箱服务器
	    String from = "delta.w@51offer.com"; 	//发邮件的出发地（发件人的信箱）
	    //String to = "delta.w@51offer.com";   	//发邮件的目的地（收件人信箱）（单人）
	   	//发邮件的目的地（收件人信箱）（多人）
	    String toList[] = {"delta.w@51offer.com", "loly.z@51offer.com", "huifan.c@51offer.com"};
	    //Properties props = System.getProperties();
	    Properties props = new Properties();

	    // 设置邮箱服务器
	    props.put("mail.smtp.host", host);
	    //props.put("mail.smtp.port", "465");
	    
	    //props.put("mail.transport.protocol", "smtp"); 
	    
	    // 设置session
	    props.put("mail.smtp.auth", "true");

	    MyAuthenticator  myauth = new MyAuthenticator ("delta.w@51offer.com", "offer1234");
	    Session session = Session.getInstance(props, myauth);
	    
	    MimeMessage message = new MimeMessage(session);
	    
	    String stringToList = getMailList(toList);  
        InternetAddress[] iaToList;
		try 
		{
			iaToList = new InternetAddress().parse(stringToList);
			
			message.setRecipients(Message.RecipientType.TO,iaToList);
			
		    // 设置发件人
			message.setFrom(new InternetAddress(from));
			// 设置日期
			message.setSentDate(new Date());
			// 设置标题
	  		message.setSubject("自动化测试报告");
		
		    Multipart mainPart = new MimeMultipart();
		    // 创建一个包含HTML内容的MimeBodyPart    
		    BodyPart html = new MimeBodyPart();    
	    
			html.setContent(mailMessage+"（本邮件为系统自动发送，请勿回复）", "text/html; charset=utf-8");
		
			mainPart.addBodyPart(html);
	    
		    //添加附件
		    // 创建一新的MimeBodyPart  
		    MimeBodyPart mdp = new MimeBodyPart();
		    //得到文件数据源
		    FileDataSource fds = new FileDataSource(filepath);
		    //得到附件本身并至入BodyPart	   
			mdp.setDataHandler(new DataHandler(fds));
			mdp.setFileName(fds.getName());
			mainPart.addBodyPart(mdp);
			//设置正文
			message.setContent(mainPart);
		
			message.saveChanges();
			//发送
			Transport.send(message);
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	private String getMailList(String[] mailArray)
	{  
		StringBuffer toList = new StringBuffer();  
		int length = mailArray.length;  
        if(mailArray!=null && length <2)
        {  
             toList.append(mailArray[0]);  
        }
        else
        {  
             for(int i=0;i<length;i++)
             {  
            	 toList.append(mailArray[i]);  
                 if(i!=(length-1))
                 {  
                	 toList.append(",");  
                 }  
             }  
        }  
        return toList.toString();  
	}  
	
	/*public static void main(String[] args) 
	{
		emailReport er = new emailReport();
		er.sendReportByMail("delta.w@51offer.com",
							"offer1234",
							"delta.w@51offer.com",
							"自动化测试报告",
							"请参考附件");
		er.sendEmail();
	}*/
}