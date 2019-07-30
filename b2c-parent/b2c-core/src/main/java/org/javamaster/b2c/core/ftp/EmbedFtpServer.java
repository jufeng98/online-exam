package org.javamaster.b2c.core.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.javamaster.b2c.core.config.FtpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yudong
 * @date 2019/7/29
 */
@Slf4j
@Component
public class EmbedFtpServer implements CommandLineRunner {
    private static FtpServerFactory serverFactory = new FtpServerFactory();
    @Autowired
    private FtpProperties ftpProperties;

    @Override
    public void run(String... args) throws Exception {
        BaseUser superUser = new BaseUser();
        superUser.setName(ftpProperties.getUsername());
        superUser.setPassword(ftpProperties.getPassword());
        Path homePath = Paths.get(ftpProperties.getHomeDir());
        if (!Files.exists(homePath)) {
            log.info("Ftp home directory doesn't not exists,will be create:{}", homePath);
            Files.createDirectories(homePath);
        }
        superUser.setHomeDirectory(ftpProperties.getHomeDir());
        List<Authority> authority = new ArrayList<>();
        authority.add(new WritePermission());
        superUser.setAuthorities(authority);
        serverFactory.getUserManager().save(superUser);
        FtpServer server = serverFactory.createServer();
        server.start();
        log.info("Start ftp server success...");
    }
}
