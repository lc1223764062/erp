  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
    
<%@include file="../header.jsp" %>
<div id="center_line"></div>

 
    

    
    <div id="toutiaoxinwen">
      <div id="center_top_right_c">
        <div id="center_top_right_c_l">
          <form action="leave_word.action?op=post" method="post">
				用户名：<input type="text" name="username"  /><br />
				 传真：<input type="text" name="fax"          /><br /> 
				 邮箱：<input  type="text" name="email"    /> <br/>
				 标题：<input type="text" name="title"          /><br /> 
				 内容：   <textarea cols="20" rows="5" name="content"></textarea></br>
			       <input type="submit" value="提交"/> 
		</form>
	 <br/>
            </div>
          </div>
         </div>
         
<%@include file="../bottom.jsp" %>