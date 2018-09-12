package com.yc.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.jsp.JspWriter;

import com.yc.bean.Acjob;
import com.yc.bean.Job;
import com.yc.biz.BaseBiz;
import com.yc.dao.DBHelper;
import com.yc.model.PageBean;
import com.yc.utils.RequestUtils;

@WebServlet("/job.action")
public class JobServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			if ("show".equals(op)) {
				showOp(request, response);
			}else if ("detail".equals(op)) {
				detailOp(request, response);
			}else if("add".equals(op)){
				addOp(request,response);
			}
		}catch (Exception e) {
			e.printStackTrace();
			String basePath = (String) request.getServletContext()
					.getAttribute("basePath");

			request.getSession().setAttribute("errorMsg", e.getMessage());
			response.sendRedirect(basePath + "500.jsp");
		}
	}

	private void addOp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//String basepath=(String) request.getServletContext().getAttribute("basePath");
		Acjob acjob = RequestUtils.parseRequest(request, Acjob.class);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		DBHelper db = new DBHelper();
		String sql ="insert into acjob(jodid,username,sex,school,telephone,email) values(?,?,?,?,?,?)";
		int r=db.doUpdate(sql,acjob.getJobid(),acjob.getUsername(),acjob.getSex(),acjob.getSchool(),acjob.getTelephone(),acjob.getEmail() );
		if(r>0){		
			out.println("<script>alert('申请成功');location.href='job.action?op=detail&id="+acjob.getJobid()+"';</script> ");
		}else{
			out.println("<script>alert('申请失败');location.href='job.action?op=show;</script> ");
		}
	}

	private void detailOp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		DBHelper db = new DBHelper();
		List<Job> list = db.select(Job.class, "select * from job where id=?",
				id);
		Job j = list != null & list.size() > 0 ? list.get(0) : null;
		request.setAttribute("job", j);
		request.getRequestDispatcher("/WEB-INF/pages/job/detail.jsp").forward(
				request, response);
	}

	private void showOp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseBiz bb = new BaseBiz();
		int start = (pages - 1) * pagesize;
		PageBean jobPageBean = bb.findByPage("select count(*) from job", null,
				"select * from job order by join_date desc limit " + start
						+ "," + pagesize, null, pages, pagesize,Job.class);
		request.setAttribute("jobPageBean", jobPageBean);
		request.getRequestDispatcher("/WEB-INF/pages/job/job.jsp").forward(
				request, response);
	}
}
