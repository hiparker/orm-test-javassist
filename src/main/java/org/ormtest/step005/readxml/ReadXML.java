package org.ormtest.step005.readxml;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created Date by 2020/1/11 0011.
 *
 * 读取xml 模仿 mybatis
 * JDom解析xml
 * 快速开发XML应用程序。
 * 是一个开源项目
 * JDOM主要用来弥补DOM和SAX在实际应用当中的不足。
 *
 * @author Parker
 */
public class ReadXML {


    static public List<Map<String,String>> getXml(String path) throws Exception {

        List<Map<String,String>> sqlList = new ArrayList<>();

        //1.创建SAXBuilder对象
        SAXBuilder saxBuilder = new SAXBuilder();
        //2.创建输入流
        InputStream is = new FileInputStream(new File(path));
        //3.将输入流加载到build中
        Document document = saxBuilder.build(is);
        //4.获取根节点
        Element rootElement = document.getRootElement();
        //5.获取子节点
        List<Element> children = rootElement.getChildren();
        for (Element child : children) {

            Map<String,String> sqlMap = new HashMap<>();

            List<Attribute> attributes = child.getAttributes();
            //打印属性
            for (Attribute attr : attributes) {
                sqlMap.put("entity",attr.getValue());
                System.out.println(attr.getName()+":"+attr.getValue());
            }
            List<Element> childrenList = child.getChildren();
            System.out.println("======获取子节点-start======");
            for (Element o : childrenList) {
                sqlMap.put("sql",o.getValue());
                System.out.println("节点名:"+o.getName()+"---"+"节点值:"+o.getValue());
            }
            System.out.println("======获取子节点-end======");

            sqlList.add(sqlMap);
        }

        return sqlList;
    }

}
