package org.javamaster.b2c.core.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author yudong
 * @date 2019/7/29
 */
public interface FilesService {
    List<String> uploadFile(MultipartFile[] multipartFiles);

    byte[] downloadFile(String completePath);
}
