package com.mouchina.web.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.mouchina.base.redis.RedisHelper;
import com.mouchina.base.resource.type.Image;
import com.mouchina.base.utils.HttpUtil;
import com.mouchina.base.utils.JsonUtils;

public class Test {

	public static void main(String[] args) {
		
		double s = 1024 * 1024 ;
		
		System.out.println();
		
		double limitSize = 10558;
		
		System.out.println("图片大小超过" + (new DecimalFormat("0.0#").format(limitSize / (1024 * 1024)) ) + "MB");
		
		try {  
			
			/*File file2 = new File("//172.16.7.96/public/01.transit/slow.txt");
			
			if (file2 != null && !file2.exists()) {
	            file2.mkdirs();
	            System.out.println("--------");
	        }	
			
			
			BufferedReader br = new BufferedReader(new FileReader(file2));
	        String str;
	        while ((str = br.readLine()) != null) {
	            System.out.println(str);
	        }
	        br.close();
			
	          OutputStream oputstream = new FileOutputStream(file2);  
	          URL url = new URL(fileUrl);  
	          HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
	          uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
	          uc.connect();  
	          InputStream iputstream = uc.getInputStream();  
	          System.out.println("file size is:"+uc.getContentLength());//打印文件长度  
	          byte[] buffer = new byte[4*1024];  
	          int byteRead = -1;     
	          while((byteRead=(iputstream.read(buffer)))!= -1){  
	              oputstream.write(buffer, 0, byteRead);  
	          }  
	          oputstream.flush();    
	          iputstream.close();  
	          oputstream.close();  */
			
	          
	          
	          
			File file = new File("C:\\Users\\Administrator\\Desktop\\11.jpg");
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1024];
			int len = -1;
			while((len = fis.read(b)) != -1) {
			    bos.write(b, 0, len);
			}
			fis.close();  
            bos.close();  
            b = bos.toByteArray();
			Image image = uploadBytesImage(b, "jpg", "https://pic.mouchina.com");
			
			if(image != null){
				System.out.println(image.getUrl());
			}
			
	      } catch (Exception e) {  
	          System.out.println("读取失败！");  
	          e.printStackTrace();  
	      }       
	}
	
	
	  public static Image uploadBytesImage(byte[] bytes, String suffix, String baseUrl) throws IOException {
	        Image image = uploadBytesAndPostBinImage(bytes, suffix, baseUrl);
	        return image;
	  }
	  
	  public static Image uploadBytesAndPostBinImage(byte[] bytes, String suffix, String baseUrl) throws IOException {
		  
		  byte[] comperssbytes = bytes; // comproessByteImage(bytes, 0.6f, suffix);
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("suffix", suffix);


	        File file = new File(baseUrl + "/image/upload");

	        if (!file.exists()) {
	            file.mkdirs();
	        }

	        String json = HttpUtil.executePostBinaryFile(baseUrl + "/image/upload", comperssbytes, "myfile", params);
	        System.out.println(json);
	        if(json != null && !json.equals("")){
	        	 Image image = JsonUtils.jsonToObject(json, Image.class);
	        	 return image;
	        }
	       
	        System.out.println("fail");
	        
	        return null;
	    }
}
