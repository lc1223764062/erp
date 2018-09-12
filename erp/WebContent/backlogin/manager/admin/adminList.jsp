<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<script type="text/javascript">
	$(function(){
		$('#dg').datagrid({
			url:'backlogin/admin.action?op=list',
			pagination:true,
			pageSize:100,
			pageList:[20,50,100,150,200,400],
			title:'管理员列表',
			idField:"id",
			rownumbers:true,
			fit:true,
			nowrap:true,
			sortName:"id",
			sortOrder:"desc",
			singleSelect:true,
			columns:[[
			     {field:'id',title:'编号',width:100},
			     {field:'username',title:'管理员名',width:100}, 
			     {field:'userpassword',title:'密码',width:100,align:'right'}, 
			     {field:'join_time',title:'加入时间',width:100,align:'right'} 
			]]
		});
	});
</script>

</head>
<body>

<table id="dg"></table>


</body>
</html>