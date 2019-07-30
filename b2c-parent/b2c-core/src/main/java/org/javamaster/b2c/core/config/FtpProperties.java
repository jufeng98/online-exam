package org.javamaster.b2c.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yudong
 * @date 2019/7/30
 */
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {
    private String homeDir;
    private String hostName;
    private int port;
    private String username;
    private String password;
}
