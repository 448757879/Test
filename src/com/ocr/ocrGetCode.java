package com.ocr;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ocrGetCode 
{
	//private static String codeUrl = "http://account.51offer.com/imageServlet";
	private static String imgPath = "test.jpg";
	/**
	 * @param args
	 * @throws IOException
	 */
	public String getCode(String codeUrl)
	{
		String code =null;
		String path = System.getProperty("user.dir") + "\\"+ imgPath;
		File file = new File(path);
		try 
		{
			InputStream is = returnInput(codeUrl);
			returnOutput(is);
			code = new OCR().recognizeText(file, "jpg");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	public static InputStream returnInput(String path) throws IOException {
		URL url = null;
		InputStream is = null;
		url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.connect();
		is = conn.getInputStream();
		return is;
	}

	public static void returnOutput(InputStream inStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		byte[] data = outStream.toByteArray();
		FileOutputStream fileOutStream = new FileOutputStream("test.jpg");
		fileOutStream.write(data);
		fileOutStream.close();
	}

}
