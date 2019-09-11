package com.cmit.filecheck.controller;

import com.cmit.filecheck.service.FileUploadService;
import com.cmit.filecheck.service.XMLReaderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;

/**
 * @author : wuniting
 * @date :   2019-09-05
 * @description :
 */
@RestController
public class FileCheckController {

    @Autowired
    private XMLReaderService xmlReaderService;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping(value = "/fileCheck", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传文件", notes = "请上传配置文件", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "上传成功！"),
            @ApiResponse(code = 500, message = "上传失败！")
    })
    public String fileCheck(@ApiParam(value = "xml配置文件", required = true) MultipartFile file, String hostname, String username, String password) {
        ArrayList<String> configFileMessage = fileUploadService.fileUpload(file);
        xmlReaderService.XMLReadandCheck(configFileMessage.get(0), configFileMessage.get(1), hostname, username, password);
        return "执行完毕";
    }

//    @RequestMapping(value = "/check", method = RequestMethod.POST)
//    public void fileCheck() {
//
////        Connection conn = remoteCommandUtil.login("192.168.122.179", "mcbadm", "Tqh_R4s58#d9");
////        String result1 = remoteCommandUtil.execute(conn,"ls /ppmpapp/ppmp/apache-tomcat-7.0.62");
////        System.out.println("执行结果：" + result1);
////        String result2 = remoteCommandUtil.execute(conn,"cksum /ppmpapp/ppmp/apache-tomcat-7.0.62/tingyun-agent-java1.6.zip");
////        System.out.println("执行结果：" + result2);
////        String result3 = remoteCommandUtil.execute(conn,"cd "+result2);
////        System.out.println("执行结果：" + result3);
////        String result4 = remoteCommandUtil.execute(conn,"ls");
////        System.out.println("执行结果：" + result3);
////        try {
////            xmlReaderService.XMLRead();
////        } catch (Exception e) {
////            e.printStackTrace();
////            System.out.println("xml文件读取异常" + e);
////        }
//    }

}
