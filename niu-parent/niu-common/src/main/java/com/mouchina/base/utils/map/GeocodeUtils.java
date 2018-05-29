package com.mouchina.base.utils.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.util.JSON;
import com.mouchina.base.utils.JsonUtils;

/**
 * 地理/逆地理编码
 * @author Administrator
 *
 */
public class GeocodeUtils {
	
	private static final String KEY = "a379316cc8caf9ddb7b5254621880465"; //高德地图web服务API key

	private static final String URL_API_LOCATION = "http://restapi.amap.com/v3/geocode/regeo?key=%s&location=%s"; //高德地图web服务地理/逆地理编码接口
	
	private static final String URL_API_ADDRESS = "http://restapi.amap.com/v3/geocode/geo?key=%s&address=%s"; //高德地图web服务地理/逆地理编码接口
	/**
	 * 根据经纬度获取区县code
	 * @param location 经度纬度字符串(108.9335329,34.2300307)
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String getAreaCode(String location) throws IOException { 
        String adcode = null, data = null; 
        Map<String, Object> map = new HashMap<>();
        String url = String.format(URL_API_LOCATION, KEY, location); 
        URL myURL = null; 
        URLConnection httpsConn = null; 
        try { 
            myURL = new URL(url); 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } 
        InputStreamReader insr = null;
        BufferedReader br = null;
        try { 
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) { 
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
                br = new BufferedReader(insr); 
                while((data= br.readLine())!=null){ 
                	map = JsonUtils.jsonToObject(data, map.getClass());
                	String status = map.get("status").toString();
                	if(status.equals("1")){
                		Object object  = map.get("regeocode");
                    	String json = JsonUtils.javaToString(object);
                    	map.clear();
                    	map = JsonUtils.jsonToObject(json, map.getClass());
                    	Object adObjcet  = map.get("addressComponent");
                    	String json1 = JsonUtils.javaToString(adObjcet);
                    	map.clear();
                    	map = JsonUtils.jsonToObject(json1, map.getClass());
                    	adcode = map.get("adcode").toString();
                    	adcode = adcode.equals("[]") ? null : adcode;
                	}
                } 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally {
            if(insr!=null){
                insr.close();
            }
            if(br!=null){
                br.close();
            }
        }
        return adcode; 
    } 
	
	 /**
	  * 根据地址获取经纬度
	  * @param addr 详细地址
	  * @return
	  * @throws IOException
	  */
     @SuppressWarnings("unchecked")
	public static Double[] getCoordinate(String addr) throws IOException { 
    	
        double lng = 0.0;//经度
        double lat = 0.0;//纬度
        String data = null; 
        String address = null; 
        try { 
            address = java.net.URLEncoder.encode(addr, "UTF-8"); 
        }catch (UnsupportedEncodingException e1) { 
            e1.printStackTrace(); 
        } 
        Map<String, Object> map = new HashMap<>();
        String url = String.format(URL_API_ADDRESS, KEY, address); 
        URL myURL = null; 
        URLConnection httpsConn = null; 
        try { 
            myURL = new URL(url); 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } 
        InputStreamReader insr = null;
        BufferedReader br = null;
        try { 
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) { 
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
                br = new BufferedReader(insr); 
                while((data= br.readLine())!=null){ 
                	map = JsonUtils.jsonToObject(data, map.getClass());
                	String status = map.get("status").toString();
                	if(status.equals("1")){
                		Object object  = map.get("geocodes");
                    	String json = JsonUtils.javaToString(object);
                    	List<Object> list = JsonUtils.jsonToList(json, Object.class);
                    	String json1 = JsonUtils.javaToString(list.get(0));
                    	map.clear();
                    	map = JsonUtils.jsonToObject(json1, map.getClass());
                    	Object adObjcet  = map.get("location");
                    	String location = adObjcet.toString();
                    	String[] strings = location.split(",");
                    	lng = strings != null && strings.length == 2 ? Double.valueOf(strings[0]) : 0;
                    	lat = strings != null && strings.length == 2 ? Double.valueOf(strings[1]) : 0;
                	}
                } 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally {
            if(insr!=null){
                insr.close();
            }
            if(br!=null){
                br.close();
            }
        }
        return new Double[]{lng,lat}; 
    } 
	
	public static void main(String[] args) {
		try {
			Double d1 = 108.9335329;
			Double d2 = 34.2300307;
			String location = d1.doubleValue() + "," + d2.toString();
			System.out.println(location);
			System.out.println(getAreaCode(location));
			
			String str = "陕西省西安市碑林区张家村街道二环南路西段101号怡兰星空";
			Double[] dd = getCoordinate(str);
			System.out.println("经度：" + dd[0] + ",纬度：" + dd[1]);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
