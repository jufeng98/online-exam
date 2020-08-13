package org.javamaster.fragmentlearning.utils;

import org.javamaster.fragmentlearning.common.App;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;

/**
 * 针对已root的设备使用
 *
 * @author yudong
 * @date 2020/6/17
 */
public class RootUtils {

    public static void modifyHosts(String pcIpAddress) throws Exception {
        String hostsPath = hosts(pcIpAddress);
        delFile("/system/etc/hosts");
        cpFile(hostsPath, "/system/etc");
    }

    @SuppressWarnings("ALL")
    public static String hosts(String pcIpAddress) throws Exception {
        exusecmd("mount -o rw,remount /system");
        exusecmd("chmod 777 /system");
        File file = new File(App.context.getCacheDir(), "hosts");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        exusecmd("chmod 777 " + file.getAbsolutePath());
        String hostContent = "";
        hostContent += "127.0.0.1 localhost" + System.lineSeparator();
        hostContent += pcIpAddress + " agent.javamaster.org" + System.lineSeparator();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(hostContent);
        fileWriter.close();
        return file.getAbsolutePath();
    }

    public static void cpFile(String filePath, String sysFilePath) throws Exception {
        exusecmd("mount -o rw,remount /system");
        exusecmd("chmod 777 /system");
        exusecmd("cp -a " + filePath + " " + sysFilePath);
    }

    public static void delFile(String filePath) throws Exception {
        exusecmd("mount -o rw,remount /system");
        exusecmd("chmod 777 /system");
        exusecmd("rm -rf " + filePath);
    }

    public static void exusecmd(String command) throws Exception {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } finally {
            if (os != null) {
                os.close();
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

}
