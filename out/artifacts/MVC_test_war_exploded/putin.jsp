<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>学生信息输入</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
    function validate()
    {
    var id=document.forms[0].id.value;
    var name=document.forms[0].name.value;
    var age=document.forms[0].age.value;
    var major=document.forms[0].major.value;
    if(id<=0){
    	alert("学号不能为空，请输入学号！");
    	return false;
    }
    else if(name.length<=0){
    	alert("姓名不能为空，请输入姓名！");
    	return false;
    }
    else if(age<=0){
    	alert("请输入合法年龄！");
    	return false;
    }
 
        else if(major.length<=0){
    	alert("专业不能为空，请输入所学专业！");
    	return false;
    }
 
    else{
    	return true;
    }
    	//document.getElementById("form").submit();
    }
    </script>
  </head>
  
  <body>
     <br>
  <center>
  <h2>学生信息输入</h2><hr>
 <form action="AllServlet" method="post" id="form" onSubmit="return validate()" >
 <input type="hidden" name="methodName" value="0"/>
<h4>  学号：<input type="text" name="id" class="{required:true}" title="学号必须为数字"></input><br></h4>
<h4>  姓名：<input type="text" name="name"title="姓名不能为空"></input><br></h4>
<h4>  年龄：<input type="text" name="age"title="年龄必须为数字"></input><br></h4>
<h4>  性别：<input type="radio" name="gender" value="男">男
            <input type="radio" name="gender" value="女">女<br></h4>
<h4>  专业：<input type="text" name="major"title="专业不能为空"></input><br></h4>
     <h4>  选择插入的数据库：<input type="radio" name="database" value="数据库1">数据库1
         <input type="radio" name="database" value="数据库2">数据库2<br></h4>
 <input type="submit" value="提交"/>
  </form>
      <h4> 提交是0号方法 也就是数据库的插入 </h4>
  <br>
  <a href="AllServlet?methodName=<%=1 %>&id=<%="" %>&name=<%="" %>">查看已输入信息</a>
  </center>
  </body>
</html>