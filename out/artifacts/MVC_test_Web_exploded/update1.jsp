<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="bean.StudentInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>学生信息修改</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 
  </head>
  
  <body>
    <br>
    <h2>学生信息</h2>  <hr>    
    <br> 
  <h3>要修改的学生信息如下</h3>
   <table width="496" border="100" cellSpacing=1 style="border: 1pt dashed ; font-size: 15pt;" height="31">
    <tr>
    <td>学号</td>
    <td>姓名</td>
    <td>年龄</td>
    <td>性别</td>
    <td>专业</td>
    </tr>
    <%
    int id=0;
    String name=null;
    int age = 0;
    String gender = null;
    String major = null;
       ArrayList<StudentInfo> result=new ArrayList<StudentInfo>();
        result=(ArrayList<StudentInfo>)request.getAttribute("result");
       if(!result.isEmpty()){
       for(int i=0;i<result.size();i++){
            StudentInfo st=result.get(i);
            id=st.getId();
            name=st.getName();
            age=st.getAge();
            gender=st.getGender();
            major=st.getMajor();
            out.print("<tr>");
            out.print("<td>"+st.getId()+"</td>");
            out.print("<td>"+st.getName()+"</td>");
            out.print("<td>"+st.getAge()+"</td>");
            out.print("<td>"+st.getGender()+"</td>");
            out.print("<td>"+st.getMajor()+"</td>"); 
            out.print("</tr>");
            }
            }
     %>   
    </table>
  
    <h3>将学生信息更改为：</h3>
 <form action="AllServlet" method="post" >
 <input type="hidden" name="methodName" value="3"/>
<h4> 学号：<input type="text" name="id"value="<%=id %>" readonly title="学号不能改变"></input><br></h4>
<h4>  姓名：<input type="text" name="name" value="<%=name %>" title="姓名不能为空"></input><br></h4>
<h4>  年龄：<input type="text" name="age" value="<%=age %>" title="年龄不能为空"></input><br></h4>
<h4>  性别：<input type="radio" name="gender" value="男" <%= ("男".equals(gender))?"checked":"" %>>男
       <input type="radio" name="gender" value="女" <%= ("女".equals(gender))?"checked":"" %>>女<br>
      
       </h4>
       
<h4>  专业：<input type="text" name="major" value="<%=major %>" title="专业不能为空"></input><br></h4>
 <input type="submit" value="修改"/>
  </form>
 
 <br>
<h3><a href=putin.jsp>返回信息输入页面</a></h3>
<h3><a href=AllServlet?methodName=<%=1 %>&id=<%="" %>&name=<%="" %>>返回信息查询页面</a></h3>
  </body>
</html>