package com.yc.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.google.gson.Gson;
import com.jspsmart.upload.SmartUploadException;
import com.yc.bean.Admin;
import com.yc.bean.JsonModel;
import com.yc.bean.Product;
import com.yc.dao.DBHelper;
import com.yc.utils.FileUploadUtils;
import com.yc.utils.RequestUtils;


@WebServlet("/backlogin/uploadFile.action")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageContext pageContext = javax.servlet.jsp.JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		FileUploadUtils fuu= new FileUploadUtils();
		JsonModel jm=new JsonModel();
		try {
			Map<String, String> map = fuu.uploadFiles(pageContext);
			Product p= RequestUtils.parseRequest(map, Product.class);
			//以上只完成了向Tomcat下 保存一张图片的功能  即文件上传ok
			//但数据库中却没有对应的记录
			
			Admin admin = (Admin) request.getSession().getAttribute("admin");
			DBHelper db = new DBHelper();
			String sql = "insert into product(Product_picture,Product_class,Product_name,Product_in,Product_explain,"
					+ "Product_auditing,index_show,join_date,change_date) values(?,?,?,?,?,?,1,now(),now())";
			db.doUpdate(sql, map.get("product_picture_relativepath"),p.getProduct_class(),p.getProduct_name(),
					p.getProduct_in(),p.getProduct_explain(),admin.getId());
			jm.setCode(1);
		} catch (Exception e) {
			jm.setCode(0);
			jm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		OutJsonString(response,jm);
	}
	
	protected void OutJsonString(HttpServletResponse response,Object obj) throws IOException{
		Gson g = new Gson();
		String jsonString = g.toJson(obj);

		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jsonString);
		out.flush();
	}

}
