package org.javamaster.b2c.core.controller;

import com.baidu.ueditor.ConfigManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.javamaster.b2c.core.model.UeditorUploadState;
import org.javamaster.b2c.core.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yudong
 * @date 2019/8/7
 */
@RestController
@RequestMapping("/core/ueditor")
public class UeditorController {

    @Autowired
    private FilesService filesService;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/getConfigOrUpload", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/plain")
    @SneakyThrows
    public String getConfigOrUpload(HttpServletRequest request, UriComponentsBuilder uriComponentsBuilder) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> multipartFileList = servletRequest.getFiles("upfile");
            MultipartFile[] multipartFiles = new MultipartFile[multipartFileList.size()];
            multipartFileList.toArray(multipartFiles);
            List<String> urls = filesService.uploadFile(multipartFiles, uriComponentsBuilder);
            MultipartFile multipartFile = multipartFileList.get(0);
            UeditorUploadState state = new UeditorUploadState();
            state.setState("SUCCESS");
            String fileName = multipartFile.getOriginalFilename();
            state.setOriginal(fileName);
            state.setSize(String.valueOf(multipartFile.getSize()));
            state.setTitle(RandomStringUtils.randomNumeric(8));
            assert fileName != null;
            state.setType(fileName.substring(fileName.lastIndexOf(".")));
            state.setUrl(urls.get(0));
            return objectMapper.writeValueAsString(state);
        } else {
            String rootPath = ResourceUtils.getFile("classpath:config.json").getAbsolutePath();
            ConfigManager configManager = ConfigManager.getInstance(rootPath, "", "");
            return configManager.getAllConfig().toString();
        }
    }

}
