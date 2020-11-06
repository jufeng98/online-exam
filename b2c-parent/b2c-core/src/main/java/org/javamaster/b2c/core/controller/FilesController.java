package org.javamaster.b2c.core.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author yudong
 * @date 2019/7/29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/core/files")
public class FilesController {

    @Autowired
    private FilesService filesService;

    @PostMapping("/uploadFile")
    public Result<List<String>> uploadFile(@NotNull @RequestPart("file") MultipartFile[] multipartFiles,
                                           UriComponentsBuilder uriComponentsBuilder) {
        List<String> urls = filesService.uploadFile(multipartFiles, uriComponentsBuilder);
        return new Result<>(urls);
    }

    @PostMapping("/vUploadFile")
    public Map<String, Object> vUploadFile(@NotNull @RequestPart("file") MultipartFile[] multipartFiles,
                                           UriComponentsBuilder uriComponentsBuilder) {
        List<String> urls = filesService.uploadFile(multipartFiles, uriComponentsBuilder);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        String contextPath = attributes.getRequest().getContextPath();
        Map<String, Object> map = new HashMap<>(3, 1);
        map.put("success", true);
        map.put("error", null);
        map.put("url", contextPath + urls.get(0));
        return map;
    }

    @DeleteMapping("/vDelFile")
    public Map<String, Object> vDelFile(@NotBlank String qquuid) {
        // TODO 删除文件
        log.info("qquuid:{}", qquuid);
        Map<String, Object> map = new HashMap<>(3, 1);
        map.put("success", true);
        map.put("error", null);
        map.put("url", null);
        return map;
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@NotBlank String completePath) {
        byte[] bytes = filesService.downloadFile(completePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/checkBigFile")
    public Result<Object> checkBigFile(String fileMd5, Integer fileSize, Integer chunkSize, String fileName) {
        return new Result<>(filesService.checkBigFile(fileMd5, fileSize, chunkSize, fileName));
    }

    @SneakyThrows
    @PostMapping("/uploadBigFile")
    public Result<String> uploadBigFile(Integer chunk, String fileMd5, String chunkMd5, MultipartFile file) {
        filesService.uploadBigFile(chunk, fileMd5, chunkMd5, file);
        return new Result<>(null);
    }

    @SneakyThrows
    @PostMapping("/mergeBigFile")
    public Result<String> mergeBigFile(String fileMd5, String fileName) {
        return new Result<>(filesService.mergeBigFile(fileMd5, fileName));
    }

}