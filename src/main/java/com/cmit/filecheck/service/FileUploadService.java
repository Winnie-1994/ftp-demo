package com.cmit.filecheck.service;

import com.cmit.filecheck.service.XMLReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wuniting
 * @date :   2019-09-10
 * @description :
 */
@Slf4j
@Service
public class FileUploadService {

    @Value("${web.upload-path}")
    private String webUploadPath;//这个是在配置文件配置的

    @Autowired
    private XMLReaderService xmlReaderService;

    public ArrayList<String> fileUpload(MultipartFile file) {
        String temp = "resources" + File.separator + "upload" + File.separator;
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件扩展名
        String extensionName = fileName.substring(fileName.indexOf("."));
        // 新的文件名 = 获取时间戳+"."文件扩展名
        String newFileName = "FileCheck_" + String.valueOf(System.currentTimeMillis()) + extensionName;

        // 数据库保存的目录
//      String datdDirectory = temp.concat(String.valueOf(1)).concat(File.separator);
        // 文件路径
        String filePath = webUploadPath.concat(temp);
        ArrayList<String> configFileMessage = new ArrayList<>();

        if (!file.isEmpty()) {
            File dest = new File(filePath, newFileName);

            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                // 上传到指定目录
                file.transferTo(dest);
                configFileMessage.add(filePath);
                configFileMessage.add(newFileName);
                log.info("配置文件上传成功");
            } catch (Exception e) {
                e.printStackTrace();
                log.error("配置文件上传失败！");
            }
        } else {
            log.error("上传配置文件内容为空！");
        }
        return configFileMessage;
    }

}

