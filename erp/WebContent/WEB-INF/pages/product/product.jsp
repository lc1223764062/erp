<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>   
<div id="center_line"></div>
    	<div id="center_top">
            <div id="gongsijianjie">
                <div id="center_top_left_t"></div>
                <div id="center_top_left_b">
                    <ul>
                    	<c:forEach items="${product_classList }" var="product_class">
                        	<li><a href="product.action?op=show&product_class=${product_class.id }&typename=${product_class.protype }">${product_class.protype }</a></li>
              			</c:forEach>
                    </ul>
                </div>
            </div>
            
<div id="toutiaoxinwen">
                <div id="center_top_right_c">
	                    <c:forEach items="${requestScope.productPageBean.list }" var="product">
	                        <a href="product.action?op=detail&id=${product.id }" id="headlineNews">${product.product_name }</a>&nbsp;<span>${product.join_date }</span>
	                        <hr width="80%"/>
	                    </c:forEach>
                    <br />
                    <yc:pageBar href="product.action?op=show&product_class=${product_class }" pageBean="${requestScope.productPageBean}"></yc:pageBar>
                </div>
                
            </div>


<%@include file="../bottom.jsp" %>  