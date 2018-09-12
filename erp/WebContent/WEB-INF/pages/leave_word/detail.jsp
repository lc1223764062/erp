<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
    
<%@include file="../header.jsp" %>
<div id="center_line"></div>

<div id="center_top">
   <div id="gongsijianjie">
     <div id="center_top_left_b">
         <h2>${leave_word.title}</h2>
         <br/>来自:${leave_word.username}
         <br/>内容:${leave_word.content}
   
         
     </div>
    </div>
    </div>
    <%@include file="../bottom.jsp" %>