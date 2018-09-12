package com.yc.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



public class RequestUtils {
	/**
	 * 工具类：将request中的参数取出，将参数值对应的存到一个clz生成的对象中
	 * 注意： 只能取出request 中的参数值是一个的情况，如果是多个值，请用request.getParameterValues("参数名")
	 * @param request
	 * @param clz  
	 * @return  clz对应的对象
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static <T> T parseRequest(HttpServletRequest request,Class<T> clz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//将参数值存成单个值
		Map<String,String[]> maps = request.getParameterMap();
		Map<String,String> m = new HashMap<String,String>();
		for(Map.Entry<String, String[]> entry:maps.entrySet()){
			m.put(entry.getKey(),entry.getValue()[0]);
		}
		return parseRequest(m,clz);
	}
	
	public static <T> T parseRequest(Map<String,String> m,Class<T> clz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//1.创建clz的对象 反射实例
		T obj = clz.newInstance();    //	Product p = new Product();  调用的是无参的构造方法
		List<Method> allSetMethods = getAllSetMethods(clz);
		//循环m  ，取出每个参数
		for(Map.Entry<String,String> entry:m.entrySet()){
			String parameterName = entry.getKey();	//	pname	price
			String parameterValue = entry.getValue();	//	apple	20
			
			//取allSetMethods 中的方法的方法名
			for(Method method:allSetMethods){
				String methodName = method.getName();
				if(methodName.equalsIgnoreCase("set"+parameterName)){
					//取出当前method中的参数的类型，判断是哪一种，再进行类型的转换
					String parameterTypeName = method.getParameterTypes()[0].getName();
					if("double".equals(parameterTypeName)||"java.lang.Double".equals(parameterTypeName)){
						double v= Double.parseDouble(parameterValue);
						method.invoke(obj, v);
					}else if("int".equals(parameterTypeName)||"java.lang.Integer".equals(parameterTypeName)){
						int v= Integer.parseInt(parameterValue);
						method.invoke(obj, v);
					}else if("float".equals(parameterTypeName)||"java.lang.Float".equals(parameterTypeName)){
						float v= Float.parseFloat(parameterValue);
						method.invoke(obj, v);
					}else{
						//激活方法： argument type mismatch               所以需要类型转换
						method.invoke(obj, parameterValue);//  price在Product类中double型，但map中price是String
					}
				}
			}
		}
		return obj;
	}
	
	/**
	 * 取出一个类中所有的set方法
	 * @param clz
	 * @return
	 */
	private static List<Method> getAllSetMethods(Class clz) {
		List<Method> allSetMethods = new ArrayList<Method>();
		Method[] ms = clz.getMethods();   //clz中所有的方法
		for(Method m:ms){
			if(m.getName().startsWith("set")){  //只要set方法
				allSetMethods.add(m);
			}
		}
		return allSetMethods;
	}
	
//	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("pid", 100+"");
//		map.put("pname","apple");
//		map.put("price", 20+"");
//		System.out.println(parseRequest(map,User.class));
//	}
}
