<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
    <%@include file="../header.jsp" %>
    <div id="center_line"></div>
    
    
              <div id="center_top">
              <div id="center_top_right_c">
                <div id="center_top_right_c_l">
                  
                  <c:forEach items="${requestScope.leave_wordPageBean.list}" var="leave_word">
                     <a href="leave_word.action?op=detail&id=${leave_word.id}">${leave_word.title}</a> <span>${leave_word.join_date}</span>
                     <hr width="70%"/>
                     
                  </c:forEach>
                      <a href="leave_word.action?op=add">发表</a> <br/>
                  <yc:pageBar pageBean="${requestScope.leave_wordPageBean}" href="leave_word.action?op=show"></yc:pageBar>
                  
                 
            </div>

           </div>
  
    <%@include file="../bottom.jsp" %>
