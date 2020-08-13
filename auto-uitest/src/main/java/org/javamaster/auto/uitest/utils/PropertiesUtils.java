package org.javamaster.auto.uitest.utils;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author yudong
 * @date 2020/8/5
 */
public class PropertiesUtils {

    private static Properties prop = new Properties();
    private static Random random = new Random();

    static {
        try {
            prop.load(new FileInputStream(ResourceUtils.getFile("application.properties")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProp(String key) {
        return prop.getProperty(key);
    }

    public static String getPropListRandomOne(String key) {
        List<String> list = getPropList(key);
        return list.get(random.nextInt(list.size()));
    }

    public static List<String> getPropList(String key) {
        String string = prop.getProperty(key);
        return Arrays.stream(string.split(",")).collect(Collectors.toList());
    }

}
