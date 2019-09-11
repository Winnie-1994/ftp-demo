package com.cmit.filecheck.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : wuniting
 * @date :   2019-09-05
 * @description :
 */
@Slf4j
@Service
public class RemoteCommandUtil {
    private static String  DEFAULTCHART="UTF-8";

    /**
     *
     * Function Desc: 登录主机,登录成功返回true，否则返回false
     * @param:
     * @return:
     * @author: wuniting
     * @date:
     */
    public static Connection login(String ip, String userName, String userPwd){

        boolean flg=false;
        Connection conn = null;
        try {
            conn = new Connection(ip);
            conn.connect();//连接
            flg=conn.authenticateWithPassword(userName, userPwd);//认证
            if(flg){
                log.info("登录成功"+conn);
                return conn;
            }else{
                log.error("用户名或密码错误"+conn);
                return conn;
            }
        } catch (IOException e) {
            log.error("登录失败"+e.getMessage());
            e.printStackTrace();
            return conn;
        }

    }


    /**
     *
     * Function Desc:远程执行shll脚本或者命令
     * @param: cmd  即将执行的命令
     * @return:  命令执行完后返回的结果值
     * @author: wuniting
     * @date:
     */
    public static ArrayList<String> execute(Connection conn,String cmd){
        ArrayList<String> result = new ArrayList<>();
        try {
            if(conn !=null){
                Session session= conn.openSession();//打开一个会话
                session.execCommand(cmd);//执行命令
                result=processStdout(session.getStdout(),DEFAULTCHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if(result.isEmpty()){
                    log.info("得到标准输出为空,链接conn:"+conn+",执行的命令："+cmd);
                    result=processStdout(session.getStderr(),DEFAULTCHART);
                }else{
                    log.info("执行命令成功,链接conn:"+conn+",执行的命令："+cmd);
                }
//                conn.close();
                session.close();
            }
        } catch (IOException e) {
            log.error("执行命令失败,链接conn:"+conn+",执行的命令："+cmd+"  "+e.getMessage(), e);
        }
        return result;
    }

    /**
     *
     * Function Desc:解析脚本执行返回的结果集
     * @param: in 输入流对象  charset 编码
     * @return:  以纯文本的格式返回
     * @author: wuniting
     * @date:
     */
    private static ArrayList<String> processStdout(InputStream in, String charset){
        InputStream stdout = new StreamGobbler(in);
//        StringBuffer buffer = new StringBuffer();
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout,charset));
            String line=null;
            while((line=br.readLine()) != null){
//                buffer.append(line+"\n");
                arrayList.add(line);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("解析脚本出错："+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("解析脚本出错："+e.getMessage());
            e.printStackTrace();
        }
//        return buffer.toString();
        return arrayList;
    }

}
