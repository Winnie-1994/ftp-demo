package com.cmit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author : wuniting
 * @date :   2019-09-05
 * @description :
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FtpDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(FtpDemoApplication.class, args);
    }
}
