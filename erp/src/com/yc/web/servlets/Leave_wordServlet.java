package com.yc.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.bean.Leave_word;
import com.yc.bean.News;
import com.yc.biz.BaseBiz;
import com.yc.dao.DBHelper;
import com.yc.model.PageBean;
import com.yc.utils.RequestUtils;

/**
 * Servlet implementation class Leave_wordServlet
 */
@WebServlet("/leave_word.action")
public class Leave_wordServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			if ("show".equals(op)) {
				showOp(request, response);
			} else if ("detail".equals(op)) {
				detailOp(request, response);
			} else if ("add".equals(op)) {
				addOp(request, response);
			} else if ("post".equals(op)) {
				postOp(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private void postOp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Leave_word leave_word=RequestUtils.parseRequest(request, Leave_word.class);
		//System.out.println(username+fax+email+title+contents);
		DBHelper db = new DBHelper();
		int power=0;
		
		List<Leave_word> list=db.select(Leave_word.class,"select power from leave_word where username=?",leave_word.getUsername());
		if(list!=null&&list.size()>0){
			power=list.get(0).getPower();
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		String sql="insert into leave_word(username,fax,email,title,content,power,join_date)values(?,?,?,?,?,?,now())";
		int r=db.doUpdate(sql, leave_word.getUsername(),leave_word.getFax(),leave_word.getEmail(),leave_word.getTitle(),leave_word.getContent(),power);
		if(r>0){
			out.println("<script>alert('提交成功');location.href='leave_word.action?op=show';</script>");
		}else{
			out.println("<script>alert('提交失败');location.href='leave_word.action?op=add';</script>");
		}
		
	}

	private void addOp(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		request.getRequestDispatcher("/WEB-INF/pages/leave_word/add.jsp").forward(request, response);
}
	

	private void detailOp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		DBHelper db = new DBHelper();
		List<Leave_word> list = db.select(Leave_word.class,
				"select * from leave_word where id=?", id);
		Leave_word n = list != null && list.size() > 0 ? list.get(0) : null;
		request.setAttribute("leave_word", n);
		request.getRequestDispatcher("/WEB-INF/pages/leave_word/detail.jsp")
				.forward(request, response);
	}

	private void showOp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseBiz bb = new BaseBiz();
		int start = (pages - 1) * pagesize;
		PageBean leave_wordPageBean = null;

		leave_wordPageBean = bb.findByPage("select count(*) from leave_word",
				null, "select * from leave_word where power=1 order by join_date desc limit "
						+ start + "," + pagesize, null, pages, pagesize,Leave_word.class);
		request.setAttribute("leave_wordPageBean", leave_wordPageBean);

		request.getRequestDispatcher("/WEB-INF/pages/leave_word/leave_word.jsp").forward(request, response);
	}

}
