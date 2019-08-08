package org.javamaster.b2c.core.service.impl;

import lombok.SneakyThrows;
import org.javamaster.b2c.core.helper.FtpHelper;
import org.javamaster.b2c.core.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yudong
 * @date 2019/7/29
 */
@Service
public class FilesServiceImpl implements FilesService {

    @Autowired
    private FtpHelper ftpHelper;

    @Override
    @SneakyThrows
    public List<String> uploadFile(MultipartFile[] multipartFiles, UriComponentsBuilder uriComponentsBuilder) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String url = ftpHelper.uploadFile("/online-exam/image", multipartFile.getOriginalFilename(),
                    multipartFile.getBytes());
            String path = uriComponentsBuilder.path("/core/files/downloadFile")
                    .queryParam("completePath", URLEncoder.encode(url, "UTF-8"))
                    .build()
                    .toUriString();
            urls.add(path);
        }
        return urls;
    }

    @Override
    @SneakyThrows
    public byte[] downloadFile(String completePath) {
        return ftpHelper.downloadFile(completePath);
    }
}
