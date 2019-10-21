package org.javamaster.b2c.core.service.impl;

import org.javamaster.b2c.core.CoreApplication;
import org.javamaster.b2c.core.model.vo.JinfoResVo;
import org.javamaster.b2c.core.model.vo.JpsResVo;
import org.javamaster.b2c.core.model.vo.PrintFlagsFinalResVo;
import org.javamaster.b2c.core.service.SystemService;
import org.javamaster.b2c.core.utils.SystemUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author yudong
 * @date 2019/7/25
 */
@Service
public class SystemServiceImpl implements SystemService {

    private Pattern pattern = Pattern.compile("^\\d+$");

    @Override
    public JpsResVo jps() {
        String string = SystemUtils.exec("jps");
        String[] strs = string.split("\\s");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            if (pattern.matcher(strs[i]).matches()) {
                list.add(strs[i]);
                if (i + 1 < strs.length) {
                    list.add(strs[i + 1]);
                } else {
                    list.add("");
                }

            }
        }
        int i = 0;
        List<Map<String, Object>> listMaps = new ArrayList<>();
        while (i < list.size()) {
            Map<String, Object> map = new HashMap<>(2, 1);
            map.put("pid", list.get(i));
            map.put("jvm", list.get(i + 1));
            i = i + 2;
            listMaps.add(map);
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = Objects.requireNonNull(requestAttributes).getRequest().getSession();
        String mainClassName = CoreApplication.class.getSimpleName();
        listMaps.forEach(listMap -> {
            for (Map.Entry<String, Object> objectObjectEntry : listMap.entrySet()) {
                if (objectObjectEntry.getValue().equals(mainClassName)) {
                    session.setAttribute("currentAppPid", listMap.get("pid"));
                }
            }
        });
        JpsResVo jpsResVo = new JpsResVo();
        jpsResVo.setListMaps(listMaps);
        return jpsResVo;
    }

    @Override
    public JinfoResVo jinfo() {
        jps();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = Objects.requireNonNull(requestAttributes).getRequest().getSession();
        Object pid = session.getAttribute("currentAppPid");
        String string = SystemUtils.exec("jinfo -flags " + pid);
        String s = "Non-default VM flags: ";
        string = string.substring(string.indexOf(s) + s.length());
        String[] strs = string.split("\\s");
        JinfoResVo jinfoResVo = new JinfoResVo();
        jinfoResVo.setStrs(strs);
        return jinfoResVo;
    }

    @Override
    public PrintFlagsFinalResVo printFlagsFinal() {
        String string = SystemUtils.exec("java -XX:+PrintFlagsFinal");
        String[] strs = string.split("\\n");
        PrintFlagsFinalResVo printFlagsFinalResVo = new PrintFlagsFinalResVo();
        printFlagsFinalResVo.setStrs(strs);
        return printFlagsFinalResVo;
    }

}