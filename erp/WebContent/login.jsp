<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/pages/header.jsp"%>

<script type="text/javascript">
	function changeImage() {
		document.getElementById("image01").src = "image.jsp?" + new Date();
	}
</script>


登录：
<FORM name="loginForm" action="backlogin/admin.action" method="post">
	<input type="hidden" name="op" value="login" /> <br />
		用户名 &nbsp;
		 <INPUT class="input" tabIndex="1" type="text" maxLength="20" size="35" name="username"> <br />
		 密 码 &nbsp;<INPUT class="input" tabIndex="2" type="password" maxLength="20" size="40" name="userpassword">
			<br />
		 验证码： <input type="text" name="val_code" /> 
		 <img id="image01" src="image.jsp" /> 
		 <a href="javascript:void(0)" onclick="changeImage()">看不清</a> <br /> 
		 <INPUT class="btn" tabIndex="6" type="submit" value="登 录">
</FORM>


<%@ include file="WEB-INF/pages/bottom.jsp"%>