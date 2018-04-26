<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加用户信息</title>
</head>
<body>
<form action="user/addUser" method="post">
 <table width="100%" border=1>
  <tr>
  	<td>USERNAME </td>
  	<td>AGE</td>
  	<td>PASSWORD</td>
  </tr>
  <tr>
  	<td><input type="text" name="userName"/> </td>
  	<td><input type="text" name="age"/></td>
  	<td><input type="text" name="password"/></td>
  </tr></table>
  <input type="submit" value="增加用户"/>
</form>
</body>
</html>