package com.yc.dao;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
//帮助数据库
public class DBHelper {
	//因为静态块在整个程序运行的过程中，只执行一次，而且在程序加载到虚拟机
	//这一局可以不要  why？
	//因为时候用的是jdni技术  由Tomcat 创建数据库连接  ，所以不要
	//但是如果将来要用原来的方案建议保留
	//但是一定要确保项目的 web-inf /lib 目录 有数据库驱动
	
	
	//设置驱动
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	
	//执行增删改
	public int doUpdate(String sql,List<Object> params) throws Exception{
		Connection con=getCon();
		PreparedStatement stmt=con.prepareStatement(sql);
		if(params!=null&&params.size()>0){
			for(int i=0;i<params.size();i++){
			stmt.setString(i+1, params.get(i).toString());
			}
		}
		int r=stmt.executeUpdate();
		closeAll(null,stmt,con);
		return r;
	}
	
	public int doUpdate(String sql,Object... params) throws Exception{
		List<Object> pp=new ArrayList<Object>();
		if(params!=null&&params.length>0){
			for(Object o:params){
				pp.add(o);
			}
		}
		return doUpdate(sql,pp);
	}
	
	
	
	//获取与数据库连接
	public Connection getCon() throws  Exception{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/erp", "root", "a");
		
		//Context initCtx = new InitialContext();//初始化容器
		//Context envCtx = (Context) initCtx.lookup("java:comp/env");//查找jndi资源
		//DataSource ds = (DataSource)  envCtx.lookup("jdbc/orcl");
		//从jndi中查找jdbc/orcl的资源，他是一个DataSource，所以类型转换
		//Connection conn = ds.getConnection();
		return con;
	}
	
	

	//关闭与数据库的连接
	public void closeAll(ResultSet rs,PreparedStatement stmt,Connection con){
		if(rs!=null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(stmt!=null){
			try{
				stmt.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(con!=null){
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	//统计查询
	public double selectFunc(String sql,List<Object> params) throws Exception{
		double result=0;
		Connection con=getCon();
		PreparedStatement stmt=con.prepareStatement(sql);
		if(params!=null&&params.size()>0){
			for(int i=0;i<params.size();i++){
			stmt.setString(i+1, params.get(i).toString());
			}
		}
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			result=rs.getDouble(1);
		}
		closeAll(rs,stmt,con);
		return result;
		
	}
	                                                     //动态的
	public double selectFunc(String sql,Object... params) throws Exception{
		List<Object> pp=new ArrayList<Object>();
		if(params!=null&&params.length>0){
			for(Object o:params){
				pp.add(o);
			}
		}
		return selectFunc(sql,pp);
	}
	
	
	
	//通用的查询，查询的结果为List<Map<String ,String>>   <br/>
	//Map<String ,String>对应的一条数据<br/>
	//TODO: public List<Product>  select (String sql,List<Object>params)
	//TODO: public List<Product>  select (String sql,Object... params)   动态数组
	public List<Map<String ,String>> select(String sql,List<Object> params) throws Exception {
		List<Map<String ,String>> list=new ArrayList<Map<String ,String>>();
		Connection con=getCon();
		PreparedStatement stmt=con.prepareStatement(sql);
		if(params!=null&&params.size()>0){
			for(int i=0;i<params.size();i++){
			stmt.setString(i+1, params.get(i).toString());
			}
		}
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsmd=rs.getMetaData();///通过原数据知道有多少列
		int cc=rsmd.getColumnCount();
		List<String> columnNames=new ArrayList<String>();
		for(int i=0;i<cc;i++){
			String cn=rsmd.getColumnName(i+1);
			columnNames.add(cn);
		}
		//循环所有记录的结果
		while(rs.next()){
			//每一条数据就是一个map的对象
			Map<String,String> map=new HashMap<String,String>();
			//循环每条记录的列名  去每一列的值
			for(int i=0;i<columnNames.size();i++){
				String cn=columnNames.get(i);
				String value=rs.getString(cn);
				map.put(cn, value);
			}
			list.add(map);
		}
		closeAll(rs,stmt,con);
		return list;
	}
	
	
	public <T> List<T> select(Class<T> cls,String sql,Object... params) throws Exception {
		List<T> list=new ArrayList<T>();
		List<Object> pp=new ArrayList<Object>();
		if(params!=null&&params.length>0){
			for(Object o:params){
				pp.add(o);
			}
		}
		List<Map<String,String>> listmap=select(sql,pp);
		//将List<MAP>转换成List<T>
		for(Map<String,String> map:listmap){
			T t=parseRequest(map,cls);
			list.add(t);
			
		}
		return list;
	}
	
	
	
	 public static <T> T parseRequest(Map<String,String> m,Class<T> clz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		 //1.创建clz的对象   反射实例
		 T obj=clz.newInstance();  //Product p=new Product();  newInstance调用的是无参构造函数
		 
		 //取set方法
		 List<Method> allSetMethod=getAllSetMethod(clz);
		//循环  m,取出每一个参数
		 for(Map.Entry<String,String> entry:m.entrySet()){
			String parameterName=entry.getKey();
			String parameterValue=entry.getValue();
			
			//取allSetMethod中所有的方法的方法名
			for(Method method:allSetMethod){
				String methodName=method.getName();
				if(methodName.equalsIgnoreCase("set"+parameterName)){
					
					//取出当前method中参数的类型，判断是哪一种，在进行类型的转换
					String parameterTypeName=method.getParameterTypes()[0].getName();
					//parameterValue  不能为null
					if(parameterValue==null){
						continue;
					}
					if("double".equals(parameterTypeName)  || "java.lang.Double".equals(parameterTypeName)){
						double v=Double.parseDouble(parameterValue);
						method.invoke(obj, v);
					}else if("int".equals(parameterTypeName)  || "java.lang.Integer".equals(parameterTypeName)){
						int v=Integer.parseInt(parameterValue);
						method.invoke(obj, v);
					}else if("float".equals(parameterTypeName)  || "java.lang.Float".equals(parameterTypeName)){
						float v=Float.parseFloat(parameterValue);
						method.invoke(obj, v);
					}else{
						//激活方法  ：argument  type  mismatch
						method.invoke(obj, parameterValue);//price 在Product类中double型，但在map中是String，所以类型转换
					}
					
				}
			}
		 }
		 
		return obj;
	 }
	 
	 
	 private static List<Method> getAllSetMethod(Class clz){
		List<Method> allSetMethod=new ArrayList<Method>();
		Method[] ms=	clz.getMethods();    //clz中所有的方法
		for(Method m:ms){
			if(m.getName().startsWith("set")){
				allSetMethod.add(m);
			}
		}
		  return allSetMethod;
	 }
	
	
	

}
