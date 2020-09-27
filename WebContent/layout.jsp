<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="bean.StudentInfo" %>
<%@ page import="bean.Page" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>学生信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <script type="text/javascript">
    function confirmdialog(){
       if(window.confirm("您确定要删除此条信息？")){
       return true;
       }
       else{
     //  alert("取消删除！");
       return false;
       }      
    }
 </script>
  </head>
  
  <body>
    
    <br>
    <h1>学生信息</h1>  <br>  <hr>    
  
     <br>
  <h3>全部学生信息如下</h3>
   <table width="510" border="100" cellSpacing=1 style="border: 1pt dashed ; font-size: 15pt;" height="31">
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
     Page pager=(Page)request.getAttribute("pager");
     List<StudentInfo> subResult=(List<StudentInfo>)request.getAttribute("subResult");
       if(!subResult.isEmpty()){
       for(int i=0;i<subResult.size();i++){
            StudentInfo st=subResult.get(i);
            out.print("<tr>");
            out.print("<td>"+st.getId()+"</td>");
            out.print("<td>"+st.getName()+"</td>");
            out.print("<td>"+st.getAge()+"</td>");
            out.print("<td>"+st.getGender()+"</td>");
            out.print("<td>"+st.getMajor()+"</td>"); 
            
     %>
     <td><a href="AllServlet?id=<%=st.getId() %>&methodName=<%=2 %>" οnclick="return confirmdialog()">删除</a></td>
     
     <td><a href="AllServlet?id=<%=st.getId() %>&name=<%="" %>&methodName=<%=4 %>">修改</a></td>
    <% 
    out.print("</tr>");
         }
       }
     %>
     </table>
     <span><font size="2">总<%=pager.getTotalRecord() %>条记录|总<%=pager.getTotalPage() %>页
     |当前<%=pager.getCurrentPage()+1 %>页|每页<%=pager.getPageSize() %>条|
     <%
        int last=pager.getCurrentRecord()-pager.getPageSize();
        int next=pager.getCurrentRecord()+pager.getPageSize();
        int currentRecord;
        if(last<0){
        out.println("首页|");
        }
        else{
        out.print("<a href='AllServlet?currentRecord="+last+"&methodName=1'>上一页</a>|");
        }
        if(next>=pager.getTotalRecord()){
          out.println("尾页|");
        }
        else{
          out.print("<a href='AllServlet?currentRecord="+next+"&methodName=1'>下一页</a>|");
        }
      %>
      </font>
      </span>
     <br>
    <form action="AllServlet" method="post">
    <input type="hidden" name="methodName" value="5"/>
      <h3>按学号姓名查询:</h3>
                              学号：<input type="text" name="id"  value="" title="学号必须为数字" ></input>
                              姓名：<input type="text" name="name" value="" title=""></input>
      <input type="submit" value="查询" />
    </form>
 <br>
<h3><a href=putin.jsp>返回信息输入页面</a></h3> 
<br>
  </body>
</html>