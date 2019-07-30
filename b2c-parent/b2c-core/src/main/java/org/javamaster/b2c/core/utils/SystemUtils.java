package org.javamaster.b2c.core.utils;

import lombok.SneakyThrows;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;

/**
 * @author yudong
 * @date 2019/6/10
 */
public class SystemUtils {
    private static String Commandline;

    static {
        String home = System.getProperty("java.home");
        home = home + "/../bin/";
        Commandline = home;
    }

    @SneakyThrows
    public static String exec(String jvmCommand) {
        Process process;
        String[] cmds;
        String os = System.getProperty("os.name");
        String windowsOs = "Windows";
        if (os.contains(windowsOs)) {
            cmds = new String[]{"cmd", "/C", Commandline + jvmCommand};
        } else {
            cmds = new String[]{"/bin/sh", "-c", Commandline + jvmCommand};
        }
        process = Runtime.getRuntime().exec(cmds);
        return FileCopyUtils.copyToString(new InputStreamReader(process.getInputStream(), "UTF-8"));
    }

}
