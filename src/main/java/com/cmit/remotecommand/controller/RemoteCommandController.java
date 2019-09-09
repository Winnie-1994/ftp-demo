package com.cmit.remotecommand.controller;

import ch.ethz.ssh2.Connection;
import com.cmit.remotecommand.config.RemoteHostProperties;
import com.cmit.remotecommand.utils.RemoteCommandUtil;
import com.cmit.remotecommand.utils.XMLReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wuniting
 * @date :   2019-09-05
 * @description :
 */
@RestController
public class RemoteCommandController {
    @Autowired
    private RemoteCommandUtil remoteCommandUtil;

    @Autowired
    private XMLReaderUtil xmlReaderUtil;

    @Autowired
    private RemoteHostProperties properties;

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public  void loginTest(){

//        Connection conn = remoteCommandUtil.login(properties.getHostname(), properties.getUsername(), properties.getPassword());
//        String result1 = remoteCommandUtil.execute(conn,"ls /home/Vander/apps/");
//        System.out.println("执行结果：" + result1);
//        String result2 = remoteCommandUtil.execute(conn,"cksum /ppmpapp/ppmp/apache-tomcat-7.0.62/tingyun-agent-java1.6.zip");
//        System.out.println("执行结果：" + result2);
//        String result3 = remoteCommandUtil.execute(conn,"cd "+result2);
//        System.out.println("执行结果：" + result3);
//        String result4 = remoteCommandUtil.execute(conn,"ls");
//        System.out.println("执行结果：" + result3);
        try{
            xmlReaderUtil.XMLRead();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("xml文件读取异常");
        }

    }
}
