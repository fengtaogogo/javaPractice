<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() +":" + 
		   request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
      <base href="<%=basePath %>" />
	  <title>信息查询</title>
	  <script>
    $(function(){
    	$(".id").click(function(){
    	var id = $(this).attr("id");
    	console.log(id);
        $.ajax({
		    type:'post',
		    url:'${pageContext.request.contextPath }/user/showUser2',
		    data:{userId: id},//key/value
		    success:function(data){
			    alert("用户ID："+data.id);	
			    alert("用户姓名："+data.userName);
			    alert("用户年龄："+data.age);
			    alert("用户密码："+data.password);
		     },
		    error:function(){
		    	alert("failure");
		    }
	    });
    	});
     });
    </script>
    <style>
    	.id{cursor: pointer}
    </style>
  </head>
 <body>
<form action="user/deleteUser" method="post">
 <table width="100%" border=1>
  <tr>
  	<td>ID</td>
  	<td>USERNAME </td>
  	<td>AGE</td>
  	<td>PASSWORD</td>
  	<td>操作</td>
    </tr>
    <c:forEach items="${list}" var="user">
    <tr> 
    <td ><input  type="checkbox" name="deleteid" value="${user.id }"/>
    	${user.id }<span id="${user.id }" class="id">点击此处查看该用户信息</span>
    </td>
  	<td>${user.userName } </td>
  	<td>${user.age } </td>
  	<td>${user.password } </td>
  	<td><a href="user/editUser/${user.id }">修改用户信息</a></td>
    </tr>
    </c:forEach>
</table><br>
<input type="submit" value="勾选ID删除用户"/>
</form>
<td><a href="${pageContext.request.contextPath }/addUser.jsp">增加用户</a></td><br><br>
<td><a href="${pageContext.request.contextPath }/searchUsers.jsp">模糊查询用户</a></td>
<br><br>
</body>
</html>
