package com.test;

import com.test.ScreenShot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;

public class Log
{
    private static Logger logger;
     
    private static String filepath = "src/log4j.properties";
     
    private static boolean flag = false;
     
    private static synchronized void getPropertyFile()
    {
        logger = Logger.getLogger("TestProject");
        PropertyConfigurator.configure(new File(filepath).getAbsolutePath());
        flag = true;
        
       /* Appender appender = LogManager.getLoggerRepository().getRootLogger().getAppender("A2");
        if(appender instanceof FileAppender) 
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

          	FileAppender fileAppender = (FileAppender)appender; 
        	fileAppender.setFile("log/"+ sdf.format(new Date().getTime()) +".log"); 
        	fileAppender.activateOptions();
        }*/
    }
     
    private static void getflag()
    {
        if( flag == false )
        {
            Log.getPropertyFile();
        }
    }
     
    public static void logInfo( String message )
    {
        Log.getflag();
        logger.info("【Success】"+message);
    }
     
    public static void logError( String message, WebDriver driver )
    {
        Log.getflag();
        logger.error("【failure】"+message);
        //错误截图
		ScreenShot ss = new ScreenShot( driver );
		ss.takeScreenShot();
    }
    
    public static void logError( String message )
    {
        Log.getflag();
        logger.error("【failure】"+message);
    }
    
    public static void logWarn( String message )
    {
        Log.getflag();
        logger.warn("【Warning】"+message);
    }
    
    public void deleteLogFile()
    {
    	File oldfile=new File("log/testlog.log");
    	
    	if( oldfile.exists() )
    	{
    		oldfile.delete();
    	}
    }
    
    public void createLogFile()
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    	String newFileName = "selenuim_"+sdf.format(new Date().getTime())+".log";
    	
    	File oldfile=new File("log/testlog.log"); 
        File newfile=new File("log/"+newFileName); 
    	
        /*if(oldfile.exists())
    	{
    		oldfile.renameTo(newfile);
    	}*/
    	
    	FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try 
        {
            fi = new FileInputStream(oldfile);
            fo = new FileOutputStream(newfile);
            //得到对应的文件通道
            in = fi.getChannel();
            //得到对应的文件通道
            out = fo.getChannel();
            //连接两个通道，并且从in通道读取，然后写入out通道
            in.transferTo(0, in.size(), out);

        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                fi.close();
                in.close();
                fo.close();
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

