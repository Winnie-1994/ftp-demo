package com.cmit.filecheck.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : wuniting
 * @date :   2019/9/9
 * @description :
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "remote")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RemoteHostProperties {

    private String hostname;

    private String username;

    private String password;

}
