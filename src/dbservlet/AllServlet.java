package dbservlet;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import bean.Page;
import bean.StudentInfo;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.YamlUtils;

import static dbservlet.XmlPrasing.parsingXMLByCurAll;


public class AllServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<List<String>> lists=new ArrayList<>();

	@Override
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
			        default:
					   throw new IllegalStateException("Unexpected value: " + method);
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
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
       doPost(request,response);
    }

    //yml文件解析
	public List<List<String>> ymlPrasing() throws IOException
	{
		Map<String, Object> map = YamlUtils.yamlHandler(new ClassPathResource("resources/application.yml"));
		List<List<String>> dataBases=new ArrayList<>();
		for(int i=1;i<map.size()/4+1;i++)
		{
			List<String> temp=new ArrayList<>();
			String url=(String)map.get("datasource.database"+i+".url");
			String username=(String)map.get("datasource.database"+i+".username");
			String password=(String)map.get("datasource.database"+i+".password");
			String driver=(String)map.get("datasource.database"+i+".driver-class-name");
			temp.add(url);
			temp.add(username);
			temp.add(password);
			temp.add(driver);
			dataBases.add(temp);
		}
		return dataBases;
	}

	//数据库连接方法
	public List<Connection> connectByYml() throws ClassNotFoundException, SQLException, IOException {
		List<List<String>> datasources=ymlPrasing();
		List<Connection> connections=new ArrayList<>();
		for(int i=0;i<datasources.size();i++)
		{
			String url=datasources.get(i).get(0)+"&user="+datasources.get(i).get(1)+"&password="+datasources.get(i).get(2);
			System.out.println(url);
			System.out.println(datasources.get(i).get(3));
			Class.forName(datasources.get(i).get(3));
			Connection connection=DriverManager.getConnection(url);
			connections.add(connection);
			//声明xml对象
			String xml="C:\\Users\\Administrator\\Desktop\\MVC-test\\MVC-test\\WebContent\\mapper\\course"+i+".xml";
			//生成xml
			createXml(connection,xml);
			lists.add(parsingXMLByCurAll(xml));
		}
		return connections;
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
	public void insert(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		List<Connection> connections = connectByYml();
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		String gender=request.getParameter("gender");
		String major=request.getParameter("major");
		int ID=Integer.parseInt(id)/10000;
		Connection conn=connections.get(ID);
		Statement stat=conn.createStatement();
		stat.execute(String.format("insert into student(%s,%s,%s,%s,%s) values(%s,'%s',%s,'%s','%s')", lists.get(ID).get(0), lists.get(ID).get(1), lists.get(ID).get(2), lists.get(ID).get(3), lists.get(ID).get(4), id, name, age, gender, major));
		close(stat, conn);
    }
    //查询方法
    public ArrayList<StudentInfo> select(String id,String name) throws ClassNotFoundException, SQLException, IOException {
		List<Connection> connections = connectByYml();
		ArrayList<StudentInfo> studentInfos = new ArrayList<>();
		for (int i = 0; i < connections.size(); i++) {
			Connection connection = connections.get(i);
			Statement stat = connection.createStatement();
			ResultSet rs = null;
			if (Objects.equals(id, "") && "".equals(name)) {
				rs = stat.executeQuery("select * from student");
			}
			if (!"".equals(id) && Objects.equals(name, "")) {
				rs = stat.executeQuery(String.format("select * from student where %s=%s", lists.get(i).get(0), id));
			}
			if ("".equals(id) && !"".equals(name)) {
				rs = stat.executeQuery(String.format("select * from student where %s='%s'", lists.get(i).get(1), name));
			}
			if (!Objects.equals(id, "") && !Objects.equals(name, "")) {
				rs = stat.executeQuery(String.format("select * from student where %s=%s and %s='%s ' ", lists.get(i).get(0), id, lists.get(i).get(1), name));
				//rs 数据库结果集
			}
			if (rs != null) {
				while (rs.next()) {
					//此处 应该将rs 通过xml方式映射到st
					StudentInfo st = new StudentInfo();
					st.setId(rs.getInt(lists.get(i).get(0)));
					st.setName(rs.getString(lists.get(i).get(1)));
					st.setAge(rs.getInt(lists.get(i).get(2)));
					st.setGender(rs.getString(lists.get(i).get(3)));
					st.setMajor(rs.getString(lists.get(i).get(4)));
					studentInfos.add(st);
				}
			}
			if (rs != null) {
				rs.close();
			}
			close(stat, connection);
		}
		return studentInfos;
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
	public Page setpage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
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
		List<Connection> connections = connectByYml();
 		String id2=request.getParameter("id");
		int id=Integer.parseInt(id2)/10000;
		Connection conn=connections.get(id);
		Statement stat=conn.createStatement();
		stat.execute(String.format("delete from student where %s=%s", lists.get(id).get(0), id2));
		request.getRequestDispatcher("delete.jsp").forward(request, response);
	}
	//信息修改方法
    public void update1(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	String id4=request.getParameter("id");
	    request.setAttribute("result", select(id4,""));
        request.getRequestDispatcher("update1.jsp").forward(request, response);
    }   
    public void update(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
		List<Connection> connections = connectByYml();
        String id3=request.getParameter("id");
        String name3=request.getParameter("name");
        String age3=request.getParameter("age");
        String gender3=request.getParameter("gender");
        String major3=request.getParameter("major");
        int id=Integer.parseInt(id3)/10000;
		Connection conn=connections.get(id);
		Statement stat=conn.createStatement();
		stat.execute(String.format("update student set %s = %s,%s = '%s', %s = %s, %s = '%s', %s = '%s' where %s = %s", lists.get(id).get(0), id3, lists.get(id).get(1), name3, lists.get(id).get(2), age3, lists.get(id).get(3), gender3, lists.get(id).get(4), major3, lists.get(id).get(0), id3));
		request.setAttribute("result", select(id3, ""));
		request.getRequestDispatcher("update.jsp").forward(request, response);
    }

    /*xml文件生成*/
	public static void createXml(Connection conn,String path) {
		try {
			// 创建解析器工厂
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			Document document = db.newDocument();
			// 不显示standalone="no"
			document.setXmlStandalone(true);
			/*根节点*/
			Element mapper = document.createElement("mapper");
			// 向mapper根节点中添加子节点property
			List<Element> propertys=new ArrayList<>();
			Element property = document.createElement("property");
			Element property2 = document.createElement("property");
			Element property3 = document.createElement("property");
			Element property4 = document.createElement("property");
			Element property5 = document.createElement("property");
			propertys.add(property);
			propertys.add(property2);
			propertys.add(property3);
			propertys.add(property4);
			propertys.add(property5);
			PreparedStatement preparedStatement = conn.prepareStatement("select * from student");
			//结果集元数据
			ResultSetMetaData resultSetMetaData = preparedStatement.getMetaData();
			//表列数
			int size = resultSetMetaData.getColumnCount();
			List<String> columnNames = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				columnNames.add(resultSetMetaData.getColumnName(i + 1));
			}
			for (int i = 0; i < size; i++) {
				propertys.get(i).setTextContent(columnNames.get(i));
				mapper.appendChild(propertys.get(i));
			}
			document.appendChild(mapper);
			// 创建TransformerFactory对象
			TransformerFactory tff = TransformerFactory.newInstance();
			// 创建 Transformer对象
			Transformer tf = tff.newTransformer();
			// 输出内容是否使用换行
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			// 创建xml文件并写入内容
			tf.transform(new DOMSource(document), new StreamResult(new File(path)));
			System.out.println("生成xml成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("生成xml失败");
		}
	}

}
