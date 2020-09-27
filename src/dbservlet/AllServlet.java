package dbservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import bean.Page;
import bean.StudentInfo;

import static dbservlet.XmlPrasing.parsingXMLByCurAll;


public class AllServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  List<String> list1;
	private List<String> list2;
 
	 //doPost方法
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
   		    response.setCharacterEncoding("UTF-8");
			String methodName=request.getParameter("methodName");
			int method=Integer.parseInt(methodName);
		try {  
			switch(method)
		       {
		    	case 0:
					insert(request,response);
					//因为这里没有break 所以执行了insert之后马上执行response
		        case 1:
                    difpage(request,response);
			        break;    
		    	case 2:
  				    delete(request,response);
  			        break;       
		        case 3:
  				    update(request,response);
  				    break;
		        case 4:
		        	update1(request,response);
			        break;
		        case 5:
		        	dispatch(request,response);
			        break;
		       }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	//doGet方法
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
       doPost(request,response);
    }
	
	
    //数据库连接方法
	public Connection connect() throws ClassNotFoundException, SQLException{
    	Connection conn=null; 
	    Class.forName("com.mysql.jdbc.Driver");
	    String url = "jdbc:mysql://localhost:3306/course3?"
                + "user=root&password=helloworld1.cpp&useUnicode=true&characterEncoding=UTF8&useSSL=false";
		conn=DriverManager.getConnection(url); 
		return conn;
	}
	//连接第二个数据流
	public Connection connect2() throws ClassNotFoundException, SQLException{
		Connection conn=null;
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/course4?"
				+ "user=root&password=helloworld1.cpp&useUnicode=true&characterEncoding=UTF8&useSSL=false";
		conn=DriverManager.getConnection(url);
		return conn;
	}
	//关闭数据库资源
	public void close(Statement stat,Connection conn) throws SQLException{
		if(stat!=null){
	    	   stat.close();
	    }
	    if(conn!=null){
	    	   conn.close();
	    }
	}
	//插入方法
	public void insert(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
    	Connection conn=null;
    	Statement stat=null;
		String id=request.getParameter("id");
        String name=request.getParameter("name");
        String age=request.getParameter("age");
        String gender=request.getParameter("gender");
        String major=request.getParameter("major");
		conn=connect();
		stat=conn.createStatement();
		Connection conn2=null;
		Statement stat2=null;
		conn2=connect2();
		stat2=conn2.createStatement();
		int ID=Integer.parseInt(id);
		if(ID<10000) {
			stat.execute("insert into student(student_id,student_name,student_age,student_gender,student_major) values(" + id + ",'" + name + "'," + age + ",'" + gender + "','" + major + "')");
			close(stat, conn);
			close(stat2, conn2);
		}
		else
		{
			stat2.execute("insert into student(studentId,studentName,studentAge,studentGender,studentMajor) values(" + id + ",'" + name + "'," + age + ",'" + gender + "','" + major + "')");
			close(stat2, conn2);
		}
    }
    //查询方法
    public ArrayList<StudentInfo> select(String id,String name) throws ClassNotFoundException, SQLException{
    	Connection conn=null;
		Statement stat=null;
		ResultSet rs=null;
		conn=connect();
		stat=conn.createStatement();
		ArrayList<StudentInfo> result=new ArrayList<StudentInfo>();
		//声明xml对象
		String xml="C:\\Users\\Administrator\\Desktop\\MVC-test\\MVC-test\\WebContent\\mapper\\course1.xml";
		list1=parsingXMLByCurAll(xml);
		if(id==""&&name==""){
			rs=stat.executeQuery("select * from student");
		}
		if(id!=""&&name==""){
			rs=stat.executeQuery("select * from student where "+list1.get(0)+"="+id);
		}
		if(id==""&&name!=""){
			rs=stat.executeQuery("select * from student where "+list1.get(1)+"="+"'"+name+"'");
		}
		if(id!=""&&name!=""){
			System.out.println("select * from student where "+list1.get(0)+"="+id +" and " +list1.get(1)+"="+"'"+name+"'");
			rs=stat.executeQuery("select * from student where "+list1.get(0)+"="+id +" and " +list1.get(1)+"="+"'"+name+" ' ");
			//rs 数据库结果集
		}
		while(rs.next())
		{
			//此处 应该将rs 通过xml方式映射到st
			StudentInfo st=new StudentInfo();
			st.setId(rs.getInt(list1.get(0)));
			st.setName(rs.getString(list1.get(1)));
			st.setAge(rs.getInt(list1.get(2)));
			st.setGender(rs.getString(list1.get(3)));
			st.setMajor(rs.getString(list1.get(4)));
			result.add(st);
		}
		if(rs!=null){
			rs.close();
		}
		close(stat,conn);
		/*连接2*/
		Connection conn2=null;
		Statement stat2=null;
		ResultSet rs2=null;
		conn2=connect2();
		stat2=conn2.createStatement();
		//声明xml对象
		String xml2="C:\\Users\\Administrator\\Desktop\\MVC-test\\MVC-test\\WebContent\\mapper\\course2.xml";
		list2=parsingXMLByCurAll(xml2);
		if(id==""&&name==""){
			rs2=stat2.executeQuery("select * from student");
		}
		if(id!=""&&name==""){
			rs2=stat2.executeQuery("select * from student where "+list2.get(0)+"="+id);
		}
		if(id==""&&name!=""){
			rs2=stat2.executeQuery("select * from student where "+list2.get(1)+"="+"'"+name+"'");
		}
		if(id!=""&&name!=""){
			rs2=stat2.executeQuery("select * from student where "+list2.get(0)+"="+id +" and " +list2.get(1)+"="+"'"+name+" ' ");
			//rs 数据库结果集
		}
		while(rs2.next())
		{
			//此处 应该将rs 通过xml方式映射到st
			StudentInfo st=new StudentInfo();
			st.setId(rs2.getInt(list2.get(0)));
			st.setName(rs2.getString(list2.get(1)));
			st.setAge(rs2.getInt(list2.get(2)));
			st.setGender(rs2.getString(list2.get(3)));
			st.setMajor(rs2.getString(list2.get(4)));
			result.add(st);
		}
		if(rs2!=null){
			rs2.close();
		}
		close(stat2,conn2);
    	return result;
    }
    //条件查询跳转
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	String id5=request.getParameter("id");
    	String name5=request.getParameter("name");  
     if(select(id5,name5).isEmpty()){
        	request.getRequestDispatcher("selectnothing.jsp").forward(request, response);
        }
       else{
    		request.setAttribute("result", select(id5,name5));
            request.getRequestDispatcher("idnameselect.jsp").forward(request, response);	
        }
    }
    //设置分页相关参数方法
	public Page setpage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
		String crd=request.getParameter("currentRecord");
		//String id=request.getParameter("id");
      //  String name=request.getParameter("name");
    	ArrayList<StudentInfo> result=select("","");
    	Page pager=new Page();
    	pager.setTotalRecord(result.size()); 
    	pager.setTotalPage(result.size(),pager.getPageSize());
    	if(crd!=null)
        {
    		int currentRecord=Integer.parseInt(crd);
            pager.setCurrentRecord(currentRecord);
            pager.setCurrentPage(currentRecord,pager.getPageSize());
        }
    	return pager;
	}
	//获得分页显示的子集
	 public void difpage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException{
		// String id=request.getParameter("id");
	 //    String name=request.getParameter("name");
		 ArrayList<StudentInfo> result=select("","");
		 Page pager=new Page();
		 pager=setpage(request,response);
  	     List<StudentInfo> subResult=null;
  	     int currentRecord=pager.getCurrentRecord();
         if(currentRecord==0){
         	if(pager.getTotalRecord()<8){
         		subResult=(List<StudentInfo>) result.subList(0,pager.getTotalRecord());
         	}
         	else{
         		subResult=(List<StudentInfo>) result.subList(0,pager.getPageSize());
         	}         
         }
         else if(pager.getCurrentRecord()+pager.getPageSize()<result.size())
         {
               subResult=(List<StudentInfo>) result.subList(pager.getCurrentRecord(),pager.getCurrentRecord()+pager.getPageSize());
         }
         else
         {
              subResult=(List<StudentInfo>) result.subList(pager.getCurrentRecord(),result.size());
         }
         request.setAttribute("pager", pager);
	     request.setAttribute("subResult", subResult);
		 request.getRequestDispatcher("layout.jsp").forward(request, response);
     }
    //信息删除方法
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	Connection conn=null;
    	Statement stat=null;
 		String id2=request.getParameter("id");
		int id=Integer.parseInt(id2);
		if(id<10000 ) {
			conn = connect();
			stat = conn.createStatement();
			stat.execute("delete from student where "+list1.get(0)+"=" + id2 + "");
			request.getRequestDispatcher("delete.jsp").forward(request, response);
		}else
		{
			conn = connect2();
			stat = conn.createStatement();
			stat.execute("delete from student where "+list2.get(0)+"=" + id2 + "");
			request.getRequestDispatcher("delete.jsp").forward(request, response);
		}
    } 
    //信息修改方法
    public void update1(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	String id4=request.getParameter("id");
	    request.setAttribute("result", select(id4,""));
        request.getRequestDispatcher("update1.jsp").forward(request, response);
    }   
    public void update(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	Connection conn=null;
    	Statement stat=null;
        String id3=request.getParameter("id");
        String name3=request.getParameter("name");
        String age3=request.getParameter("age");
        String gender3=request.getParameter("gender");
        String major3=request.getParameter("major");
        int id=Integer.parseInt(id3);
        System.out.println(id);
        if(id<10000 ) {
			conn = connect();
			stat = conn.createStatement();
			System.out.println("update student set " +list1.get(0)+" = " + id3 + ","+list1.get(1)+" = " + "'"+name3+"'" + ", "+list1.get(2)+" = " + age3 + ", "+list1.get(3)+" = " + "'"+gender3+"'" + ", "+list1.get(4)+" = " + "'"+major3 +"'"+ " where "+list1.get(0)+" = " + id3);
			stat.execute("update student set " +list1.get(0)+" = " + id3 + ","+list1.get(1)+" = " + "'"+name3+"'" + ", "+list1.get(2)+" = " + age3 + ", "+list1.get(3)+" = " + "'"+gender3+"'" + ", "+list1.get(4)+" = " + "'"+major3 +"'"+ " where "+list1.get(0)+" = " + id3);
			request.setAttribute("result", select(id3, ""));
			request.getRequestDispatcher("update.jsp").forward(request, response);
		}
        else
		{
			conn = connect2();
			stat = conn.createStatement();
			System.out.println("update student set " +list2.get(0)+" = " + id3 + ","+list2.get(1)+" = " + "'"+name3+"'" + ", "+list2.get(2)+" = " + age3 + ", "+list2.get(3)+" = " + "'"+gender3+"'" + ", "+list2.get(4)+" = " + "'"+major3 +"'"+ " where "+list2.get(0)+" = " + id3);
			stat.execute("update student set " +list2.get(0)+" = " + id3 + ","+list2.get(1)+" = " + "'"+name3+"'" + ", "+list2.get(2)+" = " + age3 + ", "+list2.get(3)+" = " + "'"+gender3+"'" + ", "+list2.get(4)+" = " + "'"+major3 +"'"+ " where "+list2.get(0)+" = " + id3);
			request.setAttribute("result", select(id3, ""));
			request.getRequestDispatcher("update.jsp").forward(request, response);
		}
    } 
 
}
