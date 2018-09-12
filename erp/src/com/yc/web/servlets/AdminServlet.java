package com.yc.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.yc.bean.Admin;
import com.yc.bean.JsonModel;
import com.yc.dao.DBHelper;
import com.yc.model.DataGridModel;
import com.yc.utils.Encrypt;
import com.yc.utils.RequestUtils;

@WebServlet("/backlogin/admin.action")
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			if ("login".equals(op)) {
				loginOp(request, response);
			} else if ("list".equals(op)) {
				listOp(request, response);
			}else if ("reg".equals(op)) {
				regOp(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String basePath = (String) request.getServletContext()
					.getAttribute("basePath");

			request.getSession().setAttribute("errorMsg", e.getMessage());
			response.sendRedirect(basePath + "500.jsp");
		}

	}

	private void regOp(HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		Admin admin = RequestUtils.parseRequest(request, Admin.class);
		admin.setUserpassword(Encrypt.md5(admin.getUserpassword()));
		JsonModel jm = new JsonModel();
		try {
			DBHelper db = new DBHelper();
			db.doUpdate("insert into admin (username,userpassword,join_time) values(?,?,now())", admin.getUsername(),admin.getUserpassword());
			jm.setCode(1);
		} catch (Exception e) {
			jm.setCode(0);
			jm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		super.OutJsonString(response, jm);
		
	}

	private void listOp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DBHelper db = new DBHelper();
		int total = (int) db.selectFunc("select count(*) from admin");

		int start = (page - 1) * rows;
		List<Admin> list = db.select(Admin.class,
				"select * from admin order by " + sort + " " + order
						+ " limit "+start+","+rows);

		DataGridModel dgm = new DataGridModel();
		dgm.setRows(list);
		dgm.setTotal(total);

		super.OutJsonString(response, dgm);
	}

	private void loginOp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String val_code = request.getParameter("val_code");
		HttpSession session = request.getSession();
		String rand = (String) session.getAttribute("rand");
		Admin admin = RequestUtils.parseRequest(request, Admin.class);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!val_code.equals(rand)) {
			out.println("<script>alert('验证码错误');location.href='login.html';</script>");
			out.flush();
			return;
		}
	
		DBHelper db = new DBHelper();

		if (admin.getUsername() == null || "".equals(admin.getUsername())) {
			throw new RuntimeException("user name should not be empty");
		}
		String pwd = Encrypt.md5(admin.getUserpassword());
		List<Admin> list = db.select(Admin.class,
				"select * from admin where username=? and userpassword=?",
				admin.getUsername(), pwd);
		if (list != null && list.size() > 0) {
			admin = (Admin) list.get(0);
			// 取session
			session.setAttribute("admin", admin);
			request.getRequestDispatcher("manager/index.jsp").forward(request,
					response);
		} else {
			out.println("<script>alert('用户名或密码错误');location.href='login.html';</script>");
			out.flush();
		}

	}

}
