<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.*"%>
<%@ include file="header.jsp" %>

<script type="text/javascript">
		$(function(){
			var newsTreeData=[
				{
					"id":1,
					"text":"新闻上架",
					"attributes":{
						"url":"<iframe width='100%' height='100%' src='backlogin/manager/news/newsAdd.jsp' />"
					}
				},
				{
					"id":2,
					"text":"新闻浏览",
					"attributes":{
						"url":"<iframe width='100%' height='100%' src='backlogin/manager/news/newsShow.jsp' />"
					}
				},
				{
					"id":3,
					"text":"新闻排行榜",
					"attributes":{
						"url":"<iframe width='100%' height='100%' src='backlogin/manager/news/newsRank.jsp' />"
					}
				}    
			];
			var productTreeData=[
			    {
					"id" : 1,
					"text" : "产品上架",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='backlogin/manager/product/productAdd.jsp' />"
					}
				}, {
					"id" : 2,
					"text" : "产品浏览",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='backlogin/manager/product/productList.jsp' />"
					}
				}, {
					"id" : 3,
					"text" : "产品排行榜",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='backlogin/manager/product/productRank.jsp' />"
					}
				} ];
			
			var adminTreeData=[
			  				{
			  					"id":1,
			  					"text":"管理员列表",
			  					"attributes":{
			  						"url":"<iframe width='100%' height='100%' src='backlogin/manager/admin/adminList.jsp' />"
			  					}
			  				},
			  				{
			  					"id":2,
			  					"text":"管理员注册",
			  					"attributes":{
			  						"url":"<iframe width='100%' height='100%' src='backlogin/manager/admin/adminReg.jsp' />"
			  					}
			  				}
			  			];
		showTree("newsTree", newsTreeData);
		showTree("productTree", productTreeData);
		showTree("adminTree", adminTreeData);
	});

	function showTree(treeId, treeData) {
		$("#" + treeId).tree({
			data : treeData,
			onClick : function(node) {
				if (node && node.attributes) {
					openTab(node);
				}

			}
		});
	}

	function openTab(node) {
		if ($("#mainTabs").tabs("exists", node.text)) {
			$("#mainTabs").tabs("select", node.text);
		} else {
			$("#mainTabs").tabs("add", {
				title : node.text,
				closable : true,
				selected : true,
				content : node.attributes.url
			});
		}

	}
</script>

</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height: 50px">N</div>
	<div data-options="region:'south',split:true" style="height: 50px;">S</div>
	<div data-options="region:'east',split:true" title="East"
		style="width: 150px;">E</div>
	<div data-options="region:'west',split:true" title="West"
		style="width: 150px;">


		<div class="easyui-accordion" style="width: 150px; height: 300px;">


			<div title="news" data-options="iconCls:'icon-ok'"
				style="overflow: auto; padding: 10px;">
				<ul id="newsTree" class="easyui-tree">

				</ul>
			</div>

			<div title="product" data-options="iconCls:'icon-ok'"
				style="overflow: auto; padding: 10px;">
				<ul id="productTree" class="easyui-tree">

				</ul>
			</div>

			<div title="admin" data-options="iconCls:'icon-ok'"
				style="overflow: auto; padding: 10px;">
				<ul id="adminTree" class="easyui-tree">

				</ul>
			</div>

			<div title="jobs" data-options="iconCls:'icon-ok'"
				style="overflow: auto; padding: 10px;"></div>

			<div title="configration" data-options="iconCls:'icon-ok'"
				style="overflow: auto; padding: 10px;"></div>

			<div title="dataDict" data-options="iconCls:'icon-ok'"
				style="overflow: auto; padding: 10px;"></div>


		</div>

	</div>
	<div
		data-options="region:'center',title:'Main Title',iconCls:'icon-ok'">
		<div class="easyui-tabs" id="mainTabs" style="width:100%;height:100%;">
			<div title="欢迎面板" style="padding: 10px">
				源辰信息欢迎您，今天是：
				<%
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				%>
				<%=sdf.format(d) %>
			</div>
		</div>
	</div>
</body>
</html>