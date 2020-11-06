package org.javamaster.b2c.core.service.impl;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.javamaster.b2c.core.config.FtpProperties;
import org.javamaster.b2c.core.enums.BizExceptionEnum;
import org.javamaster.b2c.core.exception.BizException;
import org.javamaster.b2c.core.helper.FtpHelper;
import org.javamaster.b2c.core.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author yudong
 * @date 2019/7/29
 */
@Slf4j
@Service
public class FilesServiceImpl implements FilesService {

    @Autowired
    private FtpHelper ftpHelper;
    @Autowired
    private FtpProperties ftpProperties;

    @Override
    @SneakyThrows
    public List<String> uploadFile(MultipartFile[] multipartFiles, UriComponentsBuilder uriComponentsBuilder) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String url = ftpHelper.uploadFile("/online-exam/image", multipartFile.getOriginalFilename(),
                    multipartFile.getBytes());
            String path = "/core/files/downloadFile?completePath=" + URLEncoder.encode(url, "UTF-8");
            urls.add(path);
        }
        return urls;
    }

    @Override
    public String writeFile(String fileName, String encodeBase64Str) {
        byte[] bytes = Base64Utils.decodeFromUrlSafeString(encodeBase64Str);
        return ftpHelper.uploadFile("/online-exam/logs", fileName, bytes);
    }


    @Override
    @SneakyThrows
    public byte[] downloadFile(String completePath) {
        return ftpHelper.downloadFile(completePath);
    }

    @Override
    @SneakyThrows
    public Object checkBigFile(String fileMd5, Integer fileSize, Integer chunkSize, String fileName) {
        File filePath = new File(ftpProperties.getHomeDir(), fileMd5);
        if (filePath.exists()) {
            File currentFile = new File(filePath, fileName);
            if (currentFile.exists()) {
                // 文件已经存在,直接返回url
                return "/core/files/downloadFile?completePath=" + URLEncoder.encode("/onlineExam/upload/" + fileMd5 + "/" + fileName, "UTF-8");
            }
        }
        // 文件总片数
        int chunkNum = (int) Math.ceil(1.0 * fileSize / chunkSize);
        List<Integer> chunks = IntStream.range(0, chunkNum).boxed().collect(Collectors.toList());
        File file = new File(System.getProperty("java.io.tmpdir"), fileMd5);
        if (!file.exists()) {
            return chunks;
        }
        // 检查已上传的文件片
        List<Integer> chunkNames = Files.walk(file.toPath())
                .filter(Files::isRegularFile)
                .map(path -> Integer.parseInt(path.getFileName().toString()))
                .collect(Collectors.toList());
        // 得到缺失的文件片号
        chunks.removeAll(chunkNames);
        return chunks;
    }

    @Override
    @SneakyThrows
    public void uploadBigFile(Integer chunk, String fileMd5, String chunkMd5, MultipartFile file) {
        byte[] bytes = file.getBytes();
        String md5 = DigestUtils.md5DigestAsHex(bytes);
        if (!chunkMd5.equals(md5)) {
            throw new BizException(BizExceptionEnum.CHUNK_DAMAGE);
        }
        File filePath = new File(System.getProperty("java.io.tmpdir"), fileMd5);
        if (!filePath.exists()) {
            Files.createDirectories(filePath.toPath());
        }
        File chunkFile = new File(filePath, String.valueOf(chunk));
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(chunkFile);
            StreamUtils.copy(bytes, fileOutputStream);
        } catch (Exception e) {
            log.error("{},{}", chunk, fileMd5, e);
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            Files.delete(chunkFile.toPath());
            throw new BizException(BizExceptionEnum.UPLOAD_FAILED);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    @Override
    @SneakyThrows
    public String mergeBigFile(String fileMd5, String fileName) {
        List<Path> chunkFiles = Files.walk(new File(System.getProperty("java.io.tmpdir"), fileMd5).toPath())
                .filter(Files::isRegularFile)
                .sorted()
                .collect(Collectors.toList());
        @Cleanup
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (Path chunkFile : chunkFiles) {
            byte[] chunkFileBytes = Files.readAllBytes(chunkFile);
            byteArrayOutputStream.write(chunkFileBytes);
            Files.delete(chunkFile);
        }
        byte[] fileBytes = byteArrayOutputStream.toByteArray();
        String currentFileMd5 = DigestUtils.md5DigestAsHex(fileBytes);
        log.info("file md5:{}", currentFileMd5);

        File filePath = new File(ftpProperties.getHomeDir(), fileMd5);
        Files.createDirectories(filePath.toPath());
        File currentFile = new File(filePath, fileName);
        @Cleanup
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(currentFile));
        StreamUtils.copy(fileBytes, bufferedOutputStream);
        return "/core/files/downloadFile?completePath=" + URLEncoder.encode("/onlineExam/upload/" + fileMd5 + "/" + fileName, "UTF-8");
    }

}
