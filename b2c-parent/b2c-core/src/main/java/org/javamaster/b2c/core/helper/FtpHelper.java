package org.javamaster.b2c.core.helper;

import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.javamaster.b2c.core.config.FtpProperties;
import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.utils.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author yudong
 * @date 2019/7/29
 */
@Slf4j
@Component
public class FtpHelper {
    @Autowired
    private FtpProperties ftpProperties;

    @SneakyThrows
    public FTPClient initFtpClient() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        log.info("try connect to ftp server:{}:{}", ftpProperties.getHostName(), ftpProperties.getPort());
        ftpClient.connect(ftpProperties.getHostName(), ftpProperties.getPort());
        ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword());
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            log.error("try connect to ftp server failed:{}:{},reply code:{}", ftpProperties.getHostName(), ftpProperties.getPort(), replyCode);
            throw new RuntimeException(String.format("try connect to ftp server failed:%s:%s,reply code:%s",
                    ftpProperties.getHostName(), ftpProperties.getPort(), replyCode));
        }
        log.info("try connect to ftp server success:{}:{}", ftpProperties.getHostName(), ftpProperties.getPort());
        return ftpClient;
    }


    @SneakyThrows
    @Synchronized
    public String uploadFile(String remotePath, String filename, byte[] fileBytes) {
        FTPClient ftpClient = initFtpClient();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            FtpUtils.createDirectory(ftpClient, remotePath);
            FtpUtils.changeWorkingDirectory(ftpClient, remotePath);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            filename = AppConsts.APP_PREFIX + RandomStringUtils.randomAlphanumeric(8).toUpperCase() + "_" + filename;
            boolean result = ftpClient.storeFile(filename, inputStream);
            if (!result) {
                throw new RuntimeException("upload file failed:" + filename);
            }
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
        return remotePath + AppConsts.SLASH + filename;
    }

    @SneakyThrows
    @Synchronized
    public byte[] downloadFile(String completePath) {
        int index = completePath.lastIndexOf("/");
        String remotePath = completePath.substring(0, index);
        String fileName = completePath.substring(index + 1);
        FTPClient ftpClient = initFtpClient();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            FtpUtils.changeWorkingDirectory(ftpClient, remotePath);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(fileName, outputStream);
            outputStream.flush();
            byte[] fileBytes = outputStream.toByteArray();
            return fileBytes;
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }


}
