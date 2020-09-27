package dbservlet;

import bean.StudentInfo;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//xml解析
public class XmlPrasing {
    static Document doc;
    public static List<String> parsingXMLByCurAll(String xml) {
        List<String> result = new ArrayList<>();
        try {
            //创建DocumentBuilderFactory实例,指定DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //调用工程的方法 得到dom解析器对象
            DocumentBuilder builder = dbf.newDocumentBuilder();
            // DOM 解析器解析xml文档
            doc = builder.parse(xml);
            System.out.println(doc);
            //得到根元素
            Element root = (Element) doc.getDocumentElement();

            System.out.println(root);
            //得到根元素的名称
            String rootName = root.getNodeName();
            System.out.println("rootName " + rootName);

            //得到property
            NodeList list = root.getChildNodes();
            for (int k = 0; k < list.getLength(); k++) {
                Node bookChild = list.item(k);
                //区分text类型的node以及element类型的node（子节点含我们不需要的文本型，所以我们要筛选）
                if (bookChild.getNodeType() == Node.ELEMENT_NODE) {
                    //获取和输出节点名和节点内容
                    result.add(bookChild.getTextContent());
                    System.out.println(bookChild.getNodeName() + ",  " + bookChild.getTextContent());
                }
            }
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return  result;
    }
}