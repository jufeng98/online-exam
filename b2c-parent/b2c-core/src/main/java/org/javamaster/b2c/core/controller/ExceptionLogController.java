package org.javamaster.b2c.core.controller;

import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yudong
 * @date 2019/7/29
 */
@RestController
@RequestMapping("/public/log")
public class ExceptionLogController {

    @Autowired
    private FilesService filesService;

    @PostMapping("/uploadExceptions")
    public Result<String> uploadExceptions(String fileName, String encodeBase64Str) {
        String url = filesService.writeFile(fileName, encodeBase64Str);
        return new Result<>(url);
    }

}