package com.yc.web.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




@WebFilter("/back/*")   //后台的资源都要过滤
public class CheckLoginFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//转类型在取session
		HttpServletRequest req=(HttpServletRequest)request;
		HttpSession session=req.getSession();
		if(session.getAttribute("user")!=null){
			//取出权限
//			User u=(User) session.getAttribute("user");
//			//取出所有的权限
//			Map<Integer,List<Func>> roleMap=(Map<Integer, List<Func>>) request.getServletContext().getAttribute("roleMap");
//			//查出用户权限列表
//			List<Func> funcList=roleMap.get(u.getRid());
//			session.setAttribute("funcList", funcList);
//		
		chain.doFilter(request, response);
		}else{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			
			//实际有bug   之歌只是向上一级  
			//取基底网址http://localhost:8080/xx
			String basePath=(String) request.getServletContext().getAttribute("basePath");
			out.println("out.println('<script>alert('您不是管理员，请登录');location.href='"+basePath+"login.jsp';</script>");
		}
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}



}
