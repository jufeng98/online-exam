package org.javamaster.auto.uitest.utils;


import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * @author yudong
 * @date 2020/8/3
 */
public class ResourceUtils {

    public static File getFile(String path) throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL url = (cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path));
        assert url != null;
        return new File(new URI(url.toString().replace(" ", "%20")).getSchemeSpecificPart());
    }

}
