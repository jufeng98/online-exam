package org.javamaster.b2c.core.controller;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.ConfigManager;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Field;

/**
 * @author yudong
 * @date 2019/8/7
 */
@Controller
@RequestMapping("/core/ueditor")
public class UeditorController {

    @GetMapping("/getConfig")
    @SneakyThrows
    public void getConfig(HttpServletRequest request, HttpServletResponse response) {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/plain");
        String rootPath = ResourceUtils.getFile("classpath:config.json").getAbsolutePath();
        ActionEnter actionEnter = new ActionEnter(request, rootPath);
        ConfigManager configManager = ConfigManager.getInstance(rootPath, "", "");
        Field field = actionEnter.getClass().getDeclaredField("configManager");
        field.setAccessible(true);
        field.set(actionEnter, configManager);
        @Cleanup PrintWriter printWriter = response.getWriter();
        printWriter.print(actionEnter.exec());

    }

}
