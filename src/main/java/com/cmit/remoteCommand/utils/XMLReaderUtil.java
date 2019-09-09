package com.cmit.remoteCommand.utils;

import com.sun.org.apache.xml.internal.utils.res.IntArrayWrapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @author : wuniting
 * @date :   2019-09-05
 * @description :
 */
@Service
public class XMLReaderUtil {
    static File file = new File("C:/idea_workspace/ftp-demo/src/FilesCheck.xml");//Persons.xml文件绝对路径

    /**
     *
     * Function Desc: 解析xml文件
     * @param:
     * @return:
     * @author: wuniting
     * @date:
     */
    public static void XMLRead() throws Exception {
        //①获得解析器DocumentBuilder的工厂实例DocumentBuilderFactory  然后拿到DocumentBuilder对象
        DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //②获取一个与磁盘文件关联的非空Document对象
        Document doc = newDocumentBuilder.parse(file);
        //③通过文档对象获得该文档对象的根节点
        Element root = doc.getDocumentElement();

        //通过根节点获得子节点
        NodeList fileList = root.getElementsByTagName("file");
        Integer nodeListLength = fileList.getLength();
//        System.out.println(personList);
        System.out.println("节点长度：" + nodeListLength);

        for (int i=0; i<nodeListLength; i++){
            //获取节点
            Node item = fileList.item(i);
            //获取第一个节点的所有子节点值
//            System.out.println(item.getTextContent());
            //这里转换成子类类型   ==》原因：父类没有对应的方法    这里只看类型不看值
            Element element = (Element)item;
            //获取节点下 url节点值
            NodeList fileUrl = element.getElementsByTagName("url");
            System.out.println("【第" + i + "个节点】");
            System.out.println("url：" + fileUrl.item(0).getTextContent());
            //获取节点下 filename 节点值
            NodeList fileName = element.getElementsByTagName("filename");
            for(int j=0; j< fileName.getLength(); j++){
                System.out.println("filename：" + fileName.item(j).getTextContent());
            }
        }
    }

}
