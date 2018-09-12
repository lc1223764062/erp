package com.yc.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.bean.Admin;
import com.yc.bean.JsonModel;
import com.yc.bean.News;
import com.yc.bean.Product;
import com.yc.bean.Product_class;
import com.yc.biz.BaseBiz;
import com.yc.dao.DBHelper;
import com.yc.model.DataGridModel;
import com.yc.model.PageBean;

@WebServlet({"/product.action","/backlogin/product.action"})
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if ("show".equals(op)) {
				showOp(request, response);
			}else if("detail".equals(op)){
				detailOp(request,response);
			}else if("list".equals(op)){
				listOp(request,response);
			}else if("getAlllProductClass".equals(op)){
				getAlllProductClassOp(request,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String basePath = (String) request.getServletContext().getAttribute("basePath");
			
			request.getSession().setAttribute("errorMsg",e.getMessage());
			response.sendRedirect(basePath+"500.jsp");
		}
	}


	private void getAlllProductClassOp(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<Product_class> product_classList=(List<Product_class>) request.getServletContext().getAttribute("product_classList");
		JsonModel jm = new JsonModel();
		jm.setCode(1);
		jm.setObj(product_classList);
		super.OutJsonString(response, jm);
	}


	private void listOp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DBHelper db = new DBHelper();
		int total = (int) db.selectFunc("select count(*) from Product");

		int start = (page - 1) * rows;
		List<Product> list = db.select(Product.class,
				"select * from Product order by " + sort + " " + order
						+ " limit "+start+","+rows);

		DataGridModel dgm = new DataGridModel();
		dgm.setRows(list);
		dgm.setTotal(total);

		super.OutJsonString(response, dgm);
	}


	private void detailOp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer id=Integer.parseInt(request.getParameter("id"));
		DBHelper db = new DBHelper();
		List<Product> list =db.select(Product.class, "select * from product where id =?",id);
		Product p=   list!=null && list.size()>0? list.get(0):null;
		request.setAttribute("product", p);
		request.getRequestDispatcher("/WEB-INF/pages/product/detail.jsp").forward(request, response);
	}


	private void showOp(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Integer id = null;
		if (req.getParameter("product_class") != null&& !"".equals(req.getParameter("product_class"))) {
			id = Integer.parseInt(req.getParameter("product_class"));
		}
		BaseBiz bb = new BaseBiz();
		int start = (pages - 1) * pagesize;
		PageBean productPageBean = null;
		if (id == null) {
			productPageBean = bb.findByPage("select count(*) from product", null,
					"select * from product order by change_date desc limit "
							+ start + "," + pagesize, null, pages, pagesize,
					Product.class);
		} else {
			productPageBean = bb.findByPage(
					"select count(*) from product p,Product_class pc where  p.product_class=pc.protype and pc.id=" + id, null,
					"select * from product p,Product_class pc where p.product_class=pc.protype and pc.id=" + id
							+ " order by change_date desc limit " + start + ","
							+ pagesize, null, pages, pagesize, Product.class);
		}

		req.setAttribute("productPageBean", productPageBean);
		req.setAttribute("product_class", id);
		req.getRequestDispatcher("/WEB-INF/pages/product/product.jsp").forward(req,
				resp);
	}

}
