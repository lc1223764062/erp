<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<script type="text/javascript">
$(function(){
	$("#regbtn").click(function(){
		$.ajax({
			url:'backlogin/admin.action?op=reg',
			type:'POST',
			datatype:'JSON',
			data: $("#myform").serialize(),
			success:function(data){
				//data  -> 一个json 对象  -> {code:1/0  ,msg:'' , obj:[]}
				if(data.code==1){
					alert('注册成功');
					clearAll();
				}else{
					alert('注册失败,原因：'+data.msg);
				}
			}
			
		});
	});
	
	function clearAll(){
		$("#username").val('');
		$("#userpassword").val('');
	}
});
</script>

</head>
<body align="center">

	<FORM id="myform" action="backlogin/admin.action?op=reg" method="post">
		<br />用户名 &nbsp;
		<INPUT   type="text" name="username" id="username"> <br />
			密 码 &nbsp;<INPUT type="password" name="userpassword" id="userpassword"> <br />
		<INPUT  type="button" value="注册" id="regbtn">
	</FORM>

</body>
</html>