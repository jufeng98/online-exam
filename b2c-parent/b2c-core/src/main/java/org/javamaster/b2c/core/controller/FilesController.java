package org.javamaster.b2c.core.controller;

import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author yudong
 * @date 2019/7/29
 */
@Validated
@RestController
@RequestMapping("/core/files")
public class FilesController {

    @Autowired
    private FilesService filesService;

    @PreAuthorize("hasAuthority(T(org.javamaster.b2c.core.consts.AppConsts).ROLE_ADMIN) or (#reqVo.username == #userDetails.username)")
    @PostMapping("/uploadFile")
    public Result<List<String>> uploadFile(@RequestPart("file") MultipartFile[] multipartFiles) {
        List<String> urls = filesService.uploadFile(multipartFiles);
        return new Result<>(urls);
    }

    @PreAuthorize("hasAuthority(T(org.javamaster.b2c.core.consts.AppConsts).ROLE_ADMIN) or (#reqVo.username == #userDetails.username)")
    @GetMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@NotBlank String completePath) {
        byte[] bytes = filesService.downloadFile(completePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}