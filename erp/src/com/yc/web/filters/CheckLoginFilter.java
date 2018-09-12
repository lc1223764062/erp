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




@WebFilter("/back/*")   //��̨����Դ��Ҫ����
public class CheckLoginFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//ת������ȡsession
		HttpServletRequest req=(HttpServletRequest)request;
		HttpSession session=req.getSession();
		if(session.getAttribute("user")!=null){
			//ȡ��Ȩ��
//			User u=(User) session.getAttribute("user");
//			//ȡ�����е�Ȩ��
//			Map<Integer,List<Func>> roleMap=(Map<Integer, List<Func>>) request.getServletContext().getAttribute("roleMap");
//			//����û�Ȩ���б�
//			List<Func> funcList=roleMap.get(u.getRid());
//			session.setAttribute("funcList", funcList);
//		
		chain.doFilter(request, response);
		}else{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			
			//ʵ����bug   ֮��ֻ������һ��  
			//ȡ������ַhttp://localhost:8080/xx
			String basePath=(String) request.getServletContext().getAttribute("basePath");
			out.println("out.println('<script>alert('�����ǹ���Ա�����¼');location.href='"+basePath+"login.jsp';</script>");
		}
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}



}
