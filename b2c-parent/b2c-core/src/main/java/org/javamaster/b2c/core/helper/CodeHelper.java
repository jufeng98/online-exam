package org.javamaster.b2c.core.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author yudong
 * @date 2019/7/29
 */
@Slf4j
@Component
public class CodeHelper {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String TEMPLATE_SQL = "select %s from %s order by id desc limit 1";
    private static final String[] ZERO = {"0000", "000", "00", "0", ""};

    public String generateCode(String columnCodeName, String tableName, String prefix) {
        String sql = String.format(TEMPLATE_SQL, columnCodeName, tableName);
        try {
            String code = jdbcTemplate.queryForObject(sql, String.class);
            int number = Integer.parseInt(Objects.requireNonNull(code).replace(prefix, ""));
            number += 1;
            code = String.valueOf(number);
            return prefix + ZERO[code.length() - 1] + code;
        } catch (EmptyResultDataAccessException e) {
            return prefix + "00001";
        }
    }
}
