package com.cmit.remotecommand.utils;

import ch.ethz.ssh2.Connection;
import com.cmit.remotecommand.config.RemoteHostProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * @author : wuniting
 * @date :   2019-09-05
 * @description :
 */
@Slf4j
@Service
public class XMLReaderUtil {

    @Value("${file.url}")
    private String fileurl;

    @Autowired
    private RemoteCommandUtil remoteCommandUtil;

    @Autowired
    private RemoteHostProperties properties;

    /**
     *
     * Function Desc: 解析xml文件
     * @param:
     * @return:
     * @author: wuniting
     * @date:
     */
    public void XMLRead() throws Exception {

        File file = new File(fileurl);//Persons.xml文件绝对路径
        Connection conn = remoteCommandUtil.login(properties.getHostname(), properties.getUsername(), properties.getPassword());

        //①获得解析器DocumentBuilder的工厂实例DocumentBuilderFactory  然后拿到DocumentBuilder对象
        DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //②获取一个与磁盘文件关联的非空Document对象
        Document doc = newDocumentBuilder.parse(file);
        //③通过文档对象获得该文档对象的根节点
        Element root = doc.getDocumentElement();

        //通过根节点获得子节点
        NodeList fileList = root.getElementsByTagName("file");
        Integer nodeListLength = fileList.getLength();
//        log.info(personList);
        log.info("节点长度：" + nodeListLength);

        for (int i=0; i<nodeListLength; i++){
            //获取节点
            Node item = fileList.item(i);
            //获取第一个节点的所有子节点值
//            log.info(item.getTextContent());
            //这里转换成子类类型   ==》原因：父类没有对应的方法    这里只看类型不看值
            Element element = (Element)item;
            log.info("【第" + i + "个节点】");
            //获取节点下 url节点值
            NodeList fileUrlList = element.getElementsByTagName("url");
            String fileUrl= fileUrlList.item(0).getTextContent();
            log.info("url：" + fileUrl);
            //获取节点下 filename 节点值
            NodeList filenameList = element.getElementsByTagName("filename");
            String filename =  filenameList.item(0).getTextContent();
            log.info("filename：" + filename);
            //获取节点下 fileVersion 节点值
            NodeList fileVersionList = element.getElementsByTagName("fileVersion");
            String fileVersion = fileVersionList.item(0).getTextContent();
            log.info("fileVersion：" + fileVersion);

            ArrayList<String> fileListResult = remoteCommandUtil.execute(conn,"ls "+ fileUrl);
//            log.info("执行结果：" + fileUrlCheckResult);
            int temp = 0;
            for(String result: fileListResult){
                if(filename.equals(result)){
                    log.info(fileUrl + "目录下存在文件：" + filename);
                    temp += 1;
                    ArrayList<String> fileCheckResultList = remoteCommandUtil.execute(conn,"cksum "+ fileUrl + filename);
                    String fileCheckResult = fileCheckResultList.get(0);
                    String[] fileCheckResultSplit = fileCheckResult.split(" ");
                    if (fileVersion.equals(fileCheckResultSplit[0])) {
                        temp += 1;
                        log.info("文件校验通过！");
                        break;
                    }else{
                        log.error("文件校验失败！");
                        break;
                    }
                }
            }
            if (temp == 0) {
                log.error("文件或目录不存在");
            }

        }
        conn.close();
    }

}