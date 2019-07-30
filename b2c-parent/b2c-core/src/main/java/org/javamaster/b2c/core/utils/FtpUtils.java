package org.javamaster.b2c.core.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.javamaster.b2c.core.consts.AppConsts;

/**
 * @author yudong
 * @date 2019/7/30
 */
@Slf4j
public class FtpUtils {
    @SneakyThrows
    public static void createDirectory(FTPClient ftpClient, String remotePath) {
        if (AppConsts.SLASH.equals(remotePath)) {
            return;
        }
        if (changeWorkingDirectory(ftpClient, remotePath)) {
            return;
        }
        String directory = remotePath + AppConsts.SLASH;
        // 如果远程目录不存在，则递归创建远程服务器目录
        int start = 1;
        int end;
        end = directory.indexOf(AppConsts.SLASH, start);
        StringBuilder path = new StringBuilder();
        StringBuilder paths = new StringBuilder();
        while (true) {
            String subDirectory = remotePath.substring(start, end);
            path.append(path.toString()).append(AppConsts.SLASH).append(subDirectory);
            if (!existFile(ftpClient, path.toString())) {
                if (makeDirectory(ftpClient, subDirectory)) {
                    changeWorkingDirectory(ftpClient, subDirectory);
                } else {
                    changeWorkingDirectory(ftpClient, subDirectory);
                }
            } else {
                changeWorkingDirectory(ftpClient, subDirectory);
            }

            paths.append(paths).append(AppConsts.SLASH).append(subDirectory);
            start = end + 1;
            end = directory.indexOf(AppConsts.SLASH, start);
            // 检查所有目录是否创建完毕
            if (end <= start) {
                break;
            }
        }
    }

    @SneakyThrows
    public static boolean changeWorkingDirectory(FTPClient ftpClient, String remotePath) {
        boolean change = ftpClient.changeWorkingDirectory(remotePath);
        if (!change) {
            log.error("change directory failed:" + remotePath);
        }
        return change;
    }

    @SneakyThrows
    public static boolean makeDirectory(FTPClient ftpClient, String remotePath) {
        boolean flag = ftpClient.makeDirectory(remotePath);
        if (!flag) {
            log.error("make directory failed:" + remotePath);
        }
        return flag;
    }

    @SneakyThrows
    public static boolean existFile(FTPClient ftpClient, String remotePath) {
        FTPFile[] ftpFileArr = ftpClient.listFiles(remotePath);
        if (ftpFileArr.length > 0) {
            return true;
        }
        return false;
    }
}
