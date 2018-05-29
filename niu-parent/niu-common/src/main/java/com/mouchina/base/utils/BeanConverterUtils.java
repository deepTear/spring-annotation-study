/**
 * 
 */
package com.mouchina.base.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: BeanConverterUtils 
 *
 * @Description TODO
 *
 * @author mouchina1005
 *
 * @date 2017年12月5日 上午10:31:11
 *
 */
public class BeanConverterUtils {

	/**
	 * 将传入的java实体类转换成Map(键对应实体类每个属性)返回
	 * @param bean
	 * @return
	 */
	public static Map<String,Object> convertBeanToMap(Object bean){
		Map<String,Object> map1 = new HashMap<>();
		
		//取得bean的所有属性
		
		/*用属性作为键初始化Map所有的Key   start*/
		Field[] fields = bean.getClass().getDeclaredFields();
		for(int i = 0; i < fields.length; i++){
			map1.put(fields[i].getName(), null);
		}
		/*用属性作为键初始化Map所有的Key   end*/
		
		for(Map.Entry<String, Object> entry : map1.entrySet()){
			String name = entry.getKey();//属性名
			//创建一属性描述器,将属性描述器映射到相关类中
			PropertyDescriptor pDescriptor;
			try {
				pDescriptor = new PropertyDescriptor(name, bean.getClass());
				//获取对应属性的getter方法
				Method getter = pDescriptor.getReadMethod();
				//将方法反射到类中，读取属性值
				Object o = getter.invoke(bean, null);
				map1.put(name, o);
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return map1;
	}
}
