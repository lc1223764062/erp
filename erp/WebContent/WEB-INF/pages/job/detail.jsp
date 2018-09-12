<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %> 

<div id="toutiaoxinwen">
                <div id="center_top_right_c">
                	<h2>${job.inviter }</h2>
                	<br />  创建时间：${job.join_date }
                	<hr />
                	工作地点：${job.address }  工资：${job.wage }  要求到职时间：${job.expressdate }<br />
                	${job.demand }
                </div>
                
            </div>
            <center >
            	申请：
            	<form action="job.action" method="post">
            		<input type="hidden" name="op" value="add" />
            		<input type="hidden" name="jobid" value="${job.id }" />
            		姓名：<input type="text" name="username" value="" />  <br />
            		性别：<input type="radio" name="sex" value="男" checked/>男
            		<input type="radio" name="sex" value="女" />女 <br />
            		学校：<input type="text" name="school" value="" />  <br />
            		电话：<input type="text" name="telephone" value="" />  <br />
            		邮箱：<input type="text" name="email" value="" />  <br />
            		<input type="submit" value="提交申请信息" />
            	</form>
            </center>


<%@include file="../bottom.jsp" %>  