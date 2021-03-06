package org.javamaster.b2c.core.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * @author yudong
 * @date 2019/7/29
 */
public interface FilesService {
    List<String> uploadFile(MultipartFile[] multipartFiles, UriComponentsBuilder uriComponentsBuilder);

    String writeFile(String fileName, String encodeBase64Str);

    byte[] downloadFile(String completePath);

    Object checkBigFile(String fileMd5, Integer fileSize, Integer chunkSize, String fileName);

    void uploadBigFile(Integer chunk, String fileMd5, String chunkMd5, MultipartFile file);

    String mergeBigFile(String fileMd5, String fileName);
}
