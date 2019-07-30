package org.javamaster.b2c.core.utils;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yudong
 * @date 2019/7/23
 */
public class ListUtils {
    @SneakyThrows
    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClz) {
        List<T> list = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            T t = targetClz.newInstance();
            BeanUtils.copyProperties(source, t);
            list.add(t);
        }
        return list;
    }
}
