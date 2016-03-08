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
		String host = "smtp.exmail.qq.com";  	//������ʹ�÷��ʼ��ĵ������������
	    String from = "delta.w@51offer.com"; 	//���ʼ��ĳ����أ������˵����䣩
	    //String to = "delta.w@51offer.com";   	//���ʼ���Ŀ�ĵأ��ռ������䣩�����ˣ�
	   	//���ʼ���Ŀ�ĵأ��ռ������䣩�����ˣ�
	    String toList[] = {"delta.w@51offer.com", "loly.z@51offer.com", "huifan.c@51offer.com"};
	    //Properties props = System.getProperties();
	    Properties props = new Properties();

	    // �������������
	    props.put("mail.smtp.host", host);
	    //props.put("mail.smtp.port", "465");
	    
	    //props.put("mail.transport.protocol", "smtp"); 
	    
	    // ����session
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
			
		    // ���÷�����
			message.setFrom(new InternetAddress(from));
			// ��������
			message.setSentDate(new Date());
			// ���ñ���
	  		message.setSubject("�Զ������Ա���");
		
		    Multipart mainPart = new MimeMultipart();
		    // ����һ������HTML���ݵ�MimeBodyPart    
		    BodyPart html = new MimeBodyPart();    
	    
			html.setContent(mailMessage+"�����ʼ�Ϊϵͳ�Զ����ͣ�����ظ���", "text/html; charset=utf-8");
		
			mainPart.addBodyPart(html);
	    
		    //��Ӹ���
		    // ����һ�µ�MimeBodyPart  
		    MimeBodyPart mdp = new MimeBodyPart();
		    //�õ��ļ�����Դ
		    FileDataSource fds = new FileDataSource(filepath);
		    //�õ�������������BodyPart	   
			mdp.setDataHandler(new DataHandler(fds));
			mdp.setFileName(fds.getName());
			mainPart.addBodyPart(mdp);
			//��������
			message.setContent(mainPart);
		
			message.saveChanges();
			//����
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
							"�Զ������Ա���",
							"��ο�����");
		er.sendEmail();
	}*/
}