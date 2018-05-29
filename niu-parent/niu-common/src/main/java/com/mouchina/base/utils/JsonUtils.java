package com.mouchina.base.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class JsonUtils {
	public static String javaToString(Object obj) {
		String json = "";

		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static <T> T jsonToObject(String json, Class<T> T) {
		ObjectMapper mapper = new ObjectMapper();
		T result = null;
		try {
			result = mapper.readValue(json, T);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> readValue(String jsonString, JavaType javaType) throws JsonParseException {
		ObjectMapper mapper = new ObjectMapper();
		List<T> list = null;
		try {
			list = (List<T>) mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 将json数据转换成pojo对象list
	 * <p>
	 * Title: jsonToList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = mapper.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 这个方法是将xml字符串转成Json
	public static String xmlChangeJson(String XML) throws DocumentException {
		Document document = DocumentHelper.parseText(XML);
		Element root = document.getRootElement();
		Iterator it = root.elementIterator();
		String json = "{";
		while (it.hasNext()) {
			Element element = (Element) it.next();
			String j = checkChildEle(element);
			if (j == "") {
				json += "\"" + element.getName() + "\"" + ":\"" + element.getText() + "\"" + ",";
			} else {
				json += j;
			}

		}
		json = json.substring(0, json.lastIndexOf(','));
		json += "}";
		return json;
	}

	// 用于判断是否有子节点,若有就将子节点也进行拼接,若无则返回""
	public static String checkChildEle(Element element) throws DocumentException {
		String json = "";
		List<Element> list = new ArrayList<Element>();
		list = element.elements();
		if (list.size() > 0) {
			for (Element ele : list) {
				if (ele.getName() == "prepay_id") {
					json += "\"" + ele.getName() + "\"" + ":\"" + ele.getName() + "=" + ele.getText() + "\"" + ","
							+ checkChildEle(ele);
				} else {
					json += "\"" + ele.getName() + "\"" + ":\"" + ele.getText() + "\"" + "," + checkChildEle(ele);
				}
			}
		}
		return json;
	}
}
