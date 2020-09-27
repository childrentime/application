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
    
    <title>修改成功</title>
    
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
    
    <h3>修改后的信息为：</h3>
    
    <hr>
    <br>
    <br>
<table width="450" border="100" cellSpacing=1 style="font-size:15pt;border:dashed 1pt">
    <tr>
    <td>学号</td>
    <td>姓名</td>
    <td>年龄</td>
    <td>性别</td>
    <td>专业</td>
    </tr>
    <%
     response.setCharacterEncoding("UTF-8");
     request.setCharacterEncoding("UTF-8");
       ArrayList<StudentInfo> result=new ArrayList<StudentInfo>();
       result=(ArrayList<StudentInfo>)request.getAttribute("result");
       if(!result.isEmpty()){
       for(int i=0;i<result.size();i++){
            StudentInfo st=result.get(i);
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
      <br>
      <br>
      <h3><a href=putin.jsp>返回信息输入页面</a></h3>
      <h3><a href=AllServlet?methodName=<%=1 %>&id=<%="" %>&name=<%="" %>>返回信息查询页面</a></h3>
  </body>
</html>